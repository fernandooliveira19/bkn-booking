package com.fernando.oliveira.booking.domain.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class BookingHomeResponse {

    private Long bookingId;
    private String checkIn;
    private String checkout;
    private String travelerName;
    private BigDecimal amountTotal;
    private BigDecimal amountPending;
}
