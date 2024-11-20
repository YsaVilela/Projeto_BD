package br.com.magnasistemas.apipessoa.service.validacoes.cargo.remuneracao;

import org.springframework.stereotype.Component;

import br.com.magnasistemas.apipessoa.entity.Cargo;
import br.com.magnasistemas.apipessoa.exception.InvalidDataException;

@Component
public class ReduzirRemuneracao implements ValidadorRemuneracaoCargo  {

	@Override
	public void validar(Cargo cargo, Double remuneracao) {
		if (cargo.getRemuneracao() == null)
			cargo.setRemuneracao(0.0);
		if (cargo.getRemuneracao() > remuneracao)
			throw new InvalidDataException("O salário não pode ser reduzido");		
	}
}