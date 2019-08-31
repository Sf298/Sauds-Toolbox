
package sauds.toolbox.multiprocessing.tools;

import java.util.ArrayList;
import java.util.Collections;

/**
 * 
 * @author saud
 */
public class Consolidator {
    
	public static final int OP_SUM = 0;
	public static final int OP_MEAN = 1;
	public static final int OP_MEDIAN = 2;
	
	private int op;
	private int[] sum = null;
	private int[] count = null;
	private ArrayList<Integer> values = null;
	
	public Consolidator(int threadCount, int operation) {
		if(op < OP_SUM || op > OP_MEDIAN) throw new IllegalArgumentException();
		op = operation;
		if(op <= OP_MEAN) sum = new int[threadCount];
		if(op == OP_MEAN) count = new int[threadCount];
		if(op == OP_MEDIAN) values = new ArrayList<>();
	}
	
	public Consolidator(Consolidator c) {
		op = c.op;
		sum = c.sum;
		count = c.count;
		values = new ArrayList<>(c.values);
	}
	
	public int getOperation() {
		return op;
	}
	
	public void addValue(int threadID, int val) {
		if(op <= OP_MEAN) sum[threadID] += val;
		if(op == OP_MEAN) count[threadID]++;
		if(op == OP_MEDIAN) values.add(val);
	}
	
	public int getResult() {
		if(op == OP_SUM) return sumArr(sum);
		if(op == OP_MEAN) return sumArr(sum)/sumArr(count);
		if(op == OP_MEDIAN) return median(values);
		return 0;
	}
	private int sumArr(int[] arr) {
		int out = arr[0];
		for (int i=1; i<arr.length; i++) {
			out += arr[i];
		}
		return out;
	}
	
	/**
	 * Calculates the median of an ArrayList of Integers.
	 * @param list
	 * @return 
	 */
	public static int median(ArrayList<Integer> list) {
		Collections.sort(list);
		int s = list.size();
		int hs = s>>>1; // == s / 2
		if((s&1) == 0) {
			return (list.get(hs-1) + list.get(hs))>>>1;
		} else {
			return list.get(hs);
		}
	}
	
}
