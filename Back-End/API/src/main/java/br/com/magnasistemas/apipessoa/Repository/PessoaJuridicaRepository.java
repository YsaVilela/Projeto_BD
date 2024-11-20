package br.com.magnasistemas.apipessoa.Repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import br.com.magnasistemas.apipessoa.entity.PessoaJuridica;

public interface PessoaJuridicaRepository extends JpaRepository<PessoaJuridica, Long> {
	
	Page<PessoaJuridica> findByPessoaStatusTrue(Pageable paginacao);

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM TB_PESSOA_JURIDICA; ALTER SEQUENCE TB_PESSOA_JURIDICA_id_seq RESTART WITH 1", nativeQuery = true)
	void deleteAllAndResetSequence();
	
	@Query("""
			select r from PessoaJuridica r
			where r.cnpj = :cnpj 
			""")
	Optional<PessoaJuridica>  findByCnpj(String cnpj);

	
	@Query("""
	        select r from PessoaJuridica r
	        where r.cnpj = :cnpj and r.id <> :id
	        """)
	PessoaJuridica findByCnpjAndNotId(String cnpj, Long id);

	@Query("""
            select pf from PessoaJuridica pf
            join pf.pessoa p
            where lower(p.nome) like lower(concat('%', :nome, '%'))
            and p.status = true
            """)
    Page<PessoaJuridica> findByAllNome(@Param("nome") String nome, Pageable paginacao);
}
