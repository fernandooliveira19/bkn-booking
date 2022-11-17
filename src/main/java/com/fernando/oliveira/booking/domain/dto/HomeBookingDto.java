package com.fernando.oliveira.booking.domain.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HomeBookingDto {

    private Integer bookingReserved;
    private Integer bookingPreReserved;
    private Integer bookingPaid;
    private Integer bookingPending;
    private Integer bookingToReceive;
    private Integer bookingDirect;
    private Integer bookingSite;
    private Integer bookingTotal;
}
