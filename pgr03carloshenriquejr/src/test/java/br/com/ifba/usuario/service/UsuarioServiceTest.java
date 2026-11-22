package br.com.ifba.usuario.service;

import br.com.ifba.usuario.dao.FakeUsuarioDao;
import br.com.ifba.usuario.entity.Usuario;
import br.com.ifba.usuario.exception.DadosInvalidosException;
import br.com.ifba.usuario.exception.LoginDuplicadoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UsuarioServiceTest {

    private FakeUsuarioDao dao;
    private UsuarioService service;

    // antes de cada teste: um dao falso novo (vazio) e um service que recebe ele
    @BeforeEach
    public void preparar() {
        dao = new FakeUsuarioDao();
        service = new UsuarioService(dao);
    }

    private Usuario usuario(String nome, String cpf, String login, String senha) {
        Usuario u = new Usuario(nome, cpf, login, senha);
        u.setEmail(login + "@email.com");
        return u;
    }

    @Test
    public void cadastrarUsuarioValidoAparecenaListagem() {
        Usuario carlos = usuario("Carlos", "12345678901", "carlos", "123456");

        service.cadastrar(carlos);

        assertEquals(1, service.listar().size());
        assertNotNull(carlos.getId());
    }

    @Test
    public void cadastrarComCampoVazioLancaDadosInvalidos() {
        Usuario semNome = usuario("", "12345678901", "carlos", "123456");

        DadosInvalidosException e = assertThrows(DadosInvalidosException.class,
                () -> service.cadastrar(semNome));

        assertEquals("Preencha todos os campos.", e.getMessage());
        assertTrue(dao.findAll().isEmpty()); // nada foi gravado
    }

    @Test
    public void cadastrarComCpfComLetrasLancaDadosInvalidos() {
        Usuario cpfRuim = usuario("Carlos", "1234567890a", "carlos", "123456");

        DadosInvalidosException e = assertThrows(DadosInvalidosException.class,
                () -> service.cadastrar(cpfRuim));

        assertEquals("CPF inválido. Digite 11 números.", e.getMessage());
        assertTrue(dao.findAll().isEmpty());
    }

    @Test
    public void cadastrarComSenhaCurtaLancaDadosInvalidos() {
        Usuario senhaCurta = usuario("Carlos", "12345678901", "carlos", "12345");

        assertThrows(DadosInvalidosException.class, () -> service.cadastrar(senhaCurta));
        assertTrue(dao.findAll().isEmpty());
    }

    @Test
    public void cadastrarComPalavraProibidaNoLoginLancaDadosInvalidos() {
        Usuario loginProibido = usuario("Carlos", "12345678901", "admin", "123456");

        assertThrows(DadosInvalidosException.class, () -> service.cadastrar(loginProibido));
        assertTrue(dao.findAll().isEmpty());
    }

    @Test
    public void cadastrarComPalavraProibidaNaSenhaLancaDadosInvalidos() {
        Usuario senhaProibida = usuario("Carlos", "12345678901", "carlos", "senha123");

        assertThrows(DadosInvalidosException.class, () -> service.cadastrar(senhaProibida));
        assertTrue(dao.findAll().isEmpty());
    }

    @Test
    public void cadastrarLoginDuplicadoLancaLoginDuplicadoException() {
        service.cadastrar(usuario("Carlos", "12345678901", "carlos", "123456"));
        Usuario outro = usuario("Outro Carlos", "98765432100", "carlos", "654321");

        assertThrows(LoginDuplicadoException.class, () -> service.cadastrar(outro));
        assertEquals(1, dao.findAll().size());
    }

    @Test
    public void atualizarMantendoOProprioLoginNaoLancaExcecao() {
        service.cadastrar(usuario("Carlos", "12345678901", "carlos", "123456"));
        Usuario ana = usuario("Ana", "98765432100", "ana", "654321");
        service.cadastrar(ana);

        Usuario atualizado = usuario("Ana Maria", "98765432100", "ana", "654321");
        atualizado.setId(ana.getId());

        assertDoesNotThrow(() -> service.atualizar(atualizado));
        assertEquals("Ana Maria", dao.findById(ana.getId()).getNome());
    }

    @Test
    public void atualizarComLoginDeOutroUsuarioLancaLoginDuplicadoException() {
        service.cadastrar(usuario("Carlos", "12345678901", "carlos", "123456"));
        Usuario ana = usuario("Ana", "98765432100", "ana", "654321");
        service.cadastrar(ana);

        Usuario atualizado = usuario("Ana", "98765432100", "carlos", "654321");
        atualizado.setId(ana.getId());

        assertThrows(LoginDuplicadoException.class, () -> service.atualizar(atualizado));
    }

    @Test
    public void atualizarComEmailVazioLancaDadosInvalidos() {
        Usuario ana = usuario("Ana", "98765432100", "ana", "654321");
        service.cadastrar(ana);

        Usuario atualizado = usuario("Ana", "98765432100", "ana", "654321");
        atualizado.setId(ana.getId());
        atualizado.setEmail("");

        assertThrows(DadosInvalidosException.class, () -> service.atualizar(atualizado));
    }

    @Test
    public void buscarPorNomeEmBrancoListaTodos() {
        service.cadastrar(usuario("Carlos", "12345678901", "carlos", "123456"));
        service.cadastrar(usuario("Ana", "98765432100", "ana", "654321"));

        assertEquals(2, service.buscarPorNome("   ").size());
        assertEquals(2, service.buscarPorNome(null).size());
    }

    @Test
    public void buscarPorNomeFiltraSemDiferenciarMaiusculas() {
        service.cadastrar(usuario("Carlos", "12345678901", "carlos", "123456"));
        service.cadastrar(usuario("Ana", "98765432100", "ana", "654321"));

        assertEquals(1, service.buscarPorNome("CAR").size());
    }

    @Test
    public void excluirRemoveDaListagem() {
        Usuario carlos = usuario("Carlos", "12345678901", "carlos", "123456");
        service.cadastrar(carlos);

        service.excluir(carlos);

        assertTrue(service.listar().isEmpty());
    }
}