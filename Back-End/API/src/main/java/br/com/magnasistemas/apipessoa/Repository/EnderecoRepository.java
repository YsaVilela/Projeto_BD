package br.com.magnasistemas.apipessoa.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import br.com.magnasistemas.apipessoa.entity.Endereco;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM tb_endereco; ALTER SEQUENCE tb_endereco_id_seq RESTART WITH 1", nativeQuery = true)
	void deleteAllAndResetSequence();
}