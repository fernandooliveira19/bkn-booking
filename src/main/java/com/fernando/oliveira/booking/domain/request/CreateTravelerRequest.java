package com.fernando.oliveira.booking.domain.request;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@ApiModel(value = "Requisição para cadastro de viajante")
public class CreateTravelerRequest extends TravelerRequest implements Serializable{

	private static final long serialVersionUID = 1L;


}
