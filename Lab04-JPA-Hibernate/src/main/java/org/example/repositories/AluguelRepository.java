package org.example.repositories;

import jakarta.persistence.EntityManager;
import org.example.model.Aluguel;
import org.example.util.JpaUtil;

import java.util.List;

public class AluguelRepository {

    private EntityManager manager;

    public AluguelRepository() {
        this.manager = JpaUtil.getEntityManager();
    }

    public void save(Aluguel aluguel) {
        this.manager.getTransaction().begin();
        this.manager.persist(aluguel);
        this.manager.getTransaction().commit();
    }

    public Aluguel update(Aluguel aluguel) {
        this.manager.getTransaction().begin();
        Aluguel a = this.manager.merge(aluguel);
        this.manager.getTransaction().commit();
        return a;
    }

    public List<Aluguel> findByLocacaoOrdenado(Long locacaoId) {
        return this.manager.createQuery(
                        "SELECT a FROM Aluguel a WHERE a.locacao.id = :locacaoId ORDER BY a.dataVencimento DESC", Aluguel.class)
                .setParameter("locacaoId", locacaoId)
                .getResultList();
    }
}
