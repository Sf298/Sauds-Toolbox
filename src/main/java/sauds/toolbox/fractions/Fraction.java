/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sauds.toolbox.fractions;

import java.util.ArrayList;

/**
 *
 * @author saud
 */
public class Fraction {
    
    private int nominator = 0;
    private int denominator = 0;
    
    public Fraction(int numerator, int nominator, int denominator) {
        this(nominator + (numerator * denominator), denominator);
    }
    
    public Fraction(int nominator, int denominator) {
        this.nominator = nominator;
        this.denominator = denominator;
        if(nominator>Integer.MAX_VALUE/2 || denominator>Integer.MAX_VALUE/2)
            thisReduce();
    }
    
    public Fraction(int integer) {
        this(integer, 1);
    }
    
    public Fraction(Fraction f) {
        this.nominator = f.nominator;
        this.denominator = f.denominator;
    }
    
    public Fraction() {
        this(0, 0);
    }
    
    
    private static final double MIN_VAL = 0.000000000001;
    public static Fraction parseDouble(double d, int maxIter) {
        boolean isNeg = d < 0;
        d = Math.abs(d);
        double dec = (d - (int)d);
        int numerator = (int)(d-dec);
        int i = 0;
        ArrayList<Integer> values = new ArrayList<>();
        while(true) {
            double inv = 1/dec;
            values.add((int)inv);
            dec = inv-(int)inv;
            if(dec < MIN_VAL) break;
            if(i++ > maxIter) throw new RuntimeException("maxIter hit!");
        }
        
        Fraction out = new Fraction(values.get(values.size()-1));
        for(int j=values.size()-2; j>=0; j--) {
            out = out.invert().add(values.get(j));
        }
        return out.invert().add(numerator).multiply((isNeg)?-1:1);
    }
    
    public int getNumerator() {
        return nominator/denominator;
    }
    
    public int getNominator() {
        return nominator;
    }

    public int getDenominator() {
        return denominator;
    }
    
    
    public Fraction add(Fraction f) {
        Fraction out = new Fraction();
        out.nominator = (f.nominator * this.denominator) + (this.nominator * f.denominator);
        out.denominator = f.denominator * this.denominator;
        if(out.nominator>Integer.MAX_VALUE/2 || out.denominator>Integer.MAX_VALUE/2)
            out.thisReduce();
        return out;
    }
    public Fraction add(int integer) {
        Fraction out = add(new Fraction(integer));
        return out;
    }
    
    public Fraction sub(Fraction f) {
        Fraction out = new Fraction();
        out.nominator = (this.nominator * f.denominator) - (f.nominator * this.denominator);
        out.denominator = f.denominator * this.denominator;
        if(out.nominator>Integer.MAX_VALUE/2 || out.denominator>Integer.MAX_VALUE/2)
            out.thisReduce();
        return out;
    }
    public Fraction sub(int integer) {
        Fraction out = sub(new Fraction(integer));
        return out;
    }
    
    public Fraction multiply(Fraction f) {
        Fraction out = new Fraction();
        out.nominator = this.nominator * f.nominator;
        out.denominator = this.denominator * f.denominator;
        if(out.nominator>Integer.MAX_VALUE/2 || out.denominator>Integer.MAX_VALUE/2)
            out.thisReduce();
        return out;
    }
    public Fraction multiply(int integer) {
        Fraction out = multiply(new Fraction(integer));
        return out;
    }
    
    public Fraction div(Fraction f) {
        Fraction out = new Fraction();
        out.nominator = this.nominator * f.denominator;
        out.denominator = this.denominator * f.nominator;
        if(out.nominator>Integer.MAX_VALUE/2 || out.denominator>Integer.MAX_VALUE/2)
            out.thisReduce();
        return out;
    }
    public Fraction div(int integer) {
        Fraction out = div(new Fraction(integer));
        return out;
    }
    
    public Fraction invert() {
        return new Fraction(denominator, nominator);
    }

    private void thisReduce() {
        for(int i=2; i<Math.max(nominator, denominator); i++) {
            if(nominator%i==0 && denominator%i==0) {
                nominator /= i;
                denominator /= i;
                i=2;
            }
        }
        if(nominator==0) {
            denominator = 1;
        }
    }

    public Fraction reduce() {
        Fraction out = new Fraction(this);
        out.thisReduce();
        return out;
    }
    
    public double toDouble() {
        return nominator / (double)denominator;
    }
    
    public boolean equals(Object o) {
        if(!(o instanceof Fraction)) return false;
        Fraction f = (Fraction) o;
        f = f.reduce();
        Fraction thisF = reduce();
        return f.denominator == thisF.denominator && f.nominator == thisF.nominator;
    }
    
    @Override
    public String toString() {
        return "("+nominator+"/"+denominator+")";
    }
    
    public String toFormattedString() {
        if(Math.abs(nominator) > Math.abs(denominator))
            return "["+(nominator/denominator) + "+("+(Math.abs(nominator)%denominator)+"/"+denominator+")"+"]";
        else
            return "("+nominator+"/"+denominator+")";
    }
    
}
