package br.com.magnasistemas.apipessoa.DTO.PessoaFisica;

import br.com.magnasistemas.apipessoa.DTO.Pessoa.DadosAtualizarPessoa;
import br.com.magnasistemas.apipessoa.entity.Cargo;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DadosAtualizarPessoaFisica(
		@NotNull (message = "Campo Id é obrigatótio")
		Long id,
		
		@NotBlank (message = "O cpf é obrigatório")
		@Pattern(regexp = "^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$", message = "Cpf inválido. Deve conter 11 digitos sem caracter especial")
		String cpf,
		
		@Valid
		DadosAtualizarPessoa pessoa,
		
		@NotNull (message = "Cargo é obrigatório")
		Cargo cargo) {
}
