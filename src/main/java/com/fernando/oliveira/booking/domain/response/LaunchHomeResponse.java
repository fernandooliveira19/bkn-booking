package com.fernando.oliveira.booking.domain.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class LaunchHomeResponse {

    private Long bookingId;
    private String scheduleDate;
    private BigDecimal amount;
    private String travelerName;
    private String checkIn;
    private String status;

}
