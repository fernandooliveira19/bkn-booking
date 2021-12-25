package com.fernando.oliveira.booking.service;

import com.fernando.oliveira.booking.domain.entity.Booking;
import com.fernando.oliveira.booking.domain.entity.Launch;
import com.fernando.oliveira.booking.exception.BookingException;
import com.fernando.oliveira.booking.repository.LaunchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LaunchServiceImpl implements LaunchService{

    @Autowired
    private LaunchRepository launchRepository;

    @Override
    public Launch createLaunch(Launch launch, Booking booking) {
        launch.setBooking(booking);
        return launchRepository.save(launch);
    }

    @Override
    public Launch updateLaunch(Launch launch) {
       return launchRepository.save(launch);
    }

    @Override
    public Launch findById(Long id) {
        return launchRepository.findById(id)
                .orElseThrow(() -> new BookingException("Nenhum lançamento encontrado pelo id: " + id));
    }

}
