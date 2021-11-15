package com.fernando.oliveira.booking.service;

import com.fernando.oliveira.booking.domain.entity.Booking;
import com.fernando.oliveira.booking.domain.entity.Launch;

public interface LaunchService {

    Launch createLaunch(Launch launch, Booking booking);

    Launch updateLaunch(Launch launch);
}
