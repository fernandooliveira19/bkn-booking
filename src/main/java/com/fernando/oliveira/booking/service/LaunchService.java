package com.fernando.oliveira.booking.service;

import com.fernando.oliveira.booking.domain.entity.Booking;
import com.fernando.oliveira.booking.domain.entity.Launch;

import java.util.List;

public interface LaunchService {

    Launch createLaunch(Launch launch, Booking booking);

    Launch updateLaunch(Launch launch);

    Launch findById(Long id);

    void deleteLaunch(Long id);

    List<Launch> findNextLaunchs();
}
