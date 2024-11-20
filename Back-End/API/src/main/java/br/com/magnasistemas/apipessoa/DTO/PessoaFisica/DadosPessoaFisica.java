package br.com.magnasistemas.apipessoa.DTO.PessoaFisica;

import br.com.magnasistemas.apipessoa.DTO.Pessoa.DadosPessoa;
import br.com.magnasistemas.apipessoa.entity.Cargo;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DadosPessoaFisica(		
		@Valid
		DadosPessoa pessoa,
		
		@NotBlank (message = "Cpf obrigatório")
		@Pattern(regexp = "^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$", message = "Cpf inválido")
		String cpf,
		
		@NotNull (message = "Cargo é obrigatório")
		Cargo cargo
		){}
