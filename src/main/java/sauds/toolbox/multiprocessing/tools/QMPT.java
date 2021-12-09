
package sauds.toolbox.multiprocessing.tools;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import sauds.toolbox.data.structures.PrimitiveList;

/**
 * This Queued Multi-Processing Tools (QMPT) class provides a set of tools that can be
 * used to simplify the implementation of Muti-Processing Systems.
 * It optimised for tasks that take an undetermined time per element.
 * @author Saud Fatayerji
 */
public class QMPT {
    
    /**
     * Returns the number of processors available to the Java virtual machine.
     *
     * <p> This value may change during a particular invocation of the virtual
     * machine.  Applications that are sensitive to the number of available
     * processors should therefore occasionally poll this property and adjust
     * their resource usage appropriately. </p>
     *
     * @return  the maximum number of processors available to the virtual
     *          machine; never smaller than one
     */
    public static int coreCount() {
		return Runtime.getRuntime().availableProcessors();
    }
    
    /**
     * Loops through the elements of an array using multiple threads. All
     * calls with the same procID are run sequentially and are therefore thread
     * safe. This allows each thread to consolidate its data into a dedicated variable or array.
     * @param nprocs The number of processors to use.
     * @param arr The array to loop through.
     * @param runner The runnable to call on each value in the array.
     */
    public static void run(int nprocs, byte[] arr, MTPListRunnable<Byte> runner) {
		run(nprocs, PrimitiveList.create(arr), runner);
    }
    
    /**
     * Loops through the elements of an array using multiple threads. All
     * calls with the same procID are run sequentially and are therefore thread
     * safe. This allows each thread to consolidate its data into a dedicated variable or array.
     * @param nprocs The number of processors to use.
     * @param arr The array to loop through.
     * @param runner The runnable to call on each value in the array.
     */
    public static void run(int nprocs, int[] arr, MTPListRunnable<Integer> runner) {
		run(nprocs, PrimitiveList.create(arr), runner);
    }
    
    /**
     * Loops through the elements of an array using multiple threads. All
     * calls with the same procID are run sequentially and are therefore thread
     * safe. This allows each thread to consolidate its data into a dedicated variable or array.
     * @param nprocs The number of processors to use.
     * @param arr The array to loop through.
     * @param runner The runnable to call on each value in the array.
     */
    public static void run(int nprocs, long[] arr, MTPListRunnable<Long> runner) {
		run(nprocs, PrimitiveList.create(arr), runner);
    }
    
    /**
     * Loops through the elements of an array using multiple threads. All
     * calls with the same procID are run sequentially and are therefore thread
     * safe. This allows each thread to consolidate its data into a dedicated variable or array.
     * @param nprocs The number of processors to use.
     * @param arr The array to loop through.
     * @param runner The runnable to call on each value in the array.
     */
    public static void run(int nprocs, float[] arr, MTPListRunnable<Float> runner) {
		run(nprocs, PrimitiveList.create(arr), runner);
    }
    
    /**
     * Loops through the elements of an array using multiple threads. All
     * calls with the same procID are run sequentially and are therefore thread
     * safe. This allows each thread to consolidate its data into a dedicated variable or array.
     * @param nprocs The number of processors to use.
     * @param arr The array to loop through.
     * @param runner The runnable to call on each value in the array.
     */
    public static void run(int nprocs, double[] arr, MTPListRunnable<Double> runner) {
		run(nprocs, PrimitiveList.create(arr), runner);
    }
    
    /**
     * Loops through the elements of an array using multiple threads. All
     * calls with the same procID are run sequentially and are therefore thread
     * safe. This allows each thread to consolidate its data into a dedicated variable or array.
     * @param nprocs The number of processors to use.
     * @param arr The array to loop through.
     * @param runner The runnable to call on each value in the array.
     */
    public static <T> void run(int nprocs, T[] arr, MTPListRunnable<T> runner) {
		run(nprocs, Arrays.asList(arr), runner);
    }
	
	
    /**
     * Loops through the key-value pairs of a Map using multiple threads. All
     * calls with the same procID are run sequentially and are therefore thread
     * safe. This allows each thread to consolidate its data into a dedicated variable or array.
     * @param nprocs The number of processors to use.
     * @param map The list to loop through.
     * @param runner The runnable to call on each key-value pair.
     */
    public static <K,V> void run(int nprocs, Map<K,V> map, MTPMapRunnable<K,V> runner) {
		run(nprocs, map.entrySet(), new MTPIterableRunnable<Map.Entry<K, V>>() {
			@Override
			public void iter(int procID, Iterable<Map.Entry<K, V>> iterable, Map.Entry<K, V> val) {
				runner.iter(procID, val.getKey(), val.getValue());
			}
		});
    }
	
