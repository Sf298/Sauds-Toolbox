
package sauds.toolbox.multiprocessing.tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * 
 * @author saud
 */
public class Consolidator {
	
	public static final int OP_SUM = 0;
	public static final int OP_MEAN = 1;
	public static final int OP_MEDIAN = 2;
	
	public class Integer {
		
		private int op;
		private int[] sum = null;
		private int[] count = null;
		private ArrayList<java.lang.Integer> values = null;

		public Integer(int threadCount, int operation) {
			if(op < OP_SUM || op > OP_MEDIAN) throw new IllegalArgumentException();
			op = operation;
			if(op <= OP_MEAN) sum = new int[threadCount];
			if(op == OP_MEAN) count = new int[threadCount];
			if(op == OP_MEDIAN) values = new ArrayList<>();
		}

		public int getOperation() {
			return op;
		}

		public void addValue(int threadID, int val) {
			if(op <= OP_MEAN) sum[threadID] += val;
			else if(op == OP_MEAN) count[threadID]++;
			else if(op == OP_MEDIAN) values.add(val);
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
		private int median(ArrayList<java.lang.Integer> list) {
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
	
	public class Double {
		
		private int op;
		private double[] sum = null;
		private double[] count = null;
		private ArrayList<java.lang.Double> values = null;

		public Double(int threadCount, int operation) {
			if(op < OP_SUM || op > OP_MEDIAN) throw new IllegalArgumentException();
			op = operation;
			if(op <= OP_MEAN) sum = new double[threadCount];
			if(op == OP_MEAN) count = new double[threadCount];
			if(op == OP_MEDIAN) values = new ArrayList<>();
		}

		public int getOperation() {
			return op;
		}

		public void addValue(int threadID, double val) {
			if(op <= OP_MEAN) sum[threadID] += val;
			else if(op == OP_MEAN) count[threadID]++;
			else if(op == OP_MEDIAN) values.add(val);
		}

		public double getResult() {
			if(op == OP_SUM) return sumArr(sum);
			if(op == OP_MEAN) return sumArr(sum)/sumArr(count);
			if(op == OP_MEDIAN) return median(values);
			return 0;
		}
		private double sumArr(double[] arr) {
			double out = arr[0];
			for (int i=1; i<arr.length; i++) {
				out += arr[i];
			}
			return out;
		}

		/**
		 * Calculates the median of an ArrayList of Doubles.
		 * @param list
		 * @return 
		 */
		private double median(ArrayList<java.lang.Double> list) {
			Collections.sort(list);
			int s = list.size();
			int hs = s>>>1; // == s / 2
			if((s&1) == 0) {
				return (list.get(hs-1) + list.get(hs))/2;
			} else {
				return list.get(hs);
			}
		}
		
	}
	
}
