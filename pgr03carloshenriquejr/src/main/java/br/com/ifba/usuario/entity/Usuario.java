package br.com.ifba.usuario.entity;

import br.com.ifba.infrastructure.entity.PersistenceEntity;
import br.com.ifba.usuario.interfaces.Autenticavel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// classe base do dominio - superclasse de Candidato e Recrutador
@Entity
@Table(name = "usuarios")
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "senha")
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
public class Usuario extends PersistenceEntity implements Autenticavel {

    @Getter
    @Setter
    @Column(nullable = false)
    private String nome;
    
    @Getter
    @Setter
    @EqualsAndHashCode.Include
    @Column(nullable = false, unique = true, length = 11)
    private String cpf;

    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    @Column(nullable = false, unique = true)
    private String login;

    @Getter
    @Setter
    @Column(nullable = false)
    private String senha;

    // preenchida pelo proprio banco (DEFAULT CURRENT_TIMESTAMP da V2): so tem getter e nao entra no INSERT
    @Getter
    @Column(name = "data_cadastro", insertable = false, updatable = false)
    private java.time.LocalDateTime dataCadastro;

    // o Lombok nao gera este formato (sem email e sem dataCadastro), por isso continua escrito a mao
    public Usuario(String nome, String cpf, String login, String senha) {
        this.nome = nome;
        this.cpf = cpf;
        this.login = login;
        this.senha = senha;
    }

    // metodo generico, sobrescrito por Candidato e Recrutador
    public String getDescricao() {
        return "Usuario: " + nome;
    }

    @Override
    public boolean autenticar(String login, String senha) {
        return this.login.equals(login) && this.senha.equals(senha);
    }
}