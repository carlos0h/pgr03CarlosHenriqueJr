
package br.com.ifba.usuario.entity;


// candidato e um tipo de Usuario que possui curriculo
public class Candidato extends Usuario {
    
    private Curriculo curriculo;
    public Candidato(){
        super();
    }
    
    // repassa nome, cpf, login e senha para o construtor de Usuario
    public Candidato(String nome, String cpf, String login, String senha){
        super(nome, cpf, login, senha);
    }
    
    public Curriculo getCurriculo(){
        return curriculo;
    }
    
    public void setCurriculo(Curriculo curriculo){
        this.curriculo = curriculo;
    }

    // sobrescreve a descricao generica de Usuario com uma versao especifica
    @Override
    public String getDescricao() {
    return "Candidato: " + getNome();
    }
    
    
}
