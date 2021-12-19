package combinatorics;

import java.util.*;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

/**
 * Based on the loop implementation of heap's algorithm on <a href="https://en.wikipedia.org/wiki/Heap's_algorithm#Details_of_the_algorithm">wikipedia</a>.
 */
public class Permutations {

    public static <T> Iterable<List<T>> permuteNoRepetition(List<T> items) {
        return () -> new Iterator<List<T>>() {
            final List<T> itemsCopy = new ArrayList<>(items);
            final int[] c = new int[itemsCopy.size()];
            int i = 0;
            List<T> next = new ArrayList<>(itemsCopy);

            @Override
            public boolean hasNext() {
                return i < itemsCopy.size();
            }

            @Override
            public List<T> next() {
                List<T> toReturn = next;
                next = internalNext();
                return toReturn;
            }

            public List<T> internalNext() {
                List<T> output = null;
                while (i < itemsCopy.size()) {
                    if (c[i] >= i) {
                        c[i] = 0;
                        i += 1;
                        continue;
                    }

                    if ((i & 1) == 0) {
                        T temp = itemsCopy.get(0);
                        itemsCopy.set(0, itemsCopy.get(i));
                        itemsCopy.set(i, temp);
                    } else {
                        T temp = itemsCopy.get(c[i]);
                        itemsCopy.set(c[i], itemsCopy.get(i));
                        itemsCopy.set(i, temp);
                    }
                    output = new ArrayList<>(itemsCopy);
                    c[i] += 1;
                    i = 0;
                    break;
                }
                return output;
            }
        };
    }

    public static <T> Iterable<List<T>> permuteWithRepetition(List<T> items) {
        return () -> new Iterator<>() {
            final List<T> itemsCopy = new ArrayList<>(items);
            final List<Integer> indexes = IntStream.range(0, itemsCopy.size()).mapToObj(i -> 0).collect(toList());
            boolean started = false;

            @Override
            public boolean hasNext() {
                return !indexes.stream().allMatch(i -> i == itemsCopy.size()-1);
            }

            @Override
            public List<T> next() {
                if (!started) {
                    started = true;
                    return indexes.stream().map(itemsCopy::get).collect(toList());
                }
                for (int i = indexes.size()-1; i >= 0; i--) {
                    indexes.set(i, indexes.get(i)+1);
                    if (indexes.get(i) != indexes.size()) {
                        break;
                    }
                    indexes.set(i, 0);
                }

                return indexes.stream().map(itemsCopy::get).collect(toList());
            }

        };
    }

}
