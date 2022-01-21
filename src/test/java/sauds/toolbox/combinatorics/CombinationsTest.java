package sauds.toolbox.combinatorics;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class CombinationsTest {

    @Test
    void testCombinations_items() {
        List<Integer> list = List.of(1,2,3,4,5);
        Set<List<Integer>> outputs = new HashSet<>();
        for (List<Integer> p : Combinations.combinations(list)) {
            outputs.add(p);
        }

        Iterator<List<Integer>> i = outputs.iterator();
        assertThat(i.next()).doesNotContainSequence(i.next());
        assertThat(outputs).hasSize(32);
    }

    @Test
    void testCombinations_withLength() {
        List<Integer> list = List.of(1,2,3,4,5);
        Set<List<Integer>> outputs = new HashSet<>();
        for (List<Integer> p : Combinations.combinations(list, 3)) {
            outputs.add(p);
        }

        Iterator<List<Integer>> i = outputs.iterator();
        assertThat(i.next()).doesNotContainSequence(i.next());
        assertThat(outputs).hasSize(10);
    }

}