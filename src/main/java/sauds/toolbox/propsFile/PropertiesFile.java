/*
 * To change this license header, choose License Headers in Project PropertiesFile.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sauds.toolbox.propsFile;

import sauds.toolbox.encryption.Encryptor;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Saud
 */
public class PropertiesFile {
    
    private final File file;
    private String separator;
    private TreeMap<String, String> map = new TreeMap();
    
    public PropertiesFile(File f) {
        this(f, " - ");
    }
    
    public PropertiesFile(File f, String separator) {
        file = f;
        this.separator = separator;
    }
    
    public void cloneData(PropertiesFile p) {
        p.separator = separator;
        p.map = new TreeMap(map);
    }
    
    
    public String get(String key) {
        return map.get(key).replaceAll("<!thisIsNewLine!>", "\n");
    }
    public int getAsInt(String key) {
        return Integer.parseInt(map.get(key));
    }
    public double getAsDouble(String key) {
        return Double.parseDouble(map.get(key));
    }
    
    public void put(String key, String value) {
        map.put(key, value.replaceAll("\n", "<!thisIsNewLine!>"));
    }
    
    public boolean hasKey(String key) {
        return map.containsKey(key);
    }
    
    
    public boolean fileExists() {
        return file.exists();
    }
    
    public void load() {
        load(null);
    }
    public void load(String key) {
        if(!file.exists()) {
            return;
        }
        try {
            String str = new String ( Files.readAllBytes(file.toPath()));
            if(key == null) {
                parseString(str);
            } else {
                parseString(Encryptor.decrypt(str, key));
            }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PropertiesFile.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PropertiesFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void parseString(String str) {
        Scanner in = new Scanner(str);
        while(in.hasNextLine()) {
            String line = in.nextLine();
            if(line.contains(separator)) {
                String[] lineArr = line.split(separator, 2);
                map.put(lineArr[0], lineArr[1]);
            }
        }
    }
    
    public void save() {
        save(null);
    }
    public void save(String key) {
        if(!file.exists()) {
            file.getParentFile().mkdirs();
        }
        try {
            FileWriter fw = new FileWriter(file);
            if(key == null) {
                fw.write(toString());
            } else {
                fw.write(Encryptor.encrypt(toString(), key));
            }
            fw.flush();
            fw.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PropertiesFile.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PropertiesFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(String key : map.keySet()) {
            sb.append(key)
                    .append(separator)
                    .append(map.get(key))
                    .append("\n");
        }
        return sb.toString();
    }
}
