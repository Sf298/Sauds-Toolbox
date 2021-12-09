
package sauds.toolbox.multiprocessing.tools;

import sauds.toolbox.data.structures.PrimitiveList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This Multi-Processing Tools (MPT) class provides a set of tools that can be
 * used to simplify the implementation of Muti-Processing Systems. It is
 * optimised for tasks with many element short that take a short amount of time
 * to process.
 * @author Saud Fatayerji
 */
public class MPT {
    
    /**
     * Gets the range of elements to assign to an individual processor/thread.
     * @param proc The index of the current processor
     * @param nprocs The number of processors available.
     * @param maxI Number of elements to partition.
     * @return An array of the left (inclusive) and right (exclusive) indexes respectively.
     */
    public static int[] getLR(int proc, int nprocs, int maxI) {
        // calc ileft and iright
        int a = maxI/nprocs; // cols per process
        int rem = maxI%nprocs; // remaining cols
        int left = proc * a + ((proc<rem) ? proc : rem); // is 0 based
        int right = left + a + ((proc<rem) ? 1 : 0); // right is exclusive
        return new int[] {left, right};
    }
    
    /**
     * Generates the left and right indexes for each of the threads available.
     * @param nprocs The number of processors available.
     * @param maxI Number of elements to partition.
     * @return Returns an array with values in the range of the LR values
     */
    public static int[][] getLRs(int nprocs, int maxI) {
	int[][] out = new int[nprocs][];
	for(int i=0; i<out.length; i++) {
	    out[i] = getLR(i, nprocs, maxI);
	}
	return out;
    }
    
    /**
     * Converts the left and right indexes into an array of consecutive values in
     * the range of the values provided.
     * @param lr The LR array as returned by getLR()
     * @return Returns an array with values in the range of the LR values
     */
    public static int[] lr2Range(int[] lr) {
        return lr2Range(lr[0], lr[1]);
    }
    
    /**
     * Converts the left and right indexes into an array of consecutive values in
     * the range of the values provided.
     * @param left The starting value in the range (inclusive)
     * @param right The end value in the range (exclusive)
     * @return Returns an array with values in the range of the LR values
     */
    public static int[] lr2Range(int left, int right) {
        int[] out = new int[right-left];
        for(int i=0; i<out.length; i++) {
            out[i] = left+i;
        }
        return out;
    }
    
    /**
     * Evenly distributes the values provided in the array into nprocs
     * arrays.
     * @param nprocs The number of processors for which to split the array.
     * @param arr The array of values which should be split.
     * @return An array of arrays containing all the values that should be
     * processed by the respective processor.
     */
    public static ArrayList<byte[]> split(int nprocs, byte[] arr) {
        ArrayList<byte[]> out = new ArrayList<>();
        for(int i=0; i<nprocs; i++) {
            int[] lr = getLR(i, nprocs, arr.length);
            out.add(Arrays.copyOfRange(arr, lr[0], lr[1]));
        }
        return out;
    }
    
    /**
     * Evenly distributes the values provided in the array into nprocs
     * arrays.
     * @param nprocs The number of processors for which to split the array.
     * @param arr The array of values which should be split.
     * @return An array of arrays containing all the values that should be
     * processed by the respective processor.
     */
    public static ArrayList<int[]> split(int nprocs, int[] arr) {
        ArrayList<int[]> out = new ArrayList<>();
        for(int i=0; i<nprocs; i++) {
            int[] lr = getLR(i, nprocs, arr.length);
            out.add(Arrays.copyOfRange(arr, lr[0], lr[1]));
        }
        return out;
    }
    
    /**
     * Evenly distributes the values provided in the array into nprocs
     * arrays.
     * @param nprocs The number of processors for which to split the array.
     * @param arr The array of values which should be split.
     * @return An array of arrays containing all the values that should be
     * processed by the respective processor.
     */
    public static ArrayList<long[]> split(int nprocs, long[] arr) {
        ArrayList<long[]> out = new ArrayList<>();
        for(int i=0; i<nprocs; i++) {
            int[] lr = getLR(i, nprocs, arr.length);
            out.add(Arrays.copyOfRange(arr, lr[0], lr[1]));
        }
        return out;
    }
    
    /**
     * Evenly distributes the values provided in the array into nprocs
     * arrays.
     * @param nprocs The number of processors for which to split the array.
     * @param arr The array of values which should be split.
     * @return An array of arrays containing all the values that should be
     * processed by the respective processor.
     */
    public static ArrayList<float[]> split(int nprocs, float[] arr) {
        ArrayList<float[]> out = new ArrayList<>();
        for(int i=0; i<nprocs; i++) {
            int[] lr = getLR(i, nprocs, arr.length);
            out.add(Arrays.copyOfRange(arr, lr[0], lr[1]));
        }
        return out;
    }
    
