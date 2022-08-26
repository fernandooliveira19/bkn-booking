package com.fernando.oliveira.booking.service;

import com.fernando.oliveira.booking.domain.entity.Booking;
import com.fernando.oliveira.booking.domain.enums.BookingStatusEnum;
import com.fernando.oliveira.booking.domain.enums.PaymentStatusEnum;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingService {

    List<Booking> findAll();

    List<Booking> findNextBookings();

    List<Booking> search(LocalDateTime checkIn, LocalDateTime checkOut, String travelerName, BookingStatusEnum bookingStatusEnum, PaymentStatusEnum paymentStatusEnum);

    Booking createBooking(Booking booking);

    Booking updateBooking(Booking bookingToUpdate, Long id);

    Booking detailBooking(Long id);

    Booking finishBooking(String observation, Long id);

    Booking cancelBooking(String observation, Long bookingId);
}
