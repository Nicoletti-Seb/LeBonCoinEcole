/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.Random;

/**
 *
 * @author LeoPaul
 */
public class RandomMot {
    
    private static final Random r = new Random();
    private static final String lettres = "abcdefghijklmnopqrstuvwxyz-0123456789";
    
    // methode pour générer nom aléatoire
    public static String build() {
        
        StringBuilder stringbuilder = new StringBuilder();
        for(int i = 0; i < 8; i++) {
            stringbuilder.append(lettres.charAt(r.nextInt(lettres.length())));
        }    
        return stringbuilder.toString();
    }
}
