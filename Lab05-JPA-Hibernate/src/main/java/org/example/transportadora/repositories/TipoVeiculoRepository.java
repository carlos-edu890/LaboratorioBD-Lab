package org.example.transportadora.repositories;

import org.example.transportadora.dao.GenericoDAOImplementacao;
import org.example.transportadora.model.TipoVeiculo;

public class TipoVeiculoRepository extends GenericoDAOImplementacao<TipoVeiculo, Integer> {
    public TipoVeiculoRepository() {
        super(TipoVeiculo.class);
    }
}
