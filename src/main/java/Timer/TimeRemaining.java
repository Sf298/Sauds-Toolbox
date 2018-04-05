/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Timer;

import java.math.BigInteger;
import java.util.LinkedList;

/**
 *
 * @author saud
 */
public class TimeRemaining {
    
    private Element first;
    private Element last;
    private int bufferSec;
    
    public TimeRemaining(int bufferSec) {
        this.bufferSec = bufferSec;
    }
    
    long nextTime = 0;
    long sum = 0;
    public void put() {
        long time = System.currentTimeMillis();
        if(time >= nextTime) {
            long[] value = new long[] {time, sum};
            push(new Element(null, null, value));
            sum = 0;
        }
        sum++;
    }
    
    public BigInteger get(BigInteger totalRemaining) {
        long time = System.currentTimeMillis();
        BigInteger valuePerSec = BigInteger.ZERO;
        long lastms = 1000*bufferSec;
        for (Element e = first; e!=null; e = e.next) {
            if(time - e.value[0] <= lastms) {
                valuePerSec = valuePerSec.add(BigInteger.valueOf(e.value[1]));
            } else {
                pop(e);
                break;
            }
        }
        if(valuePerSec.equals(BigInteger.ZERO)) {
            return BigInteger.valueOf(-1);
        }
        return totalRemaining.divide(valuePerSec);
    }
    
    private Element push(Element e) {
        if(first != null) {
            e.next = first;
            first.last = e;
            first = e;
        } else {
            first = last = e;
        }
        //System.out.println("push");
        return e;
        
    }
    private Element pop(Element e) {
        if(e.last != null) {
            last = e.last;
            e.last = null;
            last.next = null;
        } else {
            first = last = null;
        }
        System.out.println("pop");
        return e;
    }
    
    private class Element {
        public Element next;
        public Element last;
        public long[] value;
        public Element(Element next, Element last, long[] value) {
            this.next = next;
            this.last = last;
            this.value = value;
        }
    }
    
}
