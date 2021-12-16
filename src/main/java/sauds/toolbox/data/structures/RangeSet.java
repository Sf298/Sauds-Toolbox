package sauds.toolbox.data.structures;

import java.util.*;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class RangeSet implements Set<Long> {

    private final List<Range> ranges = new ArrayList<>();

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public int size() {
        return ranges.stream()
                .mapToInt(r -> (int) (r.max - r.min + 1))
                .sum();
    }

    @Override
    public boolean contains(Object o) {
        if (isNull(o) || !(o instanceof Long)) {
            return false;
        }
        long l = (Long) o;
        int v = Collections.binarySearch(ranges, l, (a, b) -> ((Range)a).compare((Long) b));
        return v >= 0;
    }

    @Override
    public Iterator<Long> iterator() {
        throw new UnsupportedOperationException("Cant iterate");
    }

    @Override
    public Object[] toArray() {
        throw new UnsupportedOperationException("Cant convert to array");
    }

    @Override
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException("Cant convert to array");
    }

    @Override
    public boolean add(Long l) {
        int v = Collections.binarySearch(ranges, l, (a, b) -> ((Range)a).compare((Long) b));
        if(v >= 0) {
            return false;
        }
        v = ~v;
        ranges.add(v, new Range(l));

        Range left = (v > 0) ? ranges.get(v-1) : null;
        Range middle = ranges.get(v);
        Range right = (v < ranges.size()-1) ? ranges.get(v+1) : null;
        if(nonNull(left) && middle.connected(left)) {
            middle.min = Math.min(middle.min, left.min);
            middle.max = Math.max(middle.max, left.max);
            ranges.remove(v-1);
            v--;
        }
        if(nonNull(right) && middle.connected(right)) {
            middle.min = Math.min(middle.min, right.min);
            middle.max = Math.max(middle.max, right.max);
            ranges.remove(v+1);
        }
        return true;
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException("Cant remove objects");
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return c.stream().allMatch(this::contains);
    }

    @Override
    public boolean addAll(Collection<? extends Long> c) {
        return c.stream().anyMatch(this::add);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("Cant remove objects");
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("Cant remove objects");
    }

    @Override
    public void clear() {
        ranges.clear();
    }

    @Override
    public String toString() {
        String str = ranges.toString();
        return "{" + str.substring(1, str.length()-1) + '}';
    }

    private static class Range {
        long min;
        long max;
        public Range(long l) {
            min = max = l;
        }
        public int compare(long l) {
            if(l < min) {
                return 1;
            }
            if(l > max) {
                return -1;
            }
            return 0;
        }
        public boolean connected(Range r) {
            long minOfMax = Math.min(r.max, max);
            long maxOfMin = Math.max(r.min, min);
            return minOfMax >= maxOfMin-1;
        }

        @Override
        public String toString() {
            if(min == max) {
                return String.valueOf(min);
            }
            return min + "-" + max;
        }
    }

}
