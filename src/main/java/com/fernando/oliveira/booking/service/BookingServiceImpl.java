package com.fernando.oliveira.booking.service;

import com.fernando.oliveira.booking.domain.entity.Booking;
import com.fernando.oliveira.booking.domain.enums.BookingStatusEnum;
import com.fernando.oliveira.booking.domain.enums.PaymentStatusEnum;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService{
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
    public Booking create(Booking booking) {
        return null;
    }

    @Override
    public Booking update(Booking booking) {
        return null;
    }
}
