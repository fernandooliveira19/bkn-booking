package com.fernando.oliveira.booking.controller;

import com.fernando.oliveira.booking.domain.entity.Traveler;
import com.fernando.oliveira.booking.domain.mapper.TravelerMapper;
import com.fernando.oliveira.booking.domain.request.CreateTravelerRequest;
import com.fernando.oliveira.booking.domain.request.UpdateTravelerRequest;
import com.fernando.oliveira.booking.domain.response.BookingTravelerResponse;
import com.fernando.oliveira.booking.domain.response.TravelerDetailResponse;
import com.fernando.oliveira.booking.service.BookingService;
import com.fernando.oliveira.booking.service.TravelerService;
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

@Api(tags="Travelers")
@RestController
@RequestMapping(value = "/travelers")
public class TravelerController {

	@Autowired
	private TravelerService travelerService;

	@Autowired
	private BookingService bookingService;

	@Autowired
	private TravelerMapper travelerMapper;

	@ApiOperation(value = "Realiza cadastro de viajante")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Viajante cadastrado com sucesso"),
			@ApiResponse(code = 400, message = "Dados de cadastro inválidos"),
			@ApiResponse(code = 403, message = "Você não possui permissão para acessar esse recurso"),
			@ApiResponse(code = 500, message = "Ocorreu algum erro inesperado. Tente novamente mais tarde")})
	@PostMapping
	public ResponseEntity<TravelerDetailResponse> createTraveler(@RequestBody @Valid CreateTravelerRequest request) {

		Traveler traveler = travelerMapper.requestToCreateTraveler(request);

		Traveler travelerCreated = travelerService.createTraveler(traveler);

		TravelerDetailResponse response = travelerMapper
				.travelerToTravelerDetailResponse(travelerCreated);

		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(response);

	}

	@ApiOperation(value = "Realiza busca de viajante pelo identificador")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Viajante retornado com sucesso"),
			@ApiResponse(code = 403, message = "Você não possui permissão para acessar esse recurso"),
			@ApiResponse(code = 500, message = "Ocorreu algum erro inesperado. Tente novamente mais tarde")})
	@GetMapping("/{id}")
	public ResponseEntity<TravelerDetailResponse> findById(@PathVariable("id") Long id) {

		Traveler traveler = travelerService.getTravelerDetail(id);
		TravelerDetailResponse response = travelerMapper.travelerToTravelerDetailResponse(traveler);
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(response);

	}

	@ApiOperation(value = "Realiza busca de todos viajantes")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Viajante retornado com sucesso"),
			@ApiResponse(code = 403, message = "Você não possui permissão para acessar esse recurso"),
			@ApiResponse(code = 500, message = "Ocorreu algum erro inesperado. Tente novamente mais tarde")})
	@GetMapping
	public ResponseEntity<List<TravelerDetailResponse>> findAll() {
		List<Traveler> travelerList = travelerService.findAll();
		List<TravelerDetailResponse> responses = travelerList
				.stream()
				.map(e -> travelerMapper.travelerToTravelerDetailResponse(e))
				.collect(Collectors.toList());
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(responses);

	}

	@ApiOperation(value = "Realiza atualização de dados do viajante")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Atualização realizada com sucesso"),
			@ApiResponse(code = 403, message = "Você não possui permissão para acessar esse recurso"),
			@ApiResponse(code = 404, message = "Pesquisa não retornou resultados"),
			@ApiResponse(code = 500, message = "Ocorreu algum erro inesperado. Tente novamente mais tarde")})

	@PutMapping
	public ResponseEntity<TravelerDetailResponse> update(@Valid  @RequestBody UpdateTravelerRequest request) {

		Traveler traveler = travelerService.updateTraveler(request.getId(), request);
		TravelerDetailResponse response = travelerMapper.travelerToTravelerDetailResponse(traveler);
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(response);
	}

	@ApiOperation(value = "Realiza pesquisa de viajantes pelo nome")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Pesquisa retornou dados com sucesso"),
			@ApiResponse(code = 403, message = "Você não possui permissão para acessar esse recurso"),
			@ApiResponse(code = 404, message = "Pesquisa não retornou resultados"),
			@ApiResponse(code = 500, message = "Ocorreu algum erro inesperado. Tente novamente mais tarde")})
	@GetMapping("/find")
	public ResponseEntity<List<TravelerDetailResponse>> findByName(@RequestParam String name) {

		List<Traveler> travelerList = travelerService.findByNameContainingOrderByNameAsc(name);
		List<TravelerDetailResponse> responses = travelerList
				.stream()
				.map(e -> travelerMapper.travelerToTravelerDetailResponse(e))
				.collect(Collectors.toList());

		return ResponseEntity
				.status(HttpStatus.OK)
				.body(responses);
	}

	@ApiOperation(value = "Realiza inativação de viajante pelo identificador")
	@ApiResponses(value = {
			@ApiResponse(code = 204, message = "Viajante inativado com sucesso"),
			@ApiResponse(code = 403, message = "Você não possui permissão para acessar esse recurso"),
			@ApiResponse(code = 500, message = "Ocorreu algum erro inesperado. Tente novamente mais tarde")})
	@PutMapping("/{id}/inactive")
	public ResponseEntity<Void> inactivateTraveler(@PathVariable("id") Long id) {

		travelerService.inactivateTraveler(id);

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

	}

	@ApiOperation(value = "Realiza busca de todos viajantes ativos")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Viajantes retornados com sucesso"),
			@ApiResponse(code = 403, message = "Você não possui permissão para acessar esse recurso"),
			@ApiResponse(code = 500, message = "Ocorreu algum erro inesperado. Tente novamente mais tarde")})
	@GetMapping("/actives/")
	public ResponseEntity<List<TravelerDetailResponse>> findAllActive() {

		List<Traveler> travelerList = travelerService.findActiveTravelers();
		List<TravelerDetailResponse> responses = travelerList
				.stream()
				.map(e -> travelerMapper.travelerToTravelerDetailResponse(e))
				.collect(Collectors.toList());

		return ResponseEntity
				.status(HttpStatus.OK)
				.body(responses);

	}

	@ApiOperation(value = "Realiza busca de reservas por viajante")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Viajante retornado com sucesso"),
			@ApiResponse(code = 403, message = "Você não possui permissão para acessar esse recurso"),
			@ApiResponse(code = 500, message = "Ocorreu algum erro inesperado. Tente novamente mais tarde")})
	@GetMapping("/{id}/bookings")
	public ResponseEntity<List<BookingTravelerResponse>> findBookingsByTravelerId(@PathVariable("id") Long id) {

		return ResponseEntity
				.status(HttpStatus.OK)
				.body(bookingService.findBookingsByTraveler(id));

	}
}
