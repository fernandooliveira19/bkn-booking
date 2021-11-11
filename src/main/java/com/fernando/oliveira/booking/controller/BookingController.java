package com.fernando.oliveira.booking.controller;

import com.fernando.oliveira.booking.domain.entity.Booking;
import com.fernando.oliveira.booking.domain.mapper.BookingMapper;
import com.fernando.oliveira.booking.domain.request.CreateBookingRequest;
import com.fernando.oliveira.booking.domain.response.DetailBookingResponse;
import com.fernando.oliveira.booking.service.BookingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(tags="Booking endpoint")
@RestController
@RequestMapping(value = "/v1/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private BookingMapper bookingMapper;


    @ApiOperation(value = "Realiza cadastro de reserva")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Reserva cadastrada com sucesso"),
            @ApiResponse(code = 400, message = "Dados de cadastro inválidos"),
            @ApiResponse(code = 403, message = "Você não possui permissão para acessar esse recurso"),
            @ApiResponse(code = 500, message = "Ocorreu algum erro inesperado. Tente novamente mais tarde")})
    @PostMapping
    public ResponseEntity<DetailBookingResponse> create(@RequestBody @Valid CreateBookingRequest request){

      Booking bookingToCreate = bookingMapper.createRequestToEntity(request);
      Booking bookingCreated = bookingService.createBooking(bookingToCreate);

        return ResponseEntity.status(HttpStatus.CREATED).body(new DetailBookingResponse());

    }
}
