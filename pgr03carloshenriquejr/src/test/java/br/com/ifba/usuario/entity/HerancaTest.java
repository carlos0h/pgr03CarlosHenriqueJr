package br.com.ifba.usuario.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HerancaTest {
    
    @Test
    public void candidatoDeveAutenticarUsandoMetodoHerdadoDeUsuario() {
        // Candidato nao reescreve autenticar() - o metodo vem herdado de Usuario
        Candidato candidato = new Candidato("Carlos", "12345678901", "carlos", "12345");
        
        boolean resultado = candidato.autenticar("carlos", "12345");
        
        assertTrue(resultado);
    }
    
    @Test
    public void recrutadorDeveAutenticarUsandoMetodoHerdadoDeUsuario() {
        // mesmo comportamento herdado, testado tambem em Recrutador
        Recrutador recrutador = new Recrutador("Ana", "10987654321", "ana", "54321");
        
        boolean resultado = recrutador.autenticar("ana", "54321");
        
        assertTrue(resultado);
    }
    
    @Test
    public void candidatoDeveSobrescreverDescricaoComTextoProprio() {
        // Candidato sobrescreve getDescricao() com um texto diferente de Usuario
        Candidato candidato = new Candidato("Carlos", "12345678901", "carlos", "12345");
        
        String descricao = candidato.getDescricao();
        
        assertEquals("Candidato: Carlos", descricao);
    }
    
    @Test
    public void recrutadorDeveSobrescreverDescricaoComTextoProprio() {
        // Recrutador sobrescreve getDescricao() com um texto diferente de Usuario e de Candidato
        Recrutador recrutador = new Recrutador("Ana", "10987654321", "ana", "54321");
        
        String descricao = recrutador.getDescricao();
        
        assertEquals("Recrutador: Ana", descricao);
    }

}