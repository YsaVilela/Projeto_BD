package br.com.magnasistemas.apipessoa.service.validacoes.cargo.remuneracao;

import br.com.magnasistemas.apipessoa.entity.Cargo;

public interface ValidadorRemuneracaoCargo {
	void validar (Cargo cargo, Double remuneracao);
}
