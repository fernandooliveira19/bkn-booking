package com.fernando.oliveira.booking.domain.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
public class LaunchRequest implements Serializable {

    @ApiModelProperty(name="amount", value="Valor do lançamento", example="500,00", required = true)
    @NotNull(message = "Valor do lançamento é obrigatório")
    private BigDecimal amount;

    @ApiModelProperty(name="paymentDate", value="Data de pagamento do lançamento", example="2021-10-14", required = true)
    private String paymentDate;

    @ApiModelProperty(name="scheduleDate", value="Data de agendamento do lançamento", example="2021-10-21", required = true)
    @NotBlank(message = "Data de agendamento do é obrigatório")
    private String scheduleDate;

    @ApiModelProperty(name="paymentType", value="Tipo de pagamento do lançamento", example="PIX", required = true)
    @NotBlank(message = "Tipo de pagamento do lançamento é obrigatório")
    private String paymentType;

    @ApiModelProperty(name="paymentStatus", value="Situação de pagamento do lançamento", example="PAID", required = true)
    @NotBlank(message = "Situação de pagamento do lançamento é obrigatório")
    private String paymentStatus;
}
