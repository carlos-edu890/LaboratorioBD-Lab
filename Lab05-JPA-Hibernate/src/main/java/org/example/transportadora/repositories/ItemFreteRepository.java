package org.example.transportadora.repositories;

import org.example.transportadora.dao.GenericoDAOImplementacao;
import org.example.transportadora.model.ItemFrete;

public class ItemFreteRepository extends GenericoDAOImplementacao<ItemFrete, Integer> {
    public ItemFreteRepository() {
        super(ItemFrete.class);
    }
}
