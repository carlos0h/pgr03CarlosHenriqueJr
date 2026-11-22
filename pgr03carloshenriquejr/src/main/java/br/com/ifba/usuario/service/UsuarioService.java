package br.com.ifba.usuario.service;

import br.com.ifba.usuario.dao.UsuarioIDao;
import br.com.ifba.usuario.entity.Usuario;
import br.com.ifba.usuario.exception.DadosInvalidosException;
import br.com.ifba.usuario.exception.LoginDuplicadoException;
import br.com.ifba.usuario.validar.ValidadorUsuario;
import java.util.List;

// regras de negocio de usuario; o service decide, o dao so grava e le
public class UsuarioService implements UsuarioIService {

    // depende da INTERFACE do dao, nunca da classe concreta
    private final UsuarioIDao dao;

    public UsuarioService(UsuarioIDao dao) {
        this.dao = dao;
    }

    @Override
    public void cadastrar(Usuario usuario) {
        if (!ValidadorUsuario.camposPreenchidos(usuario.getNome(), usuario.getCpf(),
                usuario.getEmail(), usuario.getLogin(), usuario.getSenha())) {
            throw new DadosInvalidosException("Preencha todos os campos.");
        }
        if (!ValidadorUsuario.cpfValido(usuario.getCpf())) {
            throw new DadosInvalidosException("CPF inválido. Digite 11 números.");
        }
        if (!ValidadorUsuario.senhaForte(usuario.getSenha())) {
            throw new DadosInvalidosException("Senha muito curta. Mínimo de 6 caracteres.");
        }
        if (ValidadorUsuario.palavraProibida(usuario.getLogin())
                || ValidadorUsuario.palavraProibida(usuario.getSenha())) {
            throw new DadosInvalidosException("Login ou senha contém palavra não permitida.");
        }
        if (dao.loginEmUso(usuario.getLogin(), null)) {
            throw new LoginDuplicadoException(usuario.getLogin());
        }
        dao.save(usuario);
    }

    @Override
    public Usuario atualizar(Usuario usuario) {
        if (!ValidadorUsuario.camposPreenchidos(usuario.getNome(), usuario.getEmail(), usuario.getLogin())) {
            throw new DadosInvalidosException("Preencha todos os campos.");
        }
        if (ValidadorUsuario.palavraProibida(usuario.getLogin())) {
            throw new DadosInvalidosException("Login contém palavra não permitida.");
        }
        if (dao.loginEmUso(usuario.getLogin(), usuario.getId())) {
            throw new LoginDuplicadoException(usuario.getLogin());
        }
        return dao.update(usuario);
    }

    @Override
    public void excluir(Usuario usuario) {
        dao.delete(usuario);
    }

    @Override
    public List<Usuario> listar() {
        return dao.findAll();
    }

    @Override
    public List<Usuario> buscarPorNome(String texto) {
        // regra: busca em branco lista todos
        if (texto == null || texto.trim().isEmpty()) {
            return dao.findAll();
        }
        return dao.findByNome(texto.trim());
    }
}