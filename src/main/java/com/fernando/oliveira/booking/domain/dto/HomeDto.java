package com.fernando.oliveira.booking.domain.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HomeDto {
    private HomeLaunchDto homeLaunch;
    private HomeBookingDto homeBooking;
}
