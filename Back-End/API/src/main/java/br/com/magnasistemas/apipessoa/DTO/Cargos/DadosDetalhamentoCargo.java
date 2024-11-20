package br.com.magnasistemas.apipessoa.DTO.Cargos;

import br.com.magnasistemas.apipessoa.entity.Cargo;

public record DadosDetalhamentoCargo(		
		Long id, 
		String nome, 
		Double remuneracao) {

public DadosDetalhamentoCargo (Cargo cargo) {
	this(cargo.getId(),
			cargo.getNome(),
			cargo.getRemuneracao());
	}
}
