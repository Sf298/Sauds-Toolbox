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
public class ClockChallenge {
    
    public static void main(String[] args) {
        // https://www.desmos.com/calculator/36rs8ze2wm
        for(int i1=0; i1<12; i1++) {
            for(int i2=0; i2<12; i2++) {
                Fraction i1f = new Fraction(i1).div(12);
                Fraction i2f = new Fraction(i2).div(12);
                Fraction x = new Fraction(12,143).multiply(i2f.multiply(12).add(i1f)).reduce();
                Fraction y = x.div(12).add(i1f).reduce();
                System.out.println(/*"("+i1+"/"+i2+") => "+*/yToHHMMSSff(y));
            }
        }
        System.out.println(yToHHMMSSff(new Fraction(1, 2)));
    }
    
    public static String yToHHMMSSff(Fraction f) {
        f = f.multiply(12).reduce();
        int hh = f.getNumerator();
        f = f.sub(hh).multiply(60);
        int mm = f.getNumerator();
        f = f.sub(mm).multiply(60);
        int ss = f.getNumerator();
        f = f.sub(ss);
        Fraction rem = f;
        return myF(hh)+":"+myF(mm)+":"+myF(ss)+rem;
    }
    
    public static String xToHHMMSSff(Fraction f) {
        f = f.multiply(60).reduce();
        int mm = f.getNumerator();
        f = f.sub(mm).multiply(60);
        int ss = f.getNumerator();
        f = f.sub(ss);
        Fraction rem = f;
        return myF(mm)+":"+myF(ss)+rem;
    }
    
    private static String myF(int number) {
        if(number<10)
            return "0"+number;
        if(number<100)
            return ""+number;
        else
            return null;
    }
    
}
