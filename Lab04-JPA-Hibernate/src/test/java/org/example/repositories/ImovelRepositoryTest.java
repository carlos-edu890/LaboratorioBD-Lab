package org.example.repositories;

import jakarta.persistence.EntityManager;
import org.example.model.Cliente;
import org.example.model.Imovel;
import org.example.model.TipoImovel;
import org.example.util.JpaUtil;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ImovelRepositoryTest {

    @Test
    void testarSalvarEListarTodosOsImoveis() {
        EntityManager manager = JpaUtil.getEntityManager();
        ImovelRepository repository = new ImovelRepository();

        // Buscando ou criando dependências necessárias para salvar um imóvel
        Cliente proprietario = manager.find(Cliente.class, 1L);
        TipoImovel tipo = manager.find(TipoImovel.class, 1L);

        Imovel imovel = new Imovel();
        imovel.setEndereco("Rua das Flores, 123");
        imovel.setCep("65000-000");
        imovel.setDormitorios(2);
        imovel.setBanheiros(2);
        imovel.setSuites(1);
        imovel.setMetragem(75);
        imovel.setValorAluguelSugerido(new BigDecimal("1500.00"));
        imovel.setProprietario(proprietario);
        imovel.setTipoImovel(tipo);

        repository.save(imovel);

        List<Imovel> lista = repository.listAll();
        assertFalse(lista.isEmpty(), "A lista de imóveis não deveria estar vazia");

        manager.close();
        repository.close();
        JpaUtil.close();
    }

    @Test
    void testarBuscarPorCep() {
        ImovelRepository repository = new ImovelRepository();

        List<Imovel> resultado = repository.findByCep("65000-000");
        assertNotNull(resultado);

        repository.close();
        JpaUtil.close();
    }

    @Test
    void testarBuscarPorFaixaDePreco() {
        ImovelRepository repository = new ImovelRepository();

        List<Imovel> resultado = repository.findByPrecoRange(new BigDecimal("1000.00"), new BigDecimal("2000.00"));
        assertNotNull(resultado);

        repository.close();
        JpaUtil.close();
    }

    @Test
    void testarBuscarPorTipo() {
        ImovelRepository repository = new ImovelRepository();

        // Passa a descrição do tipo, ex: "Apartamento" ou "Casa"
        List<Imovel> resultado = repository.findByTipo("Apartamento");
        assertNotNull(resultado);

        repository.close();
        JpaUtil.close();
    }

    @Test
    void testarBuscarPorProprietario() {
        ImovelRepository repository = new ImovelRepository();

        // Busca os imóveis do proprietário de ID 1
        List<Imovel> resultado = repository.findByProprietario(1L);
        assertNotNull(resultado);

        repository.close();
        JpaUtil.close();
    }
}