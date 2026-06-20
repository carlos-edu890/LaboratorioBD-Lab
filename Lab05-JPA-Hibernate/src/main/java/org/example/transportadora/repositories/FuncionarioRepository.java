package org.example.transportadora.repositories;

import org.example.transportadora.dao.GenericoDAOImplementacao;
import org.example.transportadora.model.Funcionario;

public class FuncionarioRepository extends GenericoDAOImplementacao<Funcionario, Integer> {
    public FuncionarioRepository() {
        super(Funcionario.class);
    }
}
