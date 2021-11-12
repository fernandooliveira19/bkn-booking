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

@Service
public class BookingServiceImpl implements BookingService{

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private LaunchService launchService;

    @Override
    public Booking createBooking(Booking booking) {

        validateCreateBooking(booking);
        setBookingStatus(booking);
        setPaymentStatus(booking);

        booking.setInsertDate(LocalDateTime.now());

        Booking bookingSaved = bookingRepository.save(booking);

        bookingSaved.getLaunchs().stream()
             .forEach( e -> launchService.createLaunch(e, bookingSaved));

        return bookingSaved;
    }



    @Override
    public List<Booking> findAll() {
        return bookingRepository.findAll();
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
    public Booking update(Booking booking) {
        return null;
    }

    public void setBookingStatus(Booking booking) {

        booking.setBookingStatus(BookingStatusEnum.PRE_RESERVED);

        booking.getLaunchs().stream().forEach(e ->{
            if(e.getPaymentStatus().equals(PaymentStatusEnum.PAID)){
                booking.setBookingStatus(BookingStatusEnum.RESERVED);
            }
        });

    }

    public void setPaymentStatus(Booking booking){
        booking.setPaymentStatus(PaymentStatusEnum.PAID);

        booking.getLaunchs().stream().forEach(e ->{
            if(e.getPaymentStatus().equals(PaymentStatusEnum.PENDING)){
                booking.setPaymentStatus(PaymentStatusEnum.PENDING);
            }
        });
    }


    public void validateCreateBooking(Booking booking){

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

    }
    public void validateTotalAmount(Booking booking){
        BigDecimal totalAmount = booking.getTotalAmount();
        BigDecimal amount = BigDecimal.ZERO;
        for(Launch launch : booking.getLaunchs()){
            amount.add(launch.getAmount());
        }

        if(!totalAmount.equals(amount)){
            throw new BookingException("Soma dos lançamentos estão diferentes do valor total da reserva");
        }
    }

    public void validateBookingDate(Booking booking){
        LocalDateTime checkIn = booking.getCheckIn();

    }
}
