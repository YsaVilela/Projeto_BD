package br.com.magnasistemas.apipessoa.controller.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.magnasistemas.apipessoa.DTO.Dominio.DadosEstados;
import br.com.magnasistemas.apipessoa.Repository.dominio.EstadoRepository;


@RestController
@RequestMapping ("estados")
@CrossOrigin(origins = "*", maxAge = 3600)
public class EstadosController {
	
	@Autowired
	private EstadoRepository repository;
	
	@GetMapping
	public ResponseEntity<Page<DadosEstados>> listar (
			@PageableDefault(size = 27, sort = {"uf"}) Pageable paginacao) {
		Page<DadosEstados> listagemDeEstados = repository.findAll(paginacao).map(DadosEstados::new);
		return ResponseEntity.ok(listagemDeEstados);
		
	}
} 