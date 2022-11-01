package com.fernando.oliveira.booking.service;

import com.fernando.oliveira.booking.domain.dto.BookingSpec;
import com.fernando.oliveira.booking.domain.entity.Booking;
import com.fernando.oliveira.booking.domain.entity.Launch;
import com.fernando.oliveira.booking.domain.entity.Traveler;
import com.fernando.oliveira.booking.domain.enums.BookingStatusEnum;
import com.fernando.oliveira.booking.domain.enums.PaymentStatusEnum;
import com.fernando.oliveira.booking.domain.mapper.BookingMapper;
import com.fernando.oliveira.booking.domain.request.CreateBookingRequest;
import com.fernando.oliveira.booking.domain.request.SearchBookingRequest;
import com.fernando.oliveira.booking.domain.request.UpdateBookingRequest;
import com.fernando.oliveira.booking.domain.response.BookingDetailResponse;
import com.fernando.oliveira.booking.exception.BookingException;
import com.fernando.oliveira.booking.repository.BookingRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private LaunchService launchService;

    @Autowired
    private TravelerService travelerService;

    @Autowired
    private BookingMapper bookingMapper;

    @Override
    public BookingDetailResponse createBooking(CreateBookingRequest request) {

        Booking booking = bookingMapper.createRequestToEntity(request);

        validateCreateUpdateBooking(booking);

        Booking bookingToSave = prepareBookingToSave(booking);

        bookingToSave.setInsertDate(LocalDateTime.now());

        Booking bookingSaved = bookingRepository.save(bookingToSave);

        bookingSaved.getLaunches()
                .stream()
                .forEach(e -> launchService.createLaunch(e, bookingSaved));

        return bookingMapper.bookingToDetailBookingResponse(bookingSaved);
    }
    
    private Booking prepareBookingToSave(Booking booking){
        
        getTraveler(booking);
        defineBookingStatus(booking);
        definePaymentStatus(booking);
        defineAmountPending(booking);
        defineAmountPaid(booking);
        
        return  booking;
    } 

    private void validateCreateUpdateBooking(Booking booking){

        if (BookingStatusEnum.FINISHED.equals(booking.getBookingStatus())) {
            validateFinishBooking(booking);
        }
        if (BookingStatusEnum.CANCELED.equals(booking.getBookingStatus())) {
            validateCancelBooking(booking);
        }

        List<Booking> otherBookings = bookingRepository.findBookingsByDate(booking.getCheckIn(), booking.getCheckOut());

        if (!otherBookings.isEmpty()) {

            if (booking.getId() == null) {
                throw new BookingException("Já existe outra reserva para o mesmo periodo");
            }
            for (Booking bkn : otherBookings) {
                if (!bkn.getId().equals(booking.getId())) {
                    throw new BookingException("Já existe outra reserva para o mesmo periodo");
                }
            }

        }

        if (booking.getLaunches() == null || booking.getLaunches().isEmpty()) {
            throw new BookingException("Reserva deve possuir lançamentos");
        }

        if (!booking.getAmountTotal().equals(getTotalAmountByLaunches(booking.getLaunches()))) {
            throw new BookingException("Soma dos lançamentos estão diferentes do valor total da reserva");
        }

    }

    private void defineAmountPending(Booking booking) {
        BigDecimal amountPending = booking.getLaunches()
                .stream()
                .filter(e -> e.getPaymentStatus().equals(PaymentStatusEnum.PENDING)
                    || e.getPaymentStatus().equals(PaymentStatusEnum.CANCELED))
                .map(Launch::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        booking.setAmountPending(amountPending);
   }


    @Override
    public List<BookingDetailResponse> findAll() {

        return bookingRepository.findAll()
                .stream()
                .map((e) -> defineBookingDetails(e))
                .map( (e) -> bookingMapper.bookingToDetailBookingResponse(e))
                .collect(Collectors.toList());
    }

    @Override
    public List<BookingDetailResponse> findNextBookings() {
        return bookingRepository.findNextBookings()
                .stream()
                .map((e) -> defineBookingDetails(e))
                .map( (e) -> bookingMapper.bookingToDetailBookingResponse(e))
                .collect(Collectors.toList());

    }

    @Override
    public List<BookingDetailResponse> search(SearchBookingRequest request) {

        if (request.getBookingStatus() == null
                && request.getPaymentStatus() == null
                && request.getContractType() == null) {

            return findNextBookings();

        } else {
            BookingSpec bookingSpec = new BookingSpec(request);

            List<Booking> result = bookingRepository.findAll(bookingSpec);

            return result.stream()
                    .map((e) -> defineBookingDetails((Booking) e))
                    .map((e) -> bookingMapper.bookingToDetailBookingResponse(e))
                    .collect(Collectors.toList());

        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public BookingDetailResponse updateBooking(UpdateBookingRequest request, Long id) {

        Booking booking = bookingMapper.updateRequestToEntity(request);

        booking.setId(id);
        validateBooking(booking);

        Booking bookingToUpdate = prepareBookingToSave(booking);

        Booking bookingBase = findById(id);
        bookingToUpdate.setInsertDate(bookingBase.getInsertDate());
        bookingToUpdate.setLastUpdate(LocalDateTime.now());

        Booking bookingUpdated = bookingRepository.save(bookingToUpdate);

        for (Launch launch : booking.getLaunches()) {

            if (launch.getId() != null) {
                launch.setBooking(bookingUpdated);
                launchService.updateLaunch(launch);
            } else {
                launchService.createLaunch(launch, bookingUpdated);
            }

        }

        return bookingMapper.bookingToDetailBookingResponse(bookingUpdated);

    }

    @Override
    public Booking findById(Long id) {
        Optional<Booking> result = bookingRepository.findById(id);
        return result
                .orElseThrow(() -> new BookingException("Não foi encontrado reserva pelo id: " + id));
    }


    public BookingDetailResponse detailBooking(Long id) {
        Booking booking = findById(id);
        defineBookingDetails(booking);
        return bookingMapper.bookingToDetailBookingResponse(booking);
    }

    public Booking defineBookingDetails(Booking booking) {

        getTraveler(booking);

        return booking;
    }

    public void defineBookingStatus(Booking booking) {

        if (BookingStatusEnum.FINISHED.equals(booking.getBookingStatus())
                || BookingStatusEnum.CANCELED.equals(booking.getBookingStatus())) {
            return;
        }

        booking.setBookingStatus(BookingStatusEnum.PRE_RESERVED);

        booking.getLaunches().stream().forEach(e -> {
            if (e.getPaymentStatus().equals(PaymentStatusEnum.PAID)) {
                booking.setBookingStatus(BookingStatusEnum.RESERVED);
            }
        });

    }

    public void definePaymentStatus(Booking booking) {

        if (BookingStatusEnum.CANCELED.equals(booking.getBookingStatus())) {
            return;
        }
        booking.setPaymentStatus(PaymentStatusEnum.PAID);

        booking.getLaunches().stream().forEach(e -> {
            if (e.getPaymentStatus().equals(PaymentStatusEnum.PENDING)) {
                booking.setPaymentStatus(PaymentStatusEnum.PENDING);
            }
        });
    }


    public void validateBooking(Booking booking) {

        if (BookingStatusEnum.FINISHED.equals(booking.getBookingStatus())) {
            validateFinishBooking(booking);
        }
        if (BookingStatusEnum.CANCELED.equals(booking.getBookingStatus())) {
            validateCancelBooking(booking);
        }

        List<Booking> otherBookings = bookingRepository.findBookingsByDate(booking.getCheckIn(), booking.getCheckOut());

        if (!otherBookings.isEmpty()) {

            if (booking.getId() == null) {
                throw new BookingException("Já existe outra reserva para o mesmo periodo");
            }
            for (Booking bkn : otherBookings) {
                if (!bkn.getId().equals(booking.getId())) {
                    throw new BookingException("Já existe outra reserva para o mesmo periodo");
                }
            }

        }

        if (booking.getLaunches() == null || booking.getLaunches().isEmpty()) {
            throw new BookingException("Reserva deve possuir lançamentos");
        }

        if (!booking.getAmountTotal().equals(getTotalAmountByLaunches(booking.getLaunches()))) {
            throw new BookingException("Soma dos lançamentos estão diferentes do valor total da reserva");
        }

    }

    private void validateCancelBooking(Booking booking) {
        if (StringUtils.isBlank(booking.getObservation())) {
            throw new BookingException("É obrigatório preencher uma observação sobre a reserva");
        }
        booking.getLaunches().stream().forEach(e -> {
            if (e.getPaymentStatus().equals(PaymentStatusEnum.PAID)) {
                throw new BookingException("Não é possível cancelar a reserva. Verificar lançamentos pagos");
            }
        });
    }

    private void validateFinishBooking(Booking booking) {
        if (booking.getCheckOut().isAfter(LocalDateTime.now())) {
            throw new BookingException("Não é permitido finalizar a reserva antes do check-out");
        }
        if (StringUtils.isBlank(booking.getObservation())) {
            throw new BookingException("É obrigatório preencher uma observação sobre a reserva");
        }
        booking.getLaunches().stream().forEach(e -> {
            if (e.getPaymentStatus().equals(PaymentStatusEnum.PENDING)) {
                throw new BookingException("Não é possível finalizar a reserva. Verificar lançamentos pendentes");
            }
        });
    }

    public BigDecimal getTotalAmountByLaunches(List<Launch> launches) {

        return launches.stream()
                .map(Launch::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

    }

    private void getTraveler(Booking booking) {
        Traveler traveler = travelerService.findById(booking.getTraveler().getId());
        booking.setTraveler(traveler);
        booking.setTravelerName(traveler.getName());
    }

    private void defineAmountPaid(Booking booking) {
        booking.setAmountPaid(booking.getAmountTotal().subtract(booking.getAmountPending()));
    }

}
