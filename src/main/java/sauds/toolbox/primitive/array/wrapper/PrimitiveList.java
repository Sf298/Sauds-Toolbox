
package sauds.toolbox.primitive.array.wrapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 *
 * @author saud
 */
public class PrimitiveList<T> implements List<T> {
    
    private static final int BYTE = 0;
    private static final int SHORT = 1;
    private static final int INT = 2;
    private static final int LONG = 3;
    private static final int FLOAT = 4;
    private static final int DOUBLE = 5;
    private static final int CHAR = 6;
    private static final int BOOL = 7;
    
    private int type;
    private byte[] by;
    private short[] ss;
    private int[] ii;
    private long[] ll;
    private float[] ff;
    private double[] dd;
    private char[] cc;
    private boolean[] bb;
    
    private PrimitiveList(){}
    
    public static PrimitiveList<Byte> create(byte[] arr) {
	PrimitiveList<Byte> out = new PrimitiveList<>();
	out.by = arr;
	out.type = BYTE;
	return out;
    }
    public static PrimitiveList<Short> create(short[] arr) {
	PrimitiveList<Short> out = new PrimitiveList<>();
	out.ss = arr;
	out.type = SHORT;
	return out;
    }
    public static PrimitiveList<Integer> create(int[] arr) {
	PrimitiveList<Integer> out = new PrimitiveList<>();
	out.ii = arr;
	out.type = INT;
	return out;
    }
    public static PrimitiveList<Long> create(long[] arr) {
	PrimitiveList<Long> out = new PrimitiveList<>();
	out.ll = arr;
	out.type = LONG;
	return out;
    }
    public static PrimitiveList<Float> create(float[] arr) {
	PrimitiveList<Float> out = new PrimitiveList<>();
	out.ff = arr;
	out.type = FLOAT;
	return out;
    }
    public static PrimitiveList<Double> create(double[] arr) {
	PrimitiveList<Double> out = new PrimitiveList<>();
	out.dd = arr;
	out.type = DOUBLE;
	return out;
    }
    public static PrimitiveList<Character> create(char[] arr) {
	PrimitiveList<Character> out = new PrimitiveList<>();
	out.cc = arr;
	out.type = CHAR;
	return out;
    }
    public static PrimitiveList<Boolean> create(boolean[] arr) {
	PrimitiveList<Boolean> out = new PrimitiveList<>();
	out.bb = arr;
	out.type = BOOL;
	return out;
    }

    @Override
    public int size() {
	switch(type) {
	    case BYTE:
		return by.length;
	    case SHORT:
		return ss.length;
	    case INT:
		return ii.length;
	    case LONG:
		return dd.length;
	    case FLOAT:
		return dd.length;
	    case DOUBLE:
		return dd.length;
	    case CHAR:
		return dd.length;
	    case BOOL:
		return dd.length;
	}
	return 0;
    } // =====

    @Override
    public boolean isEmpty() {
	return size() == 0;
    }

    @Override
    public boolean contains(Object o) {
	return indexOf(o) != -1;
    }

    @Override
    public Iterator<T> iterator()  {
	PrimitiveList<T> pl = this;
	return new Iterator<T>() {
	    int i=0;
	    
	    @Override
	    public boolean hasNext() {
		return i < pl.size();
	    }

	    @Override
	    public T next() {
		return pl.get(i++);
	    }
	};
    }

    @Override
    public Object[] toArray() {
	switch(type) {
	    case BYTE:
		return new Object[] {by};
	    case SHORT:
		return new Object[] {ss};
	    case INT:
		return new Object[] {ii};
	    case LONG:
		return new Object[] {ll};
	    case FLOAT:
		return new Object[] {ff};
	    case DOUBLE:
		return new Object[] {dd};
	    case CHAR:
		return new Object[] {cc};
	    case BOOL:
		return new Object[] {bb};
	}
	return null;
    } // =====

