package br.com.magnasistemas.apipessoa.service.validacoes.cargo.remuneracao;

import org.springframework.stereotype.Component;

import br.com.magnasistemas.apipessoa.entity.Cargo;
import br.com.magnasistemas.apipessoa.exception.InvalidDataException;

@Component
public class SalarioMinimo implements ValidadorRemuneracaoCargo {

	@Override
	public void validar(Cargo cargo, Double remuneracao) {
		if (remuneracao < 1412.00)
			throw new InvalidDataException("Salário mínimo é de 1412.00");
	}
}