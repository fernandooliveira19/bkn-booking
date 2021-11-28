package com.fernando.oliveira.booking.domain.request;

import com.fernando.oliveira.booking.validation.Status;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@ApiModel(value = "Requisição para atualização de viajante")
public class UpdateTravelerRequest  extends TravelerRequest implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(name="id", value="Id do viajante", example="1", required = true)
	@NotNull(message = "Id é obrigatório")
	private Long id;

	@ApiModelProperty(name="status", value="Situação do viajante </br><ul><li>A - Ativo</li><li>I - Inativo</li></ul>", example = "A", required = true)
	@NotBlank(message = "Status é obrigatório")
	@Status(message = "Valor de status deve ser: A ou I")
	private String status;

}