    @Override
    public <T> T[] toArray(T[] a) {
	throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public boolean add(T e) {
	throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public boolean remove(Object o) {
	throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public boolean containsAll(Collection<?> c) {
	HashSet<?> set = new HashSet<>(this);
	for(Object o : c) {
	    if(!set.contains(o))
		return false;
	}
	return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
	throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
	throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public boolean removeAll(Collection<?> c) {
	throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public boolean retainAll(Collection<?> c) {
	throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public void clear() {
	throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public T get(int index) {
	switch(type) {
	    case BYTE:
		return (T)(Byte)by[index];
	    case SHORT:
		return (T)(Short)ss[index];
	    case INT:
		return (T)(Integer)ii[index];
	    case LONG:
		return (T)(Long)ll[index];
	    case FLOAT:
		return (T)(Float)ff[index];
	    case DOUBLE:
		return (T)(Double)dd[index];
	    case CHAR:
		return (T)(Character)cc[index];
	    case BOOL:
		return (T)(Boolean)bb[index];
	}
	return null;
    } // =====

    @Override
    public T set(int index, T element) {
	switch(type) {
	    case BYTE:
		by[index] = (Byte) element;
		break;
	    case SHORT:
		ss[index] = (Short) element;
		break;
	    case INT:
		ii[index] = (Integer) element;
		break;
	    case LONG:
		ll[index] = (Long) element;
		break;
	    case FLOAT:
		ff[index] = (Float) element;
		break;
	    case DOUBLE:
		dd[index] = (Double) element;
		break;
	    case CHAR:
		cc[index] = (Character) element;
		break;
	    case BOOL:
		bb[index] = (Boolean) element;
		break;
	}
	return element;
    } // =====

    @Override
    public void add(int index, T element) {
	throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public T remove(int index) {
	throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public int indexOf(Object o) {
	switch(type) {
	    case BYTE:
		for (int i=0; i<by.length; i++)
		    if((Byte)o == by[i]) return i;
		break;
	    case SHORT:
		for (int i=0; i<ss.length; i++)
		    if((Short)o == ss[i]) return i;
		break;
	    case INT:
		for (int i=0; i<ii.length; i++)
		    if((Integer)o == ii[i]) return i;
		break;
	    case LONG:
		for (int i=0; i<ll.length; i++)
		    if((Long)o == ll[i]) return i;
		break;
	    case FLOAT:
		for (int i=0; i<ff.length; i++)
		    if((Float)o == ff[i]) return i;
		break;
	    case DOUBLE:
		for (int i=0; i<dd.length; i++)
		    if((Double)o == dd[i]) return i;
		break;
	    case CHAR:
		for (int i=0; i<cc.length; i++)
		    if((Character)o == cc[i]) return i;
		break;
	    case BOOL:
		for (int i=0; i<bb.length; i++)
		    if((Boolean)o == bb[i]) return i;
		break;
	}
	return -1;
    } // =====

    @Override
    public int lastIndexOf(Object o) {
	switch(type) {
	    case BYTE:
		for (int i=by.length-1; i>=0; i--)
		    if((Byte)o == by[i]) return i;
		break;
	    case SHORT:
		for (int i=ss.length-1; i>=0; i--)
		    if((Short)o == ss[i]) return i;
		break;
	    case INT:
		for (int i=ii.length-1; i>=0; i--)
		    if((Integer)o == ii[i]) return i;
		break;
	    case LONG:
		for (int i=ll.length-1; i>=0; i--)
		    if((Long)o == ll[i]) return i;
		break;
	    case FLOAT:
		for (int i=ff.length-1; i>=0; i--)
		    if((Float)o == ff[i]) return i;
		break;
	    case DOUBLE:
		for (int i=dd.length-1; i>=0; i--)
		    if((Double)o == dd[i]) return i;
		break;
	    case CHAR:
		for (int i=cc.length-1; i>=0; i--)
		    if((Character)o == cc[i]) return i;
		break;
	    case BOOL:
		for (int i=bb.length-1; i>=0; i--)
		    if((Boolean)o == bb[i]) return i;
		break;
	    }
	return -1;
    } // =====

    @Override
    public ListIterator<T> listIterator() {
	throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public ListIterator<T> listIterator(int index) {
	throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
	ArrayList<T> out = new ArrayList<>();
	switch(type) {
	    case BYTE:
		for(int i=fromIndex; i<toIndex; i++)
		    out.add((T)(Byte)by[i]);
		break;
	    case SHORT:
		for(int i=fromIndex; i<toIndex; i++)
		    out.add((T)(Short)ss[i]);
		break;
	    case INT:
		for(int i=fromIndex; i<toIndex; i++)
		    out.add((T)(Integer)ii[i]);
		break;
	    case LONG:
		for(int i=fromIndex; i<toIndex; i++)
		    out.add((T)(Long)ll[i]);
		break;
	    case FLOAT:
		for(int i=fromIndex; i<toIndex; i++)
		    out.add((T)(Float)ff[i]);
		break;
	    case DOUBLE:
		for(int i=fromIndex; i<toIndex; i++)
		    out.add((T)(Double)dd[i]);
		break;
	    case CHAR:
		for(int i=fromIndex; i<toIndex; i++)
		    out.add((T)(Character)cc[i]);
		break;
	    case BOOL:
		for(int i=fromIndex; i<toIndex; i++)
		    out.add((T)(Boolean)bb[i]);
		break;
	}
	return out;
    } // =====
    
}
