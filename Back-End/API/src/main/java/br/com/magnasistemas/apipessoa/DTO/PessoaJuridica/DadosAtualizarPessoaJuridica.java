package br.com.magnasistemas.apipessoa.DTO.PessoaJuridica;

import br.com.magnasistemas.apipessoa.DTO.Pessoa.DadosAtualizarPessoa;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DadosAtualizarPessoaJuridica(		
	@NotNull (message = "Campo Id é obrigatótio")
	Long id,
	
	@NotBlank (message = "O cnpj é obrigatório")
	@Pattern(regexp = "^\\d{2}\\.\\d{3}\\.\\d{3}\\/\\d{4}-\\d{2}$", message = "Cnpj Inválido")
	String cnpj,
	
	@Valid
	DadosAtualizarPessoa pessoa) {
}
