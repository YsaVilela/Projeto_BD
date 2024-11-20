package br.com.magnasistemas.apipessoa.service.validacoes.endereco;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.magnasistemas.apipessoa.Repository.EnderecoRepository;
import br.com.magnasistemas.apipessoa.entity.Endereco;
import br.com.magnasistemas.apipessoa.exception.InvalidContentException;

@Component
public class ValidadorBuscaEndereco implements ValidadorEndereco{
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Override
	public void validar(Long id) {
		Optional<Endereco> validarEndereco = enderecoRepository.findById(id);
		if (validarEndereco.isEmpty()) 
			throw new InvalidContentException ("Endereco não encontrado");		
	}
}