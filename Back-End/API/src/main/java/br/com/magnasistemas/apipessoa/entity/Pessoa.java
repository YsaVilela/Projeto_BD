package br.com.magnasistemas.apipessoa.entity;

import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Table(name = "TB_PESSOA")
@Entity (name = "Pessoa")
public class Pessoa {
	@Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id") 
	private Long id;
	
	@Column(name = "nome") 
	private String nome;
	
	@Column(name = "data_constituicao") 
	private LocalDate dataDeConstituicao;
	
	@Column(name = "email") 
	private String email;
	
	@Column(name = "telefone", unique = true) 
	private String telefone;
	
	@OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "fk_endereco")
    protected Endereco endereco;
	
	@Column(name = "status") 
	private boolean status;
	
	public String getNome() {
		return nome;
	}
	
	public void setDataDeConstituicao(LocalDate dataDeConstituicao) {
		this.dataDeConstituicao = dataDeConstituicao;
	}
	
	public LocalDate getDataDeConstituicao() {
		return dataDeConstituicao;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Long getId() {
		return id;
	}

}
