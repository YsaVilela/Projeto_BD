package br.com.magnasistemas.apipessoa.controller;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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

import br.com.magnasistemas.apipessoa.DTO.Endereco.DadosAtualizarEndereco;
import br.com.magnasistemas.apipessoa.DTO.Endereco.DadosEndereco;
import br.com.magnasistemas.apipessoa.DTO.Pessoa.DadosAtualizarPessoa;
import br.com.magnasistemas.apipessoa.DTO.Pessoa.DadosPessoa;
import br.com.magnasistemas.apipessoa.DTO.PessoaFisica.DadosAtualizarPessoaFisica;
import br.com.magnasistemas.apipessoa.DTO.PessoaFisica.DadosDetalhamentoPessoaFisica;
import br.com.magnasistemas.apipessoa.DTO.PessoaFisica.DadosPessoaFisica;
import br.com.magnasistemas.apipessoa.Repository.CargoRepository;
import br.com.magnasistemas.apipessoa.Repository.EnderecoRepository;
import br.com.magnasistemas.apipessoa.Repository.PessoaFisicaRepository;
import br.com.magnasistemas.apipessoa.Repository.PessoaRepository;
import br.com.magnasistemas.apipessoa.Repository.dominio.CidadeRepository;
import br.com.magnasistemas.apipessoa.Repository.dominio.EstadoRepository;
import br.com.magnasistemas.apipessoa.entity.Cargo;
import br.com.magnasistemas.apipessoa.entity.dominio.Cidade;
import br.com.magnasistemas.apipessoa.entity.dominio.Estados;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class PessoaFisicaControllerTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private PessoaFisicaRepository pessoaFisicaRepository;

	@Autowired
	private PessoaRepository pessoaRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private EstadoRepository estadoRepository;

	@Autowired
	private CargoRepository cargoRepository;

	Cargo iniciarCargo() {
		Cargo cargo = new Cargo();
		cargo.setNome("cargo teste");
		cargo.setRemuneracao(1.0);
		cargoRepository.save(cargo);
		return cargo;
	}

	Cidade iniciarCidade() {
		Estados estado = new Estados();
		estado.setNome("São Paulo");
		estado.setRegiao("Sudeste");
		estado.setUf("Sp");
		estado.setId(31l);

		Cidade cidade = new Cidade();
		cidade.setNome("São Paulo");
		cidade.setEstado(estado);
		cidadeRepository.save(cidade);
		return cidade;
	}

	@BeforeEach
	void iniciar() {
		iniciarCargo();
	}

	@AfterEach
	void finalizar() {
		pessoaFisicaRepository.deleteAllAndResetSequence();
		pessoaRepository.deleteAllAndResetSequence();
		enderecoRepository.deleteAllAndResetSequence();
		cidadeRepository.deleteAllAndResetSequence();
		estadoRepository.deleteAll();
		cargoRepository.deleteAllAndResetSequence();
	}

	void iniciarPessoaFisica() {
		Cidade cidade = iniciarCidade();
		Cargo cargo = iniciarCargo();
		
		DadosEndereco endereco = new DadosEndereco("01234567", "Logradouro", 1L, "A", cidade);
		DadosPessoa pessoa = new DadosPessoa("nomeTeste", "01/02/2024", "teste@magna.com", "11010341234", endereco);
		DadosPessoaFisica dadosPessoaFisica = new DadosPessoaFisica(pessoa, "12345678901", cargo);
		restTemplate.postForEntity("/pessoaFisica/cadastrar", dadosPessoaFisica, DadosDetalhamentoPessoaFisica.class);
	}

	@Test
	@DisplayName("Deve retornar um created quando criado com sucesso")
	void criarPessoaFisica() {
		Cidade cidade = iniciarCidade();
		Cargo cargo = iniciarCargo();

		DadosEndereco endereco = new DadosEndereco("01234567", "Logradouro", 1L, "A", cidade);
		DadosPessoa pessoa = new DadosPessoa("nomeTeste", "01/02/2024", "teste@magna.com", "11010341234", endereco);
		DadosPessoaFisica dadosPessoaFisica = new DadosPessoaFisica(pessoa, "12345678901", cargo);

		ResponseEntity<DadosDetalhamentoPessoaFisica> response = restTemplate.postForEntity("/pessoaFisica/cadastrar",
				dadosPessoaFisica, DadosDetalhamentoPessoaFisica.class);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}

	@Test
	@DisplayName("Deve retornar um erro quando criar com telefone existente")
	void criarPessoaFisicaTefoneJaExistente() {
		iniciarPessoaFisica();
		Cidade cidade = iniciarCidade();
		Cargo cargo = iniciarCargo();

		DadosEndereco endereco = new DadosEndereco("01234567", "Logradouro", 1L, "A", cidade);
		DadosPessoa pessoa = new DadosPessoa("nomeTeste", "01/02/2024", "teste@magna.com", "11010341234", endereco);
		DadosPessoaFisica dadosPessoaFisica = new DadosPessoaFisica(pessoa, "12345678901", cargo);

		ResponseEntity<JsonNode> response = restTemplate.postForEntity("/pessoaFisica/cadastrar", dadosPessoaFisica,
				JsonNode.class);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	@Test
	@DisplayName("Deve retornar um erro quando criar com cidadde inexistente")
	void criarPessoaFisicaCidadeInexistente() {
		Cidade cidade = iniciarCidade();
		Cargo cargo = iniciarCargo();

		DadosEndereco endereco = new DadosEndereco("01234567", "Logradouro", 1L, "A", cidade);
		DadosPessoa pessoa = new DadosPessoa("nomeTeste", "01/02/2024", "teste@magna.com", "11010341234", endereco);
		DadosPessoaFisica dadosPessoaFisica = new DadosPessoaFisica(pessoa, "12345678901", cargo);

		ResponseEntity<JsonNode> response = restTemplate.postForEntity("/pessoaFisica/cadastrar", dadosPessoaFisica,
				JsonNode.class);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	@Test
	@DisplayName("Deve retornar um erro quando criar com cargo inexistente")
	void criarPessoaFisicaCargoInexistente() {
		cargoRepository.deleteAllAndResetSequence();
		Cidade cidade = iniciarCidade();
		Cargo cargo = iniciarCargo();

		DadosEndereco endereco = new DadosEndereco("01234567", "Logradouro", 1L, "A", cidade);
		DadosPessoa pessoa = new DadosPessoa("nomeTeste", "01/02/2024", "teste@magna.com", "11010341234", endereco);
		DadosPessoaFisica dadosPessoaFisica = new DadosPessoaFisica(pessoa, "12345678901", cargo);

		ResponseEntity<JsonNode> response = restTemplate.postForEntity("/pessoaFisica/cadastrar", dadosPessoaFisica,
				JsonNode.class);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	@Test
	@DisplayName("Deve retornar codigo http 200 quando listar pessoas ativas")
	void listarPessoasFisicasAtivas() {
		iniciarPessoaFisica();
		ResponseEntity<DadosDetalhamentoPessoaFisica> response = restTemplate.getForEntity("/pessoaFisica/listar",
				DadosDetalhamentoPessoaFisica.class);

		assertTrue(response.getStatusCode().is2xxSuccessful());
	}

	@Test
	@DisplayName("Deve retornar codigo http 200 quando listar todas Pessoas")
	void listarPessoasFisicas() {
		iniciarPessoaFisica();
		ResponseEntity<DadosDetalhamentoPessoaFisica> response = restTemplate.getForEntity("/pessoaFisica/listarTodos",
				DadosDetalhamentoPessoaFisica.class);

		assertTrue(response.getStatusCode().is2xxSuccessful());
	}

	@Test
	@DisplayName("Deve devolver codigo http 200 quando buscar por id")
	void listarPessoasFisicPorId() {
		iniciarPessoaFisica();
		ResponseEntity<DadosDetalhamentoPessoaFisica> response = restTemplate.getForEntity("/pessoaFisica/buscarId/1",
				DadosDetalhamentoPessoaFisica.class);

		assertTrue(response.getStatusCode().is2xxSuccessful());
	}

	@Test
	@DisplayName("Deve retornar um erro quando buscar por um id inválido")
	void listarPessoasFisicaPorIdInválido() {

		ResponseEntity<JsonNode> response = restTemplate.getForEntity("/pessoaFisica/buscarId/1", JsonNode.class);

		assertTrue(response.getStatusCode().is4xxClientError());
	}
	
	@Test
	@DisplayName("Deve devolver codigo http 200 quando buscar por nome")
	void listarPessoasFisicPorNome() {
		iniciarPessoaFisica();
		ResponseEntity<DadosDetalhamentoPessoaFisica> response = restTemplate.getForEntity("/pessoaFisica/buscarNome/Teste",
				DadosDetalhamentoPessoaFisica.class);

		assertTrue(response.getStatusCode().is2xxSuccessful());
	}
	
	@Test
	@DisplayName("Deve retornar um erro quando buscar por um nome inválido")
	void listarPessoasFisicaPorNomeInválido() {

		ResponseEntity<JsonNode> response = restTemplate.getForEntity("/pessoaFisica/buscarNome/invalido", JsonNode.class);

		assertTrue(response.getStatusCode().is4xxClientError());
	}
	
	@Test
	@DisplayName("Deve devolver codigo http 200 quando buscar por cpf")
	void listarPessoasFisicPorCpf() {
		iniciarPessoaFisica();
		ResponseEntity<DadosDetalhamentoPessoaFisica> response = restTemplate.getForEntity("/pessoaFisica/buscarCpf/12345678901",
				DadosDetalhamentoPessoaFisica.class);

		assertTrue(response.getStatusCode().is2xxSuccessful());
	}
	
	@Test
	@DisplayName("Deve retornar um erro quando buscar por um cpf inválido")
	void listarPessoasFisicaPorCpfInválido() {

		ResponseEntity<JsonNode> response = restTemplate.getForEntity("/pessoaFisica/buscarCpf/1", JsonNode.class);

		assertTrue(response.getStatusCode().is4xxClientError());
	}

	@Test
	@DisplayName("Deve devolver codigo http 200 quando atualizar")
	void atualizarPessoaFisica() {
		iniciarPessoaFisica();
		Cidade cidade = iniciarCidade();
		Cargo cargo = iniciarCargo();

		DadosAtualizarEndereco endereco = new DadosAtualizarEndereco("01234567", "Logradouro", 1L, "A", cidade);
		DadosAtualizarPessoa pessoa = new DadosAtualizarPessoa("nomeTeste", "01/02/2024", "teste@magna.com",
				"11123456789", endereco);

		DadosAtualizarPessoaFisica dadosAtualizarPessoaFisica = new DadosAtualizarPessoaFisica(1L, "01234567891",
				pessoa, cargo);

		ResponseEntity<DadosDetalhamentoPessoaFisica> response = restTemplate.exchange("/pessoaFisica/atualizar",
				HttpMethod.PUT, new HttpEntity<>(dadosAtualizarPessoaFisica), DadosDetalhamentoPessoaFisica.class);

		assertTrue(response.getStatusCode().is2xxSuccessful());
	}

	@Test
	@DisplayName("Deve devolver erro quando atualizar e ja exista o mesmo telefone")
	void atualizarPessoaComTelefoneInvalido() {
		iniciarPessoaFisica();
		Cidade cidade = iniciarCidade();
		Cargo cargo = iniciarCargo();

		DadosEndereco endereco = new DadosEndereco("01234567", "Logradouro", 1L, "A", cidade);
		DadosPessoa pessoa = new DadosPessoa("nomeTeste", "01/02/2024", "teste@magna.com", "11010341235", endereco);
		DadosPessoaFisica dadosPessoaFisica = new DadosPessoaFisica(pessoa, "12345678905", cargo);
		restTemplate.postForEntity("/pessoaFisica/cadastrar", dadosPessoaFisica, DadosDetalhamentoPessoaFisica.class);

		DadosAtualizarEndereco enderecoAtualizado = new DadosAtualizarEndereco("01234567", "Logradouro", 1L, "A", cidade);
		DadosAtualizarPessoa pessoaAtualizada = new DadosAtualizarPessoa("nomeTeste", "01/01/2000", "teste@magna.com",
				"11010341234", enderecoAtualizado);
		DadosAtualizarPessoaFisica dadosAtualizarPessoaFisica = new DadosAtualizarPessoaFisica(1L, "12345678905",
				pessoaAtualizada, cargo);

		ResponseEntity<JsonNode> response = restTemplate.exchange("/pessoaFisica/atualizar", HttpMethod.PUT,
				new HttpEntity<>(dadosAtualizarPessoaFisica), JsonNode.class);

		assertTrue(response.getStatusCode().is4xxClientError());
	}

	@Test
	@DisplayName("Deve devolver erro quando atualizar e ja exista o mesmo cpf")
	void atualizarPessoaComCpfInvalido() {
		iniciarPessoaFisica();
		Cidade cidade = iniciarCidade();
		Cargo cargo = iniciarCargo();

		DadosEndereco endereco = new DadosEndereco("01234567", "Logradouro", 1L, "A", cidade);
		DadosPessoa pessoa = new DadosPessoa("nomeTeste", "01/02/2024", "teste@magna.com", "11010341235", endereco);
		DadosPessoaFisica dadosPessoaFisica = new DadosPessoaFisica(pessoa, "12345678905", cargo);
		restTemplate.postForEntity("/pessoaFisica/cadastrar", dadosPessoaFisica, DadosDetalhamentoPessoaFisica.class);

		DadosAtualizarEndereco enderecoAtualizado = new DadosAtualizarEndereco("01234567", "Logradouro", 1L, "A", cidade);
		DadosAtualizarPessoa pessoaAtualizada = new DadosAtualizarPessoa("nomeTeste", "01/01/2000", "teste@magna.com",
				"11010341235", enderecoAtualizado);
		DadosAtualizarPessoaFisica dadosAtualizarPessoaFisica = new DadosAtualizarPessoaFisica(1L, "12345678905",
				pessoaAtualizada, cargo);

		ResponseEntity<JsonNode> response = restTemplate.exchange("/pessoaFisica/atualizar", HttpMethod.PUT,
				new HttpEntity<>(dadosAtualizarPessoaFisica), JsonNode.class);

		assertTrue(response.getStatusCode().is4xxClientError());
	}

	@Test
	@DisplayName("Deve retornar um erro quando atualizar com pessoa desativada")
	void atualizarPessoaFisicaDesativada() {
		iniciarPessoaFisica();
		Cidade cidade = iniciarCidade();
		Cargo cargo = iniciarCargo();

		restTemplate.exchange("/pessoaFisica/atualizarStatus/1", HttpMethod.PUT, null,
				DadosDetalhamentoPessoaFisica.class);

		DadosAtualizarEndereco endereco = new DadosAtualizarEndereco("01234567", "Logradouro", 1L, "A", cidade);
		DadosAtualizarPessoa pessoa = new DadosAtualizarPessoa("nomeTeste", "00/00/0000", "teste@magna.com",
				"11123456779", endereco);

		DadosAtualizarPessoaFisica dadosAtualizarPessoaFisica = new DadosAtualizarPessoaFisica(1L, "01234567890",
				pessoa, cargo);

		ResponseEntity<JsonNode> response = restTemplate.exchange("/pessoaFisica/atualizar", HttpMethod.PUT,
				new HttpEntity<>(dadosAtualizarPessoaFisica), JsonNode.class);

		assertTrue(response.getStatusCode().is4xxClientError());
	}

	@Test
	@DisplayName("Deve retornar um erro quando atualizar com um id inválido")
	void atualizarPessoaFisicaInvalido() {
		Cidade cidade = iniciarCidade();
		Cargo cargo = iniciarCargo();

		DadosAtualizarEndereco endereco = new DadosAtualizarEndereco("01234567", "Logradouro", 1L, "A", cidade);
		DadosAtualizarPessoa pessoa = new DadosAtualizarPessoa("nomeTeste", "00/00/0000", "teste@magna.com",
				"11123456789", endereco);

		DadosAtualizarPessoaFisica dadosAtualizarPessoaFisica = new DadosAtualizarPessoaFisica(1L, "01234567891",
				pessoa, cargo);

		ResponseEntity<JsonNode> response = restTemplate.exchange("/pessoaFisica/atualizar", HttpMethod.PUT,
				new HttpEntity<>(dadosAtualizarPessoaFisica), JsonNode.class);

		assertTrue(response.getStatusCode().is4xxClientError());
	}

	@Test
	@DisplayName("Deve retornar um 204 quando deletar")
	void deletaPessoaFisica() {
		iniciarPessoaFisica();

		ResponseEntity<DadosDetalhamentoPessoaFisica> response = restTemplate.exchange("/pessoaFisica/deletar/1",
				HttpMethod.DELETE, null, DadosDetalhamentoPessoaFisica.class);
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
	}

	@Test
	@DisplayName("Deve retornar um 204 quando atualizar status")
	void atualizarStatusPessoaFisica() {
		iniciarPessoaFisica();

		ResponseEntity<DadosDetalhamentoPessoaFisica> response = restTemplate
				.exchange("/pessoaFisica/atualizarStatus/1", HttpMethod.PUT, null, DadosDetalhamentoPessoaFisica.class);
		assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
	}

}
