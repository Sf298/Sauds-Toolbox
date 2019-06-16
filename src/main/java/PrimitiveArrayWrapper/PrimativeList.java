/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * Oracle and Java are registered trademarks of Oracle and/or its affiliates.
 * Other names may be trademarks of their respective owners.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common
 * Development and Distribution License("CDDL") (collectively, the
 * "License"). You may not use this file except in compliance with the
 * License. You can obtain a copy of the License at
 * http://www.netbeans.org/cddl-gplv2.html
 * or nbbuild/licenses/CDDL-GPL-2-CP. See the License for the
 * specific language governing permissions and limitations under the
 * License.  When distributing the software, include this License Header
 * Notice in each file and include the License file at
 * nbbuild/licenses/CDDL-GPL-2-CP.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the GPL Version 2 section of the License file that
 * accompanied this code. If applicable, add the following below the
 * License Header, with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * If you wish your version of this file to be governed by only the CDDL
 * or only the GPL Version 2, indicate your decision by adding
 * "[Contributor] elects to include this software in this distribution
 * under the [CDDL or GPL Version 2] license." If you do not indicate a
 * single choice of license, a recipient has the option to distribute
 * your version of this file under either the CDDL, the GPL Version 2 or
 * to extend the choice of license to its licensees as provided above.
 * However, if you add GPL Version 2 code and therefore, elected the GPL
 * Version 2 license, then the option applies only if the new code is
 * made subject to such option by the copyright holder.
 *
 * Contributor(s):
 */
package PrimitiveArrayWrapper;

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
public class PrimativeList<T> implements List<T> {
    
    private byte[] by;
    private short[] ss;
    private int[] ii;
    private long[] ll;
    private float[] ff;
    private double[] dd;
    private char[] cc;
    private boolean[] bb;
    
    private PrimativeList(){}
    
    public static PrimativeList<Byte> create(byte[] arr) {
	PrimativeList<Byte> out = new PrimativeList<>();
	out.by = arr;
	return out;
    }
    public static PrimativeList<Short> create(short[] arr) {
	PrimativeList<Short> out = new PrimativeList<>();
	out.ss = arr;
	return out;
    }
    public static PrimativeList<Integer> create(int[] arr) {
	PrimativeList<Integer> out = new PrimativeList<>();
	out.ii = arr;
	return out;
    }
    public static PrimativeList<Long> create(long[] arr) {
	PrimativeList<Long> out = new PrimativeList<>();
	out.ll = arr;
	return out;
    }
    public static PrimativeList<Float> create(float[] arr) {
	PrimativeList<Float> out = new PrimativeList<>();
	out.ff = arr;
	return out;
    }
    public static PrimativeList<Double> create(double[] arr) {
	PrimativeList<Double> out = new PrimativeList<>();
	out.dd = arr;
	return out;
    }
    public static PrimativeList<Character> create(char[] arr) {
	PrimativeList<Character> out = new PrimativeList<>();
	out.cc = arr;
	return out;
    }
    public static PrimativeList<Boolean> create(boolean[] arr) {
	PrimativeList<Boolean> out = new PrimativeList<>();
	out.bb = arr;
	return out;
    }

