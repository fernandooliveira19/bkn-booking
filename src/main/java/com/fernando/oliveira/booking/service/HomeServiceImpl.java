package com.fernando.oliveira.booking.service;

import com.fernando.oliveira.booking.domain.entity.Booking;
import com.fernando.oliveira.booking.domain.entity.Launch;
import com.fernando.oliveira.booking.domain.mapper.BookingMapper;
import com.fernando.oliveira.booking.domain.response.BookingHomeResponse;
import com.fernando.oliveira.booking.domain.response.HomeResponse;
import com.fernando.oliveira.booking.domain.response.LaunchHomeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HomeServiceImpl implements HomeService{

    @Autowired
    private BookingService bookingService;

    @Autowired
    private LaunchService launchService;

    @Autowired
    private BookingMapper bookingMapper;

    public List<BookingHomeResponse> getNextBookings(){
        return bookingService.findNextBookings().stream()
                .map(e -> bookingMapper.bookingToBookingHomeResponse(e))
                .collect(Collectors.toList());
    }


    private List<LaunchHomeResponse> getNextLaunchs() {

        List<Launch> launchs = launchService.findNextLaunchs();
        
        return null;
    }

    @Override
    public HomeResponse getHomeResponse() {

        HomeResponse homeResponse = new HomeResponse();
        List<Booking> nextBookings = bookingService.findNextBookings();
        List<Launch> nextLaunchs = launchService.findNextLaunchs();

        return null;
    }
}
