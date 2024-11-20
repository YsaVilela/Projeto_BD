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

import br.com.magnasistemas.apipessoa.DTO.PessoaJuridica.DadosAtualizarPessoaJuridica;
import br.com.magnasistemas.apipessoa.DTO.PessoaJuridica.DadosDetalhamentoPessoaJuridica;
import br.com.magnasistemas.apipessoa.DTO.PessoaJuridica.DadosPessoaJuridica;
import br.com.magnasistemas.apipessoa.service.PessoaJuridicaService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("pessoaJuridica")
@CrossOrigin(origins = "*", maxAge = 3600)
public class PessoaJuridicaController {

	@Autowired 
	private PessoaJuridicaService service;
	
	@PostMapping("cadastrar")
	public ResponseEntity<Optional<DadosDetalhamentoPessoaJuridica>> cadastrar(@RequestBody @Valid DadosPessoaJuridica dados) {
		return ResponseEntity.status(HttpStatus.CREATED).body(service.criarPessoaJuridica(dados));
	}
	
	@GetMapping ("/buscarId/{id}")
	public ResponseEntity<Optional<DadosDetalhamentoPessoaJuridica>> buscarId (@PathVariable Long id) {
		return ResponseEntity.ok(service.buscarPorId(id));
	}
	
	@GetMapping ("/buscarNome/{nome}")
	public ResponseEntity<Page<DadosDetalhamentoPessoaJuridica>> buscarNome (@PathVariable String nome,
			@PageableDefault(size = 10, sort = {"id"}) Pageable paginacao ) {
		return ResponseEntity.ok(service.buscarPorNome(nome, paginacao));
	}
	
	@GetMapping ("/buscarCnpj/{cnpj}")
	public ResponseEntity<Optional<DadosDetalhamentoPessoaJuridica>> buscarCnpj (@PathVariable String cnpj) {
		return ResponseEntity.ok(service.buscarPorCnpj(cnpj));
	}
	
	@GetMapping ("listar")
	public ResponseEntity<Page<DadosDetalhamentoPessoaJuridica>> listar (
			@PageableDefault(size = 10) Pageable paginacao) {
		return ResponseEntity.ok(service.listar(paginacao));
	}
	
	@GetMapping ("listarTodos")
	public ResponseEntity<Page<DadosDetalhamentoPessoaJuridica>> listarTodos (
			@PageableDefault(size = 10) Pageable paginacao) {
		return ResponseEntity.ok(service.listarTodos(paginacao));
	}
	
	@PutMapping ("atualizar")
	@Transactional
	public ResponseEntity<Optional<DadosDetalhamentoPessoaJuridica>> atualizar(@RequestBody @Valid DadosAtualizarPessoaJuridica dados){
		return ResponseEntity.ok(service.atualizar(dados)) ;
	}
	
	@PutMapping ("atualizarStatus/{id}")
	@Transactional
	public ResponseEntity<HttpStatus> atualizar(@PathVariable Long id){
		service.atualizarStatus(id);
		return ResponseEntity.status(HttpStatus.ACCEPTED).build();
	}
	
	@DeleteMapping ("deletar/{id}")
	@Transactional
	public ResponseEntity<HttpStatus> deletar(@PathVariable Long id){
		service.deletar(id); 
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
