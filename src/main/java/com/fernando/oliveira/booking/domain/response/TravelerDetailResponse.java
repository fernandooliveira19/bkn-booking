package com.fernando.oliveira.booking.domain.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@ApiModel(value = "Detalhes do viajante")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TravelerDetailResponse implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(name="id", value="Identificador do viajante", example = "12345", hidden = true)
	private Long id;
	@ApiModelProperty(name="name", value="Nome completo do viajante", example="José da Silva")
	private String name;
	@ApiModelProperty(name="email", value="Email do viajante", example="jose.silva@gmail.com")
	private String email;
	@ApiModelProperty(name="document", value="CPF do viajante", example="123.456.789-00")
	private String document;
	@ApiModelProperty(name="prefixPhone", value="Prefixo do telefone", example = "11")
	private Integer prefixPhone;
	@ApiModelProperty(name="numberPhone", value="Numero do telefone", example = "98888-7777")
	private String numberPhone;
	@ApiModelProperty(name="status", value="Situação do viajante </br><ul><li>A - Ativo</li><li>I - Inativo</li></ul>", example = "A")
	private String status;

}
