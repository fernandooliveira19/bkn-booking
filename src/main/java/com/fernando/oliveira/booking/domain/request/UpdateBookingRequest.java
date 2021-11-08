package com.fernando.oliveira.booking.domain.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
@Setter
public class UpdateBookingRequest extends BookingRequest {

    @ApiModelProperty(name="bookingStatus", value="Situação da reserva", example="RESERVED", required = true)
    @NotBlank(message = "Situação da reserva é obrigatória")
    private String bookingStatus;

    @ApiModelProperty(name="paymentStatus", value="Situação do pagamento da reserva", example="PENDING", required = true)
    @NotBlank(message = "Situação do pagamento da reserva é obrigatória")
    private String paymentStatus;
}
