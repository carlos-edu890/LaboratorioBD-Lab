package org.example.transportadora.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Frete {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private int codigo;
    private int numeroNotaFiscal;
    private BigDecimal valorKmRodado;

    @ManyToOne
    @JoinColumn(name = "status_frete_id")
    private StatusFrete statusFrete;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "veiculo_id", nullable = false)
    private Veiculo veiculo;

    @ManyToOne
    @JoinColumn(name = "cidade_origem_id", nullable = false)
    private Cidade cidadeOrigem;

    @ManyToOne
    @JoinColumn(name = "cidade_destino_id", nullable = false)
    private Cidade cidadeDestino;

    @ManyToOne
    @JoinColumn(name = "categoria_frete_id", nullable = false)
    private CategoriaFrete categoriaFrete;

    @OneToMany(mappedBy = "frete", cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "frete_id")
    private List<ItemFrete> itensFrete;

    public BigDecimal calcularFrete(int quilometros) {
        BigDecimal base = valorKmRodado.multiply(new BigDecimal(quilometros));
        BigDecimal adicional = base.multiply(new BigDecimal(categoriaFrete.getPercentualAdicional()));
        return base.add(adicional);
    }
}
