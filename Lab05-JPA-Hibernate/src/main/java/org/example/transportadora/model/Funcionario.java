package org.example.transportadora.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Funcionario extends PessoaFisica {

    private int matricula;
    private LocalDate dataAdmissao;
    private String cargo;

    @OneToMany(mappedBy = "funcionario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Dependente> dependentes;

    @ManyToOne
    @JoinColumn(name = "filial_id", nullable = false)
    private Filial filial;

    public Funcionario(String nome, String email, String telefone, String cpf, Integer matricula) {
        super(nome, email, telefone, cpf);
        dependentes = new ArrayList<>();
    }

    public Funcionario(String nome, String email, String telefone, String cpf, Integer matricula, LocalDate dataAdmissao, String cargo) {
        super(nome, email, telefone, cpf);
        this.matricula = matricula;
        this.dataAdmissao = dataAdmissao;
        this.cargo = cargo;
        dependentes = new ArrayList<>();
    }
}
