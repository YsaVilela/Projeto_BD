package br.com.magnasistemas.apipessoa.DTO.PessoaJuridica;

import br.com.magnasistemas.apipessoa.DTO.Pessoa.DadosPessoa;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DadosPessoaJuridica(
		@Valid
		DadosPessoa pessoa,
		
		@NotBlank (message = "cpf obrigatório")
		@Pattern(regexp = "^\\d{2}\\.\\d{3}\\.\\d{3}\\/\\d{4}-\\d{2}$", message = "Cnpj Inválido")
		String cnpj
		){}