package com.fernando.oliveira.booking.domain.request;

import com.fernando.oliveira.booking.validation.Cpf;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
public class TravelerRequest {

    @ApiModelProperty(name="name", value="Nome completo do viajante", example="José da Silva", required = true)
    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 5, max = 250, message = "Nome inválido")
    @Pattern(regexp = "^[A-ú]+\\s[A-ú]+(\\s[A-ú]+)*", message = "Sobrenome é obrigatório")
    private String name;

    @ApiModelProperty(name="email", value="Email do viajante", example="jose.silva@gmail.com", required = true)
    @Email
    @NotBlank(message = "Email é obrigatório")
    private String email;

    @ApiModelProperty(name="document", value="Documento do viajante", example="12345678910")
    @Cpf(message = "Cpf inválido")
    private String document;

    @ApiModelProperty(name="prefixPhone", value="Prefixo do telefone", example = "11", required = true)
    @NotNull(message = "DDD é obrigatório")
    private Integer prefixPhone;

    @ApiModelProperty(name="numberPhone", value="Numero do telefone", example = "98888-7777", required = true)
    @Size(min=10, max = 10, message = "Telefone inválido")
    @NotBlank(message = "Telefone é obrigatório")
    private String numberPhone;
}
