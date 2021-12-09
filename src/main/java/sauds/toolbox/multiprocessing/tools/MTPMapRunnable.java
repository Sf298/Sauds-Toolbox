
package sauds.toolbox.multiprocessing.tools;

/**
 *
 * @author saud
 */
public interface MTPMapRunnable <K,V> {
    
    public void iter(int procID, K key, V val);
    
}
