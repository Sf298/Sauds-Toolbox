package sauds.toolbox.combinatorics;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class Combinations {

    public static <T> Iterable<List<T>> combinations(Collection<T> items) {
        return () -> new Iterator<>() {
            final List<T> itemsCopy = new ArrayList<>(items);
            BigInteger index = BigInteger.ZERO;
            final BigInteger endIndex = BigInteger.ZERO.setBit(itemsCopy.size());

            @Override
            public boolean hasNext() {
                return index.compareTo(endIndex) < 0;
            }

            @Override
            public List<T> next() {
                List<T> out = IntStream.range(0, itemsCopy.size())
                        .filter(index::testBit)
                        .mapToObj(itemsCopy::get)
                        .collect(toList());
                index = index.add(BigInteger.ONE);
                return out;
            }

        };
    }

    public static <T> Iterable<List<T>> combinations(List<T> items, Integer itemCount) {
        return () -> new Iterator<>() {
            final List<T> itemsCopy = new ArrayList<>(items);
            final int[] initial = initIndexes();
            final int[] indexes = initIndexes();

            private int[] initIndexes() {
                int[] out = new int[itemCount];
                for (int i = 0; i < itemCount; i++) {
                    out[i] = itemCount - i - 1;
                }
                return out;
            }

            @Override
            public boolean hasNext() {
                return indexes[0] < itemsCopy.size();
            }

            @Override
            public List<T> next() {
                List<T> out = Arrays.stream(indexes)
                        .mapToObj(itemsCopy::get)
                        .collect(toList());

                indexes[indexes.length-1]++;
                for (int i = indexes.length-1; i > 0; i--) {
                    if (indexes[i] >= indexes[i-1]) {
                        indexes[i] = initial[i];
                        indexes[i-1]++;
                    }
                }

                return out;
            }

        };
    }

    public static void main(String[] args) {
        List<Integer> list = List.of(1,2,3);
        for (List<Integer> c : combinations(list)) {
            System.out.println(c);
        }
    }

}
