package org.example.transportadora.repositories;

import org.example.transportadora.dao.GenericoDAOImplementacao;
import org.example.transportadora.model.Veiculo;

public class VeiculoRepository extends GenericoDAOImplementacao<Veiculo, Integer> {
    public VeiculoRepository() {
        super(Veiculo.class);
    }
}
