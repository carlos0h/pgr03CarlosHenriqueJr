/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package br.com.ifba.usuario.validar;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class ValidadorUsuarioTest {
    
    public ValidadorUsuarioTest() {
   
        
    }
    @Test
    public void deveRetornarTrueParaCPFValido(){
             //organiza, age e verifica
        
        String cpf = "12345678901";
        
        boolean resultado = ValidadorUsuario.cpfValido(cpf);
        
        assertTrue(resultado);
        
    }
    @Test
    public void deveRetornarFalseParaCPFVazio(){
        String cpf = "";
        
        boolean resultado = ValidadorUsuario.cpfValido(cpf);
        
        assertFalse(resultado);
        
    }
    
    @Test
    public void deveRetornarFalseParaCPFcomLetras(){
        String cpf = "123acbd4567";
        
        boolean resultado = ValidadorUsuario.cpfValido(cpf); 
        
        assertFalse(resultado);
        
    }
    @Test
    public void deveRetornarFalseParaCPFNulo(){
        String cpf = null;
        
        boolean resultado = ValidadorUsuario.cpfValido(cpf);
        
        assertFalse(resultado);
        
    }
    @Test
    public void deveRetornarTrueParaSenhaComTamanhoMinimo(){
    String senha = "123456"; // exatamente 6 caracteres
    
    boolean resultado = ValidadorUsuario.senhaForte(senha);
    
    assertTrue(resultado);
}

    @Test
    public void deveRetornarFalseParaSenhaMenorQueOMinimo(){
    String senha = "12345"; // 5 caracteres, um a menos que o minimo
    
    boolean resultado = ValidadorUsuario.senhaForte(senha);
    
    assertFalse(resultado);
}

    @Test
    public void deveRetornarFalseParaSenhaNula(){
    String senha = null;
    
    boolean resultado = ValidadorUsuario.senhaForte(senha);
    
    assertFalse(resultado);
}
    @Test
    public void deveRetornarTrueQuandoTodosCamposPreenchidos(){
    boolean resultado = ValidadorUsuario.camposPreenchidos("nome", "12345678901", "email@teste.com");
    
    assertTrue(resultado);
}

    @Test
    public void deveRetornarFalseQuandoAlgumCampoVazio(){
    boolean resultado = ValidadorUsuario.camposPreenchidos("nome", "", "email@teste.com");
    
    assertFalse(resultado);
}

    @Test
    public void deveRetornarFalseQuandoAlgumCampoNulo(){
    boolean resultado = ValidadorUsuario.camposPreenchidos("nome", null, "email@teste.com");
    
    assertFalse(resultado);
}

    @Test
    public void deveRetornarFalseQuandoCampoSoComEspacos(){
    boolean resultado = ValidadorUsuario.camposPreenchidos("nome", "   ", "email@teste.com");
    
    assertFalse(resultado);
}
}