package br.com.magnasistemas.apipessoa.DTO.PessoaJuridica;

import br.com.magnasistemas.apipessoa.entity.Pessoa;
import br.com.magnasistemas.apipessoa.entity.PessoaJuridica;

public record DadosDetalhamentoPessoaJuridica(
		Long id, 
		Pessoa pessoa, 
		String cnpj) {

public DadosDetalhamentoPessoaJuridica (PessoaJuridica pessoaJuridica) {
	this(pessoaJuridica.getId(),
			pessoaJuridica.getPessoa(),
			pessoaJuridica.getCnjp());
	}
}
