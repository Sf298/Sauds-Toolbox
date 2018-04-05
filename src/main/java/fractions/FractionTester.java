/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fractions;

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
    }
    
}
