/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.usuario.validar;

/**
 *
 * @author carlos
 */
public class ValidadorUsuario {
    public static boolean palavraProibida(String texto){
        String[] palavraP = {"admin", "root", "senha123"};
        
        for (String palavra : palavraP){
            if(texto.toLowerCase().contains(palavra.toLowerCase())){
                return true;
            }
        }
        return false;
    }
    
    //retorna true se o cpf estiver com os 11 digitos numeros, caso contrario vai retornar false
    public static boolean cpfValido(String cpf){
        if(cpf == null){
            return false;
        }
        return cpf.matches("\\d{11}");
    }
    
    // true se a senha for forte e false para senha fraca
    public static boolean senhaForte(String senha){
        if (senha == null){
            return false;
        } 
        return senha.length() >=6;
    }
   
    
    //true se os campos estiverem preenchidos e false caso for o contrario
    public static boolean camposPreenchidos(String... campos){
       for (String campo : campos){
           if(campo == null || campo.trim().isEmpty()){
               return false;
           } 
       }
       return true;
    }
}
