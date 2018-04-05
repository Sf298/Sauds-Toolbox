/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphs;

import java.util.ArrayList;

/**
 *
 * @author saud
 */
public class GNode {
    
    private boolean wasScanned = false;
    private final ArrayList<GNode> neighbours = new ArrayList<>();
    private final ArrayList<Double> weights = new ArrayList<>();
    private Object data;
    
    public GNode(Object data) {
        this.data = data;
    }
    
    public static GNode[][] newGNodeGrid(int width, int height, Object data) {
        GNode[][] out = new GNode[width][height];
        for(int i=0; i<out.length; i++) {
            for(int j=0; j<out[i].length; j++) {
                out[i][j] = new GNode(data);
            }
        }
        for(int i=0; i<out.length; i++) {
            for(int j=0; j<out[i].length; j++) {
                if(i>0)
                    out[i][j].addNeigbour(out[i-1][j]);
                if(j>0)
                    out[i][j].addNeigbour(out[i][j-1]);
                if(i<width-1)
                    out[i][j].addNeigbour(out[i+1][j]);
                if(j<height-1)
                    out[i][j].addNeigbour(out[i][j+1]);
            }
        }
        return out;
    }
    
    
    public void setData(Object data) {
        this.data = data;
    }
    
    public Object getData() {
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
