package sauds.toolbox.graphs2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static java.util.Objects.nonNull;

public class GridGraph<T> {

    public List<List<GridNode>> grid;

    public GridGraph(T[][] grid, int adjacency) {
        if (adjacency != 4 && adjacency != 8) {
            throw new IllegalArgumentException("Invalid adjacency '" + adjacency + "'");
        }

        this.grid = new ArrayList<>();

        for (int i = 0; i < grid.length; i++) {
            List<GridNode> gi = new ArrayList<>();
            this.grid.add(gi);
            for (int j = 0; j < grid[i].length; j++) {
                gi.add(new GridNode(grid[i][j], new int[] {i,j}));
            }
        }

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (nonNull(get(i-1, j))) get(i, j).adjacent.add(get(i-1, j));
                if (nonNull(get(i+1, j))) get(i, j).adjacent.add(get(i+1, j));
                if (nonNull(get(i, j-1))) get(i, j).adjacent.add(get(i, j-1));
                if (nonNull(get(i, j+1))) get(i, j).adjacent.add(get(i, j+1));

                if (adjacency == 8) {
                    if (nonNull(get(i-1, j-1))) get(i, j).adjacent.add(get(i-1, j-1));
                    if (nonNull(get(i+1, j-1))) get(i, j).adjacent.add(get(i+1, j-1));
                    if (nonNull(get(i-1, j+1))) get(i, j).adjacent.add(get(i-1, j+1));
                    if (nonNull(get(i+1, j+1))) get(i, j).adjacent.add(get(i+1, j+1));
                }
            }
        }
    }

    public GridNode get(int i, int j) {
        if (i < 0 || j < 0 || i >= grid.size() || j >= grid.get(i).size()) {
            return null;
        }
        List<GridNode> gridNode = grid.get(i);
        return gridNode.get(j);
    }

    public class GridNode extends Node<T> {

        int[] coord;

        public GridNode(T value, int[] coord) {
            this(value, coord, null);
        }

        public GridNode(T value, int[] coord, Collection<Node<T>> adjacent) {
            super(value, adjacent);
            this.coord = coord;
        }

        public GridNode getAdjacent(int di, int dj) {
            return get(coord[0]+di, coord[1]+dj);
        }

        @Override
        public String toString() {
            return "(" + Arrays.toString(coord) + ":" + value + ")";
        }

    }

}
