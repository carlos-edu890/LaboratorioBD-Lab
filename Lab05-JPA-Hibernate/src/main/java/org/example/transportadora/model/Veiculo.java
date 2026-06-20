package org.example.transportadora.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Veiculo {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String numeroPlaca;
    private Integer pesoMaximo;

    @ManyToOne
    @JoinColumn(name = "filial_id", nullable = false)
    private Filial filial;

    @ManyToOne
    @JoinColumn(name = "tipo_veiculo_id", nullable = false)
    private TipoVeiculo tipoVeiculo;
}
