package org.example.transportadora.services;

import jakarta.transaction.Transactional;
import org.example.transportadora.exceptions.NegocioException;
import org.example.transportadora.model.*;
import org.example.transportadora.model.*;
import org.example.transportadora.repositories.DistanciaRepository;
import org.example.transportadora.repositories.FreteRepository;
import org.example.transportadora.repositories.VeiculoRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class FreteService {

    private final FreteRepository freteRepository;
    private final VeiculoRepository veiculoRepository;
    private final DistanciaRepository distanciaRepository;

    // Injeção de dependência via construtor utilizando os DAOs da Etapa 2
    public FreteService() {
        this.freteRepository = new FreteRepository();
        this.veiculoRepository = new VeiculoRepository();
        this.distanciaRepository = new DistanciaRepository();
    }

    /**
     * Tarefa 01 – Registra um novo frete no sistema
     */
    @Transactional
    public Frete registrarFrete(Frete frete) {

        // 1. Validações básicas de dados obrigatórios
        validarDadosObrigatorios(frete);

        // 2. Validação: Cidade de origem diferente da cidade de destino
        if (frete.getCidadeOrigem().getId().equals(frete.getCidadeDestino().getId())) {
            throw new NegocioException("A cidade de origem não pode ser igual à cidade de destino.");
        }

        // 3. Validação: Distância cadastrada entre as cidades
        Distancia distancia = distanciaRepository.buscarPorOrigemEDestino(
                frete.getCidadeOrigem().getId(),
                frete.getCidadeDestino().getId()
        );
        if (distancia == null) {
            throw new NegocioException("Não há uma distância cadastrada entre as cidades informadas.");
        }

        // 4. Validação: Pelo menos 1 item no frete
        if (frete.getItensFrete() == null || frete.getItensFrete().isEmpty()) {
            throw new NegocioException("O frete deve possuir pelo menos 1 item para ser registrado.");
        }

        // Calcular o peso total dos itens do frete
        float pesoTotalItens = 0;
        for (ItemFrete item : frete.getItensFrete()) {
            pesoTotalItens += item.getPeso();
        }

        // 5. Sugestão Automática de Veículo (com base nas regras especificadas)
        Veiculo veiculoAdequado = sugerirVeiculo(pesoTotalItens);
        frete.setVeiculo(veiculoAdequado);

        // 6. Configuração de valores padrão iniciais
        frete.setStatusFrete(StatusFrete.PENDENTE); // O status deve ficar como PENDENTE

        // Calcula o valor total utilizando a regra mapeada na entidade Frete
        BigDecimal valorTotal = calcularValorFrete(frete);
        frete.setValorKmRodado(valorTotal); // Armazena o valor total calculado no atributo valorKmRodado do frete
        // Nota: se o seu modelo persistir o valor final calculado, salve-o em um atributo correspondente.

        // 7. Persistência no Banco de Dados através do DAO Genérico
        freteRepository.salvar(frete);

        return frete;
    }

    /**
     * Valida os dados estruturais obrigatórios do Frete
     */
    private void validarDadosObrigatorios(Frete frete) {
        if (frete == null) {
            throw new NegocioException("O objeto frete não pode ser nulo.");
        }
        if (frete.getVeiculo() == null && frete.getVeiculo().getFilial() == null) {
            throw new NegocioException("O veículo associado ao frete deve ser válido e estar associado a uma filial.");
        }
        if (frete.getCliente() == null) {
            throw new NegocioException("O cliente é obrigatório para registrar o frete.");
        }
        if (frete.getCidadeOrigem() == null || frete.getCidadeDestino() == null) {
            throw new NegocioException("As cidades de origem e destino são obrigatórias.");
        }
        if (frete.getCategoriaFrete() == null) {
            throw new NegocioException("A categoria do frete é obrigatória.");
        }
        if (frete.getValorKmRodado() == null || frete.getValorKmRodado().compareTo(BigDecimal.ZERO) <= 0) {
            throw new NegocioException("O valor padrão por KM rodado deve ser informado e maior que zero.");
        }
    }

    /**
     * Regra de Negócio: Sugestão Automática de Veículo
     * - Selecionar veículo que suporte o peso total dos itens
     * - Escolher o veículo com menor capacidade suficiente (otimização de frota)
     * - Garantir que o veículo seja válido e associado a uma filial
     * - Lançar exceção caso não exista veículo compatível
     */
    private Veiculo sugerirVeiculo(float pesoTotalItens) {
        List<Veiculo> todosVeiculos = veiculoRepository.buscarTodos();
        Veiculo melhorVeiculo = null;

        for (Veiculo veiculo : todosVeiculos) {
            // Validação: Veículo precisa ser válido (ter placa) e estar associado a uma filial
            if (veiculo.getNumeroPlaca() == null || veiculo.getFilial() == null) {
                continue; // Ignora veículos inconsistentes
            }

            // Verifica se o veículo suporta o peso
            if (veiculo.getPesoMaximo() >= pesoTotalItens) {
                // Aplica o critério: escolher o veículo com a menor capacidade suficiente
                if (melhorVeiculo == null || veiculo.getPesoMaximo() < melhorVeiculo.getPesoMaximo()) {
                    melhorVeiculo = veiculo;
                }
            }
        }

        // Lança exceção se nenhum veículo atender aos requisitos de peso
        if (melhorVeiculo == null) {
            throw new NegocioException("Erro: Não existe veículo disponível na frota que suporte o peso total das mercadorias (" + pesoTotalItens + " Kg).");
        }

        return melhorVeiculo;
    }


    /**
     * Tarefa 02 – Cálculo Completo do Frete
     * Calcula o valor total do frete aplicando as regras de distância, peso e categoria.
     */
    public BigDecimal calcularValorFrete(Frete frete) {

        // 1. Validação: A cidade de Origem deve ser diferente da cidade de Destino
        if (frete.getCidadeOrigem().getId().equals(frete.getCidadeDestino().getId())) {
            throw new NegocioException("Cálculo negado: A cidade de origem não pode ser igual à cidade de destino.");
        }

        // 2. Validação: Existência de distância cadastrada entre as cidades
        Distancia distancia = distanciaRepository.buscarPorOrigemEDestino(
                frete.getCidadeOrigem().getId(),
                frete.getCidadeDestino().getId()
        );
        if (distancia == null) {
            throw new NegocioException("Cálculo negado: Não há uma distância cadastrada entre as cidades informadas.");
        }

        // 3. Cálculo do Peso Total
        float pesoTotal = 0;
        if (frete.getItensFrete() != null) {
            for (ItemFrete item : frete.getItensFrete()) {
                pesoTotal += item.getPeso();
            }
        }

        // 4. Validação: Limite de peso do veículo associado
        if (frete.getVeiculo() != null && pesoTotal > frete.getVeiculo().getPesoMaximo()) {
            throw new NegocioException("Cálculo negado: O peso total dos itens (" + pesoTotal + " kg) excede o limite máximo suportado pelo veículo (" + frete.getVeiculo().getPesoMaximo() + " kg).");
        }

        // 5. Determinação do Percentual por Peso (P_peso)
        BigDecimal pPeso;
        if (pesoTotal <= 100) {
            pPeso = BigDecimal.ZERO; // 0%
        } else if (pesoTotal <= 300) {
            pPeso = new BigDecimal("0.10"); // 10%
        } else {
            pPeso = new BigDecimal("0.20"); // 20%
        }

        // 6. Preparação dos componentes da Fórmula Geral
        BigDecimal d = new BigDecimal(distancia.getQuilometros());

        // Valor do KM rodado (Base de R$ 2,00 + adicional fixo da distância se houver)
        BigDecimal vkmBase = new BigDecimal("2.00");
        BigDecimal ad = distancia.getAdicionalkmRodado() != null ? distancia.getAdicionalkmRodado() : BigDecimal.ZERO;
        BigDecimal vkmTotal = vkmBase.add(ad);

        // Percentual da Categoria (P_categoria) convertido de float para BigDecimal
        float percentualAdicionalCat = frete.getCategoriaFrete().getPercentualAdicional();
        BigDecimal pCategoria = new BigDecimal(Float.toString(percentualAdicionalCat))
                .divide(new BigDecimal("100"), 4, RoundingMode.HALF_UP); // Converte ex: 10% para 0.1000

        // 7. Aplicação da Fórmula Geral:
        // ValorFrete = (D × VKM) × (1 + P_peso) × (1 + P_categoria)
        BigDecimal baseDistancia = d.multiply(vkmTotal); // (D × VKM)
        BigDecimal fatorPeso = BigDecimal.ONE.add(pPeso); // (1 + P_peso)
        BigDecimal fatorCategoria = BigDecimal.ONE.add(pCategoria); // (1 + P_categoria)

        BigDecimal valorFreteFinal = baseDistancia
                .multiply(fatorPeso)
                .multiply(fatorCategoria);

        // Retorna o valor final arredondado com duas casas decimais (padrão monetário)
        return valorFreteFinal.setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Tarefa 03 – Atualização do Status do Frete com Regras de Transição
     * Altera o status do frete garantindo as restrições de fluxo de negócio.
     */
    @Transactional
    public Frete atualizarStatus(Integer freteId, StatusFrete novoStatus) {
        // 1. Busca o frete no banco de dados usando o DAO Genérico
        Frete frete = freteRepository.buscarId(freteId);
        if (frete == null) {
            throw new NegocioException("Erro: Frete não encontrado com o ID informado.");
        }

        StatusFrete statusAtual = frete.getStatusFrete();

        // 2. Validação: Não permitir alteração de frete CANCELADO
        if (statusAtual == StatusFrete.CANCELADO) {
            throw new NegocioException("Erro de transição: Não é permitido alterar o status de um frete que já está CANCELADO.");
        }

        // 3. Validação: Não permitir retrocesso de status e validar transições permitidas
        if (statusAtual == novoStatus) {
            return frete; // Se o status for o mesmo, não faz nada e retorna
        }

        switch (novoStatus) {
            case EM_TRANSPORTE:
                // Regra específica: exige status anterior como PENDENTE e um veículo associado
                if (statusAtual != StatusFrete.PENDENTE) {
                    throw new NegocioException("Erro de transição: O frete só pode ir para EM_TRANSPORTE se o status atual for PENDENTE.");
                }
                if (frete.getVeiculo() == null) {
                    throw new NegocioException("Erro de transição: Não é possível iniciar o transporte sem um veículo associado ao frete.");
                }
                break;

            case ENTREGUE:
                // Regra implícita de fluxo: Só pode ser entregue se estava em transporte
                if (statusAtual != StatusFrete.EM_TRANSPORTE) {
                    throw new NegocioException("Erro de transição: O frete só pode ser marcado como ENTREGUE se o status atual for EM_TRANSPORTE.");
                }
                break;

            case CANCELADO:
                // Regra implícita de fluxo: Geralmente não se cancela algo que já foi ENTREGUE (retrocesso)
                if (statusAtual == StatusFrete.ENTREGUE) {
                    throw new NegocioException("Erro de transição: Não é possível CANCELAR um frete que já foi ENTREGUE.");
                }
                break;

            case PENDENTE:
                // Se o status atual for qualquer outro (EM_TRANSPORTE ou ENTREGUE), tentar voltar para PENDENTE é um retrocesso
                throw new NegocioException("Erro de transição: Não é permitido retroceder o status do frete para PENDENTE.");
        }

        // 4. Se passou por todas as validações, atualiza o status
        frete.setStatusFrete(novoStatus);

        // 5. Salva a alteração no banco de dados através do DAO Genérico
        freteRepository.atualizar(frete);

        return frete;
    }


}
