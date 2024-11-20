package br.com.magnasistemas.apipessoa.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.magnasistemas.apipessoa.DTO.PessoaFisica.DadosAtualizarPessoaFisica;
import br.com.magnasistemas.apipessoa.DTO.PessoaFisica.DadosDetalhamentoPessoaFisica;
import br.com.magnasistemas.apipessoa.DTO.PessoaFisica.DadosPessoaFisica;
import br.com.magnasistemas.apipessoa.service.PessoaFisicaService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("pessoaFisica")
@CrossOrigin(origins = "*", maxAge = 3600)
public class PessoaFisicaController {

	@Autowired
	private PessoaFisicaService service;

	@PostMapping("cadastrar")
	public ResponseEntity<Optional<DadosDetalhamentoPessoaFisica>> cadastrar(
			@RequestBody @Valid DadosPessoaFisica dados) {
		return ResponseEntity.status(HttpStatus.CREATED).body(service.criarPessoaFisica(dados));
	}

	@GetMapping("/buscarId/{id}")
	public ResponseEntity<Optional<DadosDetalhamentoPessoaFisica>> buscarId(@PathVariable Long id) {
		return ResponseEntity.ok(service.buscarPorId(id));
	}

	@GetMapping("/buscarNome/{nome}")
	public ResponseEntity<Page<DadosDetalhamentoPessoaFisica>> buscarNome(@PathVariable String nome,
			@PageableDefault(size = 1000, sort = { "id" }) Pageable paginacao) {
		return ResponseEntity.ok(service.buscarPorNome(nome, paginacao));
	}

	@GetMapping("/buscarCpf/{cpf}")
	public ResponseEntity<Page<DadosDetalhamentoPessoaFisica>> buscarCpf(@PathVariable String cpf,
			@PageableDefault(size = 10, sort = { "id" }) Pageable paginacao) {
		return ResponseEntity.ok(service.buscarPorCpf(cpf, paginacao));
	}
	
	@GetMapping("/filtroCargo/{cargo}")
	public ResponseEntity<Page<DadosDetalhamentoPessoaFisica>> filtroCargo(@PathVariable String cargo,
			@PageableDefault(size = 10, sort = { "id" }) Pageable paginacao) {
		return ResponseEntity.ok(service.buscarPorCargo(cargo, paginacao));
	}

	@GetMapping("listar")
	public ResponseEntity<Page<DadosDetalhamentoPessoaFisica>> listar(@PageableDefault(size = 10) Pageable paginacao) {
		return ResponseEntity.ok(service.listar(paginacao));
	}

	@GetMapping("listarTodos")
	public ResponseEntity<Page<DadosDetalhamentoPessoaFisica>> listarTodos(
			@PageableDefault(size = 10) Pageable paginacao) {
		return ResponseEntity.ok(service.listarTodos(paginacao));
	}

	@PutMapping("atualizar")
	@Transactional
	public ResponseEntity<Optional<DadosDetalhamentoPessoaFisica>> atualizar(
			@RequestBody @Valid DadosAtualizarPessoaFisica dados) {
		return ResponseEntity.ok(service.atualizar(dados));
	}

	@PutMapping("atualizarStatus/{id}")
	@Transactional
	public ResponseEntity<HttpStatus> atualizar(@PathVariable Long id) {
		service.atualizarStatus(id);
		return ResponseEntity.status(HttpStatus.ACCEPTED).build();
	}

	@DeleteMapping("deletar/{id}")
	@Transactional
	public ResponseEntity<HttpStatus> deletar(@PathVariable Long id) {
		service.deletar(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
