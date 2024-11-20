package br.com.magnasistemas.apipessoa.Repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import br.com.magnasistemas.apipessoa.entity.Cargo;

public interface CargoRepository extends JpaRepository<Cargo, Long> {

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM TB_CARGO; ALTER SEQUENCE TB_CARGO_id_seq RESTART WITH 1", nativeQuery = true)
	void deleteAllAndResetSequence();

	@Query("""
			select r from Cargo r
			where r.nome = :nome
			""")
	Cargo findByNome(String nome);

	@Query("""
			select r from Cargo r
			where r.nome = :nome and r.id <> :id
			""")
	Cargo findByNomeAndNotId(String nome, Long id);

	@Query("""
			select c from Cargo c
			where lower(c.nome) = :nome
			""")
	Optional<Cargo> findByAllNome(String nome);

	@Query("""
	        select c from Cargo c
	        where lower(c.nome) like lower(concat('%', :nome, '%'))
	        """)
	Page<Cargo> findByAllNomeDinamico(@Param("nome") String nome, Pageable paginacao);

}
