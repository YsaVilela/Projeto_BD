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

import br.com.magnasistemas.apipessoa.DTO.Dominio.DadosEstados;
import br.com.magnasistemas.apipessoa.Repository.dominio.EstadoRepository;
import br.com.magnasistemas.apipessoa.entity.dominio.Estados;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class EstadosControllerTest {
	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private EstadoRepository estadosRepository;

	@AfterEach
	void finalizar() {
		estadosRepository.deleteAll();
	}

	@Test
	@DisplayName("deve devolver codigo http 200 quando listar os estados")
	void listarEstados() {
		Estados estado = new Estados();
		estado.setNome("São Paulo");
		estado.setRegiao("Sudeste");
		estado.setUf("Sp");
		estado.setId(1l);
		estadosRepository.save(estado);

		ResponseEntity<DadosEstados> response = restTemplate.getForEntity("/estados", DadosEstados.class);

		assertTrue(response.getStatusCode().is2xxSuccessful());

	}

}
