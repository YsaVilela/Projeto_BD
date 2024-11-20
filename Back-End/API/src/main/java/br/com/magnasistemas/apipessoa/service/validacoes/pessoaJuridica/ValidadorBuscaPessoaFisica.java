package br.com.magnasistemas.apipessoa.service.validacoes.pessoaJuridica;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.magnasistemas.apipessoa.Repository.PessoaFisicaRepository;
import br.com.magnasistemas.apipessoa.entity.PessoaFisica;
import br.com.magnasistemas.apipessoa.exception.InvalidContentException;

@Component
public class ValidadorBuscaPessoaFisica implements ValidadorPessoaFisica{
	
	@Autowired
	private PessoaFisicaRepository pessoaFisicaRepository;
	
	@Override
	public void validar(Long id) {
		Optional<PessoaFisica> validarPessoa = pessoaFisicaRepository.findById(id);
		if (validarPessoa.isEmpty()) 
			throw new InvalidContentException ("Pessoa física não encontrada");		
	}
}