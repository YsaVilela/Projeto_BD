package br.com.magnasistemas.apipessoa.service.validacoes.pessoaFisica;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.magnasistemas.apipessoa.Repository.PessoaJuridicaRepository;
import br.com.magnasistemas.apipessoa.entity.PessoaJuridica;
import br.com.magnasistemas.apipessoa.exception.InvalidContentException;

@Component
public class ValidadorBuscaPessoaJuridica implements ValidadorPessoaJuridica{
	
	@Autowired
	private PessoaJuridicaRepository pessoaJuridicaRepository;
	
	@Override
	public void validar(Long id) {
		Optional<PessoaJuridica> validarPessoa = pessoaJuridicaRepository.findById(id);
		if (validarPessoa.isEmpty()) 
			throw new InvalidContentException ("Pessoa juridica não encontrada");		
	}
}