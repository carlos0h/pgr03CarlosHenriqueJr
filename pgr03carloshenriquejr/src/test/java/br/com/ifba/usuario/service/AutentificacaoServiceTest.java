package br.com.ifba.usuario.service;

import br.com.ifba.usuario.entity.Candidato;
import br.com.ifba.usuario.entity.Recrutador;
import br.com.ifba.usuario.interfaces.Autenticavel;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class AutentificacaoServiceTest {
    
    @Test
    public void candidatoAutenticaPeloTipoGeralComCredenciaisCertas(){
        
        // aqui o metodo recebe Autenticavel (tipo geral), mas quem vai chegar é um Candidato
        Autenticavel pessoa = new Candidato("Carlos", "12345678901", "carlos", "12345");
        
        boolean resultado = AutentificacaoService.processarAutentificacao(pessoa, "carlos", "12345");
        
        assertTrue(resultado);
            
    }
    
    @Test
    public void candidatoAutenticaPeloTipoGeralComCredenciaisErradas(){
        // agora quem chega é um Recrutador com senha errada
        Autenticavel pessoa = new Recrutador("Vanda", "98765432100", "vanda", "54321");
        boolean resultado = AutentificacaoService.processarAutentificacao(pessoa, "vanda", "errada");
        
        assertFalse(resultado);
        
    }
    
    
    
    
}