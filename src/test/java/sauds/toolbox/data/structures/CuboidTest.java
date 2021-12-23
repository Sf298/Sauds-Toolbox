package sauds.toolbox.data.structures;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.tools4j.spockito.jupiter.TableSource;

import java.math.BigInteger;
import java.util.List;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static sauds.toolbox.data.structures.Cuboid.mergeAll;

class CuboidTest {

    private static final Cuboid underTest = new Cuboid(new Coordinate(1,2), new Coordinate(4,5));

    @Test
    void testSurrounds() {
        assertThat(Cuboid.surrounds(0,1,2,3)).isFalse();
        assertThat(Cuboid.surrounds(0,2,1,3)).isFalse();
        assertThat(Cuboid.surrounds(1,3,0,2)).isFalse();
        assertThat(Cuboid.surrounds(0,3,1,2)).isTrue();
    }

    @Test
    void testOverlaps() {
        assertThat(Cuboid.overlaps(0,1,2,3)).isFalse();
        assertThat(Cuboid.overlaps(0,2,1,3)).isTrue();
        assertThat(Cuboid.overlaps(1,3,0,2)).isTrue();
        assertThat(Cuboid.overlaps(0,3,1,2)).isFalse();
    }

    @Test
    void testUnconnected() {
        assertThat(Cuboid.unconnected(0,1,2,3)).isTrue();
        assertThat(Cuboid.unconnected(0,2,1,3)).isFalse();
        assertThat(Cuboid.unconnected(1,3,0,2)).isFalse();
        assertThat(Cuboid.unconnected(0,3,1,2)).isFalse();
    }

    @Test
    void testArea() {
        assertThat(underTest.area()).isEqualTo(16);
    }

    @TableSource({
            "| x1 | x2 | y1 | y2 | expected | name                                           |",
            "|----|----|----|----|----------|------------------------------------------------|",
            "|  1 |  4 |  2 |  5 | true     | identical                                      |",
            "|  0 |  5 |  1 |  6 | false    | this contains underTest                        |",
            "|  2 |  3 |  3 |  4 | true     | underTest contains this                        |",

            "|  1 |  4 |  3 |  6 | false    | equal x, shifted overlapping +y                |",
            "|  1 |  4 |  1 |  4 | false    | equal x, shifted overlapping -y                |",
            "|  2 |  5 |  2 |  5 | false    | shifted overlapping +x, equal y                |",
            "|  0 |  3 |  2 |  5 | false    | shifted overlapping -x, equal y                |",

            "|  1 |  4 |  6 |  9 | false    | equal x, shifted adjacent +y                   |",
            "|  1 |  4 |  0 |  1 | false    | equal x, shifted adjacent -y                   |",
            "|  5 |  8 |  2 |  5 | false    | shifted adjacent +x, equal y                   |",
            "| -3 |  0 |  2 |  5 | false    | shifted adjacent -x, equal y                   |",

            "|  1 |  4 |  7 |  9 | false    | equal x, shifted unconnected +y                |",
            "|  1 |  4 | -1 |  0 | false    | equal x, shifted unconnected -y                |",
            "|  6 |  8 |  2 |  5 | false    | shifted unconnected +x, equal y                |",
            "| -3 | -1 |  2 |  5 | false    | shifted unconnected -x, equal y                |",

            "|  2 |  5 |  3 |  6 | false    | shifted overlapping +x, shifted overlapping +y |",
            "|  2 |  5 |  1 |  4 | false    | shifted overlapping +x, shifted overlapping -y |",
            "|  0 |  3 |  3 |  6 | false    | shifted overlapping -x, shifted overlapping +y |",
            "|  0 |  3 |  1 |  4 | false    | shifted overlapping -x, shifted overlapping -y |",

            "|  2 |  3 |  1 |  6 | false    | centers overlapping (plus shape)               |"
    })
    @ParameterizedTest(name = "{5}")
    void testContains_cuboid(int x1, int x2, int y1, int y2, boolean expected, String name) {
        Cuboid current = new Cuboid(new Coordinate(x1,y1), new Coordinate(x2, y2));
        assertThat(underTest.contains(current)).isEqualTo(expected);
    }

