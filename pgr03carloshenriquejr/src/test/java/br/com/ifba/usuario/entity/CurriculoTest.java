package br.com.ifba.usuario.entity;
import br.com.ifba.usuario.enums.StatusCurriculo;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CurriculoTest {
    
    @Test
    public void aoAdicionarExperienciaListaDeveCrescer() {
        
        Curriculo curriculo = new Curriculo();
        Experiencia experiencia = new Experiencia("Desenvolvedor", "Empresa X");
        
      
        curriculo.adicionarExperiencia(experiencia);
        
        
        assertEquals(1, curriculo.getExperiencias().size()); // vai verificar se dois valores sao iguais
    }
    
    @Test
    public void curriculoRecemCriadoDeveNascerComStatusIncompleto(){
        Curriculo curriculo = new Curriculo();
        
        assertEquals(StatusCurriculo.INCOMPLETO, curriculo.getStatus()); // esperado/recebido
    }
    
    @Test
    public void usuarioDeveDevolverOMesmoCurriculoQueFoiDefinido(){
        Usuario usuario = new Usuario();
        Curriculo curriculo = new Curriculo();
        
        usuario.setCurriculo(curriculo);
        
        assertSame(curriculo, usuario.getCurriculo());
    }
}