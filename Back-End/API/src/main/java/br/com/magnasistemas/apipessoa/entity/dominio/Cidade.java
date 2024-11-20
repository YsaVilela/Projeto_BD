package br.com.magnasistemas.apipessoa.entity.dominio;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.magnasistemas.apipessoa.entity.Endereco;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Table(name = "TB_CIDADE")
@Entity (name = "Cidade")
public class Cidade {
	@Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id") 
	private Long id;
	
	@Column(name = "nome") 
	private String nome;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "fk_estado")
	protected Estados estado;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "cidade")
    @JsonIgnore
    private List<Endereco> endereco = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public Estados getEstado() {
		return estado;
	}

	public String getNome() {
		return nome;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setEstado(Estados estado) {
		this.estado = estado;
	}
	
	
}
