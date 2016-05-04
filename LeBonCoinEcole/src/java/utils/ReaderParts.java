/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import javax.servlet.http.Part;

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
    
    public static boolean readBoolean(Part part)throws IOException {
        if( part == null ){
            return false;
        }
        
        Scanner scanf = new Scanner(part.getInputStream());
        boolean result = Boolean.parseBoolean(scanf.nextLine());
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
            /*Scanner.nextFloat ne peut pas être utilisé
              car le formulaire renvoie un nombre avec une virgule
              au lieu d'un point.*/
            String value = scanf.nextLine();
            result = Float.parseFloat(value);
        }catch(NumberFormatException e){
            result = 0;
        }
        scanf.close(); 
        return result;
    }
}
