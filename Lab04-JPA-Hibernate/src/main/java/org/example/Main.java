package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.model.Cliente;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        try {
            // Substitua "NOME_DA_SUA_PU" pelo nome que está no seu <persistence-unit name="...">
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("Lab04JpaHibernate");

            EntityManager manager = emf.createEntityManager();

            System.out.println("Conexão realizada com sucesso!");

            Cliente c = new Cliente();
            c.setNome("Carlos");
            c.setCpf("43254533");
            c.setEmail("ca@email.com");

            manager.getTransaction().begin();
            manager.persist(c);
            manager.getTransaction().commit();

            manager.close();

            emf.close();
        } catch (Exception e) {
            System.err.println("Erro na configuração ou conexão:");
            e.printStackTrace();
        }
    }
}