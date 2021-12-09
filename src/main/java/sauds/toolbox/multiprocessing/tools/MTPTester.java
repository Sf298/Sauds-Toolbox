
package sauds.toolbox.multiprocessing.tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author saud
 */
public class MTPTester {
    
    public static void main(String[] args) {
		testMPTmap();
    }

	private static void testMPT() {
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
	
	private static void testMPTmap() {
		HashMap<String, String> map = new HashMap<>();
		for (int i = 0; i < 15; i++) {
			map.put(i+"", "d"+i);
		}
		
		MPT.run(4, map, new MTPMapRunnable<String, String>() {
			@Override
			public void iter(int procID, String key, String val) {
				System.out.println(procID+", "+key+", "+val);
			}
		});
	}
	
	private static void testMPTQ() {
		QMPT.run(4, 1, 30, 2, new MTPListRunnable<Integer>() {
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
     
	private static void testMPTvsMPTQ() {
		double[] arr = new double[39923712];
		for(int i=0; i<arr.length; i++) {
			arr[i] = Math.random() * 10;
		}
		
		
		long start = System.currentTimeMillis();
		Arrays.stream(arr, 0, arr.length).parallel().forEach((val) -> {
			work(val);
		});
		System.out.println();
		System.out.println(System.currentTimeMillis() - start);
		
		start = System.currentTimeMillis();
		QMPT.run(7, arr, new MTPListRunnable<Double>() {
			@Override
			public void iter(int procID, int pos, Double val) {
				work(val);
			}
		});
		System.out.println();
		System.out.println(System.currentTimeMillis() - start);
		
		start = System.currentTimeMillis();
		MPT.run(7, arr, new MTPListRunnable<Double>() {
			@Override
			public void iter(int procID, int pos, Double val) {
				work(val);
			}
		});
		System.out.println();
		System.out.println(System.currentTimeMillis() - start);
	}
	
	private static void work(double val) {
		/*try {
			Thread.sleep(10);
		} catch (InterruptedException ex) {
			Logger.getLogger(MTPTester.class.getName()).log(Level.SEVERE, null, ex);
		}*/
		double temp = val%2.0;
		temp = temp%2.0;
		temp = temp%2.0;
		temp = temp%2.0;
		temp = temp%2.0;
		temp = temp%2.0;
		temp = temp%2.0;
		temp = temp%2.0;
	}
    
}
