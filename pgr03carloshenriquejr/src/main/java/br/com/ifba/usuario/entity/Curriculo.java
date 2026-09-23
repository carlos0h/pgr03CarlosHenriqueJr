
package br.com.ifba.usuario.entity;
import java.util.ArrayList;
import java.util.List;
import br.com.ifba.usuario.enums.StatusCurriculo;

public class Curriculo {
    private String resumo;
    private List<Experiencia> experiencias;
    private StatusCurriculo status;
    
    public Curriculo(){
        this.experiencias = new ArrayList<>();
        this.status = StatusCurriculo.INCOMPLETO;
    }
    
    public Curriculo(String resumo){
        this.resumo = resumo;
        this.experiencias = new ArrayList<>();
        this.status = StatusCurriculo.INCOMPLETO;
    }
    
    public void adicionarExperiencia(Experiencia experiencia){
        this.experiencias.add(experiencia);
    }
    
    public void setStatus(StatusCurriculo status){
        this.status = status; 
        
    }
    
    public List<Experiencia> getExperiencias(){
        return experiencias;
    }
    
    public StatusCurriculo getStatus(){
        return status;
    }
    
    public String getResumo(){
        return resumo;
    }
    
    public void setResumo(String resumo){
        this.resumo = resumo;
    }
    
    
    
}
