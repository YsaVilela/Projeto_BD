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

import br.com.magnasistemas.apipessoa.DTO.Cargos.DadosAtualizarCargo;
import br.com.magnasistemas.apipessoa.DTO.Cargos.DadosCargo;
import br.com.magnasistemas.apipessoa.DTO.Cargos.DadosDetalhamentoCargo;
import br.com.magnasistemas.apipessoa.service.CargoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("cargo")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CargosController {

	@Autowired
	private CargoService service;

	@PostMapping("cadastrar")
	public ResponseEntity<Optional<DadosDetalhamentoCargo>> cadastrar(@RequestBody @Valid DadosCargo dados) {
		return ResponseEntity.status(HttpStatus.CREATED).body(service.criarCargo(dados));
	}

	@GetMapping("/buscarId/{id}")
	public ResponseEntity<Optional<DadosDetalhamentoCargo>> buscar(@PathVariable Long id) {
		return ResponseEntity.ok(service.buscarPorId(id));
	}

	@GetMapping("/buscarNome/{nome}")
	public ResponseEntity<Optional<DadosDetalhamentoCargo>> buscarNome(@PathVariable String nome) {
		return ResponseEntity.ok(service.buscarPorNome(nome));
	}

	@GetMapping("/buscarNomeDinamico/{nome}")
	public ResponseEntity<Page<DadosDetalhamentoCargo>> buscarNomeDinamico(@PathVariable String nome,
			@PageableDefault(size = 10) Pageable paginacao) {
		return ResponseEntity.ok(service.buscarPorNomeDinamico(nome, paginacao));
	}

	@GetMapping("listar")
	public ResponseEntity<Page<DadosDetalhamentoCargo>> listar(
			@PageableDefault(size = 10, sort = { "nome" }) Pageable paginacao) {
		return ResponseEntity.ok(service.listar(paginacao));
	}

	@PutMapping("atualizar")
	@Transactional
	public ResponseEntity<DadosDetalhamentoCargo> atualizar(@RequestBody @Valid DadosAtualizarCargo dados) {
		return ResponseEntity.ok(service.atualizar(dados));
	}

	@DeleteMapping("deletar/{id}")
	@Transactional
	public ResponseEntity<HttpStatus> deletar(@PathVariable Long id) {
		service.deletar(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
