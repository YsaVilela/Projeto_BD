package br.com.magnasistemas.apipessoa.Repository.dominio;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import br.com.magnasistemas.apipessoa.entity.dominio.Cidade;

public interface CidadeRepository extends JpaRepository<Cidade, Long> {
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM TB_CIDADE; ALTER SEQUENCE TB_CIDADE_id_seq RESTART WITH 1", nativeQuery = true)
	void deleteAllAndResetSequence();

	@Query("""
			select c from Cidade c
			join c.estado e
    	    where lower(c.nome) = lower(:nomeCidade)
			and lower(e.uf) = lower(:siglaEstado)
			""")
	Optional<Cidade> findByNome(@Param("nomeCidade") String nomeCidade, @Param("siglaEstado") String siglaEstado);
	
	@Query("""
			select c from Cidade c
			join c.estado e
    	    where lower(e.uf) = lower(:siglaEstado)
			""")
	Page<Cidade> findByNomeEstado(@Param("siglaEstado") String siglaEstado, Pageable paginacao);

}
