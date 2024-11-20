package br.com.magnasistemas.apipessoa.DTO.Endereco;

import br.com.magnasistemas.apipessoa.entity.dominio.Cidade;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DadosEndereco (
		@NotBlank (message = "CEP não pode ser vazio")
		@Pattern(regexp = "^\\d{5}-\\d{3}$", message = "CEP inválido")
		String cep,
		
		@NotBlank (message = "Logradouro não pode ser vazio")
		String logradouro,
		
		@NotNull(message = "Número não pode ser vazio")
		Long numero,
		
		String complemento,
		
		@NotNull(message = "Cidade não pode ser vazio")
		Cidade cidade){
}
