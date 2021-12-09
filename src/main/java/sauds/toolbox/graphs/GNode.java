
package sauds.toolbox.graphs;

import java.util.ArrayList;

/**
 *
 * @author saud
 */
public class GNode<T> {
    
    private boolean wasScanned = false;
    private final ArrayList<GNode> neighbours = new ArrayList<>();
    private final ArrayList<Double> weights = new ArrayList<>();
    private T data;
    
    public GNode(T data) {
        this.data = data;
    }
    
    public static <T> ArrayList<ArrayList<GNode<T>>> newGNodeGrid(int width, int height, T data) {
        ArrayList<ArrayList<GNode<T>>> out = new ArrayList<>();
        for(int i=0; i<width; i++) {
	    ArrayList<GNode<T>> col = new ArrayList<>();
	    out.add(col);
            for(int j=0; j<height; j++) {
		col.add(new GNode(data));
            }
        }
        for(int i=0; i<out.size(); i++) {
            for(int j=0; j<out.get(i).size(); j++) {
		GNode<T> temp = out.get(i).get(j);
                if(i>0)
                    temp.addNeigbour(out.get(i-1).get(j));
                if(j>0)
                    temp.addNeigbour(out.get(i).get(j-1));
                if(i<width-1)
                    temp.addNeigbour(out.get(i+1).get(j));
                if(j<height-1)
                    temp.addNeigbour(out.get(i).get(j+1));
            }
        }
        return out;
    }
    
    
    public void setData(T data) {
        this.data = data;
    }
    
    public T getData() {
        return data;
    }

    public boolean getWasScanned() {
        return wasScanned;
    }

    public void setWasScanned(boolean wasScanned) {
        this.wasScanned = wasScanned;
    }

    public ArrayList<GNode> getNeighbours() {
        return neighbours;
    }

    public ArrayList<Double> getWeights() {
        return weights;
    }
    
    
    public void addNeigbour(GNode n) {
        neighbours.add(n);
    }
    
    public void addNeigbour2Way(GNode n) {
        neighbours.add(n);
        n.addNeigbour(this);
    }
    
}
