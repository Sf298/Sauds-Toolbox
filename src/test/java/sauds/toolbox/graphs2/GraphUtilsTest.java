package sauds.toolbox.graphs2;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class GraphUtilsTest {

    @Test
    void boxedInteger() {
        int[][] grid = new int[][] {
                new int[] {1,2},
                new int[] {1,2,3}
        };

        Integer[][] expected = new Integer[][] {
                new Integer[] {1,2},
                new Integer[] {1,2,3}
        };

        Integer[][] actual = GraphUtils.boxed(grid);
        assertThat(actual).isDeepEqualTo(expected);
    }

    @Test
    void boxedLong() {
        long[][] grid = new long[][] {
                new long[] {1,2},
                new long[] {1,2,3}
        };

        Long[][] expected = new Long[][] {
                new Long[] {1L,2L},
                new Long[] {1L,2L,3L}
        };

        Long[][] actual = GraphUtils.boxed(grid);
        assertThat(actual).isDeepEqualTo(expected);
    }

    @Test
    void boxedDouble() {
        double[][] grid = new double[][] {
                new double[] {1,2},
                new double[] {1,2,3}
        };

        Double[][] expected = new Double[][] {
                new Double[] {1.0,2.0},
                new Double[] {1.0,2.0,3.0}
        };

        Double[][] actual = GraphUtils.boxed(grid);
        assertThat(actual).isDeepEqualTo(expected);
    }

    @Test
    void stream() {
        List<Integer> expected = List.of(1,3,2);

        List<Integer> actual = GraphUtils.stream(expected).collect(Collectors.toList());
        assertThat(actual).isEqualTo(actual);
    }

}