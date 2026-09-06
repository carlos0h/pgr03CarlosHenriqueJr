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
}
