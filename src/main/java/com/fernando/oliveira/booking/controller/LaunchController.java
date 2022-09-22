package com.fernando.oliveira.booking.controller;

import com.fernando.oliveira.booking.domain.response.LaunchDetailResponse;
import com.fernando.oliveira.booking.service.LaunchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags="Launchs")
@RestController
@RequestMapping(value = "/launchs")
public class LaunchController {

    @Autowired
    private LaunchService launchService;

    @ApiOperation(value = "Realiza exclusao de lançamento por id")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Lançamentos removido com sucesso"),
            @ApiResponse(code = 400, message = "Dados de lançamento inválidos"),
            @ApiResponse(code = 403, message = "Você não possui permissão para acessar esse recurso"),
            @ApiResponse(code = 500, message = "Ocorreu algum erro inesperado. Tente novamente mais tarde")})
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id){

        launchService.deleteLaunch(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

    @ApiOperation(value = "Realiza busca de próximos lançamento pendentes")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Próximos lançamentos pendentes retornados com sucesso"),
            @ApiResponse(code = 400, message = "Dados de lançamento inválidos"),
            @ApiResponse(code = 403, message = "Você não possui permissão para acessar esse recurso"),
            @ApiResponse(code = 500, message = "Ocorreu algum erro inesperado. Tente novamente mais tarde")})
    @GetMapping(value = "/next")
    public ResponseEntity<List<LaunchDetailResponse>> findNextLaunches(){

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(launchService.findNextLaunches());

    }
}
