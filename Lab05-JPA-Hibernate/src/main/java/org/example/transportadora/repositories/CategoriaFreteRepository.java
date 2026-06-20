package org.example.transportadora.repositories;

import org.example.transportadora.dao.GenericoDAOImplementacao;
import org.example.transportadora.model.CategoriaFrete;

public class CategoriaFreteRepository extends GenericoDAOImplementacao<CategoriaFrete, Integer> {
    public CategoriaFreteRepository() {
        super(CategoriaFrete.class);
    }
}
