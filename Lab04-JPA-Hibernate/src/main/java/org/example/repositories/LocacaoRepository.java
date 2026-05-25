package org.example.repositories;

import jakarta.persistence.EntityManager;
import org.example.model.Locacao;
import org.example.util.JpaUtil;
import java.util.List;

public class LocacaoRepository {

    private EntityManager manager;

    public LocacaoRepository() {
        this.manager = JpaUtil.getEntityManager();
    }

    public void save(Locacao locacao) {
        this.manager.getTransaction().begin();
        this.manager.persist(locacao);
        this.manager.getTransaction().commit();
    }

    public Locacao update(Locacao locacao) {
        this.manager.getTransaction().begin();
        Locacao l = this.manager.merge(locacao);
        this.manager.getTransaction().commit();
        return l;
    }

    public List<Locacao> findAtivas() {
        // No banco o campo é ativo (TINYINT/boolean)
        return this.manager.createQuery("SELECT l FROM Locacao l WHERE l.ativo = true", Locacao.class)
                .getResultList();
    }

    public List<Locacao> findByInquilino(Long inquilinoId) {
        return this.manager.createQuery("SELECT l FROM Locacao l WHERE l.inquilino.id = :inquilinoId", Locacao.class)
                .setParameter("inquilinoId", inquilinoId)
                .getResultList();
    }

    public void close() {
        if (this.manager.isOpen()) {
            this.manager.close();
        }
    }
}