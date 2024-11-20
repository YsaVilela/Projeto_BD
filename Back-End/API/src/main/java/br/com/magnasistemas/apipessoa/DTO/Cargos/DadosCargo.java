package br.com.magnasistemas.apipessoa.DTO.Cargos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class DadosCargo {
    @NotBlank(message = "O nome do cargo é obrigatório")
    private String nome;

    @NotNull(message = "Remuneração não pode ser nulo")
    private Double remuneracao;

    public DadosCargo() {
    }

    public DadosCargo(String nome, Double remuneracao) {
        this.nome = nome;
        this.remuneracao = remuneracao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getRemuneracao() {
        return remuneracao;
    }

    public void setRemuneracao(Double remuneracao) {
        this.remuneracao = remuneracao;
    }

    @Override
    public String toString() {
        return "DadosCargo{" +
                "nome='" + nome + '\'' +
                ", remuneracao=" + remuneracao +
                '}';
    }
}
