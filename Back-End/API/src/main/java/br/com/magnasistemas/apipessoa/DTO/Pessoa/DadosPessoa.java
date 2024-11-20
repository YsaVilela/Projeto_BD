package br.com.magnasistemas.apipessoa.DTO.Pessoa;

import br.com.magnasistemas.apipessoa.DTO.Endereco.DadosEndereco;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DadosPessoa(@NotBlank(message = "Nome é obrigatório") String nome,

		@NotBlank(message = "Data De Constituicao é obrigatório") 
		@Pattern(regexp = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/(\\d{4})$", message = "Formato de data inválido. Utilize 00/00/0000") 
		String dataDeConstituicao,

		@Pattern(regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$", message = "Email inválido") 
		String email,

		@NotBlank(message = "Telefone é obrigatório") 
		@Pattern(regexp = "^\\(\\d{2}\\)\\d{5}-\\d{4}$", message = "Telefone inválido") 
		String telefone,

		@Valid  
		DadosEndereco endereco) {
}
