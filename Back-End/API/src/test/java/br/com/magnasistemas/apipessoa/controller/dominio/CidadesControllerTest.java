package br.com.magnasistemas.apipessoa.controller.dominio;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.magnasistemas.apipessoa.DTO.Dominio.DadosCidade;
import br.com.magnasistemas.apipessoa.Repository.dominio.CidadeRepository;
import br.com.magnasistemas.apipessoa.Repository.dominio.EstadoRepository;
import br.com.magnasistemas.apipessoa.entity.dominio.Cidade;
import br.com.magnasistemas.apipessoa.entity.dominio.Estados;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")

class CidadesControllerTest {
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private EstadoRepository estadosRepository;

	@Autowired
	private CidadeRepository cidadeRepository;
	
	@AfterEach
	void finlizar(){
		cidadeRepository.deleteAllAndResetSequence();
		estadosRepository.deleteAll();
	}

	@Test
	@DisplayName("deve devolver codigo http 200 quando listar as cidade")
	void listarCidades() {
		Estados estado = new Estados();
		estado.setNome("São Paulo");
		estado.setRegiao("Sudeste");
		estado.setUf("Sp");
		estado.setId(1l);

		Cidade cidade = new Cidade();
		cidade.setNome("São Paulo");
		cidade.setEstado(estado);
		cidadeRepository.save(cidade);
		
		ResponseEntity<DadosCidade> response = restTemplate.getForEntity("/cidades",
				DadosCidade.class);
		assertTrue(response.getStatusCode().is2xxSuccessful());
	}

	 

}
