package br.com.ifba.infrastructure.util;

import org.flywaydb.core.Flyway;
import org.springframework.stereotype.Component;

// unica responsabilidade: aplicar as migrations do Flyway no banco
@Component
public class MigracaoBanco {

    private final ConfiguracaoBanco configuracao;

    public MigracaoBanco(ConfiguracaoBanco configuracao) {
        this.configuracao = configuracao;
    }

    public void aplicar() {
        Flyway.configure()
                .dataSource(configuracao.url(), configuracao.usuario(), configuracao.senha())
                .load()
                .migrate();
    }
}