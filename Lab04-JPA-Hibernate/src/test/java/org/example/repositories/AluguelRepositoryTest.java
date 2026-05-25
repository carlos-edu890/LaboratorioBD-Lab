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
    void testarSalvarEListarAlugueisOrdenadosPorVencimento() {
        EntityManager manager = JpaUtil.getEntityManager();
        AluguelRepository repository = new AluguelRepository();

        Locacao locacao = manager.find(Locacao.class, 1L);

        // Criando o Aluguel do mês 1
        Aluguel aluguel1 = new Aluguel();
        aluguel1.setLocacao(locacao);
        aluguel1.setDtVencimento(LocalDate.of(2026, 6, 10));
        aluguel1.setValorPago(new BigDecimal("1400.00"));
        aluguel1.setObs("Pagamento em dia");

        // Criando o Aluguel do mês 2 (Vencimento posterior)
        Aluguel aluguel2 = new Aluguel();
        aluguel2.setLocacao(locacao);
        aluguel2.setDtVencimento(LocalDate.of(2026, 7, 10));
        aluguel2.setValorPago(new BigDecimal("1400.00"));
        aluguel2.setObs("Pagamento antecipado");

        repository.save(aluguel1);
        repository.save(aluguel2);

        // Busca os aluguéis da locação ID 1 ordenados decrescentemente
        List<Aluguel> alugueis = repository.findByLocacaoOrdenado(1L);

        assertFalse(alugueis.isEmpty(), "A lista de aluguéis não deveria estar vazia");

        // Validação da ordenação decrescente: O vencimento de Julho (mês 7) deve vir ANTES do de Junho (mês 6)
        assertTrue(alugueis.get(0).getDtVencimento().isAfter(alugueis.get(1).getDtVencimento()),
                "Os aluguéis devem vir ordenados do vencimento mais recente para o mais antigo");

        manager.close();
        repository.close();
        JpaUtil.close();
    }
}