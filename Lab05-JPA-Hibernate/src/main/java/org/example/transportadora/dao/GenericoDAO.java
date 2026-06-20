package org.example.transportadora.dao;

import java.util.List;

public interface GenericoDAO<T, ID> {
    void salvar(T t);
    T atualizar(T t);
    void remover(T t);
    T buscarId(ID id);
    List<T> buscarTodos();
}