    @Test
    void testContains_coordinate() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                assertThat(underTest.contains(new Coordinate(i,j))).isEqualTo(1<=i && i<=4 && 2<=j && j<=5);
            }
        }
    }

    @TableSource({
            "| x1 | x2 | y1 | y2 | expected | name                                           |",
            "|----|----|----|----|----------|------------------------------------------------|",
            "|  1 |  4 |  2 |  5 | true     | identical                                      |",
            "|  0 |  5 |  1 |  6 | true     | this contains underTest                        |",
            "|  2 |  3 |  3 |  4 | true     | underTest contains this                        |",

            "|  1 |  4 |  3 |  6 | true     | equal x, shifted overlapping +y                |",
            "|  1 |  4 |  1 |  4 | true     | equal x, shifted overlapping -y                |",
            "|  2 |  5 |  2 |  5 | true     | shifted overlapping +x, equal y                |",
            "|  0 |  3 |  2 |  5 | true     | shifted overlapping -x, equal y                |",

            "|  1 |  4 |  6 |  9 | false    | equal x, shifted adjacent +y                   |",
            "|  1 |  4 |  0 |  1 | false    | equal x, shifted adjacent -y                   |",
            "|  5 |  8 |  2 |  5 | false    | shifted adjacent +x, equal y                   |",
            "| -3 |  0 |  2 |  5 | false    | shifted adjacent -x, equal y                   |",

            "|  1 |  4 |  7 |  9 | false    | equal x, shifted unconnected +y                |",
            "|  1 |  4 | -1 |  0 | false    | equal x, shifted unconnected -y                |",
            "|  6 |  8 |  2 |  5 | false    | shifted unconnected +x, equal y                |",
            "| -3 | -1 |  2 |  5 | false    | shifted unconnected -x, equal y                |",

            "|  2 |  5 |  3 |  6 | true     | shifted overlapping +x, shifted overlapping +y |",
            "|  2 |  5 |  1 |  4 | true     | shifted overlapping +x, shifted overlapping -y |",
            "|  0 |  3 |  3 |  6 | true     | shifted overlapping -x, shifted overlapping +y |",
            "|  0 |  3 |  1 |  4 | true     | shifted overlapping -x, shifted overlapping -y |",

            "|  2 |  3 |  1 |  6 | true     | centers overlapping (plus shape)               |"
    })
    @ParameterizedTest(name = "{5}")
    void testIntersects(int x1, int x2, int y1, int y2, boolean expected, String name) {
        Cuboid current = new Cuboid(new Coordinate(x1,y1), new Coordinate(x2, y2));
        assertThat(underTest.intersects(current)).isEqualTo(expected);
    }

    @TableSource({
            "| x1 | x2 | y1 | y2 | expected | name                                           |",
            "|----|----|----|----|----------|------------------------------------------------|",
            "|  1 |  4 |  2 |  5 | false    | identical                                      |",
            "|  0 |  5 |  1 |  6 | false    | this contains underTest                        |",
            "|  2 |  3 |  3 |  4 | false    | underTest contains this                        |",

            "|  1 |  4 |  3 |  6 | false    | equal x, shifted overlapping +y                |",
            "|  1 |  4 |  1 |  4 | false    | equal x, shifted overlapping -y                |",
            "|  2 |  5 |  2 |  5 | false    | shifted overlapping +x, equal y                |",
            "|  0 |  3 |  2 |  5 | false    | shifted overlapping -x, equal y                |",

            "|  1 |  4 |  6 |  9 | true     | equal x, shifted adjacent +y                   |",
            "|  1 |  4 |  0 |  1 | true     | equal x, shifted adjacent -y                   |",
            "|  5 |  8 |  2 |  5 | true     | shifted adjacent +x, equal y                   |",
            "| -3 |  0 |  2 |  5 | true     | shifted adjacent -x, equal y                   |",

            "|  1 |  4 |  7 |  9 | false    | equal x, shifted unconnected +y                |",
            "|  1 |  4 | -1 |  0 | false    | equal x, shifted unconnected -y                |",
            "|  6 |  8 |  2 |  5 | false    | shifted unconnected +x, equal y                |",
            "| -3 | -1 |  2 |  5 | false    | shifted unconnected -x, equal y                |",

            "|  2 |  5 |  3 |  6 | false    | shifted overlapping +x, shifted overlapping +y |",
            "|  2 |  5 |  1 |  4 | false    | shifted overlapping +x, shifted overlapping -y |",
            "|  0 |  3 |  3 |  6 | false    | shifted overlapping -x, shifted overlapping +y |",
            "|  0 |  3 |  1 |  4 | false    | shifted overlapping -x, shifted overlapping -y |",

            "|  2 |  3 |  1 |  6 | false    | centers overlapping (plus shape)               |"
    })
    @ParameterizedTest(name = "{5}")
    void testAlignedAdjacently(int x1, int x2, int y1, int y2, boolean expected, String name) {
        Cuboid current = new Cuboid(new Coordinate(x1,y1), new Coordinate(x2, y2));
        assertThat(underTest.alignedAdjacently(current)).isEqualTo(expected);
    }

    @Test
    void testSliceThenMerge() {
        Cuboid initial = new Cuboid(new Coordinate(1,2,3), new Coordinate(6,7,8));
        List<Cuboid> sliced = initial.slice(1, 4);
        assertThat(sliced).hasSize(2);

        assertThat(sliced.stream().map(Cuboid::area).reduce(BigInteger.ZERO, BigInteger::add))
                .isEqualTo(initial.area());

        Cuboid merged = sliced.get(0).merge(sliced.get(1));
        assertThat(merged).isEqualTo(initial);
    }

    @Test
    void testSegmentThenMerge() {
        Cuboid outer = new Cuboid(new Coordinate(2,2,2), new Coordinate(5,5,5));
        Cuboid inner = new Cuboid(new Coordinate(3,3,3), new Coordinate(4,4,4));
        List<Cuboid> sliced = outer.segment(inner);

        List<Integer> areas = sliced.stream().map(Cuboid::area).map(BigInteger::intValue).collect(toList());
        assertThat(areas).containsExactlyInAnyOrder(8, 4,4,4,4,4,4, 2,2,2,2, 2,2,2,2, 2,2,2,2, 1,1,1,1, 1,1,1,1);

        List<Cuboid> merged = mergeAll(sliced);
        assertThat(merged).isEqualTo(singletonList(outer));
    }

}