
package br.com.ifba.usuario.service;
import br.com.ifba.usuario.interfaces.Autenticavel;

public class AutenticacaoService {
    
    // vai receber o tipo geral do Autenticavel
    // só quem chega aqui é um Candidato, Usuario comum ou Recrutador
    
    public static boolean processarAutenticacao(Autenticavel pessoa, String login, String senha){
        return pessoa.autenticar(login, senha);
    }
}
