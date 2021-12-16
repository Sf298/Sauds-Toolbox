package sauds.toolbox.graphs;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;

import static java.lang.Character.isUpperCase;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

class NodeTest {

    @Test
    void breadthFirstBasic() {
        Integer[][] grid = new Integer[][] {new Integer[] {3,2,1,2,3}};
        GridGraph<Integer> graph = new GridGraph<>(grid, 4);

        List<Integer> actual = new ArrayList<>();
        for (Node<Integer> n : graph.get(0, 2).breadthFirst()) {
            actual.add(n.value);
        }

        assertThat(actual).isEqualTo(List.of(1,2,2,3,3));
    }

    @Test
    void breadthFirstFiltered() {
        Integer[][] grid = new Integer[][] {new Integer[] {3,2,1,2,3,4,5,1}};
        GridGraph<Integer> graph = new GridGraph<>(grid, 4);

        List<Integer> actual = new ArrayList<>();
        for (Node<Integer> n : graph.get(0, 2).breadthFirst(n -> n.value < 5)) {
            actual.add(n.value);
        }

        assertThat(actual).isEqualTo(List.of(1,2,2,3,3,4));
    }

    @Test
    void breadthFirstFilteredPreProcessed() {
        Integer[][] grid = new Integer[][] {new Integer[] {1,2,3,4,5,6,7,2}};
        GridGraph<Integer> graph = new GridGraph<>(grid, 4);

        graph.get(0,0).breadthFirstPrefiltered(n -> n.value < 8, n -> n.adjacent.forEach(n1 -> n1.value++));

        assertThat(graph.grid.get(0).stream().map(n -> n.value).collect(toList())).isEqualTo(List.of(2,4,5,6,7,7,8,2));
    }

    @Test
    void depthFirstBasic() {
        Integer[][] grid = new Integer[][] {new Integer[] {3,2,1,2,3}};
        GridGraph<Integer> graph = new GridGraph<>(grid, 4);

        List<Integer> actual = new ArrayList<>();
        for (Node<Integer> n : graph.get(0, 2).depthFirst()) {
            actual.add(n.value);
        }

        assertThat(actual).isEqualTo(List.of(1,2,3,2,3));
    }

    @Test
    void testDepthFirst() {
        Integer[][] grid = new Integer[][] {new Integer[] {3,2,1,2,3,4,1}};
        GridGraph<Integer> graph = new GridGraph<>(grid, 4);

        List<Integer> actual = new ArrayList<>();
        for (Node<Integer> n : graph.get(0, 2).depthFirst(n -> n.value < 4)) {
            actual.add(n.value);
        }

        assertThat(actual).isEqualTo(List.of(1,2,3,2,3));
    }

    @Test
    void testWalks() {
        EdgeGraph<String> graph = new EdgeGraph<>(List.of(
                List.of("start", "A"),
                List.of("start", "b"),
                List.of("A", "c"),
                List.of("c", "A"),
                List.of("A", "b"),
                List.of("b", "A"),
                List.of("b", "d"),
                List.of("d", "b"),
                List.of("A", "end"),
                List.of("b", "end")
        ));

        BiPredicate<List<Node<String>>, Node<String>> predicate = (path, newNode) -> isUpperCase(newNode.value.charAt(0)) || !path.contains(newNode);

        List<List<Node<String>>> actual = new ArrayList<>();
        for (List<Node<String>> path : graph.getByValue("start").walks(graph.getByValue("end"), predicate)) {
            actual.add(path);
        }

        assertThat(actual).hasSize(10);
    }

    private static GridGraph<Integer>.GridNode toGN(Node<Integer> n) {
        return (GridGraph<Integer>.GridNode) n;
    }

}