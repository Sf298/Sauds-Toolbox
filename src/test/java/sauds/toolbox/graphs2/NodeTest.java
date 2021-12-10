package sauds.toolbox.graphs2;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class NodeTest {

    @Test
    void breadthFirstBasic() {
        Integer[][] grid = new Integer[][] {new Integer[] {1,2,3,4,5}};
        GridGraph<Integer> graph = new GridGraph<>(grid, 4);

        List<Integer> actual = new ArrayList<>();
        for (Node<Integer> n : graph.get(0, 2).breadthFirst()) {
            actual.add(n.value);
        }

        assertThat(actual).isEqualTo(List.of(3,4,2,5,1));
    }

    @Test
    void breadthFirstFiltered() {
        Integer[][] grid = new Integer[][] {new Integer[] {1,2,3,4,5,6,7,2}};
        GridGraph<Integer> graph = new GridGraph<>(grid, 4);

        List<Integer> actual = new ArrayList<>();
        for (Node<Integer> n : graph.get(0, 2).breadthFirst(n -> n.value < 7)) {
            actual.add(n.value);
        }

        assertThat(actual).isEqualTo(List.of(3,2,4,1,5,6));
    }

    @Test
    void depthFirstBasic() {
        Integer[][] grid = new Integer[][] {new Integer[] {1,2,3,4,5}};
        GridGraph<Integer> graph = new GridGraph<>(grid, 4);

        List<Integer> actual = new ArrayList<>();
        for (Node<Integer> n : graph.get(0, 2).depthFirst()) {
            actual.add(n.value);
        }

        assertThat(actual).isEqualTo(List.of(3,4,5,2,1));
    }

    @Test
    void testDepthFirst() {
        Integer[][] grid = new Integer[][] {new Integer[] {1,2,3,4,5,6,7,2}};
        GridGraph<Integer> graph = new GridGraph<>(grid, 4);

        List<Integer> actual = new ArrayList<>();
        for (Node<Integer> n : graph.get(0, 2).depthFirst(n -> n.value < 7)) {
            actual.add(n.value);
        }

        assertThat(actual).isEqualTo(List.of(3,4,5,6,2,1));
    }
}