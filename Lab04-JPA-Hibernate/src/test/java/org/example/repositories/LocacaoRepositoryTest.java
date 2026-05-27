package org.example.repositories;

import jakarta.persistence.EntityManager;
import org.example.model.Cliente;
import org.example.model.Locacao;
import org.example.util.JpaUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LocacaoRepositoryTest {

    private LocacaoRepository repository = new LocacaoRepository();

    @Test
    void testarSeObjetoFoiPersistido() {
        EntityManager manager = JpaUtil.getEntityManager();

        List<Locacao> locacaos = List.of(
                new Locacao(true),
                new Locacao(false),
                new Locacao(true),
                new Locacao(false),
                new Locacao(true),
                new Locacao(false),
                new Locacao(true),
                new Locacao(false),
                new Locacao(true),
                new Locacao(false)
        );

        locacaos.get(0).setCliente(manager.find(Cliente.class, 1L));
        locacaos.get(1).setCliente(manager.find(Cliente.class, 2L));
        locacaos.get(2).setCliente(manager.find(Cliente.class, 4L));
        locacaos.get(3).setCliente(manager.find(Cliente.class, 1L));
        locacaos.get(4).setCliente(manager.find(Cliente.class, 1L));
        locacaos.get(5).setCliente(manager.find(Cliente.class, 5L));
        locacaos.get(6).setCliente(manager.find(Cliente.class, 8L));
        locacaos.get(7).setCliente(manager.find(Cliente.class, 2L));
        locacaos.get(8).setCliente(manager.find(Cliente.class, 1L));
        locacaos.get(9).setCliente(manager.find(Cliente.class, 1L));

        for (Locacao locacao: locacaos) {
            repository.save(locacao);
        }

        Locacao loc = manager.getReference(Locacao.class, 1L);;

        Assertions.assertNotNull(loc);

        manager.close();
        JpaUtil.close();
    }

    @Test
    void testarSeObjetoFoiAtualizado() {
        EntityManager manager = JpaUtil.getEntityManager();

        Locacao locacao = manager.find(Locacao.class, 1L);

        manager.detach(locacao);

        locacao.setObs("Locação atualizada");
        locacao.setAtivo(false);
        Locacao atualizada = repository.update(locacao);

        assertNotNull(atualizada);
        assertEquals("Locação atualizada", atualizada.getObs());
        assertFalse(atualizada.getAtivo());

        manager.close();
        JpaUtil.close();
    }

    @Test
    void testarSeRetornaPorAtivas() {
        EntityManager manager = JpaUtil.getEntityManager();

        List<Locacao> ativas = repository.findAtivas();

        assertNotNull(ativas);
        assertTrue(ativas.size() >= 2);
        assertTrue(ativas.stream().allMatch(l -> l.getAtivo().equals(true)));

        manager.close();
        JpaUtil.close();
    }

    @Test
    void testarSeRetornaPorInquilino() {
        EntityManager manager = JpaUtil.getEntityManager();

        List<Locacao> locacoesPorInquilino = repository.findByInquilino(2L);

        assertNotNull(locacoesPorInquilino);
        assertTrue(locacoesPorInquilino.stream()
                .anyMatch(l -> l.getCliente().getId().longValue() == 2L));

        manager.close();
        JpaUtil.close();
    }
}