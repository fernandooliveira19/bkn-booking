package com.fernando.oliveira.booking.domain.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.processing.Generated;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class BookingRequest implements Serializable {

    @ApiModelProperty(name="travelerId", value="Id do viajante", example="1", required = true)
    @NotNull(message = "Viajante é obrigatório")
    private Long travelerId;

    @ApiModelProperty(name="checkIn", value="Data de check-in", example="2021-10-15 10:00", required = true)
    @NotBlank(message = "Data de check-in é obrigatória")
    private String checkIn;

    @ApiModelProperty(name="checkOut", value="Data de check-out", example="2021-10-20 10:00", required = true)
    @NotBlank(message = "Data de check-out é obrigatória")
    private String checkOut;

    @ApiModelProperty(name="totalAmount", value="Valor total da reserva", example="1200.0", required = true)
    @NotNull(message = "Nome é obrigatório")
    private BigDecimal totalAmount;

    @ApiModelProperty(name="adults", value="Quantidade de adultos", example="3", required = true)
    @NotNull(message = "Quantidade de adultos é obrigatório")
    @Min(value = 1, message = "Quantidade de adultos deve ser maior que 1")
    @Max(value = 8, message = "Quantidade de adultos deve ser até 8")
    private Integer adults;

    @ApiModelProperty(name="children", value="Quantidade de crianças", example="2", required = true)
    @NotNull(message = "Quantidade de crianças é obrigatório")
    @Max(value = 8, message = "Quantidade de crianças deve ser até 7")
    private Integer children;

    private List<LaunchRequest> launchs;


}
