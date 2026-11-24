package br.com.ifba.infrastructure.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.Map;

// unica responsabilidade: guardar a fabrica do JPA e entregar EntityManagers
public class JPAUtil {

    private static EntityManagerFactory factory;

    private JPAUtil() {
    }

    // cria a fabrica com os dados de conexao do application.properties; so faz isso uma vez
    public static synchronized void iniciar(ConfiguracaoBanco configuracao) {
        if (factory == null) {
            Map<String, Object> conexao = Map.of(
                    "jakarta.persistence.jdbc.driver", configuracao.driver(),
                    "jakarta.persistence.jdbc.url", configuracao.url(),
                    "jakarta.persistence.jdbc.user", configuracao.usuario(),
                    "jakarta.persistence.jdbc.password", configuracao.senha());
            factory = Persistence.createEntityManagerFactory("matchrhPU", conexao);
        }
    }

    public static synchronized EntityManager getEntityManager() {
        if (factory == null) {
            throw new IllegalStateException("O banco ainda não foi iniciado.");
        }
        return factory.createEntityManager();
    }
}