package br.com.magnasistemas.apipessoa.service.validacoes.cargo;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.magnasistemas.apipessoa.Repository.CargoRepository;
import br.com.magnasistemas.apipessoa.entity.Cargo;
import br.com.magnasistemas.apipessoa.exception.InvalidContentException;

@Component
public class ValidadorBuscaCargo implements ValidadorCargo{
	
	@Autowired
	private CargoRepository cargoRepository;
	
	@Override
	public void validar(Long id) {
		Optional<Cargo> validarCargo = cargoRepository.findById(id);
		if (validarCargo.isEmpty()) 
			throw new InvalidContentException ("Cargo não encontrado");		
	}
}
