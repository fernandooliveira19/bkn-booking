package com.fernando.oliveira.booking.service;

import com.fernando.oliveira.booking.domain.entity.Booking;
import com.fernando.oliveira.booking.domain.entity.Launch;
import com.fernando.oliveira.booking.domain.enums.BookingStatusEnum;
import com.fernando.oliveira.booking.domain.enums.PaymentStatusEnum;
import com.fernando.oliveira.booking.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return null;
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
        if(booking.getLaunchs() == null || booking.getLaunchs().isEmpty()){

        }
    }
}
