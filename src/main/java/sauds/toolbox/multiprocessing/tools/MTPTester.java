
package sauds.toolbox.multiprocessing.tools;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author saud
 */
public class MTPTester {
    
    public static void main(String[] args) {
	ArrayList<Integer> list = new ArrayList<>();
	for (int i = 0; i < 15; i++) {
	    list.add(i);
	}
	MPT.run(4, list, new MTPListRunnable<Integer>() {
	    @Override
	    public void iter(int procID, int idx, Integer val) {
		System.out.println(procID+", "+idx+", "+val);
		try {
		    Thread.sleep(1000);
		} catch (InterruptedException ex) {
		    Logger.getLogger(MTPTester.class.getName()).log(Level.SEVERE, null, ex);
		}
	    }
	});
    }
    
}
