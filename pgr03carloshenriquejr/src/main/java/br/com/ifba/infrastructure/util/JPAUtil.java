package br.com.ifba.infrastructure.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

// unica responsabilidade: guardar a fabrica do JPA e entregar EntityManagers
public class JPAUtil {

    private static EntityManagerFactory factory;

    private JPAUtil() {
    }

    // aplica as migrations e cria a fabrica; so faz isso uma vez
    public static synchronized void iniciar() {
        if (factory == null) {
            new MigracaoBanco(new ConfiguracaoBanco()).aplicar();
            factory = Persistence.createEntityManagerFactory("matchrhPU");
        }
    }

    public static EntityManager getEntityManager() {
        iniciar();
        return factory.createEntityManager();
    }
}