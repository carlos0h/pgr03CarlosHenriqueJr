package br.com.ifba.usuario.repositorio;

import br.com.ifba.usuario.entity.Usuario;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import br.com.ifba.usuario.exception.UsuarioNaoEncontradoException;
import br.com.ifba.usuario.exception.LoginDuplicadoException;

public class RepositorioUsuarioEmMemoria {
    
    private final List<Usuario> usuarios = new ArrayList<>();
    // o final é pq não pode ser reatribuida a outra lista 
    private final Map<String, Usuario> porLogin = new HashMap<>();
    // map(guarda pares de chaves) e list(guarda itens numa sequencia)
    public void cadastrar(Usuario usuario){
        
       if (porLogin.containsKey(usuario.getLogin())) {
       throw new LoginDuplicadoException(usuario.getLogin());
}
        usuarios.add(usuario);
        porLogin.put(usuario.getLogin(), usuario);
    }
    
    public List<Usuario> listarTodos() {
        return usuarios;
    }
    
    public Usuario buscarPorLogin(String login) {
    Usuario usuario = porLogin.get(login);
    if (usuario == null) {
        throw new UsuarioNaoEncontradoException(login);
    }
    return usuario;
    }
    
}
