package com.fernando.oliveira.booking.service;

import com.fernando.oliveira.booking.domain.entity.Booking;
import com.fernando.oliveira.booking.domain.enums.BookingStatusEnum;
import com.fernando.oliveira.booking.domain.enums.PaymentStatusEnum;
import com.fernando.oliveira.booking.domain.request.CreateBookingRequest;
import com.fernando.oliveira.booking.domain.request.SearchBookingRequest;
import com.fernando.oliveira.booking.domain.request.UpdateBookingRequest;
import com.fernando.oliveira.booking.domain.response.BookingDetailResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingService {

    List<BookingDetailResponse> findAll();

    List<BookingDetailResponse> findNextBookings();

    List<BookingDetailResponse> search(SearchBookingRequest request);

    BookingDetailResponse createBooking(CreateBookingRequest request);

    BookingDetailResponse updateBooking(UpdateBookingRequest request, Long id);

    BookingDetailResponse detailBooking(Long id);

    Booking findById(Long id);



}
