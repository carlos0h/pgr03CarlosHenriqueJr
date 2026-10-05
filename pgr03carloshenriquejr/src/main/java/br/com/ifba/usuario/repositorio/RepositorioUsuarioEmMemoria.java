package br.com.ifba.usuario.repositorio;

import br.com.ifba.usuario.entity.Usuario;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class RepositorioUsuarioEmMemoria {
    
    private final List<Usuario> usuarios = new ArrayList<>();
    // o final é pq não pode ser reatribuida a outra lista 
    private final Map<String, Usuario> porLogin = new HashMap<>();
    // map(guarda pares de chaves) e list(guarda itens numa sequencia)
    public void cadastrar(Usuario usuario){
        if (porLogin.containsKey(usuario.getLogin())) {
            throw new IllegalArgumentException("Já existe um usuário cadastrado com esse login.");
        }
        usuarios.add(usuario);
        porLogin.put(usuario.getLogin(), usuario);
    }
    
    public List<Usuario> listarTodos() {
        return usuarios;
    }
    
    public Usuario buscaPorLogin(String login){
        return porLogin.get(login);
    }
    
}
