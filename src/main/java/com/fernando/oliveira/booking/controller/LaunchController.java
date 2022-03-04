package com.fernando.oliveira.booking.controller;

import com.fernando.oliveira.booking.service.LaunchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags="Launchs")
@RestController
@RequestMapping(value = "/v1/launchs")
public class LaunchController {

    @Autowired
    private LaunchService launchService;

    @ApiOperation(value = "Realiza exclusao de lançamento por id")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Lançamento removido com sucesso"),
            @ApiResponse(code = 400, message = "Dados de lançamento inválidos"),
            @ApiResponse(code = 403, message = "Você não possui permissão para acessar esse recurso"),
            @ApiResponse(code = 500, message = "Ocorreu algum erro inesperado. Tente novamente mais tarde")})
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id){

        launchService.deleteLaunch(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }
}
