package br.com.magnasistemas.apipessoa.service.validacoes.pessoa;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.magnasistemas.apipessoa.Repository.PessoaRepository;
import br.com.magnasistemas.apipessoa.entity.Pessoa;
import br.com.magnasistemas.apipessoa.exception.InvalidContentException;

@Component
public class ValidadorBuscaPessoa implements ValidadorPessoa{
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Override
	public void validar(Long id) {
		Optional<Pessoa> validarPessoa = pessoaRepository.findById(id);
		if (validarPessoa.isEmpty()) 
			throw new InvalidContentException ("Pessoa não encontrada");		
	}
}