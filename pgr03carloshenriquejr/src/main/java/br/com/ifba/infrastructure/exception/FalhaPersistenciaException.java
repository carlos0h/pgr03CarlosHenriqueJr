package br.com.ifba.infrastructure.exception;

// lancada quando uma operacao de acesso ao banco falha
public class FalhaPersistenciaException extends RuntimeException {

    public FalhaPersistenciaException(String mensagem) {
        super(mensagem);
    }

    // guarda tambem o erro original (a causa), pra nao perder o detalhe tecnico
    public FalhaPersistenciaException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}