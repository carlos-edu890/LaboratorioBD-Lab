package org.example.transportadora.model;

import jakarta.persistence.Entity;

@Entity
public enum StatusFrete {
    PENDENTE,
    EM_TRANSPORTE,
    ENTREGUE,
    CANCELADO
}
