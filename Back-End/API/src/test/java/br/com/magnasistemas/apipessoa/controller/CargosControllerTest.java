package br.com.magnasistemas.apipessoa.controller;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.JsonNode;

import br.com.magnasistemas.apipessoa.DTO.Cargos.DadosAtualizarCargo;
import br.com.magnasistemas.apipessoa.DTO.Cargos.DadosCargo;
import br.com.magnasistemas.apipessoa.DTO.Cargos.DadosDetalhamentoCargo;
import br.com.magnasistemas.apipessoa.Repository.CargoRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CargosControllerTest {
	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private CargoRepository cargoRepository;
	
	void iniciarCargo() {
		DadosCargo dadosCargo = new DadosCargo("Cargo Teste", 1500.45);
		restTemplate.postForEntity("/cargo/cadastrar", dadosCargo, DadosDetalhamentoCargo.class);
	}

	@AfterEach
	void finalizar() {
		cargoRepository.deleteAllAndResetSequence();
	}
	
	@Test
	@DisplayName("Deve retornar um created quando criado com sucesso")
	void criarCargo() {
		DadosCargo dadosCargo = new DadosCargo("Cargo Teste", 2500.45);

		ResponseEntity<DadosDetalhamentoCargo> response = restTemplate.postForEntity("/cargo/cadastrar", dadosCargo,
				DadosDetalhamentoCargo.class);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}
	
	@ParameterizedTest
	@MethodSource("provideInvalidCargoData")
	@DisplayName("Deve retornar erro quando algum campo é nulo")
	void criarCargoComCamposNulos(String nome, Double remuneracao) {
		DadosCargo dadosCargo = new DadosCargo(nome, remuneracao);
		@SuppressWarnings("rawtypes")
		ResponseEntity<List> response = restTemplate.postForEntity("/cargo/cadastrar", dadosCargo, List.class);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	private static Stream<Object[]> provideInvalidCargoData() {
		return Stream.of(new Object[] { null, 1600.00 }, new Object[] { " ", 1600.00 }, new Object[] { "Cargo", null });
	} 

	@Test
	@DisplayName("Deve retornar erro quando criar dois cargos com o mesmo nome")
	void criarCargoNomeRepetido() {
		iniciarCargo();
		DadosCargo dadosCargo = new DadosCargo("Cargo Teste", 1500.45);

		ResponseEntity<JsonNode> response = restTemplate.postForEntity("/cargo/cadastrar", dadosCargo, JsonNode.class);

		assertTrue(response.getStatusCode().is4xxClientError());
	}

	@Test
	@DisplayName("Deve devolver codigo http 400 quando criar um cargo com o salario abaixo do minimo")
	void criarRemuneracaoInvalida() {
		DadosCargo dadosCargo = new DadosCargo("Cargo Teste", 150.00);

		ResponseEntity<JsonNode> response = restTemplate.postForEntity("/cargo/cadastrar", dadosCargo, JsonNode.class);

		assertTrue(response.getStatusCode().is4xxClientError());
	}

	@Test
	@DisplayName("Deve devolver codigo http 200 quando buscar um cargo por id")
	void listarCargosPorId() {
		iniciarCargo();
		ResponseEntity<DadosDetalhamentoCargo> response = restTemplate.getForEntity("/cargo/buscarId/1",
				DadosDetalhamentoCargo.class);

		assertTrue(response.getStatusCode().is2xxSuccessful());
	}
	
	@Test
	@DisplayName("Deve devolver codigo http 200 quando buscar um cargo por nome")
	void listarCargosPorNome() {
		iniciarCargo();
		ResponseEntity<DadosDetalhamentoCargo> response = restTemplate.getForEntity("/cargo/buscarNome/Teste",
				DadosDetalhamentoCargo.class);

		assertTrue(response.getStatusCode().is2xxSuccessful());
	}

	@Test
	@DisplayName("Deve retornar um erro quando buscar um cargo por um id inválido")
	void listarCargoPorIdInválido() {

		ResponseEntity<JsonNode> response = restTemplate.getForEntity("/cargo/buscarId/1", JsonNode.class);

		assertTrue(response.getStatusCode().is4xxClientError());
	}
	
	@Test
	@DisplayName("Deve retornar um erro quando buscar um cargo por um nome inválido")
	void listarCargoPorNomeInválido() {

		ResponseEntity<JsonNode> response = restTemplate.getForEntity("/cargo/buscarNome/invalido", JsonNode.class);

		assertTrue(response.getStatusCode().is4xxClientError());
	}

	@Test
	@DisplayName("Deve retornar codigo http 200 quando listar um cargo")
	void listarCargos() {
		iniciarCargo();
		ResponseEntity<DadosDetalhamentoCargo> response = restTemplate.getForEntity("/cargo/listar",
				DadosDetalhamentoCargo.class);

		assertTrue(response.getStatusCode().is2xxSuccessful());
	}

	@Test
	@DisplayName("Deve devolver codigo http 200 quando atualizar um cargo")
	void atualizarCargo() {
		iniciarCargo();

		DadosAtualizarCargo dadosAtualizarCargo = new DadosAtualizarCargo(1L, "Cargo Atualizar", 1600.21);

		ResponseEntity<DadosDetalhamentoCargo> response = restTemplate.exchange("/cargo/atualizar", HttpMethod.PUT,
				new HttpEntity<>(dadosAtualizarCargo), DadosDetalhamentoCargo.class);

		assertTrue(response.getStatusCode().is2xxSuccessful());
	}
	
	@Test
	@DisplayName("Deve devolver erro quando atualizar um cargo para um que ja possua o mesmo nome")
	void atualizarCargoComNomeInvalido() { 
		iniciarCargo();
		
		DadosCargo dadosCargo = new DadosCargo("Cargo", 1500.45);
		restTemplate.postForEntity("/cargo/cadastrar", dadosCargo, DadosDetalhamentoCargo.class);

		DadosAtualizarCargo dadosAtualizarCargo = new DadosAtualizarCargo(2L, "Cargo Teste", 1600.21);

		ResponseEntity<JsonNode> response = restTemplate.exchange("/cargo/atualizar", HttpMethod.PUT,
				new HttpEntity<>(dadosAtualizarCargo), JsonNode.class);

		assertTrue(response.getStatusCode().is4xxClientError());
	}

	@Test
	@DisplayName("Deve retornar um erro quando atualizar com um id inválido")
	void atualizarCargoInvalido() {
		DadosAtualizarCargo dadosAtualizarCargo = new DadosAtualizarCargo(1L, "Cargo Atualizar", 1600.21);

		ResponseEntity<JsonNode> response = restTemplate.exchange("/cargo/atualizar", HttpMethod.PUT,
				new HttpEntity<>(dadosAtualizarCargo), JsonNode.class);

		assertTrue(response.getStatusCode().is4xxClientError());
	}

	@Test
	@DisplayName("Deve devolver codigo http 200 quando atualizar um cargo com remuneracao reduzida")
	void atualizarCargoComRemunacaoMenor() {
		iniciarCargo();
		DadosAtualizarCargo dadosAtualizarCargo = new DadosAtualizarCargo(1L, "Cargo Atualizar", 1400.21);

		ResponseEntity<JsonNode> response = restTemplate.exchange("/cargo/atualizar", HttpMethod.PUT,
				new HttpEntity<>(dadosAtualizarCargo), JsonNode.class);

		assertTrue(response.getStatusCode().is4xxClientError());
	}

	@Test
	@DisplayName("Deve retornar um 204 quando deletar")
	void deletaCargo() {
		iniciarCargo();

		ResponseEntity<DadosDetalhamentoCargo> response = restTemplate.exchange("/cargo/deletar/1", HttpMethod.DELETE,
				null, DadosDetalhamentoCargo.class);
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
	}
	
}
