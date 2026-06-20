package org.example.transportadora.repositories;

import org.example.transportadora.dao.GenericoDAOImplementacao;
import org.example.transportadora.model.Cidade;

public class CidadeRepository extends GenericoDAOImplementacao<Cidade, Integer> {
    public CidadeRepository() {
        super(Cidade.class);
    }
}
