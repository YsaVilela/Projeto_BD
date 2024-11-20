package br.com.magnasistemas.apipessoa.DTO.Dominio;

import br.com.magnasistemas.apipessoa.entity.dominio.Estados;

public record DadosEstados(
		Long id,
		String nome,
		String uf,
		String região) {
	
	public DadosEstados (Estados estado) {
		this(estado.getId(),
				estado.getNome(),
				estado.getUf(),
				estado.getRegiao());
	}
}
