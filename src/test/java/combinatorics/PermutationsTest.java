package combinatorics;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
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

        assertThat(outputs).hasSize(factorial(list.size()));
    }

    @Test
    void testPermuteWithRepetition() {
        List<Integer> list = List.of(1,2,3,4,5);
        Set<List<Integer>> outputs = new HashSet<>();
        for (List<Integer> p : Permutations.permuteWithRepetition(list)) {
            outputs.add(p);
        }

        assertThat(outputs).hasSize((int) Math.pow(list.size(), list.size()));
    }

    private static int factorial(int number) {
        int result = 1;
        while (number > 1) {
            result *= number--;
        }
        return result;
    }

}