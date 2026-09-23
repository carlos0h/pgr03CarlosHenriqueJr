/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.usuario.entity;


public class Experiencia {
   private String cargo;
   private String empresa;
   
   public Experiencia(){
       
   }
   
   public Experiencia(String cargo, String empresa){
       this.cargo = cargo;
       this.empresa = empresa;
   }
   public String getCargo() {
        return cargo;
    }
    
    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
    
    public String getEmpresa() {
        return empresa;
    }
    
    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }
}
