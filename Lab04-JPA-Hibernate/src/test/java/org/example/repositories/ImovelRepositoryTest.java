package org.example.repositories;

import jakarta.persistence.EntityManager;
import org.example.model.Cliente;
import org.example.model.Imovel;
import org.example.model.TipoImovel;
import org.example.util.JpaUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ImovelRepositoryTest {

    private ImovelRepository imovelRepository = new ImovelRepository();

    @Test
    void save() {
        EntityManager manager = JpaUtil.getEntityManager();

        List<TipoImovel> tipoImovels = List.of(
                new TipoImovel("Apartamento"),
                new TipoImovel("Casa"),
                new TipoImovel("Cobertura"),
                new TipoImovel("Kitnet"),
                new TipoImovel("Duplex"),
                new TipoImovel("Triplex"),
                new TipoImovel("Studio"),
                new TipoImovel("Sobrado"),
                new TipoImovel("Flat"),
                new TipoImovel("Loft"));

        for (TipoImovel tipo : tipoImovels) {
            manager.getTransaction().begin();
            manager.persist(tipo);
            manager.getTransaction().commit();
        }

        List<Imovel> imoveis = List.of(
                new Imovel("01001-000", new BigDecimal("2500.00")),
                new Imovel("01310-000", new BigDecimal("3800.50")),
                new Imovel("04538-132", new BigDecimal("5200.75")),
                new Imovel("20020-001", new BigDecimal("1800.00")),
                new Imovel("30130-010", new BigDecimal("2200.30")),
                new Imovel("40210-150", new BigDecimal("1950.90")),
                new Imovel("50010-000", new BigDecimal("3100.00")),
                new Imovel("60861-610", new BigDecimal("2750.45")),
                new Imovel("70040-010", new BigDecimal("4250.80")),
                new Imovel("80010-100", new BigDecimal("1600.25"))
        );

        Long i = 1L;
        for (Imovel imovel: imoveis) {
            imovel.setTipoImovel(manager.getReference(TipoImovel.class, i));
            i++;
        }

        imoveis.get(0).setProprietario(manager.getReference(Cliente.class, 1L));
        imoveis.get(1).setProprietario(manager.getReference(Cliente.class, 1L));
        imoveis.get(2).setProprietario(manager.getReference(Cliente.class, 1L));
        imoveis.get(5).setProprietario(manager.getReference(Cliente.class, 4L));
        imoveis.get(6).setProprietario(manager.getReference(Cliente.class, 5L));
        imoveis.get(7).setProprietario(manager.getReference(Cliente.class, 7L));
        imoveis.get(8).setProprietario(manager.getReference(Cliente.class, 7L));

        for (Imovel imovel: imoveis) {
            imovelRepository.save(imovel);
        }

        List<Imovel> im = imovelRepository.listAll();

        Assertions.assertNotNull(im);

        manager.close();
        JpaUtil.close();
    }

    @Test
    void update() {
        EntityManager manager = JpaUtil.getEntityManager();

        Imovel imovel = manager.find(Imovel.class, 1);

        manager.detach(imovel);

        imovel.setObs("Observação atualizada");
        Imovel atualizado = imovelRepository.update(imovel);

        Assertions.assertNotNull(atualizado);
        Assertions.assertEquals("Observação atualizada", atualizado.getObs());

        manager.close();
        JpaUtil.close();
    }

    @Test
    void listAll() {
        EntityManager manager = JpaUtil.getEntityManager();

        List<Imovel> todos = imovelRepository.listAll();

        Assertions.assertNotNull(todos);
        Assertions.assertTrue(todos.size() >= 1);

        manager.close();
        JpaUtil.close();
    }

    @Test
    void findByCep() {
        EntityManager manager = JpaUtil.getEntityManager();

        String cep = "60861-610";
        List<Imovel> encontrados = imovelRepository.findByCep(cep);

        Assertions.assertNotNull(encontrados);
        Assertions.assertFalse(encontrados.isEmpty());
        Assertions.assertTrue(encontrados.stream().anyMatch(i -> cep.equals(i.getCep())));

        manager.close();
        JpaUtil.close();
    }

    @Test
    void findByPrecoRange() {
        EntityManager manager = JpaUtil.getEntityManager();

        List<Imovel> medio = imovelRepository.findByPrecoRange(new BigDecimal("1500.00"), new BigDecimal("3500.00"));

        assertNotNull(medio);

        Assertions.assertTrue(medio.stream().anyMatch(i -> i.getValorAluguelSugerido().compareTo(new BigDecimal("1800.00")) == 0));
        Assertions.assertTrue(medio.stream().anyMatch(i -> i.getValorAluguelSugerido().compareTo(new BigDecimal("3100.00")) == 0));

        manager.close();
        JpaUtil.close();
    }

    @Test
    void findByTipo() {
        EntityManager manager = JpaUtil.getEntityManager();

        List<Imovel> porTipo = imovelRepository.findByTipo("Casa");

        assertNotNull(porTipo);
        assertTrue(porTipo.stream().anyMatch(i -> "01310-000".equals(i.getCep())));

        manager.close();
        JpaUtil.close();
    }

    @Test
    void findByProprietario() {
        EntityManager manager = JpaUtil.getEntityManager();

        List<Imovel> porProp = imovelRepository.findByProprietario(5L);

        assertNotNull(porProp);
        assertTrue(porProp.stream().anyMatch(i -> "30130-010".equals(i.getCep())));

        manager.close();
        JpaUtil.close();
    }
}