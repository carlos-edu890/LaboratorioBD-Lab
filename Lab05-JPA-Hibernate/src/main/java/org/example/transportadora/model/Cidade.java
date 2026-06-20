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
public class Cidade {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String uf;
    private String nome;
    private String estado;
}
