package org.example.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CompositeType;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "alugueis")
public class Aluguel {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dataVencimento;
    private BigDecimal valorPago;
    private String obs;

    @ManyToOne
    @JoinColumn(name = "id_locacao")
    private Locacao locacao;
    private java.time.Instant Instant;

    public Aluguel() {
    }

    public Aluguel(LocalDate dataVencimento, BigDecimal valorPago, String obs, Locacao locacao) {
        this.dataVencimento = dataVencimento;
        this.valorPago = valorPago;
        this.obs = obs;
        this.locacao = locacao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "dt_vencimento")
    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    @Column(name = "valor_pago")
    public BigDecimal getValorPago() {
        return valorPago;
    }

    public void setValorPago(BigDecimal valorPago) {
        this.valorPago = valorPago;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public void setLocacao(Locacao locacao) {
    }

    public void setDtVencimento(LocalDate of) {
    }

    public Instant getDtVencimento() {
        return this.Instant;
    }
}
