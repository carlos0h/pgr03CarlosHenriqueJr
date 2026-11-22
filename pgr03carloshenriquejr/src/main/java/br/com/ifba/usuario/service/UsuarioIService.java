package br.com.ifba.usuario.service;

import br.com.ifba.usuario.entity.Usuario;
import java.util.List;

// contrato das regras de negocio de usuario
public interface UsuarioIService {

    void cadastrar(Usuario usuario);

    Usuario atualizar(Usuario usuario);

    void excluir(Usuario usuario);

    List<Usuario> listar();

    List<Usuario> buscarPorNome(String texto);
}