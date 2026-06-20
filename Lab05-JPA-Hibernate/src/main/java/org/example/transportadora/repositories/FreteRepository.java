package org.example.transportadora.repositories;


import org.example.transportadora.dao.GenericoDAOImplementacao;
import org.example.transportadora.model.Frete;
import org.example.transportadora.model.StatusFrete;
import org.example.transportadora.util.JpaUtil;

import java.util.List;

public class FreteRepository extends GenericoDAOImplementacao<Frete, Integer> {

    public FreteRepository() {
        super(Frete.class);
    }

    public List<Frete> buscarPorStatus(StatusFrete status) {
        String jpql = "SELECT f FROM Frete f WHERE f.statusFrete = :status";
        return JpaUtil.getEntityManager().createQuery(jpql, Frete.class)
                .setParameter("status", status)
                .getResultList();
    }
}
