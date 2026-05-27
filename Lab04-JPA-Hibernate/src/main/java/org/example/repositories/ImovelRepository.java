package org.example.repositories;

import jakarta.persistence.EntityManager;
import org.example.model.Imovel;
import org.example.util.JpaUtil;

import java.math.BigDecimal;
import java.util.List;

public class ImovelRepository {

    private EntityManager manager;

    public ImovelRepository() {
        this.manager = JpaUtil.getEntityManager();
    }

    public void save(Imovel imovel) {
        this.manager.getTransaction().begin();
        this.manager.persist(imovel);
        this.manager.getTransaction().commit();
    }

    public Imovel update(Imovel imovel) {
        this.manager.getTransaction().begin();
        Imovel i = this.manager.merge(imovel);
        this.manager.getTransaction().commit();
        return i;
    }

    public List<Imovel> listAll() {
        return this.manager.createQuery("SELECT i FROM Imovel i", Imovel.class)
                .getResultList();
    }

    public List<Imovel> findByCep(String cep) {
        return this.manager.createQuery("SELECT i FROM Imovel i WHERE i.cep = :cep", Imovel.class)
                .setParameter("cep", cep)
                .getResultList();
    }

    public List<Imovel> findByPrecoRange(BigDecimal precoMin, BigDecimal precoMax) {
        return this.manager.createQuery(
                        "SELECT i FROM Imovel i WHERE i.valorAluguelSugerido BETWEEN :min AND :max", Imovel.class)
                .setParameter("min", precoMin)
                .setParameter("max", precoMax)
                .getResultList();
    }

    public List<Imovel> findByTipo(String descricaoTipo) {
        return this.manager.createQuery(
                        "SELECT i FROM Imovel i WHERE i.tipoImovel.descricao = :descricao", Imovel.class)
                .setParameter("descricao", descricaoTipo)
                .getResultList();
    }

    public List<Imovel> findByProprietario(Long proprietarioId) {
        return this.manager.createQuery(
                        "SELECT i FROM Imovel i WHERE i.proprietario.id = :proprietarioId", Imovel.class)
                .setParameter("proprietarioId", proprietarioId)
                .getResultList();
    }
}

    public void close() {
        if (this.manager.isOpen()) {
            this.manager.close();
        }
    }
}
