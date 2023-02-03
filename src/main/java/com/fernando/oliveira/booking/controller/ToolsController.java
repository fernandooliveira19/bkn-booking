package com.fernando.oliveira.booking.controller;

import com.fernando.oliveira.booking.domain.mapper.ToolsMapper;
import com.fernando.oliveira.booking.domain.request.PreviewBookingRequest;
import com.fernando.oliveira.booking.domain.response.PreviewBookingResponse;
import com.fernando.oliveira.booking.service.ToolsService;
import com.fernando.oliveira.booking.utils.FormatterUtils;
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

@Api(tags="Tools")
@RestController
@RequestMapping(value = "/tools")
public class ToolsController {

    @Autowired
    private ToolsService toolsService;

    @Autowired
    private ToolsMapper toolsMapper;

    @ApiOperation(value = "Calcula valor total da reserva a partir do periodo e valor da diaria")
    @ApiResponses(value = {
            @ApiResponse(code = 403, message = "Você não possui permissão para acessar esse recurso"),
            @ApiResponse(code = 500, message = "Ocorreu algum erro inesperado. Tente novamente mais tarde")})
    @PostMapping(value = "/bookings/preview")
    public ResponseEntity<PreviewBookingResponse> calculateAmountTotal(@RequestBody @Valid PreviewBookingRequest request){

        PreviewBookingResponse response = toolsMapper
                .previewBookingDtoToResponse(
                        toolsService.calculateAmount(FormatterUtils.getIsoLocalDateTime(request.getCheckIn()), FormatterUtils.getIsoLocalDateTime(request.getCheckOut()), request.getDailyValue()));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


}
