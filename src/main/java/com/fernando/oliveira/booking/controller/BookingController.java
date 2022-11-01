package com.fernando.oliveira.booking.controller;

import com.fernando.oliveira.booking.domain.entity.Booking;
import com.fernando.oliveira.booking.domain.enums.BookingStatusEnum;
import com.fernando.oliveira.booking.domain.enums.ContractTypeEnum;
import com.fernando.oliveira.booking.domain.enums.PaymentStatusEnum;
import com.fernando.oliveira.booking.domain.request.CreateBookingRequest;
import com.fernando.oliveira.booking.domain.request.SearchBookingRequest;
import com.fernando.oliveira.booking.domain.request.UpdateBookingRequest;
import com.fernando.oliveira.booking.domain.response.BookingDetailResponse;
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
import java.util.List;

@Api(tags="Bookings")
@RestController
@RequestMapping(value = "/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

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
    public ResponseEntity<BookingDetailResponse> create(@RequestBody @Valid CreateBookingRequest request){

      return ResponseEntity
              .status(HttpStatus.CREATED)
              .body(bookingService.createBooking(request));

    }

    @ApiOperation(value = "Retorna lista de todas reservas")
    @ApiResponses(value = {
            @ApiResponse(code = 403, message = "Você não possui permissão para acessar esse recurso"),
            @ApiResponse(code = 500, message = "Ocorreu algum erro inesperado. Tente novamente mais tarde")})
    @GetMapping
    public ResponseEntity<List<BookingDetailResponse>> findAll(){

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(bookingService.findAll());
    }
    @ApiOperation(value = "Realiza atualização de reserva")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Reserva atualizada com sucesso"),
            @ApiResponse(code = 400, message = "Dados de cadastro inválidos"),
            @ApiResponse(code = 403, message = "Você não possui permissão para acessar esse recurso"),
            @ApiResponse(code = 500, message = "Ocorreu algum erro inesperado. Tente novamente mais tarde")})
    @PutMapping(value = "/{id}")
    public ResponseEntity<BookingDetailResponse> update(@RequestBody @Valid UpdateBookingRequest request,
                                                        @PathVariable("id") Long id){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(bookingService.updateBooking(request, id));

    }

    @ApiOperation(value = "Realiza busca de reserva por id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Reserva retornada com sucesso"),
            @ApiResponse(code = 400, message = "Dados inválidos"),
            @ApiResponse(code = 403, message = "Você não possui permissão para acessar esse recurso"),
            @ApiResponse(code = 500, message = "Ocorreu algum erro inesperado. Tente novamente mais tarde")})
    @GetMapping(value = "/{id}")
    public ResponseEntity<BookingDetailResponse> detail(@PathVariable("id") Long id){

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(bookingService.detailBooking(id));

    }

    @ApiOperation(value = "Retorna lista de próximas reservas ativas ordenadas por data")
    @ApiResponses(value = {
            @ApiResponse(code = 403, message = "Você não possui permissão para acessar esse recurso"),
            @ApiResponse(code = 500, message = "Ocorreu algum erro inesperado. Tente novamente mais tarde")})
    @GetMapping("/next")
    public ResponseEntity<List<BookingDetailResponse>> findNext(){

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(bookingService.findNextBookings());
    }

    @ApiOperation(value = "Gera contrato de reserva no formato pdf")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Contrato gerado com sucesso"),
            @ApiResponse(code = 400, message = "Dados de cadastro inválidos"),
            @ApiResponse(code = 403, message = "Você não possui permissão para acessar esse recurso"),
            @ApiResponse(code = 500, message = "Ocorreu algum erro inesperado. Tente novamente mais tarde")})
    @GetMapping("/{id}/contract")
    public ResponseEntity<InputStreamResource> createContract(@PathVariable("id") Long bookingId){
        Booking booking = bookingService.findById(bookingId);
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
        Booking booking = bookingService.findById(bookingId);
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
    public ResponseEntity<List<BookingDetailResponse>> search(
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

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(bookingService.search(request));

    }

}
