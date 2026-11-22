package br.com.ifba.usuario.exception;

// lancada quando os dados informados nao respeitam as regras do usuario
public class DadosInvalidosException extends RuntimeException {

    public DadosInvalidosException(String mensagem) {
        super(mensagem);
    }
}