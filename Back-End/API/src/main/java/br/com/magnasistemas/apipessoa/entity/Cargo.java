package br.com.magnasistemas.apipessoa.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Table(name = "TB_CARGO")
@Entity (name = "Cargo")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Cargo {
	@Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id") 
	private Long id;
	
	@Column(name = "nome") 
	private String nome;
	
	@Column(name = "remuneracao") 
	private Double remuneracao;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "cargo")
    @JsonIgnore
    private List<PessoaFisica> pessoaFisica = new ArrayList<>();
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setRemuneracao(Double remuneracao) {
		this.remuneracao = remuneracao;
	}
	
	public Double getRemuneracao() {
		return remuneracao;
	}

	public Long getId() {
		return id;
	}
}
