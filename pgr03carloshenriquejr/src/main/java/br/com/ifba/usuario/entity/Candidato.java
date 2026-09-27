
package br.com.ifba.usuario.entity;


// candidato e um tipo de Usuario que possui curriculo
public class Candidato extends Usuario {
    
    private Curriculo curriculo;
    public Candidato(){
        super();
    }
    
    // aqui o candidato está sem o curriculo ainda(cadastro inicial e coloca curriculo dps)
    public Candidato(String nome, String cpf, String login, String senha){
        super(nome, cpf, login, senha);
    }
    
    // candidato que vai se cadastrar com o curriculo pronto
    public Candidato(String nome, String cpf, String login, String senha, Curriculo curriculo){
        super(nome, cpf, login, senha);
        this.curriculo = curriculo;
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
