
package br.com.ifba.usuario.entity;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class UsuarioTest {
    
    @Test
    public void deveAutenticarComCredenciaisCorretas(){
        // arrange
        Usuario usuario = new Usuario();
        usuario.setLogin("carlos");
        usuario.setSenha("12345");
        //act
        boolean resultado = usuario.autenticar("carlos", "12345");
        //assert
        assertTrue(resultado);
        
    }
    @Test
    public void naoDeveAutenticarComSenhaIncorreta(){
        
        Usuario usuario = new Usuario();
        usuario.setLogin("carlos");
        usuario.setSenha("12345");
        
        boolean resultado = usuario.autenticar("carlos", "senhaErrada");
        
        assertFalse(resultado);
    }
    
    
    
}