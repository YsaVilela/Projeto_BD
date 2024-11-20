package br.com.magnasistemas.apipessoa.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.magnasistemas.apipessoa.DTO.PessoaJuridica.DadosAtualizarPessoaJuridica;
import br.com.magnasistemas.apipessoa.DTO.PessoaJuridica.DadosDetalhamentoPessoaJuridica;
import br.com.magnasistemas.apipessoa.DTO.PessoaJuridica.DadosPessoaJuridica;
import br.com.magnasistemas.apipessoa.Repository.PessoaJuridicaRepository;
import br.com.magnasistemas.apipessoa.entity.Pessoa;
import br.com.magnasistemas.apipessoa.entity.PessoaJuridica;
import br.com.magnasistemas.apipessoa.exception.CustomDataIntegrityException;
import br.com.magnasistemas.apipessoa.exception.InvalidContentException;
import br.com.magnasistemas.apipessoa.service.validacoes.endereco.ValidadorEndereco;
import br.com.magnasistemas.apipessoa.service.validacoes.pessoa.ValidadorPessoa;
import br.com.magnasistemas.apipessoa.service.validacoes.pessoaFisica.ValidadorPessoaJuridica;
import jakarta.validation.Valid;

@Service
public class PessoaJuridicaService {
	
	@Autowired
	private PessoaService pessoaService;
	
	@Autowired
	private PessoaJuridicaRepository pessoaJuridicaRepository;
	
	@Autowired
	private List<ValidadorPessoa> validadoresPessoa;
	
	@Autowired
	private List<ValidadorPessoaJuridica> validadoresPessoaJuridica;
	
	@Autowired
	private List<ValidadorEndereco> validadoresEndereco;
	
	void validacao(String cnpj) {
		if (!pessoaJuridicaRepository.findByCnpj(cnpj).isEmpty())
			throw new CustomDataIntegrityException("Cnpj já cadastrado.");
	}
	
	public Optional<DadosDetalhamentoPessoaJuridica> criarPessoaJuridica (DadosPessoaJuridica dados) {
		validacao(dados.cnpj());
		
		Pessoa pessoa = pessoaService.criarPessoa(dados.pessoa());
		PessoaJuridica pessoaJuridica = new PessoaJuridica();
		pessoaJuridica.setCnpj(dados.cnpj());
		pessoaJuridica.setPessoa(pessoa);
		pessoaJuridicaRepository.save(pessoaJuridica);
		
		return pessoaJuridicaRepository.findById(pessoaJuridica.getId()).map(DadosDetalhamentoPessoaJuridica::new);
	}
	
	public Optional<DadosDetalhamentoPessoaJuridica> buscarPorId(Long id) {
		validadoresPessoaJuridica.forEach(r -> r.validar(id));
		return pessoaJuridicaRepository.findById(id).map(DadosDetalhamentoPessoaJuridica::new);
	}
	
	public Page<DadosDetalhamentoPessoaJuridica> buscarPorNome(String nome, Pageable paginacao) {		
		if (pessoaJuridicaRepository.findByAllNome(nome, paginacao).isEmpty())
			throw new InvalidContentException("Nenhuma pessoa encontrada");

		return pessoaJuridicaRepository.findByAllNome(nome, paginacao).map(DadosDetalhamentoPessoaJuridica::new);
	}
	
	public Optional<DadosDetalhamentoPessoaJuridica> buscarPorCnpj(String cnpj) {
		if (pessoaJuridicaRepository.findByCnpj(cnpj).isEmpty())
			throw new InvalidContentException("Nenhuma pessoa encontrada.");
		
		return pessoaJuridicaRepository.findByCnpj(cnpj).map(DadosDetalhamentoPessoaJuridica::new);
	}
	
	public Page<DadosDetalhamentoPessoaJuridica> listar(Pageable paginacao) {
		return pessoaJuridicaRepository.findByPessoaStatusTrue(paginacao).map(DadosDetalhamentoPessoaJuridica::new);
	}
	
	public Page<DadosDetalhamentoPessoaJuridica> listarTodos(Pageable paginacao) {
		return pessoaJuridicaRepository.findAll(paginacao).map(DadosDetalhamentoPessoaJuridica::new);
	}
	
	public Optional<DadosDetalhamentoPessoaJuridica> atualizar(@Valid DadosAtualizarPessoaJuridica dados) {
		validadoresPessoaJuridica.forEach(r -> r.validar(dados.id()));
		if (pessoaJuridicaRepository.findByCnpjAndNotId(dados.cnpj(), dados.id()) != null)
			throw new CustomDataIntegrityException("Cnpj já cadastrado.");
		
		PessoaJuridica pessoaJuridica = pessoaJuridicaRepository.getReferenceById(dados.id());

		validadoresPessoa.forEach(r -> r.validar(pessoaJuridica.getPessoa().getId()));
		validadoresEndereco.forEach(r -> r.validar(pessoaJuridica.getPessoa().getEndereco().getId()));

		Pessoa pessoa = pessoaService.atualizar(dados.pessoa(), pessoaJuridica.getPessoa().getId());		
		pessoaJuridica.setPessoa(pessoa);
		pessoaJuridica.setCnpj(dados.cnpj());
		
		pessoaJuridicaRepository.save(pessoaJuridica);
	
		return pessoaJuridicaRepository.findById(pessoaJuridica.getId()).map(DadosDetalhamentoPessoaJuridica::new);
	}
	
	public void atualizarStatus(Long id) {
		validadoresPessoaJuridica.forEach(r -> r.validar(id));

		PessoaJuridica pessoaJuridica = pessoaJuridicaRepository.getReferenceById(id);
		Long idPessoa = pessoaJuridica.getPessoa().getId();
		pessoaService.atualizarStatus(idPessoa);
	}
	
	public void deletar(Long id) {
		validadoresPessoaJuridica.forEach(r -> r.validar(id));

		PessoaJuridica pessoaJuridica = pessoaJuridicaRepository.getReferenceById(id);
		pessoaJuridicaRepository.deleteById(id);
		pessoaService.deletarPessoa(pessoaJuridica.getPessoa().getId());
	}
}
