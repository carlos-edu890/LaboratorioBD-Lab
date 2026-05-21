package org.example.repositories;

import jakarta.persistence.EntityManager;
import org.example.model.Cliente;
import org.example.util.JpaUtil;

public class ClienteRepository {

    private EntityManager manager;

    public ClienteRepository() {
        this.manager = JpaUtil.getEntityManager();
    }

    public void save(Cliente cliente) {
        this.manager.getTransaction().begin();
        this.manager.persist(cliente);
        this.manager.getTransaction().commit();
    }

    public void update(Cliente cliente) {
        this.manager.getTransaction().begin();
        this.manager.merge(cliente);
        this.manager.getTransaction().commit();
    }

    public Cliente getCliente(String emailOrCpf) {
        return this.manager.createQuery("SELECT c FROM Cliente c WHERE c.email = :emailOrCpf OR c.cpf = :emailOrCpf", Cliente.class)
                .setParameter("emailOrCpf", emailOrCpf)
                .setMaxResults(1)
                .getSingleResult();
    }
}
