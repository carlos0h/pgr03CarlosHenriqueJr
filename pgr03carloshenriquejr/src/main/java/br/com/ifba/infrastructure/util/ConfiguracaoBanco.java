package br.com.ifba.infrastructure.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

// unica responsabilidade: guardar os dados de conexao que o Spring le do application.properties
@Component
public class ConfiguracaoBanco {

    private final String url;
    private final String usuario;
    private final String senha;
    private final String driver;

    public ConfiguracaoBanco(
            @Value("${spring.datasource.url}") String url,
            @Value("${spring.datasource.username}") String usuario,
            @Value("${spring.datasource.password}") String senha,
            @Value("${spring.datasource.driver-class-name}") String driver) {
        this.url = url;
        this.usuario = usuario;
        this.senha = senha;
        this.driver = driver;
    }

    public String url() {
        return url;
    }

    public String usuario() {
        return usuario;
    }

    public String senha() {
        return senha;
    }

    public String driver() {
        return driver;
    }
}