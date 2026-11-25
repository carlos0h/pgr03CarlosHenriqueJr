package br.com.ifba.usuario.dao;

import br.com.ifba.usuario.entity.Usuario;
import br.com.ifba.usuario.repositorio.UsuarioRepository;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

// dublê de teste: faz o papel do UsuarioRepository guardando tudo numa List em memoria, sem banco
public class FakeUsuarioDao implements UsuarioRepository {

    private final List<Usuario> usuarios = new ArrayList<>();
    private long proximoId = 1;

    @Override
    public <S extends Usuario> S save(S usuario) {
        if (usuario.getId() == null) {
            usuario.setId(proximoId++); // o banco gera o id; aqui o dublê faz isso
            usuarios.add(usuario);
            return usuario;
        }
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getId().equals(usuario.getId())) {
                usuarios.set(i, usuario);
                return usuario;
            }
        }
        usuarios.add(usuario);
        return usuario;
    }

    
    @Override
    public boolean existsById(Long id) {
        return usuarios.stream().anyMatch(u -> u.getId().equals(id));
    }

    @Override
    public void deleteById(Long id) {
        usuarios.removeIf(u -> u.getId().equals(id));
    }

    @Override
    public List<Usuario> findAllByOrderByIdAsc() {
        return new ArrayList<>(usuarios);
    }

    @Override
    public List<Usuario> findByNomeContainingIgnoreCaseOrderByNomeAsc(String texto) {
        List<Usuario> resultado = new ArrayList<>();
        for (Usuario u : usuarios) {
            if (u.getNome().toLowerCase().contains(texto.toLowerCase())) {
                resultado.add(u);
            }
        }
        resultado.sort(Comparator.comparing(Usuario::getNome));
        return resultado;
    }

    @Override
    public boolean existsByLogin(String login) {
        return usuarios.stream().anyMatch(u -> u.getLogin().equals(login));
    }

    @Override
    public boolean existsByLoginAndIdNot(String login, Long id) {
        return usuarios.stream().anyMatch(u -> u.getLogin().equals(login) && !u.getId().equals(id));
    }
    
    @Override
    public List<Usuario> buscarPorNomeOuLogin(String texto) {
        String procurado = texto.toLowerCase();
        List<Usuario> resultado = new ArrayList<>();
        for (Usuario u : usuarios) {
            if (u.getNome().toLowerCase().contains(procurado) || u.getLogin().toLowerCase().contains(procurado)) {
                resultado.add(u);
            }
        }
        resultado.sort(Comparator.comparing(Usuario::getNome));
        return resultado;
    }
    
    // apoio dos testes (nao fazem parte do contrato do repositorio)
    public List<Usuario> findAll() {
        return new ArrayList<>(usuarios);
    }

    public Usuario findById(Long id) {
        return usuarios.stream().filter(u -> u.getId().equals(id)).findFirst().orElseThrow();
    }
    
}