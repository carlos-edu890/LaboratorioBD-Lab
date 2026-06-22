package org.example.transportadora.services;

import org.example.transportadora.exceptions.NegocioException;
import org.example.transportadora.model.*;
import org.example.transportadora.repositories.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class FreteServiceTest {

    private FreteService freteService;
    private Cliente clienteValido;
    private Cidade cidadeOrigem;
    private Cidade cidadeDestino;
    private CategoriaFrete categoriaNormal;

    // Repositórios centrais para evitar "new" repetidos nos testes
    private final CidadeRepository cidadeRepository = new CidadeRepository();
    private final ClienteRepository clienteRepository = new ClienteRepository();
    private final CategoriaFreteRepository categoriaFreteRepository = new CategoriaFreteRepository();
    private final DistanciaRepository distanciaRepository = new DistanciaRepository();
    private final FilialRepository filialRepository = new FilialRepository();
    private final TipoVeiculoRepository tipoVeiculoRepository = new TipoVeiculoRepository();
    private final VeiculoRepository veiculoRepository = new VeiculoRepository();

    @BeforeEach
    public void setUp() {
        freteService = new FreteService();

        // Inicializa entidades base
        clienteValido = new Cliente("João", "joao@email.com", "999999999", "12345678900", 1, "Contato João", true);
        cidadeOrigem = new Cidade();
        cidadeDestino = new Cidade();

        categoriaNormal = new CategoriaFrete();
        categoriaNormal.setPercentualAdicional(0.0f);

        // Salva dados básicos de infraestrutura do cenário
        cidadeRepository.salvar(cidadeOrigem);
        cidadeRepository.salvar(cidadeDestino);
        clienteRepository.salvar(clienteValido);
        categoriaFreteRepository.salvar(categoriaNormal);

        // Configura e salva a distância padrão do cenário
        Distancia distanciaFicticia = new Distancia();
        distanciaFicticia.setCidadeOrigem(cidadeOrigem);
        distanciaFicticia.setCidadeDestino(cidadeDestino);
        distanciaFicticia.setQuilometros(430);
        distanciaFicticia.setAdicionalkmRodado(BigDecimal.ZERO);
        distanciaRepository.salvar(distanciaFicticia);
    }

    @Test
    public void deveRegistrarFreteComSucessoEStatusPendente() {
        // Arrange
        Frete frete = criarFreteBase();

        List<ItemFrete> itens = new ArrayList<>();
        // CORREÇÃO: Passando o 'frete' para o item criar o vínculo bidirecional
        itens.add(criarItemFrete("Caixa de Ferramentas", 80.0f, frete));
        frete.setItensFrete(itens);

        // Configura veículo compatível necessário para as regras do serviço
        criarESalvarVeiculoValido("BRA-2E19", 500.0f, 2000.0f);

        // Act
        Frete freteSalvo = freteService.registrarFrete(frete);

        // Assert
        assertNotNull(freteSalvo, "O serviço deve retornar o frete salvo.");
        assertEquals(StatusFrete.PENDENTE, freteSalvo.getStatusFrete(), "O status inicial deve ser PENDENTE.");
    }

    @Test
    public void deveAtualizarStatusParaEmTransporteComSucesso() {
        // Arrange
        Frete freteOriginal = criarFreteBase();
        freteOriginal.setStatusFrete(StatusFrete.PENDENTE);

        List<ItemFrete> itens = new ArrayList<>();
        // CORREÇÃO: Passando o 'freteOriginal' para criar o vínculo
        itens.add(criarItemFrete("Item Padrão", 50.0f, freteOriginal));
        freteOriginal.setItensFrete(itens);

        // Veículo obrigatório para a transição EM_TRANSPORTE
        Veiculo veiculo = criarESalvarVeiculoValido("ABC-1234", 50.0f, 500.0f);
        freteOriginal.setVeiculo(veiculo);

        Frete freteCadastrado = freteService.registrarFrete(freteOriginal);

        // Act
        Frete freteAtualizado = freteService.atualizarStatus(freteCadastrado.getId(), StatusFrete.EM_TRANSPORTE);

        // Assert
        assertNotNull(freteAtualizado, "O frete retornado não deve ser nulo.");
        assertEquals(StatusFrete.EM_TRANSPORTE, freteAtualizado.getStatusFrete(), "O status deveria mudar para EM_TRANSPORTE.");
    }

    @Test
    public void deveLancarexcecaoAoTentarRetrocederStatusParaPendente() {
        // Arrange
        Frete freteOriginal = criarFreteBase();
        freteOriginal.setStatusFrete(StatusFrete.PENDENTE);

        Veiculo veiculo = criarESalvarVeiculoValido("XYZ-9999", 80.0f, 500.0f);
        freteOriginal.setVeiculo(veiculo);

        List<ItemFrete> itens = new ArrayList<>();
        // CORREÇÃO: Passando o 'freteOriginal' para criar o vínculo
        itens.add(criarItemFrete("Item Leve", 10.0f, freteOriginal));
        freteOriginal.setItensFrete(itens);

        // Registra e avança o status para EM_TRANSPORTE primeiro
        Frete freteEmTransporte = freteService.registrarFrete(freteOriginal);
        freteService.atualizarStatus(freteEmTransporte.getId(), StatusFrete.EM_TRANSPORTE);

        // Act & Assert
        NegocioException excecao = assertThrows(NegocioException.class, () -> {
            freteService.atualizarStatus(freteEmTransporte.getId(), StatusFrete.PENDENTE);
        }, "Deveria ter lançado NegocioException ao tentar retroceder o status.");

        assertTrue(excecao.getMessage().contains("Não é permitido retroceder o status"),
                "A mensagem de erro deve alertar sobre a proibição do retrocesso.");
    }

    // --- MÉTODOS AUXILIARES (HELPERS) PARA CRIAÇÃO DE CENÁRIOS ---

    private Frete criarFreteBase() {
        Frete frete = new Frete();
        frete.setCliente(clienteValido);
        frete.setCidadeOrigem(cidadeOrigem);
        frete.setCidadeDestino(cidadeDestino);
        frete.setCategoriaFrete(categoriaNormal);
        frete.setValorKmRodado(new BigDecimal("2.00"));
        return frete;
    }

    // CORREÇÃO AQUI: Adicionado o parâmetro "Frete fretePai"
    private ItemFrete criarItemFrete(String descricao, float peso, Frete fretePai) {
        ItemFrete item = new ItemFrete();
        item.setDescricao(descricao);
        item.setPeso(peso);
        item.setFrete(fretePai); // A mágica do JPA acontece aqui
        return item;
    }

    private Veiculo criarESalvarVeiculoValido(String placa, float pesoMaximoVeiculo, float pesoMaximoTipo) {
        Filial filial = new Filial();
        filial.setNome("Filial Padrão");
        filialRepository.salvar(filial);

        TipoVeiculo tipoVeiculo = new TipoVeiculo();
        tipoVeiculo.setDescricao("Tipo Padrão");
        tipoVeiculo.setPesoMaximo(pesoMaximoTipo);
        tipoVeiculoRepository.salvar(tipoVeiculo);

        Veiculo veiculo = new Veiculo();
        veiculo.setNumeroPlaca(placa);
        veiculo.setPesoMaximo((int) pesoMaximoVeiculo);
        veiculo.setFilial(filial);
        veiculo.setTipoVeiculo(tipoVeiculo);
        veiculoRepository.salvar(veiculo);

        return veiculo;
    }
}