package br.com.magnasistemas.apipessoa.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.magnasistemas.apipessoa.DTO.PessoaFisica.DadosAtualizarPessoaFisica;
import br.com.magnasistemas.apipessoa.DTO.PessoaFisica.DadosDetalhamentoPessoaFisica;
import br.com.magnasistemas.apipessoa.DTO.PessoaFisica.DadosPessoaFisica;
import br.com.magnasistemas.apipessoa.Repository.CargoRepository;
import br.com.magnasistemas.apipessoa.Repository.PessoaFisicaRepository;
import br.com.magnasistemas.apipessoa.entity.Pessoa;
import br.com.magnasistemas.apipessoa.entity.PessoaFisica;
import br.com.magnasistemas.apipessoa.exception.CustomDataIntegrityException;
import br.com.magnasistemas.apipessoa.exception.InvalidContentException;
import br.com.magnasistemas.apipessoa.service.validacoes.cargo.ValidadorCargo;
import br.com.magnasistemas.apipessoa.service.validacoes.endereco.ValidadorEndereco;
import br.com.magnasistemas.apipessoa.service.validacoes.pessoa.ValidadorPessoa;
import br.com.magnasistemas.apipessoa.service.validacoes.pessoaJuridica.ValidadorPessoaFisica;
import jakarta.validation.Valid;

@Service
public class PessoaFisicaService {
	
	@Autowired
	private PessoaFisicaRepository pessoaFisicaRepository;
	
	@Autowired
	private CargoRepository cargoRepository;
	
	@Autowired
	private PessoaService pessoaService;
	
	@Autowired
	private List<ValidadorPessoa> validadoresPessoa;
	
	@Autowired
	private List<ValidadorPessoaFisica> validadoresPessoaFisica;
	
	@Autowired
	private List<ValidadorEndereco> validadoresEndereco;
	
	@Autowired
	private List<ValidadorCargo> validadoresCargo;
	
	void validacao(String cpf) {
		if (!pessoaFisicaRepository.findByCpf(cpf).isEmpty())
			throw new CustomDataIntegrityException("Cpf já possui cadastro.");
	}
	
	public Optional<DadosDetalhamentoPessoaFisica> criarPessoaFisica(@Valid DadosPessoaFisica dados) {
		validacao(dados.cpf());
		validadoresCargo.forEach(r -> r.validar(dados.cargo().getId()));
		
		Pessoa pessoa = pessoaService.criarPessoa(dados.pessoa());
		PessoaFisica pessoaFisica = new PessoaFisica();
		pessoaFisica.setCpf(dados.cpf());
		pessoaFisica.setPessoa(pessoa);
		pessoaFisica.setCargo(cargoRepository.getReferenceById(dados.cargo().getId()));	
		pessoaFisicaRepository.save(pessoaFisica);
		
		return pessoaFisicaRepository.findById(pessoaFisica.getId()).map(DadosDetalhamentoPessoaFisica::new);
	}
	
	public Optional<DadosDetalhamentoPessoaFisica> buscarPorId(Long id) {
		validadoresPessoaFisica.forEach(r -> r.validar(id));
		return pessoaFisicaRepository.findById(id).map(DadosDetalhamentoPessoaFisica::new);
	}
	
	public Page<DadosDetalhamentoPessoaFisica> buscarPorNome(String nome, Pageable paginacao) {		
		if (pessoaFisicaRepository.findByAllNome(nome, paginacao).isEmpty())
			throw new InvalidContentException("Nenhuma pessoa encontrada");

		return pessoaFisicaRepository.findByAllNome(nome, paginacao).map(DadosDetalhamentoPessoaFisica::new);
	}
	
	public Page<DadosDetalhamentoPessoaFisica> buscarPorCpf(String cpf, Pageable paginacao) {
		if (pessoaFisicaRepository.findByCpfDinamico(cpf, paginacao).isEmpty())
			throw new InvalidContentException("Nenhuma pessoa encontrada.");
		
		return pessoaFisicaRepository.findByCpfDinamico(cpf, paginacao).map(DadosDetalhamentoPessoaFisica::new);
	} 
	
	public Page<DadosDetalhamentoPessoaFisica> buscarPorCargo(String cargo, Pageable paginacao) {
		if (pessoaFisicaRepository.findByCargo(cargo, paginacao).isEmpty())
			throw new InvalidContentException("Nenhuma pessoa encontrada.");
		
		return pessoaFisicaRepository.findByCargo(cargo, paginacao).map(DadosDetalhamentoPessoaFisica::new);
	} 
	
	public Page<DadosDetalhamentoPessoaFisica> listar(Pageable paginacao) {
		return pessoaFisicaRepository.findByPessoaStatusTrue(paginacao).map(DadosDetalhamentoPessoaFisica::new);
	}
	
	public Page<DadosDetalhamentoPessoaFisica> listarTodos(Pageable paginacao) {
		return pessoaFisicaRepository.findAll(paginacao).map(DadosDetalhamentoPessoaFisica::new);
	}
	
	public Optional<DadosDetalhamentoPessoaFisica> atualizar(@Valid DadosAtualizarPessoaFisica dados) {	
		validadoresPessoaFisica.forEach(r -> r.validar(dados.id()));
		validadoresCargo.forEach(r -> r.validar(dados.cargo().getId()));
		if (pessoaFisicaRepository.findByCpfAndNotId(dados.cpf(), dados.id()) != null)
			throw new CustomDataIntegrityException("Cpf já possui cadastro.");
		PessoaFisica pessoaFisica = pessoaFisicaRepository.getReferenceById(dados.id());
		
		validadoresPessoa.forEach(r -> r.validar(pessoaFisica.getPessoa().getId()));
		validadoresEndereco.forEach(r -> r.validar(pessoaFisica.getPessoa().getEndereco().getId()));

		Pessoa pessoa = pessoaService.atualizar(dados.pessoa(), pessoaFisica.getPessoa().getId());
		pessoaFisica.setCpf(dados.cpf());
		pessoaFisica.setPessoa(pessoa);
		pessoaFisica.setCargo(cargoRepository.getReferenceById(dados.cargo().getId()));
		pessoaFisicaRepository.save(pessoaFisica);
	
		return pessoaFisicaRepository.findById(pessoaFisica.getId()).map(DadosDetalhamentoPessoaFisica::new);
	}
	
	public void atualizarStatus(Long id) {
		validadoresPessoaFisica.forEach(r -> r.validar(id));

		PessoaFisica pessoaFisica = pessoaFisicaRepository.getReferenceById(id);
		Long idPessoa = pessoaFisica.getPessoa().getId();
		pessoaService.atualizarStatus(idPessoa);
	}
	
	public void deletar(Long id) {
		validadoresPessoaFisica.forEach(r -> r.validar(id));

		PessoaFisica pessoaFisica = pessoaFisicaRepository.getReferenceById(id);
		pessoaFisicaRepository.deleteById(id);
		pessoaService.deletarPessoa(pessoaFisica.getPessoa().getId());
	}
}
