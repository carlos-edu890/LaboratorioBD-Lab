package org.example.repositories;

import jakarta.persistence.EntityManager;
import org.example.model.Cliente;
import org.example.model.Imovel;
import org.example.model.Locacao;
import org.example.util.JpaUtil;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class LocacaoRepositoryTest {

    @Test
    void testarSalvarEBuscarLocacoesAtivas() {
        EntityManager manager = JpaUtil.getEntityManager();
        LocacaoRepository repository = new LocacaoRepository();

        Cliente inquilino = manager.find(Cliente.class, 2L); // Bruno Santos
        Imovel imovel = manager.find(Imovel.class, 1L);

        Locacao locacao = new Locacao();
        locacao.setAtivo(true);
        locacao.setDataInicio(LocalDate.now());
        locacao.setDataFim(LocalDate.now().plusYears(1));
        locacao.setDiaVencimento(10);
        locacao.setPercMulta(new BigDecimal("10.00"));
        locacao.setValorAluguel(new BigDecimal("1400.00"));
        locacao.setInquilino(inquilino);
        locacao.setImovel(imovel);

        repository.save(locacao);

        List<Locacao> ativas = repository.findAtivas();
        assertFalse(ativas.isEmpty(), "Deveria listar ao menos uma locação ativa");

        manager.close();
        repository.close();
        JpaUtil.close();
    }

    @Test
    void testarBuscarLocacoesPorInquilino() {
        LocacaoRepository repository = new LocacaoRepository();

        // Busca locações associadas ao inquilino ID 2
        List<Locacao> resultado = repository.findByInquilino(2L);
        assertNotNull(resultado);

        repository.close();
        JpaUtil.close();
    }
}