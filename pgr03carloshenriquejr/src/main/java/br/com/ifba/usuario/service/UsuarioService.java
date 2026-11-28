package br.com.ifba.usuario.service;

import br.com.ifba.infrastructure.exception.FalhaPersistenciaException;
import br.com.ifba.usuario.entity.Usuario;
import br.com.ifba.usuario.exception.DadosInvalidosException;
import br.com.ifba.usuario.exception.LoginDuplicadoException;
import br.com.ifba.usuario.repositorio.UsuarioRepository;
import br.com.ifba.usuario.validar.ValidadorUsuario;
import java.util.List;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

// regras de negocio de usuario; o service decide, o repositorio so grava e le
@Slf4j
@Service
@RequiredArgsConstructor
public class UsuarioService implements UsuarioIService {

    private final UsuarioRepository repository;

    @Override
    public void cadastrar(Usuario usuario) {
        log.info("Cadastrando usuário: {}", usuario.getLogin());
        if (!ValidadorUsuario.camposPreenchidos(usuario.getNome(), usuario.getCpf(),
                usuario.getEmail(), usuario.getLogin(), usuario.getSenha())) {
            throw recusar(usuario.getLogin(), "Preencha todos os campos.");
        }
        if (!ValidadorUsuario.cpfValido(usuario.getCpf())) {
            throw recusar(usuario.getLogin(), "CPF inválido. Digite 11 números.");
        }
        boolean senhaForte = ValidadorUsuario.senhaForte(usuario.getSenha());
        log.debug("Senha forte: {}", senhaForte);
        if (!senhaForte) {
            throw recusar(usuario.getLogin(), "Senha muito curta. Mínimo de 6 caracteres.");
        }
        if (ValidadorUsuario.palavraProibida(usuario.getLogin())
                || ValidadorUsuario.palavraProibida(usuario.getSenha())) {
            throw recusar(usuario.getLogin(), "Login ou senha contém palavra não permitida.");
        }
        if (comResultado("Não foi possível verificar o login.",
                () -> repository.existsByLogin(usuario.getLogin()))) {
            log.warn("Login já existe: {}", usuario.getLogin());
            throw new LoginDuplicadoException(usuario.getLogin());
        }
        semResultado("Não foi possível salvar o registro.", () -> repository.save(usuario));
        log.info("Usuário cadastrado: {}", usuario.getLogin());
    }

    @Override
    public Usuario atualizar(Usuario usuario) {
        log.info("Atualizando usuário: {}", usuario.getLogin());
        if (!ValidadorUsuario.camposPreenchidos(usuario.getNome(), usuario.getEmail(), usuario.getLogin())) {
            throw recusar(usuario.getLogin(), "Preencha todos os campos.");
        }
        if (ValidadorUsuario.palavraProibida(usuario.getLogin())) {
            throw recusar(usuario.getLogin(), "Login contém palavra não permitida.");
        }
        if (comResultado("Não foi possível verificar o login.",
                () -> repository.existsByLoginAndIdNot(usuario.getLogin(), usuario.getId()))) {
            log.warn("Login já existe: {}", usuario.getLogin());
            throw new LoginDuplicadoException(usuario.getLogin());
        }
        if (!comResultado("Não foi possível verificar o registro.",
                () -> repository.existsById(usuario.getId()))) {
            log.warn("Usuário {} não existe mais", usuario.getId());
            throw new FalhaPersistenciaException("O registro não existe mais.");
        }
        Usuario salvo = comResultado("Não foi possível atualizar o registro.", () -> repository.save(usuario));
        log.info("Usuário atualizado: {}", salvo.getLogin());
        return salvo;
    }

    @Override
    public void excluir(Usuario usuario) {
        log.info("Excluindo usuário: {}", usuario.getLogin());
        if (!comResultado("Não foi possível verificar o registro.",
                () -> repository.existsById(usuario.getId()))) {
            log.warn("Usuário {} não existe mais", usuario.getId());
            throw new FalhaPersistenciaException("O registro não existe mais.");
        }
        semResultado("Não foi possível remover o registro.", () -> repository.deleteById(usuario.getId()));
        log.info("Usuário excluído: {}", usuario.getLogin());
    }

    @Override
    public List<Usuario> listar() {
        log.debug("Listando usuários");
        return comResultado("Não foi possível listar os usuários.", repository::findAllByOrderByIdAsc);
    }

    @Override
    public List<Usuario> buscarPorNome(String texto) {
        log.debug("Buscando usuários pelo nome: '{}'", texto);
        // regra: busca em branco lista todos
        if (texto == null || texto.trim().isEmpty()) {
            return listar();
        }
        return comResultado("Não foi possível buscar os usuários.",
                () -> repository.findByNomeContainingIgnoreCaseOrderByNomeAsc(texto.trim()));
    }

    // registra a recusa no log e devolve a excecao para quem chamou lancar
    private DadosInvalidosException recusar(String login, String motivo) {
        log.warn("Dados recusados para '{}': {}", login, motivo);
        return new DadosInvalidosException(motivo);
    }

    // qualquer falha do banco vira a excecao do dominio, guardando a causa original
    private <T> T comResultado(String mensagem, Supplier<T> operacao) {
        try {
            return operacao.get();
        } catch (RuntimeException e) {
            log.error("Falha de persistência: {}", mensagem, e);
            throw new FalhaPersistenciaException(mensagem, e);
        }
    }

    private void semResultado(String mensagem, Runnable operacao) {
        try {
            operacao.run();
        } catch (RuntimeException e) {
            log.error("Falha de persistência: {}", mensagem, e);
            throw new FalhaPersistenciaException(mensagem, e);
        }
    }
}