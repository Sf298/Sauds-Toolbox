
package sauds.toolbox.multiprocessing.tools;

/**
 *
 * @author saud
 */
public interface MTPListRunnable <T> {
    
    public void iter(int procID, int idx, T val);
    
}
