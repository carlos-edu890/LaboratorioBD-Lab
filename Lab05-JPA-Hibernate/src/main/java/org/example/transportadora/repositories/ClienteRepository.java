package org.example.transportadora.repositories;

import org.example.transportadora.dao.GenericoDAOImplementacao;
import org.example.transportadora.model.Cliente;

public class ClienteRepository extends GenericoDAOImplementacao<Cliente, Integer> {
    public ClienteRepository() {
        super(Cliente.class);
    }
}
