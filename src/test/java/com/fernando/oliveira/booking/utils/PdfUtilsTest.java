package com.fernando.oliveira.booking.utils;

import com.fernando.oliveira.booking.domain.entity.Booking;
import com.fernando.oliveira.booking.mother.BookingMother;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PdfUtilsTest {

    @Test
    void givenBookingWhenBuildRequestContractThenReturnContractName(){
        Booking booking = BookingMother.getBooking01Saved();

        String result = PdfUtils.getContractName(booking);
        assertEquals("contrato_ana_2021-10-15", result);
    }

    @Test
    void givenLocalDateTimeWhenBuildRequestContractThenReturnDateFormatted(){
        Booking booking = BookingMother.getBooking01Saved();

        String result = PdfUtils.getContractDateFormat(booking.getCheckIn());
        assertEquals("2021-10-15", result);
    }

    @Test
    void givenCheckInWhenBuildRequestContractThenReturnDescriptionCheckIn(){
        Booking booking = BookingMother.getBooking01Saved();

        String result = PdfUtils.getDescriptionCheckIn(booking.getCheckIn());
        assertEquals("início: 15/10/2021 após 12:30", result);

    }
}
