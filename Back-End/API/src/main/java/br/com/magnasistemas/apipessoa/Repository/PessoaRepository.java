package br.com.magnasistemas.apipessoa.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import br.com.magnasistemas.apipessoa.entity.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa,Long>{

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM TB_PESSOA; ALTER SEQUENCE TB_PESSOA_id_seq RESTART WITH 1", nativeQuery = true)
	void deleteAllAndResetSequence();
	
	@Query("""
			select r from Pessoa r
			where r.telefone = :telefone 
			""")
	Pessoa findByTelefone(String telefone);

	
	@Query("""
	        select r from Pessoa r
	        where r.telefone = :telefone and r.id <> :id
	        """)
	Pessoa findByTelefoneAndNotId(String telefone, Long id);
}
