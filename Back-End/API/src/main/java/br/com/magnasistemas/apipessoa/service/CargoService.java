package br.com.magnasistemas.apipessoa.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.magnasistemas.apipessoa.DTO.Cargos.DadosAtualizarCargo;
import br.com.magnasistemas.apipessoa.DTO.Cargos.DadosCargo;
import br.com.magnasistemas.apipessoa.DTO.Cargos.DadosDetalhamentoCargo;
import br.com.magnasistemas.apipessoa.Repository.CargoRepository;
import br.com.magnasistemas.apipessoa.Repository.PessoaFisicaRepository;
import br.com.magnasistemas.apipessoa.entity.Cargo;
import br.com.magnasistemas.apipessoa.exception.CustomDataIntegrityException;
import br.com.magnasistemas.apipessoa.exception.InvalidContentException;
import br.com.magnasistemas.apipessoa.service.validacoes.cargo.remuneracao.ValidadorRemuneracaoCargo;
import jakarta.validation.Valid;

@Service
public class CargoService {
	@Autowired
	private CargoRepository cargoRepository;
	
	@Autowired
	private PessoaFisicaRepository pessoaFisicaRepository;

	@Autowired
	private List<ValidadorRemuneracaoCargo> validadoresRemuneracaoCargo;

	public Optional<DadosDetalhamentoCargo> criarCargo(DadosCargo dados) {
		if (cargoRepository.findByNome(dados.getNome()) != null)
			throw new CustomDataIntegrityException("Já existe cargo com mesmo nome.");

		Cargo cargo = new Cargo();
		cargo.setNome(dados.getNome());
		validadoresRemuneracaoCargo.forEach(r -> r.validar(cargo, dados.getRemuneracao()));
		cargo.setRemuneracao(dados.getRemuneracao());
		cargoRepository.save(cargo);
		return cargoRepository.findById(cargo.getId()).map(DadosDetalhamentoCargo::new);

	}
	
	public Optional<DadosDetalhamentoCargo> buscarPorId(Long id) {
		Optional<Cargo> validarCargo = cargoRepository.findById(id);
		if (validarCargo.isEmpty())
			throw new InvalidContentException("Cargo não encontrado");
 
		return cargoRepository.findById(id).map(DadosDetalhamentoCargo::new);
	}
	

	public Optional<DadosDetalhamentoCargo> buscarPorNome(String nome) {
		if (cargoRepository.findByAllNome(nome).isEmpty())
			throw new InvalidContentException("Nenhum cargo encontrado");

		return cargoRepository.findByAllNome(nome).map(DadosDetalhamentoCargo::new);
	}
	
	public Page<DadosDetalhamentoCargo> buscarPorNomeDinamico(String nome, Pageable paginacao) {
		if (cargoRepository.findByAllNomeDinamico(nome, paginacao).isEmpty())
			throw new InvalidContentException("Nenhum cargo encontrado");

		return cargoRepository.findByAllNomeDinamico(nome, paginacao).map(DadosDetalhamentoCargo::new);
	}

	public Page<DadosDetalhamentoCargo> listar(Pageable paginacao) {
		return cargoRepository.findAll(paginacao).map(DadosDetalhamentoCargo::new);
	}

	public DadosDetalhamentoCargo atualizar(@Valid DadosAtualizarCargo dados) {
		Optional<Cargo> validarCargo = cargoRepository.findById(dados.id());
		
		if (validarCargo.isEmpty())
			throw new InvalidContentException("Cargo não encontrado");
		if (cargoRepository.findByNomeAndNotId(dados.nome(), dados.id()) != null)
			throw new CustomDataIntegrityException("Já existe cargo com mesmo nome.");

		Cargo cargo = cargoRepository.getReferenceById(dados.id());
		validadoresRemuneracaoCargo.forEach(r -> r.validar(cargo, dados.remuneracao()));
		cargo.setNome(dados.nome());
		cargo.setRemuneracao(dados.remuneracao());
		cargoRepository.save(cargo);
		return new DadosDetalhamentoCargo(cargo);

	}
	
	public void deletar(Long id) {
		if(!pessoaFisicaRepository.findByCargoId(id).isEmpty()) 
			throw new CustomDataIntegrityException("Há profissionais cadastrados com esse cargo");
		
		cargoRepository.deleteById(id);
	}

}