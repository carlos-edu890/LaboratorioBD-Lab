package org.example.repositories;

import jakarta.persistence.EntityManager;
import org.example.model.Cliente;
import org.example.util.JpaUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClienteRepositoryTest {

    @Test
    void testarSeObjetoFoiPersistido() {
        EntityManager manager = JpaUtil.getEntityManager();

        Cliente c = new Cliente();
        c.setNome("Carlos");
        c.setCpf("43254533");
        c.setEmail("ca@email.com");

        ClienteRepository repository = new ClienteRepository();
        repository.save(c);

        Cliente c1 = manager.find(Cliente.class, c.getId());

        manager.close();

        assertNotNull(c1, "Cliente deveria ter sido salvo no banco");
        assertEquals("Carlos", c1.getNome());
        assertEquals("43254533", c1.getCpf());
        assertEquals("ca@email.com", c1.getEmail());

        JpaUtil.close();
    }

    @Test
    void testarSeObjetoFoiAtualizado() {
        Cliente c = new Cliente();
        c.setNome("Robert");
        c.setCpf("43254533");
        c.setEmail("ca@email.com");

        ClienteRepository repository = new ClienteRepository();
        repository.save(c);

        EntityManager manager = JpaUtil.getEntityManager();

        Cliente c1 = manager.find(Cliente.class, c.getId());
        c1.setNome("Joao");
        repository.update(c1);

        assertEquals("Joao", manager.find(Cliente.class, c.getId()).getNome());
        manager.close();
        JpaUtil.close();
    }

    @Test
    void BuscarClientePorEmal() {
        Cliente c = new Cliente();
        c.setNome("Robert");
        c.setCpf("43254533");
        c.setEmail("ca@email.com");

        ClienteRepository repository = new ClienteRepository();
        repository.save(c);

        EntityManager manager = JpaUtil.getEntityManager();

        Cliente c1 = repository.getCliente("ca@email.com");

        assertNotNull(c1, "Cliente deveria ter sido encontrado no banco");

        manager.close();
        JpaUtil.close();
    }

    @Test
    void BuscarClientePorCpf() {
        Cliente c = new Cliente();
        c.setNome("Jobert");
        c.setCpf("43254535");
        c.setEmail("jo@email.com");

        ClienteRepository repository = new ClienteRepository();
        repository.save(c);

        EntityManager manager = JpaUtil.getEntityManager();

        Cliente c1 = repository.getCliente("43254535");

        assertNotNull(c1, "Cliente deveria ter sido encontrado no banco");

        manager.close();
        JpaUtil.close();
    }
}