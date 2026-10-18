package br.com.ifba.infrastructure.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

// centraliza a fabrica: so existe uma para o programa inteiro
public class JPAUtil {

    private static EntityManagerFactory factory;

    private JPAUtil() {
    }

    // a fabrica e criada na primeira chamada; se falhar, tenta de novo na proxima
    public static synchronized EntityManager getEntityManager() {
        if (factory == null) {
            factory = Persistence.createEntityManagerFactory("matchrhPU");
        }
        return factory.createEntityManager();
    }
}