    /**
     * Loops through and removes the key-value pairs of a Map using multiple threads. All
     * calls with the same procID are run sequentially and are therefore thread
     * safe. This allows each thread to consolidate its data into a dedicated variable or array.
     * @param nprocs The number of processors to use.
     * @param map The list to loop through.
     * @param runner The runnable to call on each key-value pair.
     */
    public static <K,V> void runPop(int nprocs, Map<K,V> map, MTPMapRunnable<K,V> runner) {
		runPop(nprocs, map.entrySet(), new MTPListRunnable<Map.Entry<K, V>>() {
			@Override
			public void iter(int procID, int idx, Map.Entry<K, V> val) {
				runner.iter(procID, val.getKey(), val.getValue());
			}
		});
    }
	
    
    /**
     * Loops through a range of numbers using multiple threads. All calls with
     * the same procID are run sequentially and are therefore thread safe. This
     * allows each thread to consolidate its data into a dedicated variable or array.
     * @param nprocs The number of processors to use.
     * @param start The first value in the range. (inclusive)
     * @param stop The last value in the range. (exclusive)
     * @param step The number to increment by at each step.
     * @param runner The runnable to call on each value in the range.
     */
    public static void run(int nprocs, int start, int stop, int step, MTPListRunnable<Integer> runner) {
		int[] i = new int[] {start};
		Thread[] t = new Thread[nprocs];
		for(int j=0; j<nprocs; j++) {
			int procID = j;
			t[j] = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						while(true) {
							int pos = next(stop, i, step);
							runner.iter(procID, pos, pos);
						}
					} catch(IndexOutOfBoundsException ex) {}
				}
			});
			t[j].start();
		}
		for(int j=0; j<nprocs; j++) {
			try {
				t[j].join();
			} catch (InterruptedException ex) {}
		}
    }
    
    /**
     * Loops through the elements of a List using multiple threads. All
     * calls with the same procID are run sequentially and are therefore thread
     * safe. This allows each thread to consolidate its data into a dedicated variable or array.
     * @param nprocs The number of processors to use.
     * @param list The list to loop through.
     * @param runner The runnable to call on each value in the List.
     */
    public static <T> void run(int nprocs, List<T> list, MTPListRunnable<T> runner) {
		int[] i = new int[] {0};
		Thread[] t = new Thread[nprocs];
		for(int j=0; j<nprocs; j++) {
			int procID = j;
			t[j] = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						while(true) {
							int pos = next(list.size(), i);
							runner.iter(procID, pos, list.get(pos));
						}
					} catch(IndexOutOfBoundsException ex) {}
				}
			});
			t[j].start();
		}
		for(int j=0; j<nprocs; j++) {
			try {
				t[j].join();
			} catch (InterruptedException ex) {}
		}
    }
	
    /**
     * Loops through the elements of a List using multiple threads. All
     * calls with the same procID are run sequentially and are therefore thread
     * safe. This allows each thread to consolidate its data into a dedicated variable or array.
     * @param nprocs The number of processors to use.
     * @param iterable The Iterable to loop through.
     * @param runner The runnable to call on each value in the List.
     */
    public static <T> void run(int nprocs, Iterable<T> iterable, MTPIterableRunnable<T> runner) {
		int[] i = new int[] {0};
		Thread[] t = new Thread[nprocs];
		Iterator<T> iter = iterable.iterator();
		for(int j=0; j<nprocs; j++) {
			int procID = j;
			t[j] = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						while(true) {
							runner.iter(procID, iterable, next(iter));
						}
					} catch(IndexOutOfBoundsException ex) {}
				}
			});
			t[j].start();
		}
		for(int j=0; j<nprocs; j++) {
			try {
				t[j].join();
			} catch (InterruptedException ex) {}
		}
    }
	
    /**
     * Loops through and removes the elements of a List using multiple threads. All
     * calls with the same procID are run sequentially and are therefore thread
     * safe. This allows each thread to consolidate its data into a dedicated variable or array.
     * @param nprocs The number of processors to use.
     * @param list The list to loop through.
     * @param runner The runnable to call on each value in the List.
     */
    public static <T> void runPop(int nprocs, Collection<T> c, MTPListRunnable<T> runner) {
		int[] i = new int[] {0};
		Thread[] t = new Thread[nprocs];
		for(int j=0; j<nprocs; j++) {
			int procID = j;
			t[j] = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						while(true) {
							Object[] nxt = popNext(c, i);
							runner.iter(procID, (Integer)nxt[0], (T) nxt[1]);
						}
					} catch(IndexOutOfBoundsException ex) {}
				}
			});
			t[j].start();
		}
		for(int j=0; j<nprocs; j++) {
			try {
				t[j].join();
			} catch (InterruptedException ex) {}
		}
    }
	
    
	private static synchronized int next(int listSize, int[] i) {
		return next(listSize, i, 1);
	}
	private static synchronized int next(int listSize, int[] i, int step) {
		int out = i[0];
		if(out >= listSize) throw new IndexOutOfBoundsException();
		i[0] = out + step;
		return out;
	}
	private static synchronized <T> T next(Iterator<T> iter) {
		return iter.next();
	}
	private static synchronized <T> Object[] popNext(Collection<T> c, int[] i) {
		int out = i[0];
		if(c.isEmpty()) throw new IndexOutOfBoundsException();
		T nxt = null;
		for(T t : c) {
			nxt = t;
			break;
		}
		c.remove(nxt);
		i[0] = out + 1;
		return new Object[]{out, nxt};
	}
	
}
