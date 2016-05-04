/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import modele.Category;

/**
 *
 * @author LeoPaul
 */
public class RandomBuild {
    
    private static final Random r = new Random();
    private static final String letters = "abcdefghijklmnopqrstuvwxyz";
    private static final String numbers = "0123456789";
    
    // methode pour générer nom aléatoire
    public static String name() {
        StringBuilder stringbuilder = new StringBuilder();
        for(int i = 0; i < 8; i++) {
            stringbuilder.append(letters.charAt(r.nextInt(letters.length())));
        }    
        return stringbuilder.toString();
    }
    
    public static String telNumber() {
        StringBuilder stringbuilder = new StringBuilder("06");
        for(int i = 0; i < 8; i++) {
            stringbuilder.append(numbers.charAt(r.nextInt(numbers.length())));
        }    
        return stringbuilder.toString();
    }
    
    public static String number(int length) {
        StringBuilder stringbuilder = new StringBuilder();
        for(int i = 0; i < length; i++) {
            stringbuilder.append(numbers.charAt(r.nextInt(numbers.length())));
        }    
        return stringbuilder.toString();
    }
    
    public static String word() {
        StringBuilder stringbuilder = new StringBuilder();
        int size = r.nextInt(8) + 2;
        for(int i = 0; i < size; i++) {
            stringbuilder.append(letters.charAt(r.nextInt(letters.length())));
        }    
        return stringbuilder.toString();
    }
    
    public static String title() {
        StringBuilder stringbuilder = new StringBuilder();
        int nbWord = r.nextInt(3)+1;
        for(int i = 0; i < nbWord; i++) {
            stringbuilder.append(word()).append(" ");
        }
        return stringbuilder.toString();
    }
    
    public static String description() {
        StringBuilder stringbuilder = new StringBuilder();
        int nbWord = r.nextInt(100)+5;
        for(int i = 0; i < nbWord; i++) {
            stringbuilder.append(word()).append(" ");
        }
        return stringbuilder.toString();
    }
    
    public static float price() {
        int res = (int) (r.nextFloat()*r.nextInt(1000) * 100);
        return ((float) res) /100;
    }

    public static List<Category> categories(Collection<Category> allCategories) {
        ArrayList<Category> listCat = new ArrayList<>();
        int nbCat = r.nextInt(4) + 1;
        for(int i = 0; i<nbCat; i++) {
            Category randomCategorie = getRandomCategorie(r.nextInt(allCategories.size()), allCategories);
            listCat.add(randomCategorie);
        }
        return listCat;
    }
    
    public static Category getRandomCategorie(int index, Collection<Category> allCategories) {
        Iterator<Category> iterator = allCategories.iterator();
        int i = 0;
        while(iterator.hasNext()) {
            if(index == i) {
                return iterator.next();
            } else {
                iterator.next();
            }
        }
        
        return null;
    }
}
