package br.com.magnasistemas.apipessoa.controller.dominio;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.magnasistemas.apipessoa.DTO.Dominio.DadosCidade;
import br.com.magnasistemas.apipessoa.Repository.dominio.CidadeRepository;


@RestController
@RequestMapping ("cidades")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CidadesController {
	
	@Autowired
	private CidadeRepository repository;
	
	@GetMapping
	public ResponseEntity<Page<DadosCidade>> listar (
			@PageableDefault(size = 10, sort = {"id"}) Pageable paginacao) {
		Page<DadosCidade> listagemDeCidades = repository.findAll(paginacao).map(DadosCidade::new);
		return ResponseEntity.ok(listagemDeCidades);
	} 
	
	@GetMapping ("/buscarCidadePorEstado/{siglaEstado}")
	public ResponseEntity<Page<DadosCidade>> listarCidadePorEstado ( @PathVariable String siglaEstado,
			@PageableDefault(size = 3000, sort = {"nome"}) Pageable paginacao) {
		Page<DadosCidade> listagemDeCidades = repository.findByNomeEstado(siglaEstado, paginacao).map(DadosCidade::new);
		return ResponseEntity.ok(listagemDeCidades);
	} 
	
	@GetMapping ("/{siglaEstado}/{nomeCidade}")
	public ResponseEntity<Optional<DadosCidade>> buscarPorNomeEstado (@PathVariable String siglaEstado, @PathVariable String nomeCidade) {
		Optional<DadosCidade> cidade = repository.findByNome(nomeCidade, siglaEstado).map(DadosCidade::new);
		return ResponseEntity.ok(cidade);
	} 
	
 
} 