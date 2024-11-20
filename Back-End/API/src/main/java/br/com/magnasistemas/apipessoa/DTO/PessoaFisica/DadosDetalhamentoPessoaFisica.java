package br.com.magnasistemas.apipessoa.DTO.PessoaFisica;

import br.com.magnasistemas.apipessoa.entity.Cargo;
import br.com.magnasistemas.apipessoa.entity.Pessoa;
import br.com.magnasistemas.apipessoa.entity.PessoaFisica;

public record DadosDetalhamentoPessoaFisica(		
		Long id,
		Pessoa pessoa,
		String cpf,
		Cargo cargo) {
	
	public DadosDetalhamentoPessoaFisica (PessoaFisica pessoaFisica) {
		this(pessoaFisica.getId(),
				pessoaFisica.getPessoa(),
				pessoaFisica.getCpf(),
				pessoaFisica.getCargo());
	}
}
  