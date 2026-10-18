package br.com.ifba.usuario.exception;

// lancada quando se procura um usuario que nao esta no banco
public class UsuarioNaoEncontradoException extends RuntimeException {

    public UsuarioNaoEncontradoException(Long id) {
        super("Usuário com id " + id + " não foi encontrado.");
    }


public UsuarioNaoEncontradoException(String login) {
    super("Usuário com login \"" + login + "\" não foi encontrado.");
}
}
