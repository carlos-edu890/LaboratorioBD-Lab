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
public class ItemFrete {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String descricao;

    @Column(name = "peso", columnDefinition = "DECIMAL(10,2) NOT NULL")
    private float peso;

    @ManyToOne
    @JoinColumn(name = "frete_id")
    private Frete frete;
}
