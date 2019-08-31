
package sauds.toolbox.multiprocessing.tools;

/**
 *
 * @author saud
 */
public interface MTPIterableRunnable <T> {
    
    public void iter(int procID, Iterable<T> iterable, T val);
    
}
