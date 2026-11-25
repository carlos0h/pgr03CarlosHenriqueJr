package br.com.ifba.usuario.service;

import br.com.ifba.infrastructure.exception.FalhaPersistenciaException;
import br.com.ifba.usuario.entity.Usuario;
import br.com.ifba.usuario.exception.DadosInvalidosException;
import br.com.ifba.usuario.exception.LoginDuplicadoException;
import br.com.ifba.usuario.repositorio.UsuarioRepository;
import br.com.ifba.usuario.validar.ValidadorUsuario;
import java.util.List;
import java.util.function.Supplier;
import org.springframework.stereotype.Service;

// regras de negocio de usuario; o service decide, o repositorio so grava e le
@Service
public class UsuarioService implements UsuarioIService {

    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
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
        if (comResultado("Não foi possível verificar o login.",
                () -> repository.existsByLogin(usuario.getLogin()))) {
            throw new LoginDuplicadoException(usuario.getLogin());
        }
        semResultado("Não foi possível salvar o registro.", () -> repository.save(usuario));
    }

    @Override
    public Usuario atualizar(Usuario usuario) {
        if (!ValidadorUsuario.camposPreenchidos(usuario.getNome(), usuario.getEmail(), usuario.getLogin())) {
            throw new DadosInvalidosException("Preencha todos os campos.");
        }
        if (ValidadorUsuario.palavraProibida(usuario.getLogin())) {
            throw new DadosInvalidosException("Login contém palavra não permitida.");
        }
        if (comResultado("Não foi possível verificar o login.",
                () -> repository.existsByLoginAndIdNot(usuario.getLogin(), usuario.getId()))) {
            throw new LoginDuplicadoException(usuario.getLogin());
        }
        if (!comResultado("Não foi possível verificar o registro.",
                () -> repository.existsById(usuario.getId()))) {
            throw new FalhaPersistenciaException("O registro não existe mais.");
        }
        return comResultado("Não foi possível atualizar o registro.", () -> repository.save(usuario));
    }

    @Override
    public void excluir(Usuario usuario) {
        if (!comResultado("Não foi possível verificar o registro.",
                () -> repository.existsById(usuario.getId()))) {
            throw new FalhaPersistenciaException("O registro não existe mais.");
        }
        semResultado("Não foi possível remover o registro.", () -> repository.deleteById(usuario.getId()));
    }

    @Override
    public List<Usuario> listar() {
        return comResultado("Não foi possível listar os usuários.", repository::findAllByOrderByIdAsc);
    }

    @Override
    public List<Usuario> buscarPorNome(String texto) {
        // regra: busca em branco lista todos
        if (texto == null || texto.trim().isEmpty()) {
            return listar();
        }
        return comResultado("Não foi possível buscar os usuários.",
                () -> repository.findByNomeContainingIgnoreCaseOrderByNomeAsc(texto.trim()));
    }

    // qualquer falha do banco vira a excecao do dominio, guardando a causa original
    private <T> T comResultado(String mensagem, Supplier<T> operacao) {
        try {
            return operacao.get();
        } catch (RuntimeException e) {
            throw new FalhaPersistenciaException(mensagem, e);
        }
    }

    private void semResultado(String mensagem, Runnable operacao) {
        try {
            operacao.run();
        } catch (RuntimeException e) {
            throw new FalhaPersistenciaException(mensagem, e);
        }
    }
}