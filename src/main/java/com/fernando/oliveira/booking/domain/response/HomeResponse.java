package com.fernando.oliveira.booking.domain.response;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HomeResponse implements Serializable {

    private HomeLaunchResponse homeLaunch;

    private HomeBookingResponse homeBooking;


}
