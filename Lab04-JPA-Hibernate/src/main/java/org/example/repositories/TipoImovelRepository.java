package org.example.repositories;

import jakarta.persistence.EntityManager;
import org.example.model.TipoImovel;
import org.example.util.JpaUtil;

public class TipoImovelRepository {

    private EntityManager manager;

    public TipoImovelRepository() {
        this.manager = JpaUtil.getEntityManager();
    }

    public void save(TipoImovel tipo) {
        this.manager.getTransaction().begin();
        this.manager.persist(tipo);
        this.manager.getTransaction().commit();
    }

    public void close() {
        if (this.manager.isOpen()) {
            this.manager.close();
        }
    }
}