    @Override
    public int size() {
	if(ii != null)
	    return ii.length;
	if(dd != null)
	    return dd.length;
	return 0;
    }

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
	PrimativeList<T> pl = this;
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
	if(by != null)
	    return new Object[] {by};
	if(ss != null)
	    return new Object[] {ss};
	if(ii != null)
	    return new Object[] {ii};
	if(ll != null)
	    return new Object[] {ll};
	if(ff != null)
	    return new Object[] {ff};
	if(dd != null)
	    return new Object[] {dd};
	if(cc != null)
	    return new Object[] {cc};
	if(bb != null)
	    return new Object[] {bb};
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
	if(by != null)
	    return (T)(Byte)by[index];
	if(ss != null)
	    return (T)(Short)ss[index];
	if(ii != null)
	    return (T)(Integer)ii[index];
	if(ll != null)
	    return (T)(Long)ll[index];
	if(ff != null)
	    return (T)(Float)ff[index];
	if(dd != null)
	    return (T)(Double)dd[index];
	if(cc != null)
	    return (T)(Character)cc[index];
	if(bb != null)
	    return (T)(Boolean)bb[index];
	return null;
    } // =====

    @Override
    public T set(int index, T element) {
	if(by != null)
	    by[index] = (Byte) element;
	if(ss != null)
	    ss[index] = (Short) element;
	if(ii != null)
	    ii[index] = (Integer) element;
	if(ll != null)
	    ll[index] = (Long) element;
	if(ff != null)
	    ff[index] = (Float) element;
	if(dd != null)
	    dd[index] = (Double) element;
	if(cc != null)
	    cc[index] = (Character) element;
	if(bb != null)
	    bb[index] = (Boolean) element;
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
	if(by != null) {
	    for (int i=0; i<by.length; i++)
		if((Byte)o == by[i]) return i;
	} else if(ss != null) {
	    for (int i=0; i<ss.length; i++)
		if((Short)o == ss[i]) return i;
	} else if(ii != null) {
	    for (int i=0; i<ii.length; i++)
		if((Integer)o == ii[i]) return i;
	} else if(ll != null) {
	    for (int i=0; i<ll.length; i++)
		if((Long)o == ll[i]) return i;
	} else if(ff != null) {
	    for (int i=0; i<ff.length; i++)
		if((Float)o == ff[i]) return i;
	} else if(dd != null) {
	    for (int i=0; i<dd.length; i++)
		if((Double)o == dd[i]) return i;
	} else if(cc != null) {
	    for (int i=0; i<cc.length; i++)
		if((Character)o == cc[i]) return i;
	} else if(bb != null) {
	    for (int i=0; i<bb.length; i++)
		if((Boolean)o == bb[i]) return i;
	}
	return -1;
    } // =====

    @Override
    public int lastIndexOf(Object o) {
	if(by != null) {
	    for (int i=by.length-1; i>=0; i--)
		if((Byte)o == by[i]) return i;
	} else if(ss != null) {
	    for (int i=ss.length-1; i>=0; i--)
		if((Short)o == ss[i]) return i;
	} else if(ii != null) {
	    for (int i=ii.length-1; i>=0; i--)
		if((Integer)o == ii[i]) return i;
	} else if(ll != null) {
	    for (int i=ll.length-1; i>=0; i--)
		if((Long)o == ll[i]) return i;
	} else if(ff != null) {
	    for (int i=ff.length-1; i>=0; i--)
		if((Float)o == ff[i]) return i;
	} else if(dd != null) {
	    for (int i=dd.length-1; i>=0; i--)
		if((Double)o == dd[i]) return i;
	} else if(cc != null) {
	    for (int i=cc.length-1; i>=0; i--)
		if((Character)o == cc[i]) return i;
	} else if(bb != null) {
	    for (int i=bb.length-1; i>=0; i--)
		if((Boolean)o == bb[i]) return i;
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
	if(by != null) {
	    for(int i=fromIndex; i<toIndex; i++)
		out.add((T)(Byte)by[i]);
	} else if(ss != null) {
	    for(int i=fromIndex; i<toIndex; i++)
		out.add((T)(Short)ss[i]);
	} else if(ii != null) {
	    for(int i=fromIndex; i<toIndex; i++)
		out.add((T)(Integer)ii[i]);
	} else if(ll != null) {
	    for(int i=fromIndex; i<toIndex; i++)
		out.add((T)(Long)ll[i]);
	} else if(ff != null) {
	    for(int i=fromIndex; i<toIndex; i++)
		out.add((T)(Float)ff[i]);
	} else if(dd != null) {
	    for(int i=fromIndex; i<toIndex; i++)
		out.add((T)(Double)dd[i]);
	} else if(cc != null) {
	    for(int i=fromIndex; i<toIndex; i++)
		out.add((T)(Character)cc[i]);
	} else if(bb != null) {
	    for(int i=fromIndex; i<toIndex; i++)
		out.add((T)(Boolean)bb[i]);
	}
	return out;
    } // =====
    
}
