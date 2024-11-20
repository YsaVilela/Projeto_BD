package br.com.magnasistemas.apipessoa.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Table(name = "TB_PESSOA_FISICA")
@Entity (name = "PessoaFisica")
public class PessoaFisica {
	@Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id") 
	private Long id;
	
	@Column(name = "cpf") 
	private String cpf;
		
	@OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "fk_pessoa")
    protected Pessoa pessoa;
	
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "fk_cargo")
    protected Cargo cargo;
	
	public String getCpf() {
		return cpf;
	}
	
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}
	
	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
	
	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	public Long getId() {
		return id;
	}

}