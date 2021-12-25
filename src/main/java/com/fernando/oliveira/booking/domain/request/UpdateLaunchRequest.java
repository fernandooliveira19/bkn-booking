package com.fernando.oliveira.booking.domain.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@ApiModel(value = "Requisição para atualização de lançamento")
public class UpdateLaunchRequest extends LaunchRequest{

    @ApiModelProperty(name="id", value="Id do lançamento", example="1")
    private Long id;
}
