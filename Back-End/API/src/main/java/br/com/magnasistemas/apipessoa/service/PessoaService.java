package br.com.magnasistemas.apipessoa.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.magnasistemas.apipessoa.DTO.Pessoa.DadosAtualizarPessoa;
import br.com.magnasistemas.apipessoa.DTO.Pessoa.DadosPessoa;
import br.com.magnasistemas.apipessoa.Repository.EnderecoRepository;
import br.com.magnasistemas.apipessoa.Repository.PessoaRepository;
import br.com.magnasistemas.apipessoa.Repository.dominio.CidadeRepository;
import br.com.magnasistemas.apipessoa.entity.Endereco;
import br.com.magnasistemas.apipessoa.entity.Pessoa;
import br.com.magnasistemas.apipessoa.entity.dominio.Cidade;
import br.com.magnasistemas.apipessoa.exception.CustomDataIntegrityException;
import br.com.magnasistemas.apipessoa.exception.InvalidContentException;
import br.com.magnasistemas.apipessoa.service.validacoes.pessoa.ValidadorPessoa;
import jakarta.validation.Valid;

@Service
public class PessoaService {
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private List<ValidadorPessoa> validadoresPessoa;
	
	void validacao(String telefone) {
		if (pessoaRepository.findByTelefone(telefone)!= null)
			throw new CustomDataIntegrityException("Número de telefone já cadastrado");		
	}
	
	public Pessoa criarPessoa(@Valid DadosPessoa dados) {
		Endereco endereco = new Endereco();
		endereco.setCep(dados.endereco().cep());
		endereco.setLogradouro(dados.endereco().logradouro());
		endereco.setNumero(dados.endereco().numero());
		endereco.setComplemento(dados.endereco().complemento());

		Optional<Cidade> validarCidade = cidadeRepository.findById(dados.endereco().cidade().getId());
		if (validarCidade.isEmpty()) 
			throw new InvalidContentException ("Cidade não encontrada");
		endereco.setCidade(cidadeRepository.getReferenceById(dados.endereco().cidade().getId()));	
		
		enderecoRepository.save(endereco);
		
		Pessoa pessoa = new Pessoa();
		pessoa.setNome(dados.nome());
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate dataConvertida = LocalDate.parse(dados.dataDeConstituicao(), formatter);
		
		validacao(dados.telefone());

		pessoa.setDataDeConstituicao(dataConvertida);
		pessoa.setEmail(dados.email());
		pessoa.setTelefone(dados.telefone());
		pessoa.setEndereco(enderecoRepository.getReferenceById(endereco.getId()));
		pessoa.setStatus(true);
		pessoaRepository.save(pessoa);
		
		return pessoaRepository.getReferenceById(pessoa.getId());
	}
	
	public Pessoa atualizar(@Valid DadosAtualizarPessoa dados, long id) {
		validadoresPessoa.forEach(v -> v.validar(id));
		if (pessoaRepository.findByTelefoneAndNotId(dados.telefone(), id) != null)
			throw new CustomDataIntegrityException("Número de telefone já cadastrado");		
		
		Pessoa pessoa = pessoaRepository.getReferenceById(id);
		
		Endereco endereco = enderecoRepository.getReferenceById(pessoa.getEndereco().getId());
		endereco.setCep(dados.endereco().cep());
		endereco.setLogradouro(dados.endereco().logradouro());
		endereco.setNumero(dados.endereco().numero());
		endereco.setComplemento(dados.endereco().complemento());
		endereco.setCidade(cidadeRepository.getReferenceById(dados.endereco().cidade().getId()));
		enderecoRepository.save(endereco);		
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate dataConvertida = LocalDate.parse(dados.dataDeConstituicao(), formatter);

		pessoa.setNome(dados.nome());
		pessoa.setDataDeConstituicao(dataConvertida); 
		pessoa.setTelefone(dados.telefone());
		pessoa.setEndereco(endereco);
		
		pessoaRepository.save(pessoa);
		
		return pessoaRepository.getReferenceById(pessoa.getId());
	}
	
	public void atualizarStatus(Long id) {
		Pessoa pessoa = pessoaRepository.getReferenceById(id);
		boolean statusPessoa = pessoa.isStatus();
		pessoa.setStatus(!statusPessoa);
	}
	
	public void deletarPessoa(Long id) {
		Pessoa pessoa = pessoaRepository.getReferenceById(id);
		pessoaRepository.deleteById(id);
		enderecoRepository.deleteById(pessoa.getEndereco().getId()); 
	}
	
}
