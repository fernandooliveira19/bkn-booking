package com.fernando.oliveira.booking.controller;

import com.fernando.oliveira.booking.domain.entity.Booking;
import com.fernando.oliveira.booking.domain.mapper.BookingMapper;
import com.fernando.oliveira.booking.domain.request.CreateBookingRequest;
import com.fernando.oliveira.booking.domain.request.UpdateBookingRequest;
import com.fernando.oliveira.booking.domain.response.DetailBookingResponse;
import com.fernando.oliveira.booking.service.BookingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

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
      DetailBookingResponse response = bookingMapper.bookingToDetailBookingResponse(bookingCreated);
      return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @ApiOperation(value = "Retorna lista de todas reservas")
    @ApiResponses(value = {
            @ApiResponse(code = 403, message = "Você não possui permissão para acessar esse recurso"),
            @ApiResponse(code = 500, message = "Ocorreu algum erro inesperado. Tente novamente mais tarde")})
    @GetMapping
    public ResponseEntity<List<DetailBookingResponse>> findAll(){

        List<Booking> bookings = bookingService.findAll();
        List<DetailBookingResponse> response = bookings.stream()
                .map(e -> bookingMapper.bookingToDetailBookingResponse(e))
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @ApiOperation(value = "Realiza atualização de reserva")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Reserva atualizada com sucesso"),
            @ApiResponse(code = 400, message = "Dados de cadastro inválidos"),
            @ApiResponse(code = 403, message = "Você não possui permissão para acessar esse recurso"),
            @ApiResponse(code = 500, message = "Ocorreu algum erro inesperado. Tente novamente mais tarde")})
    @PutMapping(value = "/{id}")
    public ResponseEntity<DetailBookingResponse> update(@RequestBody @Valid UpdateBookingRequest request,
                                                        @PathVariable("id") Long id){

        Booking bookingToUpdate = bookingMapper.updateRequestToEntity(request);
        Booking bookingUpdated = bookingService.updateBooking(bookingToUpdate, id);
        DetailBookingResponse response = bookingMapper.bookingToDetailBookingResponse(bookingUpdated);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }
}
