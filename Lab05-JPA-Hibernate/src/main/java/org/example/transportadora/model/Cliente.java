package org.example.transportadora.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cliente extends PessoaFisica {

    private String contato;
    private Boolean ativo;

    public Cliente(String nome, String email, String telefone, String cpf, Integer matricula, String contato, Boolean ativo) {
        super(nome, email, telefone, cpf);
        this.contato = contato;
        this.ativo = ativo;
    }
}
