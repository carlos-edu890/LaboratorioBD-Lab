package org.example.repositories;

import jakarta.persistence.EntityManager;
import org.example.model.Aluguel;
import org.example.model.Locacao;
import org.example.util.JpaUtil;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AluguelRepositoryTest {

    @Test
    void save() {
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

        alugueis.get(0).setLocacao(manager.find(Locacao.class, 1L));
        alugueis.get(1).setLocacao(manager.find(Locacao.class, 1L));
        alugueis.get(2).setLocacao(manager.find(Locacao.class, 1L));
        alugueis.get(3).setLocacao(manager.find(Locacao.class, 1L));
        alugueis.get(4).setLocacao(manager.find(Locacao.class, 1L));
        alugueis.get(5).setLocacao(manager.find(Locacao.class, 2L));
        alugueis.get(6).setLocacao(manager.find(Locacao.class, 2L));
        alugueis.get(7).setLocacao(manager.find(Locacao.class, 2L));
        alugueis.get(8).setLocacao(manager.find(Locacao.class, 2L));
        alugueis.get(9).setLocacao(manager.find(Locacao.class, 2L));

        
    }

    @Test
    void update() {
    }

    @Test
    void findByLocacaoOrdenado() {
    }
}