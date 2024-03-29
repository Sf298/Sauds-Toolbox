
package sauds.toolbox.data.wrappers;

import java.util.ArrayList;
import java.util.Arrays;
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
    private static final int OBJ = 8;
    
    private final int type;
    private byte[] by;
    private short[] ss;
    private int[] ii;
    private long[] ll;
    private float[] ff;
    private double[] dd;
    private char[] cc;
    private boolean[] bb;
    private Object[] oo;
    
    private PrimitiveList(int type) {
		if (type < 0 || type > 8)
			throw new ClassCastException("Unknown type");
		this.type = type;
	}
    
    public static PrimitiveList<Byte> create(byte[] arr) {
		PrimitiveList<Byte> out = new PrimitiveList<>(BYTE);
		out.by = arr;
		return out;
    }
    public static PrimitiveList<Short> create(short[] arr) {
		PrimitiveList<Short> out = new PrimitiveList<>(SHORT);
		out.ss = arr;
		return out;
    }
    public static PrimitiveList<Integer> create(int[] arr) {
		PrimitiveList<Integer> out = new PrimitiveList<>(INT);
		out.ii = arr;
		return out;
    }
    public static PrimitiveList<Long> create(long[] arr) {
		PrimitiveList<Long> out = new PrimitiveList<>(LONG);
		out.ll = arr;
		return out;
    }
    public static PrimitiveList<Float> create(float[] arr) {
		PrimitiveList<Float> out = new PrimitiveList<>(FLOAT);
		out.ff = arr;
		return out;
    }
    public static PrimitiveList<Double> create(double[] arr) {
		PrimitiveList<Double> out = new PrimitiveList<>(DOUBLE);
		out.dd = arr;
		return out;
    }
    public static PrimitiveList<Character> create(char[] arr) {
		PrimitiveList<Character> out = new PrimitiveList<>(CHAR);
		out.cc = arr;
		return out;
    }
    public static PrimitiveList<Boolean> create(boolean[] arr) {
		PrimitiveList<Boolean> out = new PrimitiveList<>(BOOL);
		out.bb = arr;
		return out;
    }
    public static PrimitiveList create(Object[] arr) {
		PrimitiveList out = new PrimitiveList(OBJ);
		out.oo = arr;
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
				return ll.length;
			case FLOAT:
				return ff.length;
			case DOUBLE:
				return dd.length;
			case CHAR:
				return cc.length;
			case BOOL:
				return bb.length;
			case OBJ:
				return oo.length;
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
		return new Iterator<>() {
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
			case OBJ:
				return new Object[] {oo};
		}
		return null;
    } // =====

    @Override
    public <T1> T1[] toArray(T1[] a) {
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
		return new HashSet<>(this).containsAll(c);
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
			case OBJ:
			return (T)oo[index];
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
			case OBJ:
				oo[index] = element;
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
		for (int i=0; i<by.length; i++)
			if(o.equals(get(i)))
				return i;
		return -1;
    } // =====

    @Override
    public int lastIndexOf(Object o) {
		for (int i=by.length-1; i>=0; i--)
			if(o.equals(get(i)))
				return i;
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
		for(int i=fromIndex; i<toIndex; i++)
			out.add(get(i));
		return out;
    } // =====

	@Override
	public int hashCode() {
		switch(type) {
			case BYTE:  return Arrays.hashCode(by);
			case SHORT:  return Arrays.hashCode(ss);
			case INT:  return Arrays.hashCode(ii);
			case LONG:  return Arrays.hashCode(ll);
			case FLOAT:  return Arrays.hashCode(ff);
			case DOUBLE:  return Arrays.hashCode(dd);
			case CHAR:  return Arrays.hashCode(cc);
			case BOOL:  return Arrays.hashCode(bb);
			case OBJ:  return Arrays.hashCode(oo);
		}
		return 0;
	} // =====

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final PrimitiveList<?> other = (PrimitiveList<?>) obj;
		if(this.type != other.type) return false;
		if(this.by!=null) return Arrays.equals(this.by, other.by);
		if(this.ss!=null) return Arrays.equals(this.ss, other.ss);
		if(this.ii!=null) return Arrays.equals(this.ii, other.ii);
		if(this.ll!=null) return Arrays.equals(this.ll, other.ll);
		if(this.ff!=null) return Arrays.equals(this.ff, other.ff);
		if(this.dd!=null) return Arrays.equals(this.dd, other.dd);
		if(this.cc!=null) return Arrays.equals(this.cc, other.cc);
		if(this.bb!=null) return Arrays.equals(this.bb, other.bb);
		if(this.oo!=null) return Arrays.deepEquals(this.oo, other.oo);
		return true;
	}
    
}
