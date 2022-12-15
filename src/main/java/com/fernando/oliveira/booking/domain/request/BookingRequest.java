package com.fernando.oliveira.booking.domain.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
public class BookingRequest implements Serializable {

    @ApiModelProperty(name="travelerId", value="Id do viajante", example="1", required = true)
    @NotNull(message = "Viajante é obrigatório")
    private Long travelerId;

    @ApiModelProperty(name="checkIn", value="Data de check-in", example="2021-10-15T10:00", required = true)
    @NotBlank(message = "Data de check-in é obrigatória")
    private String checkIn;

    @ApiModelProperty(name="checkOut", value="Data de check-out", example="2021-10-20T10:00", required = true)
    @NotBlank(message = "Data de check-out é obrigatória")
    private String checkOut;

    @ApiModelProperty(name="totalAmount", value="Valor total da reserva", example="1.200,00", required = true)
    @NotNull(message = "Valor total é obrigatório")
    private BigDecimal amountTotal;

    @ApiModelProperty(name="contractType", value="Data de check-out", example="DIRECT", required = true)
    @NotBlank(message = "Tipo de contrato é obrigatório")
    private String contractType;

    @ApiModelProperty(name="adults", value="Quantidade de adultos", example="3")
    @Min(value = 1, message = "Quantidade de adultos deve ser maior que 1")
    @Max(value = 8, message = "Quantidade de adultos deve ser até 8")
    private Integer adults;

    @ApiModelProperty(name="children", value="Quantidade de crianças", example="2")
    @Max(value = 8, message = "Quantidade de crianças deve ser até 7")
    private Integer children;

    @ApiModelProperty(name="observation", value="Observação", example="Teve um desconto relativo a quantidade de dias")
    private String observation;

    @ApiModelProperty(name="websiteServiceFee", value="Valor taxa de serviço website", example="300,00")
    private BigDecimal websiteServiceFee;


}
