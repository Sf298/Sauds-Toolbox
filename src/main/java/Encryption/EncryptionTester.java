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
public class EncryptionTester {
    
    public static void main(String[] args) {
        String str = "abcdefghijklmnopqrstuvwxyz";
        String key = "ABCD";
        String cyph = Encryptor.encrypt(str,key);
        System.out.println(cyph);
        System.out.println(Encryptor.decrypt(cyph, key));
    }
    
}
