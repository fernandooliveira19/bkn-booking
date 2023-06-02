package com.fernando.oliveira.booking.service.impl;

import com.fernando.oliveira.booking.domain.entity.Booking;
import com.fernando.oliveira.booking.domain.entity.Launch;
import com.fernando.oliveira.booking.domain.entity.Traveler;
import com.fernando.oliveira.booking.domain.enums.BookingStatusEnum;
import com.fernando.oliveira.booking.domain.enums.ContractTypeEnum;
import com.fernando.oliveira.booking.domain.enums.ExceptionMessageEnum;
import com.fernando.oliveira.booking.domain.enums.PaymentStatusEnum;
import com.fernando.oliveira.booking.domain.mapper.BookingMapper;
import com.fernando.oliveira.booking.domain.request.SearchBookingRequest;
import com.fernando.oliveira.booking.domain.spec.BookingSpec;
import com.fernando.oliveira.booking.exception.BookingException;
import com.fernando.oliveira.booking.repository.BookingRepository;
import com.fernando.oliveira.booking.service.BookingService;
import com.fernando.oliveira.booking.service.LaunchService;
import com.fernando.oliveira.booking.service.ToolsService;
import com.fernando.oliveira.booking.service.TravelerService;
import com.fernando.oliveira.booking.utils.MessageUtils;
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

    @Autowired
    private MessageUtils messageUtils;


    @Autowired
    private ToolsService toolsService;

    @Override
    public Booking createBooking(Booking booking) {

        validateBooking(booking);

        Booking bookingToSave = prepareBookingToSave(booking);
        booking.setInsertDate(LocalDateTime.now());

        Booking bookingSaved = bookingRepository.save(bookingToSave);

        bookingSaved.getLaunches()
                .stream()
                .forEach(e -> launchService.createLaunch(e, bookingSaved));

        return bookingSaved;
    }

    private Booking prepareBookingToSave(Booking booking){
        defineTraveler(booking);
        defineBookingStatus(booking);
        definePaymentStatus(booking);
        defineAmountPending(booking);
        defineAmountPaid(booking);
        return booking;

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
    public List<Booking> findAll() {
        return bookingRepository.findAll()
                .stream()
                .map((e) -> defineBookingDetails(e))
                .collect(Collectors.toList());
    }

    @Override
    public List<Booking> findNextBookings() {
        return bookingRepository.findNextBookings()
                .stream()
                .map((e) -> defineBookingDetails(e))
                .collect(Collectors.toList());

    }

    @Override
    public List<Booking> search(SearchBookingRequest request) {

        if (request.getBookingStatus() == null
                && request.getPaymentStatus() == null
                && request.getContractType() == null) {
            return findNextBookings();
        } else {
            BookingSpec bookingSpec = new BookingSpec(request);
            List<Booking> result = bookingRepository.findAll(bookingSpec);
            return result.stream()
                    .map((e) -> defineBookingDetails(e))
                    .collect(Collectors.toList());
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Booking updateBooking(Booking booking, Long id) {

        booking.setId(id);
        validateBooking(booking);

        Booking bookingToUpdate = prepareBookingToSave(booking);
        Booking bookingBase = findById(id);
        bookingToUpdate.setInsertDate(bookingBase.getInsertDate());
        bookingToUpdate.setLastUpdate(LocalDateTime.now());
        bookingToUpdate.setObservation(booking.getObservation());

        Booking bookingUpdated = bookingRepository.save(bookingToUpdate);

        for (Launch launch : booking.getLaunches()) {

            if (launch.getId() != null) {
                launch.setBooking(bookingUpdated);

                if(BookingStatusEnum.CANCELED.equals(bookingUpdated.getBookingStatus())){
                    launch.setPaymentStatus(PaymentStatusEnum.CANCELED);
                }
                launchService.updateLaunch(launch);
            } else {
                launchService.createLaunch(launch, bookingUpdated);
            }
        }
        return bookingUpdated;
    }

    public Booking findById(Long id) {

        Optional<Booking> result = bookingRepository.findById(id);

        return result
                .orElseThrow(() -> new BookingException(messageUtils.getMessage(ExceptionMessageEnum.BOOKING_NOT_FOUND.getMessageKey(), new Object[] {id})));

    }

    public Booking detailBooking(Long id) {
        return defineBookingDetails(findById(id));
    }

    @Override
    public List<Booking> findBookingsByTraveler(Long travelerId) {

        return bookingRepository.findByTraveler(travelerId).stream()
                .map((e) -> calculateRangeAndAverageRentals(e))
                .collect(Collectors.toList());
    }

    private Booking calculateRangeAndAverageRentals(Booking booking){

        Long rentDays = toolsService.rentDays(booking.getCheckIn(), booking.getCheckOut());

        booking.setRentDays(rentDays);
        booking.setAverageValue(toolsService.averageValue(rentDays, booking.getAmountTotal()));

        return booking;
    }

    public Booking defineBookingDetails(Booking booking) {
        defineTraveler(booking);
        return booking;
    }

    public void defineBookingStatus(Booking booking) {

        if (BookingStatusEnum.FINISHED.equals(booking.getBookingStatus())
                || BookingStatusEnum.CANCELED.equals(booking.getBookingStatus())) {
            return;
        }

        booking.setBookingStatus(BookingStatusEnum.PRE_RESERVED);

        booking.getLaunches().stream().forEach(e -> {
            if (e.getPaymentStatus().equals(PaymentStatusEnum.PAID)
                   || e.getPaymentStatus().equals(PaymentStatusEnum.TO_RECEIVE) ) {
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

            if(e.getPaymentStatus().equals(PaymentStatusEnum.TO_RECEIVE)){
                booking.setPaymentStatus(PaymentStatusEnum.TO_RECEIVE);
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
                throw new BookingException(messageUtils.getMessage(ExceptionMessageEnum.BOOKING_ALREADY_EXISTS));
            }
            for (Booking bkn : otherBookings) {
                if (!bkn.getId().equals(booking.getId())) {
                    throw new BookingException(messageUtils.getMessage(ExceptionMessageEnum.BOOKING_ALREADY_EXISTS));
                }
            }
        }

        if (booking.getLaunches() == null || booking.getLaunches().isEmpty()) {
            throw new BookingException(messageUtils.getMessage(ExceptionMessageEnum.BOOKING_MUST_HAVE_LAUNCHES));
        }

        BigDecimal amountTotal = booking.getAmountTotal();

        if(ContractTypeEnum.SITE.equals(booking.getContractType())){
            amountTotal = amountTotal.subtract(booking.getWebsiteServiceFee());
        }

        if (!amountTotal.equals(getTotalAmountByLaunches(booking.getLaunches()))) {
            throw new BookingException(messageUtils.getMessage(ExceptionMessageEnum.BOOKING_SUM_LAUNCHES_AMOUNT_ERROR));
        }

    }

    private void validateCancelBooking(Booking booking) {
        if (StringUtils.isBlank(booking.getObservation())) {
            throw new BookingException(messageUtils.getMessage(ExceptionMessageEnum.BOOKING_OBSERVATION_REQUIRED));
        }
        booking.getLaunches().stream().forEach(e -> {
            if (e.getPaymentStatus().equals(PaymentStatusEnum.PAID)) {
                throw new BookingException(messageUtils.getMessage(ExceptionMessageEnum.BOOKING_CANCEL_LAUNCHES_PAID_ERROR));
            }
        });
    }

    private void validateFinishBooking(Booking booking) {
        if (booking.getCheckOut().isAfter(LocalDateTime.now())) {
            throw new BookingException(messageUtils.getMessage(ExceptionMessageEnum.BOOKING_FINISH_BEFORE_CHECKOUT_ERROR));
        }
        if (StringUtils.isBlank(booking.getObservation())) {

            throw new BookingException(messageUtils.getMessage(ExceptionMessageEnum.BOOKING_OBSERVATION_REQUIRED));
        }
        booking.getLaunches().stream().forEach(e -> {
            if (e.getPaymentStatus().equals(PaymentStatusEnum.PENDING)) {

                throw new BookingException(messageUtils.getMessage(ExceptionMessageEnum.BOOKING_FINISH_LAUNCHES_PENDING_ERROR));
            }
        });
    }

    public BigDecimal getTotalAmountByLaunches(List<Launch> launches) {

        return launches.stream()
                .map(Launch::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private void defineTraveler(Booking booking) {
        Traveler traveler = travelerService.findById(booking.getTraveler().getId());
        booking.setTraveler(traveler);
        booking.setTravelerName(traveler.getName());
    }

    private void defineAmountPaid(Booking booking) {
        booking.setAmountPaid(booking.getAmountTotal().subtract(booking.getAmountPending()));

    }

}
