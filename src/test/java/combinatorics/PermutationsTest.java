package combinatorics;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class PermutationsTest {

    @Test
    void testPermuteNoRepetition() {
        List<Integer> list = List.of(1,2,3,4,5,6,7);
        Set<List<Integer>> outputs = new HashSet<>();
        for (List<Integer> p : Permutations.permuteNoRepetition(list)) {
            outputs.add(p);
        }

        Iterator<List<Integer>> i = outputs.iterator();
        assertThat(i.next()).doesNotContainSequence(i.next());
        assertThat(outputs).hasSize(factorial(list.size()));
    }

    @Test
    void testPermuteWithRepetition() {
        List<Integer> list = List.of(1,2,3,4,5);
        Set<List<Integer>> outputs = new HashSet<>();
        for (List<Integer> p : Permutations.permuteWithRepetition(list)) {
            outputs.add(p);
        }

        Iterator<List<Integer>> i = outputs.iterator();
        assertThat(i.next()).doesNotContainSequence(i.next());
        assertThat(outputs).hasSize((int) Math.pow(list.size(), list.size()));
    }

    @Test
    void testPermuteCoordinates() {
        Set<int[]> outputs = new HashSet<>();
        for (int[] c : Permutations.permuteCoordinates(2,3,7)) {
            outputs.add(c);
        }

        assertThat(outputs).hasSize(42);
        assertThat(outputs).noneMatch(c -> c[0]<0 || c[1]<0 || c[2]<0);
        assertThat(outputs).noneMatch(c -> c[0]>=2 || c[1]>=3 || c[2]>=7);
    }

    @Test
    void testPermuteCuboidCoordinates() {
        Set<int[]> outputs = new HashSet<>();
        for (int[] c : Permutations.permuteCuboidCoordinates(2,3)) {
            outputs.add(c);
        }

        assertThat(outputs).hasSize(9);
        assertThat(outputs).noneMatch(c -> c[0]<0 || c[1]<0);
        assertThat(outputs).noneMatch(c -> c[0]>=3 || c[1]>=3);
    }

    private static int factorial(int number) {
        int result = 1;
        while (number > 1) {
            result *= number--;
        }
        return result;
    }

}