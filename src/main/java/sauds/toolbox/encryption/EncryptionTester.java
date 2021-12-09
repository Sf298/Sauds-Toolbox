/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sauds.toolbox.encryption;

/**
 *
 * @author demon
 */
public class EncryptionTester {
    
    public static void main(String[] args) {
        String str = "a = s";
        String key = "npauvfnpfjlksmnvnpfd";
        /*String cyph = Encryptor.encrypt(str,key);
        System.out.println(cyph);
        System.out.println(Encryptor.decrypt(cyph, key));*/
        
        for(int i=0; i<str.length(); i++) {
            int inC = str.charAt(i);
            int inK = key.charAt(i%key.length());
            int val = (i*2) % key.length() / 3;
            
            int cypher = (inC + inK + val) % 128;
            int decryped = (128 + cypher - inK - val) % 128;
            System.out.println(inC+" -> "+cypher+" -> "+decryped);
        }
    }
    
}
