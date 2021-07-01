/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sauds.toolbox.data.structures;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.nio.file.Files;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author saud
 */
public class BigList<T> implements Iterable<T>{
    
    private final int pageSize;
    private final BigInteger MAX_VALUE;
    private final File storageDir;
    
    private BigInteger pageID = BigInteger.ZERO;
    private Object[] inUse;
    private boolean pageModified = false;
    private BigInteger size = BigInteger.ZERO;
    private T lastAdded;
    
    private Thread savingThread;
    private BigInteger savingPageID;
    private boolean savingThreadIsInit = false;
    
    /**
     * Creates an array that is not limited to RAM space.
     * @param storageDir a folder on disc to store unloaded pages.
     * @param pageSize the number of items per page.
     */
    public BigList(File storageDir, int pageSize) {
        if(storageDir == null) {
            this.storageDir = getAppDataPath("BigListTEMP", "");
            try {
                Files.createDirectories(this.storageDir.toPath());
            } catch (IOException ex) {
                Logger.getLogger(BigList.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            this.storageDir = storageDir;
        }
        System.out.println(this.storageDir);
        this.inUse = new Object[pageSize];
        this.pageSize = pageSize;
        MAX_VALUE = BigInteger.valueOf(pageSize);
        for(File f : this.storageDir.listFiles()) {
            f.delete();
        }
    }
    
    /**
     * Creates an array that is not limited to RAM space.
     * Default page size: 500,000
     * @param storageDir a folder on disc to store unloaded pages.
     */
    public BigList(File storageDir) {
        this(storageDir, 500000);
    }
    
    /**
     * Creates an array that is not limited to RAM space.
     * Default storage directory: temporary
     * @param pageSize the number of items per page.
     */
    public BigList(int pageSize) {
        this(null, pageSize);
    }
    
    /**
     * Creates an array that is not limited to RAM space.
     * Default page size: 500,000
     *         storage directory: temporary
     */
    public BigList() {
        this(null);
    }
    
    public BigInteger size() {
        return size;
    }
    
    /**
     * Get an element from the array
     * @param i the index to get
     * @return the value at the index
     */
    public T get(BigInteger i) {
        BigInteger pageRequired = i.divide(MAX_VALUE);
        int indexInPage = i.mod(MAX_VALUE).intValue();
        
        if(pageRequired.compareTo(pageID) != 0) {
            loadPage(pageRequired);
        }
        
        return (T) inUse[indexInPage];
    }
    
    public BigInteger binSearch(T t) {
        if(!(t instanceof Comparable))
            throw new IllegalArgumentException("Input is not Comparable");
        Comparable c = (Comparable) t;
        
        BigInteger TWO = BigInteger.valueOf(2);
        BigInteger last = size;
        BigInteger first = BigInteger.ZERO.subtract(BigInteger.ONE);
        while(true) {
            BigInteger mid = first.add(last).divide(TWO);
            Comparable v = (Comparable) get(mid);
            if(c.compareTo(v) < 0) {
                last = mid;
            } else if(c.compareTo(v) > 0) {
                first = mid;
            } else {
                return mid;
            }
            if(last.subtract(first).compareTo(TWO) < 0) {
                return first;
            }
        }
    }
    
    public T getLastAdded() {
        return lastAdded;
    }
    public BigInteger getLastPage() {
        return size.divide(MAX_VALUE);
    }
    
    /**
     * set an element in the list
     * @param i the index of the value to change
     * @param t the value to change
     */
    public void set(BigInteger i, T t) {
        if(i.compareTo(size) > 0) {
            return;
        } else if(i.compareTo(size) == 0) {
            lastAdded = t;
        }
        
        BigInteger pageRequired = i.divide(MAX_VALUE);
        int indexInPage = i.mod(MAX_VALUE).intValue();
        
        if(pageRequired.compareTo(pageID) != 0) {
            loadPage(pageRequired);
        }
        
        inUse[indexInPage] = t;
        pageModified = true;
        size = size.add(BigInteger.ONE);
    }
    
    /**
     * Add item to the end of the list
     * @param t the value to store
     */
    public void add(T t) {
        set(size, t);
    }
    
    private void loadPage(BigInteger page) {
        // save page
        if(!pageFile(pageID).exists())
            pageFile(pageID).getParentFile().mkdirs();
        
        if(pageModified) {
            // wait till saving thread is available
            if(savingThread != null && savingThread.isAlive()) {
                try {
                    savingThread.join();
                } catch (InterruptedException ex) {
                    Logger.getLogger(BigList.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            // save while pages are loading
            savingThreadIsInit = false;
            savingThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    savingPageID = pageID;
                    Object[] tempInUse = inUse;
                    savingThreadIsInit = true;
                    try {
                        FileOutputStream fos = new FileOutputStream(pageFile(savingPageID));
                        ObjectOutputStream oos = new ObjectOutputStream(fos);
                        oos.writeObject(tempInUse);
                        //System.out.println("done writing page "+savingPageID);
                        oos.close();
                        fos.close();
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(BigList.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(BigList.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    savingPageID = null;
                }
            });
            savingThread.start();
            while(!savingThreadIsInit) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ex) {
                    Logger.getLogger(BigList.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        // load
        if(pageFile(page).exists()) {
            if(savingPageID!=null && page.compareTo(savingPageID) == 0) {
                try {
                    savingThread.join();
                } catch (InterruptedException ex) {
                    Logger.getLogger(BigList.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            try {
                FileInputStream fis = new FileInputStream(pageFile(page));
                ObjectInputStream ois = new ObjectInputStream(fis);
                inUse = (T[]) ois.readObject();
                pageModified = false;
                ois.close();
                fis.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(BigList.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(BigList.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            inUse = new Object[pageSize];
            pageModified = true;
        }
        pageID = page;
    }
    
    /**
     * get the file path for the page
     * @param page the page
     * @return the file
     */
    private File pageFile(BigInteger page) {
        return new File(storageDir.getAbsolutePath() + "/" + page);
    }
    

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            BigInteger i = BigInteger.ZERO;
            @Override
            public boolean hasNext() {
                return i.compareTo(size) < 0;
            }

            @Override
            public T next() {
                T out = get(i);
                i = i.add(BigInteger.ONE);
                return out;
            }
        };
    }
    
    
    private static String getAppDataPath() {
        String workingDirectory;
        String OS = (System.getProperty("os.name")).toUpperCase();
        if (OS.contains("WIN")) {
            workingDirectory = System.getenv("AppData");
        } else {
            workingDirectory = System.getProperty("user.home");
            workingDirectory += "/Library/Application Support";
        }
        return workingDirectory;
    }
    private static File getAppDataPath(String company, String filename) {
        return getAppDataPath(company, "", filename);
    }
    private static File getAppDataPath(String company, String subpath, String filename) {
        String path = getAppDataPath() + "/" + company + "/" + subpath + "/" + filename;
        while(path.contains("//")) {
            path = path.replaceAll("//", "/");
        }
        return new File(path);
    }
    
}
