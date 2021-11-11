package com.fernando.oliveira.booking.mother;

import com.fernando.oliveira.booking.domain.entity.Booking;
import com.fernando.oliveira.booking.domain.entity.Launch;
import com.fernando.oliveira.booking.domain.enums.BookingStatusEnum;
import com.fernando.oliveira.booking.domain.enums.PaymentStatusEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

public class BookingMother {
    private BookingMother(){

    }

    public static Booking getBookingToSave(LocalDateTime checkIn, LocalDateTime checkOut,
                                           BigDecimal totalAmount,
                                           Long travelerId,
                                           Integer adults,
                                           Integer children,
                                           List<Launch> launchs) {
        return Booking.builder()
                .checkIn(checkIn)
                .checkOut(checkOut)
                .totalAmount(totalAmount)
                .travelerId(travelerId)
                .adults(adults)
                .children(children)
                .launchs(launchs)
                .build();
    }

    public static Booking getBookingSaved(LocalDateTime checkIn, LocalDateTime checkOut, BigDecimal totalAmount, Long travelerId, Integer adults, Integer children, List<Launch> launchs, BookingStatusEnum bookingStatus, PaymentStatusEnum paymentStatus) {
        return Booking.builder()
                .id(1L)
                .checkIn(checkIn)
                .checkOut(checkOut)
                .totalAmount(totalAmount)
                .travelerId(travelerId)
                .adults(adults)
                .children(children)
                .launchs(launchs)
                .bookingStatus(bookingStatus)
                .paymentStatus(paymentStatus)
                .insertDate(LocalDateTime.of(2021, Month.AUGUST,22, 9, 39))
                .build();

    }
}
