
package br.com.ifba.usuario.entity;


// recrutador e um tipo de Usuario que pertence a uma empresa
public class Recrutador extends Usuario {
    private String empresa;
    
    public Recrutador(){
        super();
    }
    // repassa nome, cpf, login e senha para o construtor de Usuario
    public Recrutador (String nome, String cpf, String login, String senha){
        super(nome, cpf, login, senha);
    }
    
    public String getEmpresa(){
        return empresa;
    }
    
    public void setEmpresa(String empresa){
        this.empresa = empresa;
    }
    // sobrescreve a descricao generica de Usuario com uma versao especifica
    @Override
    public String getDescricao(){
        return "Recrutador: " + getNome();
    }
    
    
}
