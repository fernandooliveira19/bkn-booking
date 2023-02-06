package com.fernando.oliveira.booking.domain.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PreviewBookingRequest {

    @ApiModelProperty(name="checkIn", value="Data de check-in", example="2021-10-15T10:00", required = true)
    @NotBlank(message = "Data de check-in é obrigatória")
    private String checkIn;

    @ApiModelProperty(name="checkOut", value="Data de check-out", example="2021-10-20T10:00", required = true)
    @NotBlank(message = "Data de check-out é obrigatória")
    private String checkOut;

    @ApiModelProperty(name="dailyValue", value="Valor da diária", example="350.00", required = true)
    @NotNull(message = "Valor da diária é obrigatório")
    private BigDecimal dailyRate;

    @ApiModelProperty(name="cleaningFee", value="Valor da taxa de limpeza", example="120.00", required = true)
    private BigDecimal cleaningFee;


}
