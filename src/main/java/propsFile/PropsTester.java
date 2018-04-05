/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package propsFile;

import java.io.File;

/**
 *
 * @author demon
 */
public class PropsTester {
    
    public static void main(String[] args) {
        Properties p1 = new Properties(new File("/plaintxt.dat"));
        p1.put("dummy1", "lol1");
        p1.put("dummy2", "lol2");
        p1.put("dummy3", "lol3");
        p1.put("dummy4", "lol4");
        p1.put("dummy5", "lol5");
        Properties p2 = new Properties(new File("/cyphtxt.dat"));
        p1.cloneData(p2);
        
        p1.save();
        p1.load();
        String key = "iLoVePiE";
        p2.save(key);
        p2.load(key);
        
        System.out.println(new File("/plaintxt.dat").getAbsolutePath());
        System.out.println(p1.toString());
        System.out.println(p2.toString());
    }
    
}
