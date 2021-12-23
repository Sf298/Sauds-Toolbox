package sauds.toolbox.data.structures;

import org.junit.jupiter.params.ParameterizedTest;
import org.tools4j.spockito.jupiter.TableSource;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

class VolumeSetTest {


    private static final Cuboid initialCuboid = new Cuboid(new Coordinate(1,2), new Coordinate(4,5));

    @TableSource({
            "| x1 | x2 | y1 | y2 | area | name                                           |",
            "|----|----|----|----|------|------------------------------------------------|",
            "|  1 |  4 |  2 |  5 |   16 | identical                                      |",
            "|  0 |  5 |  1 |  6 |   36 | this contains initialCuboid                    |",
            "|  2 |  3 |  3 |  4 |   16 | initialCuboid contains this                    |",

            "|  1 |  4 |  3 |  6 |   20 | equal x, shifted overlapping +y                |",
            "|  1 |  4 |  1 |  4 |   20 | equal x, shifted overlapping -y                |",
            "|  2 |  5 |  2 |  5 |   20 | shifted overlapping +x, equal y                |",
            "|  0 |  3 |  2 |  5 |   20 | shifted overlapping -x, equal y                |",

            "|  2 |  3 |  3 |  6 |   18 | inner x, shifted overlapping +y                |",
            "|  2 |  3 |  1 |  4 |   18 | inner x, shifted overlapping -y                |",
            "|  2 |  5 |  3 |  4 |   18 | shifted overlapping +x, inner y                |",
            "|  0 |  3 |  3 |  4 |   18 | shifted overlapping -x, inner y                |",

            "|  0 |  5 |  3 |  6 |   28 | outer x, shifted overlapping +y                |",
            "|  0 |  5 |  1 |  4 |   28 | outer x, shifted overlapping -y                |",
            "|  2 |  5 |  1 |  6 |   28 | shifted overlapping +x, outer y                |",
            "|  0 |  3 |  1 |  6 |   28 | shifted overlapping -x, outer y                |",

            "|  1 |  4 |  6 |  9 |   32 | equal x, shifted adjacent +y                   |",
            "|  1 |  4 |  0 |  1 |   24 | equal x, shifted adjacent -y                   |",
            "|  5 |  8 |  2 |  5 |   32 | shifted adjacent +x, equal y                   |",
            "| -3 |  0 |  2 |  5 |   32 | shifted adjacent -x, equal y                   |",

            "|  1 |  4 |  7 |  9 |   28 | equal x, shifted unconnected +y                |",
            "|  1 |  4 | -1 |  0 |   24 | equal x, shifted unconnected -y                |",
            "|  6 |  8 |  2 |  5 |   28 | shifted unconnected +x, equal y                |",
            "| -3 | -1 |  2 |  5 |   28 | shifted unconnected -x, equal y                |",

            "|  2 |  5 |  3 |  6 |   23 | shifted overlapping +x, shifted overlapping +y |",
            "|  2 |  5 |  1 |  4 |   23 | shifted overlapping +x, shifted overlapping -y |",
            "|  0 |  3 |  3 |  6 |   23 | shifted overlapping -x, shifted overlapping +y |",
            "|  0 |  3 |  1 |  4 |   23 | shifted overlapping -x, shifted overlapping -y |",

            "|  2 |  3 |  1 |  6 |   20 | centers overlapping (plus shape)               |"
    })
    @ParameterizedTest(name = "{5}")
    void testAdd(int x1, int x2, int y1, int y2, int expectedArea, String name) {
        VolumeSet underTest = new VolumeSet();
        underTest.add(initialCuboid);

        Cuboid testCuboid = new Cuboid(new Coordinate(x1,y1), new Coordinate(x2,y2));
        underTest.add(testCuboid);

        assertThat(countLayeredPoints(underTest)).isLessThanOrEqualTo(1);

        long actualArea = underTest.area();
        assertThat(actualArea).isEqualTo(expectedArea);
    }


