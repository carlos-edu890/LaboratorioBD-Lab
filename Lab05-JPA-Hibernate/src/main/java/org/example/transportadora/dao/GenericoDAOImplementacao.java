package org.example.transportadora.dao;

import jakarta.persistence.EntityManager;
import org.example.transportadora.util.JpaUtil;

import java.util.List;

public abstract class GenericoDAOImplementacao<T, ID> implements GenericoDAO<T, ID> {
    private EntityManager manager;

    private final Class<T> classePersistente;

    public GenericoDAOImplementacao(Class<T> classePersistente) {
        this.classePersistente = classePersistente;
        this.manager = JpaUtil.getEntityManager();
    }

    @Override
    public void salvar(T t) {
        this.manager.getTransaction().begin();
        this.manager.persist(t);
        this.manager.getTransaction().commit();
    }

    @Override
    public T atualizar(T t) {
        this.manager.getTransaction().begin();
        T a = this.manager.merge(t);
        this.manager.getTransaction().commit();
        return a;
    }

    @Override
    public void remover(T t) {
        this.manager.getTransaction().begin();
        this.manager.remove(t);
        this.manager.getTransaction().commit();
    }

    public T buscarId(ID id) {
        return this.manager.find(this.classePersistente, id);
    }

    @Override
    public List<T> buscarTodos() {
        String jpql = "SELECT e FROM " + classePersistente.getSimpleName() + " e";
        return this.manager.createQuery(jpql, classePersistente).getResultList();
    }
}
