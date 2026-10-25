package br.com.ifba.usuario.entity;
import br.com.ifba.usuario.interfaces.Autenticavel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// classe base do dominio - superclasse de Candidato e Recrutador
@Entity
@Table(name = "usuarios")
public class Usuario implements Autenticavel{
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
       
    @Column(nullable = false)
    private String nome;
    
    @Column(nullable = false, unique = true, length = 11)
    private String cpf;
    
    private String email;
    
    @Column(nullable = false, unique = true)
    private String login;
    
    @Column(nullable = false)
    private String senha;
    
    // preenchida pelo proprio banco (DEFAULT CURRENT_TIMESTAMP da V2), por isso nao entra no INSERT
    @Column(name = "data_cadastro", insertable = false, updatable = false)
    private java.time.LocalDateTime dataCadastro;
    
    public Usuario(){
    }
    
    public Usuario(String nome, String cpf, String login, String senha) {
        this.nome = nome;
        this.cpf = cpf;
        this.login = login;
        this.senha = senha;
    }
    
    public Long getId() {
        return id;
    }
    public java.time.LocalDateTime getDataCadastro() {
        return dataCadastro;
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
    
    // dois usuarios vao ser iguais se tiverem o msm CPF 
    @Override
    public boolean equals(Object obj){
        if (this == obj){
            return true;
        }
        if (obj == null || getClass() != obj.getClass()){
            return false;
        }
        Usuario outro = (Usuario) obj;
        return cpf != null && cpf.equals(outro.cpf);
    }
    
    @Override
    public int hashCode(){
        if(cpf != null){
            return cpf.hashCode();
        }else{
            return 0; 
        }
    }
    
}