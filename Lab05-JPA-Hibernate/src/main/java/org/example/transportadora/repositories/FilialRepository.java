package org.example.transportadora.repositories;

import org.example.transportadora.dao.GenericoDAOImplementacao;
import org.example.transportadora.model.Filial;

public class FilialRepository extends GenericoDAOImplementacao<Filial, Integer> {
    public FilialRepository() {
        super(Filial.class);
    }
}
