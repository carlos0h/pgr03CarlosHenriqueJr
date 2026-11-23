package br.com.ifba.infrastructure.util;

import org.flywaydb.core.Flyway;

// unica responsabilidade: aplicar as migrations do Flyway no banco
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