package br.com.magnasistemas.apipessoa.DTO.Cargos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizarCargo(
		@NotNull (message = "Campo Id é obrigatótio")
		Long id,
		
		@NotBlank (message = "O cargo deve possuir um nome")
		String nome,
		
		@NotNull (message = "Campo remuneração é obrigatótio")
		Double remuneracao) {
}
