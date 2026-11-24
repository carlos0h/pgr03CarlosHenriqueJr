package br.com.ifba.infrastructure.util;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

// sobe o banco quando o Spring monta a aplicacao: aplica as migrations e cria a fabrica do JPA
@Component
public class InicializadorBanco {

    private final MigracaoBanco migracao;
    private final ConfiguracaoBanco configuracao;

    public InicializadorBanco(MigracaoBanco migracao, ConfiguracaoBanco configuracao) {
        this.migracao = migracao;
        this.configuracao = configuracao;
    }

    // roda quando o Spring termina de montar este objeto
    @PostConstruct
    public void iniciar() {
        migracao.aplicar();
        JPAUtil.iniciar(configuracao);
    }
}