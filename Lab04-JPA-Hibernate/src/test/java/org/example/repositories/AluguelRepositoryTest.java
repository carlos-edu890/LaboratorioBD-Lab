package org.example.repositories;

import jakarta.persistence.EntityManager;
import org.example.model.Aluguel;
import org.example.model.Locacao;
import org.example.util.JpaUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AluguelRepositoryTest {

    private AluguelRepository repository = new AluguelRepository();

    @Test
    void testarSeObjetoFoiPersistido() {
        EntityManager manager = JpaUtil.getEntityManager();

        List<Aluguel> alugueis = List.of(
                new Aluguel("Pago em dia", new BigDecimal("1200.00"), LocalDate.of(2026, 1, 10)),
                new Aluguel("Pago em dia", new BigDecimal("1200.00"), LocalDate.of(2026, 2, 10)),
                new Aluguel("Pago com atraso (juros inclusos)", new BigDecimal("1250.00"), LocalDate.of(2026, 3, 10)),
                new Aluguel("Pago em dia", new BigDecimal("1200.00"), LocalDate.of(2026, 4, 10)),
                new Aluguel("Pago antecipado", new BigDecimal("1200.00"), LocalDate.of(2026, 5, 10)),
                new Aluguel("Pago em dia", new BigDecimal("1300.00"), LocalDate.of(2026, 6, 10)),
                new Aluguel("Pago em dia", new BigDecimal("1300.00"), LocalDate.of(2026, 7, 10)),
                new Aluguel("Pago em dia", new BigDecimal("1300.00"), LocalDate.of(2026, 8, 10)),
                new Aluguel("Transferência bancária", new BigDecimal("1300.00"), LocalDate.of(2026, 9, 10)),
                new Aluguel("Depósito identificado", new BigDecimal("1300.00"), LocalDate.of(2026, 10, 10))
        );

        Locacao locacao1 = manager.find(Locacao.class, 1L);
        Locacao locacao2 = manager.find(Locacao.class, 2L);

        locacao1.adicionarAluguel(alugueis.get(0));
        locacao1.adicionarAluguel(alugueis.get(1));
        locacao1.adicionarAluguel(alugueis.get(2));
        locacao1.adicionarAluguel(alugueis.get(3));
        locacao2.adicionarAluguel(alugueis.get(4));
        locacao2.adicionarAluguel(alugueis.get(5));

        for (Aluguel a: alugueis) {
            repository.save(a);
        }

        Assertions.assertNotNull(manager.find(Aluguel.class, 1L));

        manager.close();
        JpaUtil.close();
    }

    @Test
    void testarSeObjetoFoiAtualizado() {
        EntityManager manager = JpaUtil.getEntityManager();

        Aluguel aluguel = manager.find(Aluguel.class, 1L);

        manager.detach(aluguel);

        aluguel.setValorPago(new BigDecimal("1250.00"));
        aluguel.setObs("Pago com atraso (juros inclusos)");

        Aluguel a = repository.update(aluguel);

        Assertions.assertEquals(new BigDecimal("1250.00"), a.getValorPago());
        Assertions.assertEquals("Pago com atraso (juros inclusos)", a.getObs());

        manager.close();
        JpaUtil.close();
    }

    @Test
    void testarSeRetornaOrdenado() {
        EntityManager manager = JpaUtil.getEntityManager();

        List<Aluguel> alugueis = repository.findByLocacaoOrdenado(1L);

        Assertions.assertNotNull(alugueis);
        Assertions.assertFalse(alugueis.isEmpty());

        Assertions.assertEquals(4, alugueis.size());

        Assertions.assertTrue(alugueis.get(0).getDataVencimento().compareTo(alugueis.get(1).getDataVencimento()) >= 0);
        Assertions.assertTrue(alugueis.get(1).getDataVencimento().compareTo(alugueis.get(2).getDataVencimento()) >= 0);;

        manager.close();
        JpaUtil.close();
    }
}