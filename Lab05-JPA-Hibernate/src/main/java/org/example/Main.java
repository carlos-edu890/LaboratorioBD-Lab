package org.example;

import org.example.transportadora.model.*;
import org.example.transportadora.repositories.*;
import org.example.transportadora.services.FreteService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println(" INICIANDO APLICAÇÃO E POPULANDO BANCO DE DADOS");
        System.out.println("==================================================");

        try {
            // 1. Instanciando Repositórios e Serviços
            CidadeRepository cidadeRepository = new CidadeRepository();
            ClienteRepository clienteRepository = new ClienteRepository();
            CategoriaFreteRepository categoriaFreteRepository = new CategoriaFreteRepository();
            DistanciaRepository distanciaRepository = new DistanciaRepository();
            FilialRepository filialRepository = new FilialRepository();
            TipoVeiculoRepository tipoVeiculoRepository = new TipoVeiculoRepository();
            VeiculoRepository veiculoRepository = new VeiculoRepository();
            FreteService freteService = new FreteService();

            System.out.println("-> Cadastrando Cidades...");
            Cidade cidadeOrigem = new Cidade();
            cidadeOrigem.setNome("São Luís");
            cidadeOrigem.setUf("MA");
            cidadeRepository.salvar(cidadeOrigem);

            Cidade cidadeDestino = new Cidade();
            cidadeDestino.setNome("Imperatriz");
            cidadeDestino.setUf("MA");
            cidadeRepository.salvar(cidadeDestino);

            System.out.println("-> Cadastrando Cliente...");
            Cliente cliente = new Cliente("Eliã", "elia@email.com", "9899999999", "11122233344", 1, "Contato Eliã", true);
            clienteRepository.salvar(cliente);

            System.out.println("-> Cadastrando Categoria de Frete...");
            CategoriaFrete categoria = new CategoriaFrete();
            categoria.setNome("Expresso");
            categoria.setPercentualAdicional(10.0f);
            categoriaFreteRepository.salvar(categoria);

            System.out.println("-> Cadastrando Distância (430 km)...");
            Distancia distancia = new Distancia();
            distancia.setCidadeOrigem(cidadeOrigem);
            distancia.setCidadeDestino(cidadeDestino);
            distancia.setQuilometros(430);
            distancia.setAdicionalkmRodado(new BigDecimal("1.50"));
            distanciaRepository.salvar(distancia);

            System.out.println("-> Cadastrando Frota (Filial, Tipo e Veículo)...");
            Filial filial = new Filial();
            filial.setNome("Filial São Luís");
            filialRepository.salvar(filial);

            TipoVeiculo tipoVeiculo = new TipoVeiculo();
            tipoVeiculo.setDescricao("Caminhão Baú");
            tipoVeiculo.setPesoMaximo(5000.0f);
            tipoVeiculoRepository.salvar(tipoVeiculo);

            Veiculo veiculo = new Veiculo();
            veiculo.setNumeroPlaca("LAB-2026");
            veiculo.setPesoMaximo(5000);
            veiculo.setFilial(filial);
            veiculo.setTipoVeiculo(tipoVeiculo);
            veiculoRepository.salvar(veiculo);

            System.out.println("-> Simulando o Registro de um Frete...");
            Frete frete = new Frete();
            frete.setCliente(cliente);
            frete.setCidadeOrigem(cidadeOrigem);
            frete.setCidadeDestino(cidadeDestino);
            frete.setCategoriaFrete(categoria);
            frete.setVeiculo(veiculo);
            frete.setValorKmRodado(new BigDecimal("5.00"));
            frete.setCodigo(999);
            frete.setNumeroNotaFiscal(123456);

            // Adicionando Itens ao Frete (com o vínculo bidirecional corrigido!)
            ItemFrete item1 = new ItemFrete();
            item1.setDescricao("Eletrônicos");
            item1.setPeso(150.0f);
            item1.setFrete(frete);

            List<ItemFrete> itens = new ArrayList<>();
            itens.add(item1);
            frete.setItensFrete(itens);

            // Salvando tudo via serviço
            Frete freteSalvo = freteService.registrarFrete(frete);

            System.out.println("==================================================");
            System.out.println("✅ SUCESSO! Banco populado e Frete registrado.");
            System.out.println("📍 ID do Frete: " + freteSalvo.getId());
            System.out.println("🚦 Status Atual: " + freteSalvo.getStatusFrete());
            System.out.println("==================================================");

        } catch (Exception e) {
            System.err.println("❌ Ocorreu um erro na execução: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Encerra a aplicação Java (importante para fechar as threads do Hibernate)
            System.exit(0);
        }
    }
}