    /**
     * Evenly distributes the values provided in the array into nprocs
     * arrays.
     * @param nprocs The number of processors for which to split the array.
     * @param arr The array of values which should be split.
     * @return An array of arrays containing all the values that should be
     * processed by the respective processor.
     */
    public static ArrayList<double[]> split(int nprocs, double[] arr) {
        ArrayList<double[]> out = new ArrayList<>();
        for(int i=0; i<nprocs; i++) {
            int[] lr = getLR(i, nprocs, arr.length);
            out.add(Arrays.copyOfRange(arr, lr[0], lr[1]));
        }
        return out;
    }
    
    /**
     * Evenly distributes the values provided in the array into nprocs
     * arrays.
     * @param nprocs The number of processors for which to split the array.
     * @param arr The array of values which should be split.
     * @return An array of arrays containing all the values that should be
     * processed by the respective processor.
     */
    public static <T> ArrayList<T[]> split(int nprocs, T[] arr) {
        ArrayList<T[]> out = new ArrayList<>();
        for(int i=0; i<nprocs; i++) {
            int[] lr = getLR(i, nprocs, arr.length);
            out.add(Arrays.copyOfRange(arr, lr[0], lr[1]));
        }
        return out;
    }
    
    /**
     * Evenly distributes the values provided in the List into nprocs
     * ArrayLists.
     * @param nprocs The number of processors for which to split the list.
     * @param list The list of values which should be split.
     * @return An array of Lists containing all the values that should be
     * processed by the respective processor.
     */
    public static <T> ArrayList<List<T>> split(int nprocs, List<T> list) {
        ArrayList<List<T>> out = new ArrayList<>();
        for(int i=0; i<nprocs; i++) {
            int[] lr = getLR(i, nprocs, list.size());
            out.add(list.subList(lr[0], lr[1]));
        }
        return out;
    }
    
    /**
     * Evenly distributes the values provided in the HashMap into nprocs
     * HashMaps.
     * @param nprocs The number of processors for which to split the list.
     * @param map The map of values who's contents will be split.
     * @return An array of HashMaps containing all the key-value pairs that
     * should be processed by the respective processor.
     */
    public static <K,V> HashMap<K,V>[] split(int nprocs, Map<K,V> map) {
        ArrayList<List<K>> keys = split(nprocs, new ArrayList<>(map.keySet()));
        HashMap<K,V>[] out = new HashMap[10];
        for(int i=0; i<keys.size(); i++) {//ArrayList<K> kList : keys) {
            HashMap<K,V> m = new HashMap<>();
            for(K k : keys.get(i)) {
                m.put(k, map.get(k));
            }
            out[i] = m;
        }
        return out;
    }
    
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
     * Loops through a range of numbers using multiple threads. All calls with
     * the same procID are run sequentially and are therefore thread safe. This
     * allows each thread to consolidate its data into a dedicated variable or array.
     * @param nprocs The number of processors to use.
     * @param start The first value in the range. (inclusive)
     * @param stop The last value in the range. (exclusive)
     * @param step The number to increment by at each step.
     * @param runner The runnable to call on each value in the range.
     */
    public static void run(int nprocs, int start, int stop, int step, MTPListRunnable runner) {
	int phase = start % step;
	int[][] lRs = getLRs(nprocs, stop-start);
	Thread[] threads = new Thread[nprocs];
	for(int i=0; i<nprocs; i++) {
	    int procID = i;
	    int[] currRange = lRs[i];
	    currRange[0] += start;
	    currRange[1] += start;
	    
	    threads[procID] = new Thread(() -> {
		int threadPhase = currRange[0]%step;
		int segmStart = currRange[0]+(phase-threadPhase+step)%step;
		for(int j=segmStart; j<currRange[1]; j+=step) {
		    runner.iter(procID, j, j);
		}
	    });
	    threads[procID].start();
	}
	
	// join threads
	for(int i=0; i<threads.length; i++) {
	    try {
		threads[i].join();
	    } catch (InterruptedException ex) {}
	}
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
     * Loops through the elements of a Collection using multiple threads. All
     * calls with the same procID are run sequentially and are therefore thread
     * safe. This allows each thread to consolidate its data into a dedicated variable or array.
     * @param nprocs The number of processors to use.
     * @param c The list to loop through.
     * @param runner The runnable to call on each value in the List.
     */
    public static <T> void run(int nprocs, Collection<T> c, MTPListRunnable<T> runner) {
		run(nprocs, PrimitiveList.create(c.toArray()), runner);
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
		run(nprocs, map.entrySet(), new MTPListRunnable<Map.Entry<K, V>>() {
			@Override
			public void iter(int procID, int idx, Map.Entry<K, V> val) {
				runner.iter(procID, val.getKey(), val.getValue());
			}
		});
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
		int[][] lRs = getLRs(nprocs, list.size());
		Thread[] threads = new Thread[nprocs];
		for(int i=0; i<nprocs; i++) {
			int procID = i;
			int[] currRange = lRs[i];

			threads[procID] = new Thread(() -> {
			for(int j=currRange[0]; j<currRange[1]; j++) {
				runner.iter(procID, j, list.get(j));
			}
			});
			threads[procID].start();
		}

		// join threads
		for(int i=0; i<threads.length; i++) {
			try {
			threads[i].join();
			} catch (InterruptedException ex) {}
		}
    }
    
}
