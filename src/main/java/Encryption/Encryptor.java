/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Encryption;

/**
 *
 * @author demon
 */
public class Encryptor {
    
    public static String encrypt(String str, String key) {
        StringBuilder out = new StringBuilder();
        for(int i=0; i<str.length(); i++) {
            out.append((char)(
                    str.charAt(i)
                    + key.charAt(i%key.length())
                    + ((i*2) % key.length() / 3)
            ));
        }
        return out.toString();
    }
    
    public static String decrypt(String str, String key) {
        StringBuilder out = new StringBuilder();
        for(int i=0; i<str.length(); i++) {
            out.append((char)(
                    str.charAt(i)
                    - key.charAt(i%key.length())
                    - ((i*2) % key.length()) / 3
            ));
        }
        return out.toString();
    }
    
}
