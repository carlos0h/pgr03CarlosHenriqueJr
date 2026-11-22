package br.com.ifba.usuario.controller;

import br.com.ifba.usuario.entity.Usuario;
import java.util.List;

// o que as telas podem pedir; e a unica coisa que a view conhece
public interface UsuarioIController {

    void cadastrar(String nome, String cpf, String email, String login, String senha);

    void atualizar(Usuario original, String nome, String email, String login);

    void excluir(Usuario usuario);

    List<Usuario> listar();

    List<Usuario> buscarPorNome(String texto);
}

