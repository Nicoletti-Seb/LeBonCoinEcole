/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import javax.servlet.http.Part;
import modele.Category;
import modele.Email;
import modele.PhoneNumber;

/**
 *
 * @author Seb
 */
public class ReaderParts {
    
    public static byte[] readByteArray(Part part) throws IOException{
        if( part == null ){
            return null;
        }
        
        InputStream is = part.getInputStream();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buff = new byte[1024];
        
        while( is.available() > 0 ){
            int nb = is.read(buff);
            baos.write(buff, 0, nb);
        }
        
        is.close();
        return baos.toByteArray(); 
    }
    
    public static String readString(Part part)throws IOException {
        if( part == null ){
            return "";
        }
        
        Scanner scanf = new Scanner(part.getInputStream());
        String result = scanf.nextLine();
        scanf.close(); 
        return result;
    }
    
    public static float readNumbers(Part part)throws IOException {
        if( part == null ){
            return 0f;
        }
        
        Scanner scanf = new Scanner(part.getInputStream());
        
        float result;
        try{
            result = scanf.nextFloat();
        }catch(InputMismatchException e){
            result = 0;
        }
        scanf.close(); 
        return result;
    }
    
    public static List<Category> readCategories( Part part )throws IOException{
        Scanner scanf = new Scanner(part.getInputStream());
        List<Category> list = new ArrayList<Category>();
        
        while( scanf.hasNext() ){
            list.add(new Category(scanf.nextLine()));
        }
        scanf.close();
        
        return list;
    }
    
    public static List<PhoneNumber> readPhonesNumbers( Part part )throws IOException{
        Scanner scanf = new Scanner(part.getInputStream());
        List<PhoneNumber> list = new ArrayList<PhoneNumber>();
        
        while( scanf.hasNext() ){
            list.add(new PhoneNumber(scanf.nextLine()));
        }
        scanf.close();
        
        return list;
    }
    
    public static List<Email> readEmails( Part part )throws IOException{
        Scanner scanf = new Scanner(part.getInputStream());
        List<Email> list = new ArrayList<Email>();
        
        while( scanf.hasNext() ){
            list.add(new Email(scanf.nextLine()));
        }
        scanf.close();
        
        return list;
    }
}
