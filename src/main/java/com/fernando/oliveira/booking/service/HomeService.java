package com.fernando.oliveira.booking.service;

import com.fernando.oliveira.booking.domain.response.ReservedDateResponse;

import java.util.List;

public interface HomeService {

    List<ReservedDateResponse> reservedDatesFromNextBookings();

}
