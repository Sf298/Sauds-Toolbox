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
            int inC = str.charAt(i);
            int inK = key.charAt(i%key.length());
            int val = (i*2) % key.length() / 3;
            
            int cypher = (inC + inK + val) % 128;
            out.append((char)cypher);
        }
        return out.toString();
    }
    
    public static String decrypt(String str, String key) {
        StringBuilder out = new StringBuilder();
        for(int i=0; i<str.length(); i++) {
            int inC = str.charAt(i);
            int inK = key.charAt(i%key.length());
            int val = (i*2) % key.length() / 3;
            
            int decryped = (128 + inC - inK - val) % 128;
            out.append((char)decryped);
        }
        return out.toString();
    }
    
}
