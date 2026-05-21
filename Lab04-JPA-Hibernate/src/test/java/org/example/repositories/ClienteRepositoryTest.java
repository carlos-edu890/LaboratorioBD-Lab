package org.example.repositories;

import jakarta.persistence.EntityManager;
import org.example.model.Cliente;
import org.example.util.JpaUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClienteRepositoryTest {

    @Test
    void testarSeObjetoFoiPersistido() {
        EntityManager manager = JpaUtil.getEntityManager();

        List<Cliente> clientes = List.of(
                new Cliente("Ana Silva", "123.456.789-00", "ana.silva@email.com"),
                new Cliente("Bruno Santos", "234.567.890-11", "bruno@email.com"),
                new Cliente("Carlos Oliveira", "345.678.901-22", "carlos.oliveira@email.com"),
                new Cliente("Daniela Rodrigues", "456.789.012-33", "daniela.rodrigues@email.com"),
                new Cliente("Eduardo Costa", "567.890.123-44", "eduardo.costa@email.com"),
                new Cliente("Fernanda Almeida", "678.901.234-55", "fernanda.almeida@email.com"),
                new Cliente("Gabriel Souza", "789.012.345-66", "gabriel.souza@email.com"),
                new Cliente("Juliana Lima", "890.123.456-77", "juliana.lima@email.com"),
                new Cliente("Lucas Pereira", "901.234.567-88", "lucas.pereira@email.com"),
                new Cliente("Mariana Ribeiro", "012.345.678-99", "mariana.ribeiro@email.com")
        );

        ClienteRepository repository = new ClienteRepository();

        for (Cliente c: clientes) {
            repository.save(c);
        }

        Cliente c1 = manager.find(Cliente.class, 1L);

        manager.close();

        assertNotNull(c1, "Cliente deveria ter sido salvo no banco");
        assertEquals("Ana Silva", c1.getNome());
        assertEquals("123.456.789-00", c1.getCpf());
        assertEquals("ana.silva@email.com", c1.getEmail());

        JpaUtil.close();
    }

    @Test
    void testarSeObjetoFoiAtualizado() {
        ClienteRepository repository = new ClienteRepository();
        EntityManager manager = JpaUtil.getEntityManager();

        Cliente c = manager.find(Cliente.class, 2L);

        manager.detach(c);

        c.setNome("Bruno");

        Cliente c1 = repository.update(c);

        assertEquals("Bruno", c1.getNome());

        manager.close();
        JpaUtil.close();
    }

    @Test
    void BuscarClientePorEmail() {
        ClienteRepository repository = new ClienteRepository();

        EntityManager manager = JpaUtil.getEntityManager();

        Cliente c = repository.getCliente("carlos.oliveira@email.com");

        assertNotNull(c, "Cliente deveria ter sido encontrado no banco");

        manager.close();
        JpaUtil.close();
    }

    @Test
    void BuscarClientePorCpf() {
        ClienteRepository repository = new ClienteRepository();

        EntityManager manager = JpaUtil.getEntityManager();

        Cliente c = repository.getCliente("345.678.901-22");

        assertNotNull(c, "Cliente deveria ter sido encontrado no banco");

        manager.close();
        JpaUtil.close();
    }
}