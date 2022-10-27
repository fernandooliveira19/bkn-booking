package com.fernando.oliveira.booking.service;

import com.fernando.oliveira.booking.domain.entity.Booking;
import com.fernando.oliveira.booking.domain.enums.BookingStatusEnum;
import com.fernando.oliveira.booking.domain.enums.PaymentStatusEnum;
import com.fernando.oliveira.booking.domain.request.CreateBookingRequest;
import com.fernando.oliveira.booking.domain.request.SearchBookingRequest;
import com.fernando.oliveira.booking.domain.response.BookingDetailResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingService {

    List<Booking> findAll();

    List<Booking> findNextBookings();

    List<Booking> search(SearchBookingRequest request);

    BookingDetailResponse createBooking(CreateBookingRequest request);

    Booking updateBooking(Booking bookingToUpdate, Long id);

    Booking detailBooking(Long id);



}
