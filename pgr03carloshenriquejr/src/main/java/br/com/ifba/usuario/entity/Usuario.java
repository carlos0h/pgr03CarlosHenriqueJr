package br.com.ifba.usuario.entity;
import br.com.ifba.usuario.interfaces.Autenticavel;


// classe base do dominio - superclasse de Candidato e Recrutador
public class Usuario implements Autenticavel{
       
    private String nome;
    private String cpf;
    private String email;
    private String login;
    private String senha;
    
    public Usuario(){
    }
    
    public Usuario(String nome, String cpf, String login, String senha) {
        this.nome = nome;
        this.cpf = cpf;
        this.login = login;
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }
    
    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    // metodo generico, sobrescrito por Candidato e Recrutador
    public String getDescricao() {
        return "Usuario: " + nome;
    }
    
    
    
    // compara login e senha recebidos com os deste usuario
    @Override
    public boolean autenticar(String login, String senha){
        return this.login.equals(login) && this.senha.equals(senha);
    }
    
}