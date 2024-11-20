package br.com.magnasistemas.apipessoa.Repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import br.com.magnasistemas.apipessoa.entity.PessoaFisica;

public interface PessoaFisicaRepository extends JpaRepository<PessoaFisica, Long> {
	
	Page<PessoaFisica> findByPessoaStatusTrue(Pageable paginacao);
	
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM TB_PESSOA_FISICA; ALTER SEQUENCE TB_PESSOA_FISICA_id_seq RESTART WITH 1", nativeQuery = true)
	void deleteAllAndResetSequence();
	
	@Query("""
			select r from PessoaFisica r
			where r.cpf = :cpf 
			""")
	 Optional<PessoaFisica> findByCpf(String cpf);

	@Query("""
			select r from PessoaFisica r
            where lower(r.cpf) like lower(concat('%', :cpf, '%'))
            """)
	Page<PessoaFisica> findByCpfDinamico(String cpf, Pageable paginacao);
	
	@Query("""
	        select r from PessoaFisica r
	        where r.cpf = :cpf and r.id <> :id
	        """)
	PessoaFisica findByCpfAndNotId(String cpf, Long id);
	
	@Query("""
	        select pf from PessoaFisica pf
	        join pf.cargo p
	        where p.id = :id
	        """)
	Optional<PessoaFisica> findByCargoId(@Param("id") long id);

	
	@Query("""
            select pf from PessoaFisica pf
            join pf.pessoa p
            where lower(p.nome) like lower(concat('%', :nome, '%'))
            and p.status = true
            """)
    Page<PessoaFisica> findByAllNome(@Param("nome") String nome, Pageable paginacao);
	
    @Query("""
            select pf from PessoaFisica pf
            join pf.pessoa p
            join pf.cargo c
            where lower(c.nome) like lower(concat('%', :nomeCargo, '%')) 
            and p.status = true
            """)
    Page<PessoaFisica> findByCargo(@Param("nomeCargo") String nomeCargo, Pageable paginacao);
}