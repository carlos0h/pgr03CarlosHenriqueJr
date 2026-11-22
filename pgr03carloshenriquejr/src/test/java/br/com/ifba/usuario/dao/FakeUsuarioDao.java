package br.com.ifba.usuario.dao;

import br.com.ifba.infrastructure.exception.FalhaPersistenciaException;
import br.com.ifba.usuario.entity.Usuario;
import br.com.ifba.usuario.exception.UsuarioNaoEncontradoException;
import java.util.ArrayList;
import java.util.List;

// dublê de teste: faz o papel do UsuarioIDao guardando tudo numa List em memoria, sem banco
public class FakeUsuarioDao implements UsuarioIDao {

    private final List<Usuario> usuarios = new ArrayList<>();
    private long proximoId = 1;

    @Override
    public void save(Usuario usuario) {
        usuario.setId(proximoId++); // o banco de verdade gera o id; aqui o fake faz isso
        usuarios.add(usuario);
    }

    @Override
    public Usuario update(Usuario usuario) {
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getId().equals(usuario.getId())) {
                usuarios.set(i, usuario);
                return usuario;
            }
        }
        throw new FalhaPersistenciaException("O registro não existe mais.");
    }

    @Override
    public void delete(Usuario usuario) {
        boolean removido = usuarios.removeIf(u -> u.getId().equals(usuario.getId()));
        if (!removido) {
            throw new FalhaPersistenciaException("O registro não existe mais.");
        }
    }

    @Override
    public Usuario findById(Long id) {
        for (Usuario u : usuarios) {
            if (u.getId().equals(id)) {
                return u;
            }
        }
        throw new FalhaPersistenciaException("Registro com id " + id + " não encontrado.");
    }

    @Override
    public List<Usuario> findAll() {
        return new ArrayList<>(usuarios);
    }

    @Override
    public Usuario findByLogin(String login) {
        for (Usuario u : usuarios) {
            if (u.getLogin().equals(login)) {
                return u;
            }
        }
        throw new UsuarioNaoEncontradoException(login);
    }

    @Override
    public List<Usuario> findByNome(String texto) {
        List<Usuario> resultado = new ArrayList<>();
        for (Usuario u : usuarios) {
            if (u.getNome().toLowerCase().contains(texto.toLowerCase())) {
                resultado.add(u);
            }
        }
        return resultado;
    }

    @Override
    public boolean loginEmUso(String login, Long idIgnorado) {
        for (Usuario u : usuarios) {
            boolean ehOutroUsuario = idIgnorado == null || !idIgnorado.equals(u.getId());
            if (ehOutroUsuario && u.getLogin().equals(login)) {
                return true;
            }
        }
        return false;
    }
}