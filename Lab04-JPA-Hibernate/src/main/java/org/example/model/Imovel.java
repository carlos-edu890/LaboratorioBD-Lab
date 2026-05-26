package org.example.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "imoveis")
public class Imovel {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String endereco;
    private String cep;
    private Integer domitorios;
    private Integer banheiros;
    private Integer suites;
    private Integer metragem;
    private BigDecimal valorAluguelSugerido;
    private String obs;

    @ManyToOne
    @JoinColumn(name = "tipo_imovel_id")
    private TipoImovel tipoImovel;

    @ManyToOne
    @JoinColumn(name = "proprietario_id")
    private Cliente proprietario;

    @OneToMany(mappedBy = "imovel")
    private List<Locacao> historicoLocacoes = new ArrayList<>();;


    public Imovel() {

    }

    public Imovel(String cep, BigDecimal valorAluguelSugerido) {
        this.cep = cep;
        this.valorAluguelSugerido = valorAluguelSugerido;
    }

    public Imovel(String cep, BigDecimal valorAluguelSugerido, TipoImovel tipoImovel) {
        this.cep = cep;
        this.valorAluguelSugerido = valorAluguelSugerido;
        this.tipoImovel = tipoImovel;
    }

    public Long getId() {
        return id;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Integer getDomitorios() {
        return domitorios;
    }

    public void setDomitorios(Integer domitorios) {
        this.domitorios = domitorios;
    }

    public Integer getBanheiros() {
        return banheiros;
    }

    public void setBanheiros(Integer banheiros) {
        this.banheiros = banheiros;
    }

    public Integer getSuites() {
        return suites;
    }

    public void setSuites(Integer suites) {
        this.suites = suites;
    }

    public Integer getMetragem() {
        return metragem;
    }

    public void setMetragem(Integer metragem) {
        this.metragem = metragem;
    }

    public BigDecimal getValorAluguelSugerido() {
        return valorAluguelSugerido;
    }

    @Column(name = "valor_aluguel_sugerido", precision = 10, scale = 2)
    public void setValorAluguelSugerido(BigDecimal valorAluguelSugerido) {
        this.valorAluguelSugerido = valorAluguelSugerido;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public TipoImovel getTipoImovel() {
        return tipoImovel;
    }

    public void setTipoImovel(TipoImovel tipoImovel) {
        this.tipoImovel = tipoImovel;
    }

    public Cliente getProprietario() {
        return proprietario;
    }

    public void setProprietario(Cliente proprietario) {
        this.proprietario = proprietario;
    }

    public List<Locacao> getHistoricoLocacoes() {
        return historicoLocacoes;
    }

    public void setHistoricoLocacoes(Locacao locacao) {
        this.historicoLocacoes.add(locacao);
    }
}
