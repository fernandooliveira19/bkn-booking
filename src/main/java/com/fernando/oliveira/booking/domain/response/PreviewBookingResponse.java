package com.fernando.oliveira.booking.domain.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PreviewBookingResponse {

    @ApiModelProperty(name="amountTotal", value="Valor total da reserva", example="1250,00")
    private BigDecimal amountTotal;

    @ApiModelProperty(name="rentDays", value="Total de diárias", example="2")
    private Long rentDays;

}
