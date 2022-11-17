package com.fernando.oliveira.booking.controller;

import com.fernando.oliveira.booking.domain.dto.HomeDto;
import com.fernando.oliveira.booking.domain.mapper.HomeMapper;
import com.fernando.oliveira.booking.domain.response.HomeResponse;
import com.fernando.oliveira.booking.service.HomeService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags="Home")
@RestController
@RequestMapping(value = "/home")
public class HomeController {

    @Autowired
    private HomeService homeService;

    @Autowired
    private HomeMapper homeMapper;

    @GetMapping
    public ResponseEntity<HomeResponse> getHomeResponse(){

        HomeDto dto = homeService.getHomeDetails();
        HomeResponse response = homeMapper.dtoToResponse(dto);

        return ResponseEntity.ok(response);
    }
}
