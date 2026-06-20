package org.example.transportadora.repositories;

import jakarta.persistence.NoResultException;
import org.example.transportadora.dao.GenericoDAOImplementacao;
import org.example.transportadora.model.Distancia;
import org.example.transportadora.util.JpaUtil;

public class DistanciaRepository extends GenericoDAOImplementacao<Distancia, Integer> {
    public DistanciaRepository() {
        super(Distancia.class);
    }

    public Distancia buscarPorOrigemEDestino(Integer cidadeOrigemId, Integer cidadeDestinoId){
        try {
            String jpql = "SELECT d FROM Distancia d WHERE d.cidadeOrigem.id = :origemId AND d.cidadeDestino.id = :destinoId";
            return JpaUtil.getEntityManager().createQuery(jpql, Distancia.class)
                    .setParameter("origemId", cidadeOrigemId)
                    .setParameter("destinoId", cidadeDestinoId)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null; // Retorna null se a distância entre as duas cidades não estiver mapeada
        }
    }
}
