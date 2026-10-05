package br.com.ifba.usuario.repositorio;

import br.com.ifba.usuario.entity.Usuario;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RepositorioUsuarioEmMemoriaTest {
    
    @Test
    public void cadastrarUmUsuarioEEleApareceEmListarTodos(){
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
        
    assertEquals(carlos, repositorio.buscaPorLogin("carlos"));
    assertEquals(ana, repositorio.buscaPorLogin("ana"));
    }
    
    
    @Test
    public void buscarPorLoginComLoginQueNaoExisteDevolveNulo() {
    RepositorioUsuarioEmMemoria repositorio = new RepositorioUsuarioEmMemoria();
        
    Usuario resultado = repositorio.buscaPorLogin("naoExiste");
        
    assertNull(resultado);
    }
    
    
    @Test
    public void cadastrarLoginDuplicadoLancaExcecao() {
    RepositorioUsuarioEmMemoria repositorio = new RepositorioUsuarioEmMemoria();
    Usuario u1 = new Usuario("Carlos", "12345678901", "carlos", "12345");
    Usuario u2 = new Usuario("Outro Carlos", "11122233344", "carlos", "senha2");
        
    repositorio.cadastrar(u1);
        
    assertThrows(IllegalArgumentException.class, () -> {
    repositorio.cadastrar(u2);
    });
    }
}
