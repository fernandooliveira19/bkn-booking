package com.fernando.oliveira.booking.service;

import com.fernando.oliveira.booking.domain.entity.Booking;
import com.fernando.oliveira.booking.domain.entity.Launch;
import com.fernando.oliveira.booking.domain.enums.BookingStatusEnum;
import com.fernando.oliveira.booking.domain.enums.PaymentStatusEnum;
import com.fernando.oliveira.booking.exception.BookingException;
import com.fernando.oliveira.booking.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService{

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private LaunchService launchService;

    @Override
    public Booking createBooking(Booking booking) {

        validateBooking(booking);

        Booking bookingToSave = defineBookingDetails(booking);
        booking.setInsertDate(LocalDateTime.now());

        Booking bookingSaved = bookingRepository.save(bookingToSave);

        bookingSaved.getLaunchs().stream()
             .forEach( e -> launchService.createLaunch(e, bookingSaved));

        return bookingSaved;
    }

    private void defineAmountPending(Booking booking) {
        BigDecimal amountPending = booking.getLaunchs().stream()
                .filter(e -> e.getPaymentStatus().equals(PaymentStatusEnum.PENDING))
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
        return null;
    }

    @Override
    public List<Booking> search(LocalDateTime checkIn, LocalDateTime checkOut, String travelerName, BookingStatusEnum bookingStatusEnum, PaymentStatusEnum paymentStatusEnum) {
        return null;
    }



    @Override
    public Booking updateBooking(Booking booking, Long id) {

        validateBooking(booking);

        Booking bookingToUpdate = defineBookingDetails(booking);

        Booking bookingBase = findById(id);

        setBookingData(bookingBase, bookingToUpdate);

        Booking bookingUpdated = bookingRepository.save(bookingToUpdate);

        bookingUpdated.getLaunchs().stream()
                .forEach( e -> launchService.updateLaunch(e));



        return bookingUpdated;

    }

    public Booking findById(Long id) {

        Optional<Booking> result = bookingRepository.findById(id);

        return result
                .orElseThrow(() -> new BookingException("Não foi encontrado reserva pelo id: "  + id));

    }

    public Booking detailBooking(Long id){
        Booking booking = findById(id);

        return  defineBookingDetails(booking);
    }

    public Booking defineBookingDetails(Booking booking){
        defineBookingStatus(booking);
        definePaymentStatus(booking);
        defineAmountPending(booking);
        return booking;
    }

    private void setBookingData(Booking booking, Booking bookingToUpdate) {
       bookingToUpdate.setBookingStatus(booking.getBookingStatus());
        bookingToUpdate.setPaymentStatus(booking.getPaymentStatus());
        bookingToUpdate.setAmountPending(booking.getAmountPending());
        bookingToUpdate.setLastUpdate(LocalDateTime.now());
        bookingToUpdate.setAdults(booking.getAdults());
        bookingToUpdate.setChildren(booking.getChildren());
        bookingToUpdate.setCheckIn(booking.getCheckIn());
        bookingToUpdate.setCheckOut(booking.getCheckOut());
        bookingToUpdate.setLaunchs(booking.getLaunchs());
        bookingToUpdate.setTotalAmount(booking.getTotalAmount());
        bookingToUpdate.setTravelerId(booking.getTravelerId());
    }

    public void defineBookingStatus(Booking booking) {

        booking.setBookingStatus(BookingStatusEnum.PRE_RESERVED);

        booking.getLaunchs().stream().forEach(e ->{
            if(e.getPaymentStatus().equals(PaymentStatusEnum.PAID)){
                booking.setBookingStatus(BookingStatusEnum.RESERVED);
            }
        });

    }

    public void definePaymentStatus(Booking booking){
        booking.setPaymentStatus(PaymentStatusEnum.PAID);

        booking.getLaunchs().stream().forEach(e ->{
            if(e.getPaymentStatus().equals(PaymentStatusEnum.PENDING)){
                booking.setPaymentStatus(PaymentStatusEnum.PENDING);
            }
        });
    }


    public void validateBooking(Booking booking){

        List<Booking> otherBookings = bookingRepository.findBookingsByDate(booking.getCheckIn(), booking.getCheckOut());

        if(!otherBookings.isEmpty()){

            if(booking.getId() == null){
                throw new BookingException("Já existe outra reserva para o mesmo periodo");
            }
            for(Booking bkn: otherBookings){
                if(!bkn.getId().equals(booking.getId())){
                    throw new BookingException("Já existe outra reserva para o mesmo periodo");
                }
            }

        }

        if(booking.getLaunchs() == null || booking.getLaunchs().isEmpty()){
            throw new BookingException("Reserva não possui lançamentos");
        }

        if(!booking.getTotalAmount().equals(getTotalAmountByLaunchs(booking.getLaunchs()))){
            throw new BookingException("Soma dos lançamentos estão diferentes do valor total da reserva");
        }

    }
    public BigDecimal getTotalAmountByLaunchs(List<Launch> launchs){

        return launchs.stream()
                .map(Launch::getAmount)
        .reduce(BigDecimal.ZERO, BigDecimal::add);

    }


}
