package com.fernando.oliveira.booking.controller;

import com.fernando.oliveira.booking.domain.request.CreateTravelerRequest;
import com.fernando.oliveira.booking.domain.request.UpdateTravelerRequest;
import com.fernando.oliveira.booking.domain.response.TravelerDetailResponse;
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

@Api(tags="Travelers")
@RestController
@RequestMapping(value = "/travelers")
public class TravelerController {

	@Autowired
	private TravelerService travelerService;

	@ApiOperation(value = "Realiza cadastro de viajante")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Viajante cadastrado com sucesso"),
			@ApiResponse(code = 400, message = "Dados de cadastro inválidos"),
			@ApiResponse(code = 403, message = "Você não possui permissão para acessar esse recurso"),
			@ApiResponse(code = 500, message = "Ocorreu algum erro inesperado. Tente novamente mais tarde")})
	@PostMapping
	public ResponseEntity<TravelerDetailResponse> createTraveler(@RequestBody @Valid CreateTravelerRequest request) {

		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(travelerService.createTraveler(request));

	}

	@ApiOperation(value = "Realiza busca de viajante pelo identificador")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Viajante retornado com sucesso"),
			@ApiResponse(code = 403, message = "Você não possui permissão para acessar esse recurso"),
			@ApiResponse(code = 500, message = "Ocorreu algum erro inesperado. Tente novamente mais tarde")})
	@GetMapping("/{id}")
	public ResponseEntity<TravelerDetailResponse> findById(@PathVariable("id") Long id) {

		return ResponseEntity
				.status(HttpStatus.OK)
				.body(travelerService.getTravelerDetail(id));

	}

	@ApiOperation(value = "Realiza busca de todos viajantes")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Viajante retornado com sucesso"),
			@ApiResponse(code = 403, message = "Você não possui permissão para acessar esse recurso"),
			@ApiResponse(code = 500, message = "Ocorreu algum erro inesperado. Tente novamente mais tarde")})
	@GetMapping
	public ResponseEntity<List<TravelerDetailResponse>> findAll() {

		return ResponseEntity
				.status(HttpStatus.OK)
				.body(travelerService.findAll());

	}

	@ApiOperation(value = "Realiza atualização de dados do viajante")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Atualização realizada com sucesso"),
			@ApiResponse(code = 403, message = "Você não possui permissão para acessar esse recurso"),
			@ApiResponse(code = 404, message = "Pesquisa não retornou resultados"),
			@ApiResponse(code = 500, message = "Ocorreu algum erro inesperado. Tente novamente mais tarde")})

	@PutMapping
	public ResponseEntity<TravelerDetailResponse> update(@Valid  @RequestBody UpdateTravelerRequest request) {

		return ResponseEntity
				.status(HttpStatus.OK)
				.body(travelerService.updateTraveler(request.getId(), request));
	}

	@ApiOperation(value = "Realiza pesquisa de viajantes pelo nome")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Pesquisa retornou dados com sucesso"),
			@ApiResponse(code = 403, message = "Você não possui permissão para acessar esse recurso"),
			@ApiResponse(code = 404, message = "Pesquisa não retornou resultados"),
			@ApiResponse(code = 500, message = "Ocorreu algum erro inesperado. Tente novamente mais tarde")})
	@GetMapping("/find")
	public ResponseEntity<List<TravelerDetailResponse>> findByName(@RequestParam String name) {

		return ResponseEntity
				.status(HttpStatus.OK)
				.body(travelerService.findByNameContainingOrderByNameAsc(name));
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

		return ResponseEntity
				.status(HttpStatus.OK)
				.body(travelerService.findActiveTravelers());

	}
}
