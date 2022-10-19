package com.fernando.oliveira.booking.service;

import com.fernando.oliveira.booking.domain.entity.Booking;
import com.fernando.oliveira.booking.domain.entity.Launch;
import com.fernando.oliveira.booking.domain.mapper.LaunchMapper;
import com.fernando.oliveira.booking.domain.response.LaunchDetailResponse;
import com.fernando.oliveira.booking.exception.BookingException;
import com.fernando.oliveira.booking.repository.LaunchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LaunchServiceImpl implements LaunchService{

    @Autowired
    private LaunchRepository launchRepository;

    @Autowired
    private LaunchMapper launchMapper;

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

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void deleteLaunch(Long id) {
        launchRepository.delete(findById(id));

    }

    @Override
    public List<LaunchDetailResponse> findNextLaunches() {

        return launchRepository.findNextLaunches()
                .stream()
                .map(e -> launchMapper.launchToDetailLaunchResponse(e))
                .collect(Collectors.toList());

    }

}
