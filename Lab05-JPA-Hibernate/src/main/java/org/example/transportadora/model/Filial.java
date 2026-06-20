package org.example.transportadora.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Filial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;
    private String endereco;
    private String telefone;

    @OneToMany(mappedBy = "filial")
    private List<Funcionario> funcionarios;

    @OneToMany(mappedBy = "filial")
    private List<Veiculo> veiculos;
}
