package com.fernando.oliveira.booking.controller;

import com.fernando.oliveira.booking.domain.response.HomeResponse;
import com.fernando.oliveira.booking.service.BookingService;
import com.fernando.oliveira.booking.service.LaunchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

public class HomeController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private LaunchService launchService;

    @GetMapping
    public ResponseEntity<HomeResponse> getHomeResponse(){
        HomeResponse homeResponse = new HomeResponse();
        

        return ResponseEntity.ok(homeResponse);
    }
}
