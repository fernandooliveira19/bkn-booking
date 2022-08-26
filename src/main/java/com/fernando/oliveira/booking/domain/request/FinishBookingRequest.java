package com.fernando.oliveira.booking.domain.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
public class FinishBookingRequest {

    @ApiModelProperty(name="observation", value="Observação", example="Teve um desconto relativo a quantidade de dias")
    private String observation;


}
