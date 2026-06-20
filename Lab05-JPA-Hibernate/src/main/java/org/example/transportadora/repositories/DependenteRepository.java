package org.example.transportadora.repositories;

import org.example.transportadora.dao.GenericoDAOImplementacao;
import org.example.transportadora.model.Dependente;

public class DependenteRepository extends GenericoDAOImplementacao<Dependente, Integer> {
    public DependenteRepository() {
        super(Dependente.class);
    }
}
