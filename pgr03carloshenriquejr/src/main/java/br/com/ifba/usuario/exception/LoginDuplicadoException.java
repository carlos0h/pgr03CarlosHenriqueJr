package br.com.ifba.usuario.exception;

// lancada quando se tenta cadastrar um login que ja existe
public class LoginDuplicadoException extends RuntimeException {

    public LoginDuplicadoException(String login) {
        super("Já existe um usuário cadastrado com o login \"" + login + "\".");
    }
}