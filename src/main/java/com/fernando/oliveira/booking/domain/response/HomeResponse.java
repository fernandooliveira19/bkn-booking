package com.fernando.oliveira.booking.domain.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class HomeResponse implements Serializable {

    private List<BookingHomeResponse> bookingHomeResponse;

    private List<LaunchHomeResponse> launchHomeResponse;

}
