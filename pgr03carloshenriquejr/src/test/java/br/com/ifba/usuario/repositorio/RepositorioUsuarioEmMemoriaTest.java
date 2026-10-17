package br.com.ifba.usuario.repositorio;

import br.com.ifba.usuario.entity.Usuario;
import br.com.ifba.usuario.exception.LoginDuplicadoException;
import br.com.ifba.usuario.exception.UsuarioNaoEncontradoException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RepositorioUsuarioEmMemoriaTest {

    @Test
    public void cadastrarUmUsuarioEEleApareceEmListarTodos() {
        RepositorioUsuarioEmMemoria repositorio = new RepositorioUsuarioEmMemoria();
        Usuario usuario = new Usuario("Carlos", "12345678901", "carlos", "12345");

        repositorio.cadastrar(usuario);

        assertTrue(repositorio.listarTodos().contains(usuario));
    }

    @Test
    public void cadastraDoisEBuscarPorLoginDevolveOCerto() {
        RepositorioUsuarioEmMemoria repositorio = new RepositorioUsuarioEmMemoria();
        Usuario carlos = new Usuario("Carlos", "12345678901", "carlos", "12345");
        Usuario ana = new Usuario("Ana", "98765432100", "ana", "54321");

        repositorio.cadastrar(carlos);
        repositorio.cadastrar(ana);

        assertEquals(carlos, repositorio.buscarPorLogin("carlos"));
        assertEquals(ana, repositorio.buscarPorLogin("ana"));
    }

    @Test
    public void buscarPorLoginComLoginQueNaoExisteLancaExcecao() {
        RepositorioUsuarioEmMemoria repositorio = new RepositorioUsuarioEmMemoria();

        assertThrows(UsuarioNaoEncontradoException.class, () -> {
            repositorio.buscarPorLogin("naoExiste");
        });
    }

    @Test
    public void cadastrarLoginDuplicadoLancaExcecao() {
        RepositorioUsuarioEmMemoria repositorio = new RepositorioUsuarioEmMemoria();
        Usuario u1 = new Usuario("Carlos", "12345678901", "carlos", "12345");
        Usuario u2 = new Usuario("Outro Carlos", "11122233344", "carlos", "senha2");

        repositorio.cadastrar(u1);

        LoginDuplicadoException e = assertThrows(LoginDuplicadoException.class, () -> {
            repositorio.cadastrar(u2);
        });
        assertTrue(e.getMessage().contains("carlos"));
    }

    @Test
    public void cadastrarLoginsDiferentesNaoLancaExcecao() {
        RepositorioUsuarioEmMemoria repositorio = new RepositorioUsuarioEmMemoria();
        Usuario carlos = new Usuario("Carlos", "12345678901", "carlos", "12345");
        Usuario ana = new Usuario("Ana", "98765432100", "ana", "54321");

        repositorio.cadastrar(carlos);

        assertDoesNotThrow(() -> repositorio.cadastrar(ana));
    }

}