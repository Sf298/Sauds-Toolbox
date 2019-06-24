/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sauds.toolbox.fractions;

import java.util.Random;

/**
 *
 * @author saud
 */
public class FractionTester {
    
    public static void main(String[] args) {
        Fraction f1 = new Fraction(60, 1);
        Fraction f2 = new Fraction(1, 60);
        Fraction out = f1.sub(f2);
        System.out.println(out.toFormattedString()+ ", " + out.toDouble());
        
        System.out.println("BEGINNING");
        Random r = new Random();
        for (int i = 0; i < 1000; i++) {
            System.out.println(i);
            Fraction expected = new Fraction(r.nextInt(200), r.nextInt(200));
            Fraction actual = Fraction.parseDouble(expected.toDouble(), Integer.MAX_VALUE);
            if(!actual.equals(expected)) {
                System.out.println(actual+", "+expected);
                System.out.println(actual.toDouble()+", "+expected.toDouble());
            }
        }
    }
    
}
