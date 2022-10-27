package com.fernando.oliveira.booking.domain.request;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ApiModel(value = "Requisição para cadastro de reservas")
public class CreateBookingRequest extends BookingRequest implements Serializable {

    private List<CreateLaunchRequest> launches;
}
