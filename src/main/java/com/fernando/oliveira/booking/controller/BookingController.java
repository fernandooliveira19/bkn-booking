package com.fernando.oliveira.booking.controller;

import com.fernando.oliveira.booking.domain.entity.Booking;
import com.fernando.oliveira.booking.domain.enums.BookingStatusEnum;
import com.fernando.oliveira.booking.domain.enums.ContractTypeEnum;
import com.fernando.oliveira.booking.domain.enums.PaymentStatusEnum;
import com.fernando.oliveira.booking.domain.mapper.BookingMapper;
import com.fernando.oliveira.booking.domain.request.CreateBookingRequest;
import com.fernando.oliveira.booking.domain.request.SearchBookingRequest;
import com.fernando.oliveira.booking.domain.request.UpdateBookingRequest;
import com.fernando.oliveira.booking.domain.response.DetailBookingResponse;
import com.fernando.oliveira.booking.service.AuthorizationAccessService;
import com.fernando.oliveira.booking.service.BookingService;
import com.fernando.oliveira.booking.service.ContractService;
import com.fernando.oliveira.booking.utils.FormatterUtils;
import com.fernando.oliveira.booking.utils.PdfUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags="Bookings")
@RestController
@RequestMapping(value = "/v1/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private BookingMapper bookingMapper;

    @Autowired
    private ContractService contractService;

    @Autowired
    private AuthorizationAccessService authorizationAccessService;


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
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @ApiOperation(value = "Realiza busca de reserva por id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Reserva retornada com sucesso"),
            @ApiResponse(code = 400, message = "Dados inválidos"),
            @ApiResponse(code = 403, message = "Você não possui permissão para acessar esse recurso"),
            @ApiResponse(code = 500, message = "Ocorreu algum erro inesperado. Tente novamente mais tarde")})
    @GetMapping(value = "/{id}")
    public ResponseEntity<DetailBookingResponse> detail(@PathVariable("id") Long id){

        Booking booking = bookingService.detailBooking(id);
        DetailBookingResponse response = bookingMapper.bookingToDetailBookingResponse(booking);
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @ApiOperation(value = "Retorna lista de próximas reservas ativas ordenadas por data")
    @ApiResponses(value = {
            @ApiResponse(code = 403, message = "Você não possui permissão para acessar esse recurso"),
            @ApiResponse(code = 500, message = "Ocorreu algum erro inesperado. Tente novamente mais tarde")})
    @GetMapping("/next")
    public ResponseEntity<List<DetailBookingResponse>> findNext(){

        List<Booking> bookings = bookingService.findNextBookings();
        List<DetailBookingResponse> response = bookings.stream()
                .map(e -> bookingMapper.bookingToDetailBookingResponse(e))
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @ApiOperation(value = "Gera contrato de reserva no formato pdf")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Contrato gerado com sucesso"),
            @ApiResponse(code = 400, message = "Dados de cadastro inválidos"),
            @ApiResponse(code = 403, message = "Você não possui permissão para acessar esse recurso"),
            @ApiResponse(code = 500, message = "Ocorreu algum erro inesperado. Tente novamente mais tarde")})
    @GetMapping("/{id}/contract")
    public ResponseEntity<InputStreamResource> createContract(@PathVariable("id") Long bookingId){
        Booking booking = bookingService.detailBooking(bookingId);
        ByteArrayInputStream bis = contractService.createContract(booking);

        return ResponseEntity
                .ok()
                .headers(PdfUtils.getHttpHeaders(PdfUtils.getContractName(booking)))
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));

    }

    @ApiOperation(value = "Gera autoraização de acesso no formato pdf")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Autorizacao gerada com sucesso"),
            @ApiResponse(code = 400, message = "Dados de cadastro inválidos"),
            @ApiResponse(code = 403, message = "Você não possui permissão para acessar esse recurso"),
            @ApiResponse(code = 500, message = "Ocorreu algum erro inesperado. Tente novamente mais tarde")})
    @GetMapping("/{id}/authorization")
    public ResponseEntity<InputStreamResource> authorizationAccess(@PathVariable("id") Long bookingId){
        Booking booking = bookingService.detailBooking(bookingId);
        ByteArrayInputStream bis = authorizationAccessService.createAuthorizationAccess(booking);

        return ResponseEntity
                .ok()
                .headers(PdfUtils.getHttpHeaders(PdfUtils.getAuthorizationAccessName(booking)))
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));

    }

    @ApiOperation(value = "Realiza busca avançada de reservas")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Autorizacao gerada com sucesso"),
            @ApiResponse(code = 400, message = "Dados de cadastro inválidos"),
            @ApiResponse(code = 403, message = "Você não possui permissão para acessar esse recurso"),
            @ApiResponse(code = 500, message = "Ocorreu algum erro inesperado. Tente novamente mais tarde")})
    @GetMapping("/search")
    public ResponseEntity<List<DetailBookingResponse>> search(
            @RequestParam(value = "date", required = false) String date,
            @RequestParam(value = "paymentStatus", required = false) PaymentStatusEnum paymentStatus,
            @RequestParam(value = "bookingStatus", required = false) BookingStatusEnum bookingStatus,
            @RequestParam(value = "contractType", required = false) ContractTypeEnum contractType){



        SearchBookingRequest request = SearchBookingRequest.builder()
                .date(FormatterUtils.getLocalDateFormat(date))
                .bookingStatus(bookingStatus)
                .paymentStatus(paymentStatus)
                .contractType(contractType)
                .build();

        List<Booking> bookings = bookingService.search(request);

        List<DetailBookingResponse> response = bookings.stream()
                .map(e -> bookingMapper.bookingToDetailBookingResponse(e))
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

}
