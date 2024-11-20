package br.com.magnasistemas.apipessoa.DTO.Dominio;

import br.com.magnasistemas.apipessoa.entity.dominio.Cidade;
import br.com.magnasistemas.apipessoa.entity.dominio.Estados;

public record DadosCidade (
	Long id,
	Estados estado,
	String nome)
{

	public DadosCidade(Cidade cidade) {
		this(cidade.getId(),
				cidade.getEstado(),
				cidade.getNome());
		
	}
}


