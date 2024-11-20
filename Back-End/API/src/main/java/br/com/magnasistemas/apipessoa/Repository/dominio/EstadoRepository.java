package br.com.magnasistemas.apipessoa.Repository.dominio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import br.com.magnasistemas.apipessoa.entity.dominio.Estados;

public interface EstadoRepository extends JpaRepository <Estados,Long>{
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM TB_ESTADOS; ALTER SEQUENCE TB_ESTADOS_id_seq RESTART WITH 1", nativeQuery = true)
	void deleteAllAndResetSequence();
}
