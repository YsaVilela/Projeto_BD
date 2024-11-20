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
import br.com.magnasistemas.apipessoa.DTO.PessoaJuridica.DadosAtualizarPessoaJuridica;
import br.com.magnasistemas.apipessoa.DTO.PessoaJuridica.DadosDetalhamentoPessoaJuridica;
import br.com.magnasistemas.apipessoa.DTO.PessoaJuridica.DadosPessoaJuridica;
import br.com.magnasistemas.apipessoa.Repository.EnderecoRepository;
import br.com.magnasistemas.apipessoa.Repository.PessoaJuridicaRepository;
import br.com.magnasistemas.apipessoa.Repository.PessoaRepository;
import br.com.magnasistemas.apipessoa.Repository.dominio.CidadeRepository;
import br.com.magnasistemas.apipessoa.Repository.dominio.EstadoRepository;
import br.com.magnasistemas.apipessoa.entity.dominio.Cidade;
import br.com.magnasistemas.apipessoa.entity.dominio.Estados;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class PessoaJuridicaControllerTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private PessoaJuridicaRepository pessoaJuridicaRepository;

	@Autowired
	private PessoaRepository pessoaRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private EstadoRepository estadoRepository;

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
		iniciarCidade();
	}

	@AfterEach
	void finalizar() {
		pessoaJuridicaRepository.deleteAllAndResetSequence();
		pessoaRepository.deleteAllAndResetSequence();
		enderecoRepository.deleteAllAndResetSequence();
		cidadeRepository.deleteAllAndResetSequence();
		estadoRepository.deleteAll();
	}

	void iniciarPessoaJuridica() {
		Cidade cidade = iniciarCidade();

		DadosEndereco endereco = new DadosEndereco("01234567", "Logradouro", 1L, "A", cidade);
		DadosPessoa pessoa = new DadosPessoa("nomeTeste", "01/02/2024", "teste@magna.com", "11010341234", endereco);
		DadosPessoaJuridica dadosPessoaJuridica = new DadosPessoaJuridica(pessoa, "12345678901234");
		restTemplate.postForEntity("/pessoaJuridica/cadastrar", dadosPessoaJuridica,
				DadosDetalhamentoPessoaJuridica.class);
	}

	@Test
	@DisplayName("Deve retornar um created quando criado com sucesso")
	void criarPessoaJuridica() {
		Cidade cidade = iniciarCidade();

		DadosEndereco endereco = new DadosEndereco("01234567", "Logradouro", 1L, "A", cidade);
		DadosPessoa pessoa = new DadosPessoa("nomeTeste", "01/02/2024", "teste@magna.com", "11123456789", endereco);
		DadosPessoaJuridica dadosPessoaJuridica = new DadosPessoaJuridica(pessoa, "12345678901234");

		ResponseEntity<DadosDetalhamentoPessoaJuridica> response = restTemplate
				.postForEntity("/pessoaJuridica/cadastrar", dadosPessoaJuridica, DadosDetalhamentoPessoaJuridica.class);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}

	@Test
	@DisplayName("Deve retornar um erro quando criar com cnpj ja existente")
	void criarPessoasJuridicaComCnpjExistente() {
		iniciarPessoaJuridica();
		Cidade cidade = iniciarCidade();

		DadosEndereco endereco = new DadosEndereco("01234567", "Logradouro", 1L, "A", cidade);
		DadosPessoa pessoa = new DadosPessoa("nomeTeste", "01/02/2024", "teste@magna.com", "11010341235", endereco);
		DadosPessoaJuridica dadosPessoaJuridica = new DadosPessoaJuridica(pessoa, "12345678901234");

		ResponseEntity<JsonNode> response = restTemplate.postForEntity("/pessoaJuridica/cadastrar", dadosPessoaJuridica,
				JsonNode.class); 

		assertTrue(response.getStatusCode().is4xxClientError());
	}
	
	@Test
	@DisplayName("Deve retornar um erro quando criar com telefone ja existente")
	void criarPessoasJuridicaComTelefoneExistente() {
		iniciarPessoaJuridica();
		Cidade cidade = iniciarCidade();

		DadosEndereco endereco = new DadosEndereco("01234567", "Logradouro", 1L, "A", cidade);
		DadosPessoa pessoa = new DadosPessoa("nomeTeste", "01/02/2024", "teste@magna.com", "11010341234", endereco);
		DadosPessoaJuridica dadosPessoaJuridica = new DadosPessoaJuridica(pessoa, "12345678901230");


		ResponseEntity<JsonNode> response = restTemplate.postForEntity("/pessoaJuridica/cadastrar", dadosPessoaJuridica,
				JsonNode.class); 

		assertTrue(response.getStatusCode().is4xxClientError());
	}

	@Test
	@DisplayName("Deve retornar codigo http 200 quando listar pessoas ativas")
	void listarPessoasJuridicasAtivas() {
		iniciarPessoaJuridica();
		ResponseEntity<DadosDetalhamentoPessoaJuridica> response = restTemplate.getForEntity("/pessoaJuridica/listar",
				DadosDetalhamentoPessoaJuridica.class);

		assertTrue(response.getStatusCode().is2xxSuccessful());
	}

	@Test
	@DisplayName("Deve retornar codigo http 200 quando listar todos")
	void listarTodasPessoasJuridicas() {
		iniciarPessoaJuridica();
		ResponseEntity<DadosDetalhamentoPessoaJuridica> response = restTemplate
				.getForEntity("/pessoaJuridica/listarTodos", DadosDetalhamentoPessoaJuridica.class);

		assertTrue(response.getStatusCode().is2xxSuccessful());
	}

	@Test
	@DisplayName("Deve devolver codigo http 200 quando buscar por id")
	void listarPessoasJuridicaPorId() {
		iniciarPessoaJuridica();
		ResponseEntity<DadosDetalhamentoPessoaJuridica> response = restTemplate.getForEntity("/pessoaJuridica/buscarId/1",
				DadosDetalhamentoPessoaJuridica.class);

		assertTrue(response.getStatusCode().is2xxSuccessful());
	}

	@Test
	@DisplayName("Deve retornar um erro quando buscar por um id inválido")
	void listarPessoasJuridicaPorIdInválido() {

		ResponseEntity<JsonNode> response = restTemplate.getForEntity("/pessoaJuridica/buscarId/1", JsonNode.class);

		assertTrue(response.getStatusCode().is4xxClientError());
	}
	
	@Test
	@DisplayName("Deve devolver codigo http 200 quando buscar por nome")
	void listarPessoasJuridicaPorNome() {
		iniciarPessoaJuridica();
		ResponseEntity<DadosDetalhamentoPessoaJuridica> response = restTemplate.getForEntity("/pessoaJuridica/buscarNome/Teste",
				DadosDetalhamentoPessoaJuridica.class);

		assertTrue(response.getStatusCode().is2xxSuccessful());
	}
	
	@Test
	@DisplayName("Deve retornar um erro quando buscar por um nome inválido")
	void listarPessoasJuridicaPorNomeInválido() {

		ResponseEntity<JsonNode> response = restTemplate.getForEntity("/pessoaJuridica/buscarNome/teste", JsonNode.class);

		assertTrue(response.getStatusCode().is4xxClientError());
	}
	
	@Test
	@DisplayName("Deve devolver codigo http 200 quando buscar por cnpj")
	void listarPessoasJuridicaPorCnpj() {
		iniciarPessoaJuridica();
		ResponseEntity<DadosDetalhamentoPessoaJuridica> response = restTemplate.getForEntity("/pessoaJuridica/buscarCnpj/12345678901234",
				DadosDetalhamentoPessoaJuridica.class);

		assertTrue(response.getStatusCode().is2xxSuccessful());
	}
	
	@Test
	@DisplayName("Deve retornar um erro quando buscar por um cnpj inválido")
	void listarPessoasJuridicaPorCnpjInválido() {

		ResponseEntity<JsonNode> response = restTemplate.getForEntity("/pessoaJuridica/buscarCnpj/1", JsonNode.class);

		assertTrue(response.getStatusCode().is4xxClientError());
	}

	@Test
	@DisplayName("Deve devolver codigo http 200 quando atualizar")
	void atualizarPessoaJuridica() {
		iniciarPessoaJuridica();
		Cidade cidade = iniciarCidade();

		DadosAtualizarEndereco endereco = new DadosAtualizarEndereco("01234567", "Logradouro", 1L, "A", cidade);
		DadosAtualizarPessoa pessoa = new DadosAtualizarPessoa("nomeTeste", "01/02/2024", "teste@magna.com",
				"11123456789", endereco);

		DadosAtualizarPessoaJuridica dadosAtualizarPessoaJuridica = new DadosAtualizarPessoaJuridica(1L,
				"01234567891123", pessoa);

		ResponseEntity<JsonNode> response = restTemplate.exchange("/pessoaJuridica/atualizar", HttpMethod.PUT,
				new HttpEntity<>(dadosAtualizarPessoaJuridica), JsonNode.class);

		assertTrue(response.getStatusCode().is2xxSuccessful());
	}

	@Test
	@DisplayName("Deve devolver erro quando atualizar e ja exista o mesmo telefone")
	void atualizarPessoaComTelefoneExistente() {
		iniciarPessoaJuridica();
		Cidade cidade = iniciarCidade();

		DadosEndereco endereco = new DadosEndereco("01234567", "Logradouro", 1L, "A", cidade);
		DadosPessoa pessoa = new DadosPessoa("nomeTeste", "01/02/2024", "teste@magna.com", "11010341235", endereco);
		DadosPessoaJuridica dadosPessoaJuridica = new DadosPessoaJuridica(pessoa, "12345678901235");
		restTemplate.postForEntity("/pessoaJuridica/cadastrar", dadosPessoaJuridica,
				DadosDetalhamentoPessoaJuridica.class);

		DadosAtualizarEndereco enderecoAtualizado = new DadosAtualizarEndereco("01234567", "Logradouro", 1L, "A", cidade);
		DadosAtualizarPessoa pessoaAtualizada = new DadosAtualizarPessoa("nomeTeste", "01/01/2000", "teste@magna.com",
				"11010341234", enderecoAtualizado);
		DadosAtualizarPessoaJuridica dadosAtualizarPessoaJuridica = new DadosAtualizarPessoaJuridica(1L,
				"12345678901235", pessoaAtualizada);

		ResponseEntity<JsonNode> response = restTemplate.exchange("/pessoaJuridica/atualizar", HttpMethod.PUT,
				new HttpEntity<>(dadosAtualizarPessoaJuridica), JsonNode.class);

		assertTrue(response.getStatusCode().is4xxClientError());
	}
	
	@Test
	@DisplayName("Deve devolver erro quando atualizar e ja exista o mesmo cnpj")
	void atualizarPessoaComCnpjExistente() {
		iniciarPessoaJuridica();
		Cidade cidade = iniciarCidade();

		DadosEndereco endereco = new DadosEndereco("01234567", "Logradouro", 1L, "A", cidade);
		DadosPessoa pessoa = new DadosPessoa("nomeTeste", "01/02/2024", "teste@magna.com", "11010341235", endereco);
		DadosPessoaJuridica dadosPessoaJuridica = new DadosPessoaJuridica(pessoa, "12345678901235");
		restTemplate.postForEntity("/pessoaJuridica/cadastrar", dadosPessoaJuridica,
				DadosDetalhamentoPessoaJuridica.class);

		DadosAtualizarEndereco enderecoAtualizado = new DadosAtualizarEndereco("01234567", "Logradouro", 1L, "A", cidade);
		DadosAtualizarPessoa pessoaAtualizada = new DadosAtualizarPessoa("nomeTeste", "01/01/2000", "teste@magna.com",
				"11010341235", enderecoAtualizado);
		DadosAtualizarPessoaJuridica dadosAtualizarPessoaJuridica = new DadosAtualizarPessoaJuridica(1L,
				"12345678901234", pessoaAtualizada);

		ResponseEntity<JsonNode> response = restTemplate.exchange("/pessoaJuridica/atualizar", HttpMethod.PUT,
				new HttpEntity<>(dadosAtualizarPessoaJuridica), JsonNode.class);

		assertTrue(response.getStatusCode().is4xxClientError());
	}

	@Test
	@DisplayName("Deve retornar um erro quando atualizar com um id inválido")
	void atualizarPessoaJuridicaComIdInvalido() {
		Cidade cidade = iniciarCidade();

		DadosAtualizarEndereco endereco = new DadosAtualizarEndereco("01234567", "Logradouro", 1L, "A", cidade);
		DadosAtualizarPessoa pessoa = new DadosAtualizarPessoa("nomeTeste", "01/01/2000", "teste@magna.com",
				"11123456789", endereco);

		DadosAtualizarPessoaJuridica dadosAtualizarPessoaJuridica = new DadosAtualizarPessoaJuridica(1L,
				"01234567891123", pessoa);

		ResponseEntity<JsonNode> response = restTemplate.exchange("/pessoaJuridica/atualizar", HttpMethod.PUT,
				new HttpEntity<>(dadosAtualizarPessoaJuridica), JsonNode.class);

		assertTrue(response.getStatusCode().is4xxClientError());
	}

	@Test
	@DisplayName("Deve retornar um erro quando atualizar com pessoa desativada")
	void atualizarPessoaFisicaDesativada() {
		Cidade cidade = iniciarCidade();
		iniciarPessoaJuridica();
		restTemplate.exchange("/pessoaJuridica/atualizarStatus/1", HttpMethod.PUT, null,
				DadosDetalhamentoPessoaJuridica.class);

		DadosAtualizarEndereco endereco = new DadosAtualizarEndereco("01234567", "Logradouro", 1L, "A", cidade);
		DadosAtualizarPessoa pessoa = new DadosAtualizarPessoa("nomeTeste", "01/01/2000", "teste@magna.com",
				"11123456789", endereco);

		DadosAtualizarPessoaJuridica dadosAtualizarPessoaJuridica = new DadosAtualizarPessoaJuridica(1L,
				"01234567891123", pessoa);

		ResponseEntity<JsonNode> response = restTemplate.exchange("/pessoaJuridica/atualizar", HttpMethod.PUT,
				new HttpEntity<>(dadosAtualizarPessoaJuridica), JsonNode.class);

		assertTrue(response.getStatusCode().is4xxClientError());
	}

	@Test
	@DisplayName("Deve retornar um 204 quando deletar")
	void deletaPessoaJuridica() {
		iniciarPessoaJuridica();

		ResponseEntity<DadosDetalhamentoPessoaJuridica> response = restTemplate.exchange("/pessoaJuridica/deletar/1",
				HttpMethod.DELETE, null, DadosDetalhamentoPessoaJuridica.class);
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
	}

	@Test
	@DisplayName("Deve retornar um 204 quando atualizar status")
	void atualizarStatusPessoaJuridica() {
		iniciarPessoaJuridica();

		ResponseEntity<DadosDetalhamentoPessoaJuridica> response = restTemplate.exchange(
				"/pessoaJuridica/atualizarStatus/1", HttpMethod.PUT, null, DadosDetalhamentoPessoaJuridica.class);
		assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
	}
}