    @TableSource({
            "| x1 | x2 | y1 | y2 | area | name                                           |",
            "|----|----|----|----|------|------------------------------------------------|",
            "|  1 |  4 |  2 |  5 |    0 | identical                                      |",
            "|  0 |  5 |  1 |  6 |    0 | this contains initialCuboid                    |",
            "|  2 |  3 |  3 |  4 |   12 | initialCuboid contains this                    |",

            "|  1 |  4 |  3 |  6 |    4 | equal x, shifted overlapping +y                |",
            "|  1 |  4 |  1 |  4 |    4 | equal x, shifted overlapping -y                |",
            "|  2 |  5 |  2 |  5 |    4 | shifted overlapping +x, equal y                |",
            "|  0 |  3 |  2 |  5 |    4 | shifted overlapping -x, equal y                |",

            "|  2 |  3 |  3 |  6 |   10 | inner x, shifted overlapping +y                |",
            "|  2 |  3 |  1 |  4 |   10 | inner x, shifted overlapping -y                |",
            "|  2 |  5 |  3 |  4 |   10 | shifted overlapping +x, inner y                |",
            "|  0 |  3 |  3 |  4 |   10 | shifted overlapping -x, inner y                |",

            "|  0 |  5 |  3 |  6 |    4 | outer x, shifted overlapping +y                |",
            "|  0 |  5 |  1 |  4 |    4 | outer x, shifted overlapping -y                |",
            "|  2 |  5 |  1 |  6 |    4 | shifted overlapping +x, outer y                |",
            "|  0 |  3 |  1 |  6 |    4 | shifted overlapping -x, outer y                |",

            "|  1 |  4 |  6 |  9 |   16 | equal x, shifted adjacent +y                   |",
            "|  1 |  4 |  0 |  1 |   16 | equal x, shifted adjacent -y                   |",
            "|  5 |  8 |  2 |  5 |   16 | shifted adjacent +x, equal y                   |",
            "| -3 |  0 |  2 |  5 |   16 | shifted adjacent -x, equal y                   |",

            "|  1 |  4 |  7 |  9 |   16 | equal x, shifted unconnected +y                |",
            "|  1 |  4 | -1 |  0 |   16 | equal x, shifted unconnected -y                |",
            "|  6 |  8 |  2 |  5 |   16 | shifted unconnected +x, equal y                |",
            "| -3 | -1 |  2 |  5 |   16 | shifted unconnected -x, equal y                |",

            "|  2 |  5 |  3 |  6 |    7 | shifted overlapping +x, shifted overlapping +y |",
            "|  2 |  5 |  1 |  4 |    7 | shifted overlapping +x, shifted overlapping -y |",
            "|  0 |  3 |  3 |  6 |    7 | shifted overlapping -x, shifted overlapping +y |",
            "|  0 |  3 |  1 |  4 |    7 | shifted overlapping -x, shifted overlapping -y |",

            "|  2 |  3 |  1 |  6 |    8 | centers overlapping (plus shape)               |"
    })
    @ParameterizedTest(name = "{5}")
    void testSubtract(int x1, int x2, int y1, int y2, int expectedArea, String name) {
        VolumeSet underTest = new VolumeSet();
        underTest.add(initialCuboid);

        Cuboid testCuboid = new Cuboid(new Coordinate(x1,y1), new Coordinate(x2,y2));
        underTest.subtract(testCuboid);

        assertThat(countLayeredPoints(underTest)).isLessThanOrEqualTo(1);

        long actualArea = underTest.area();
        assertThat(actualArea).isEqualTo(expectedArea);
    }


    private static long countLayeredPoints(VolumeSet volumeSet) {
        int minX = volumeSet.cuboids.stream().mapToInt(c -> c.min(0)).min().orElse(0);
        int maxX = volumeSet.cuboids.stream().mapToInt(c -> c.max(0)).max().orElse(0);
        int minY = volumeSet.cuboids.stream().mapToInt(c -> c.min(1)).min().orElse(0);
        int maxY = volumeSet.cuboids.stream().mapToInt(c -> c.max(1)).max().orElse(0);

        long totalCount = 0;
        for (int j = minY; j <= maxY; j++) {
            for (int i = minX; i <= maxX; i++) {
                int I = i, J = j;
                long count = volumeSet.cuboids.stream().filter(r -> r.contains(new Coordinate(I,J))).count();
                if (count > 1) {
                    totalCount++;
                }
            }
        }
        return totalCount;
    }

}