package org.example;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        try {
            // Substitua "NOME_DA_SUA_PU" pelo nome que está no seu <persistence-unit name="...">
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("Lab04JpaHibernate");

            System.out.println("Conexão realizada com sucesso!");
            emf.close();
        } catch (Exception e) {
            System.err.println("Erro na configuração ou conexão:");
            e.printStackTrace();
        }
    }
}