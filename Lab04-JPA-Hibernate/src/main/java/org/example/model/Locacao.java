package org.example.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "locacao")
public class Locacao {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean ativo;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private Integer diaVencimento;
    private BigDecimal percentualMulta;
    private BigDecimal valorAluguel;
    private String obs;

    @ManyToOne @JoinColumn(name = "id_imovel")
    private Imovel imovel;

    @ManyToOne @JoinColumn(name = "id_inquilino")
    private Cliente cliente;

    @OneToMany(mappedBy = "locacao")
    private List<Aluguel> alugueis = new ArrayList<>();

    public Locacao() {

    }

    public Locacao(Boolean ativo) {
        this.ativo = ativo;
    }

    public Long getId() {
        return id;
    }

    public List<Aluguel> getAlugueis() {
        return alugueis;
    }

    public void adicionarAluguel(Aluguel aluguel) {
        this.alugueis.add(aluguel);
        aluguel.setLocacao(this);
    }

    public Imovel getImovel() {
        return imovel;
    }

    public void setImovel(Imovel imovel) {
        this.imovel = imovel;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    @Column(name = "data_inicio")
    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    @Column(name = "data_fim")
    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    @Column(name = "dia_vencimento")
    public Integer getDiaVencimento() {
        return diaVencimento;
    }

    public void setDiaVencimento(Integer diaVencimento) {
        this.diaVencimento = diaVencimento;
    }

    @Column(name = "perc_multa")
    public BigDecimal getPercentualMulta() {
        return percentualMulta;
    }

    public void setPercentualMulta(BigDecimal percentualMulta) {
        this.percentualMulta = percentualMulta;
    }

    @Column(name = "valor_aluguel")
    public BigDecimal getValorAluguel() {
        return valorAluguel;
    }

    public void setValorAluguel(BigDecimal valorAluguel) {
        this.valorAluguel = valorAluguel;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }
}
