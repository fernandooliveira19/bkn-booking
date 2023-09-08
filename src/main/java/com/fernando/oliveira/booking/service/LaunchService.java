package com.fernando.oliveira.booking.service;

import com.fernando.oliveira.booking.domain.entity.Booking;
import com.fernando.oliveira.booking.domain.entity.Launch;
import com.fernando.oliveira.booking.exception.BookingException;
import com.fernando.oliveira.booking.repository.LaunchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class LaunchService {

    @Autowired
    private LaunchRepository launchRepository;

    public Launch createLaunch(Launch launch, Booking booking) {
        launch.setBooking(booking);
        return launchRepository.save(launch);
    }

    public Launch updateLaunch(Launch launch) {
        return launchRepository.save(launch);
    }

    public Launch findById(Long id) {
        return launchRepository.findById(id)
                .orElseThrow(() -> new BookingException("Nenhum lançamento encontrado pelo id: " + id));
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void deleteLaunch(Long id) {
        launchRepository.delete(findById(id));
    }

    public List<Launch> findNextLaunches() {
        return launchRepository.findNextLaunches();
    }

}