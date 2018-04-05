/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphs;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author saud
 */
public class GNodeIterator {
    public static int FLOOD_FILL = 0;
    public static int DEPTH_FIRST_SEARCH = 1;
    
    private final ArrayList<GNode> nodeList = new ArrayList<>();
    private final LinkedList<GNode> q = new LinkedList<>();
    private final LinkedList<GNode> prev = new LinkedList<>();
    private final int fillType;

    public GNodeIterator(GNode seed, int fillType) {
        seed.setWasScanned(true);
        q.add(seed);
        prev.add(null);
        this.fillType = fillType;
    }

    public boolean hasNext() {
        return !q.isEmpty();
    }

    public GNode next() {
        GNode curr;
        if(fillType == DEPTH_FIRST_SEARCH) {
            curr = q.removeLast();
        } else {
            curr = q.removeFirst();
        }
        nodeList.add(curr);

        for(GNode node : curr.getNeighbours()) {
            if(!node.getWasScanned() && canAddNextNode(node, curr)) {
                node.setWasScanned(true);
                q.add(node);
                prev.add(curr);
            }
        }

        resetScans();
        return curr;
    }

    private void resetScans() {
        if(!hasNext()) {
            for(GNode node : nodeList) {
                node.setWasScanned(false);
            }
        }
    }

    public boolean canAddNextNode(GNode node, GNode parent) {
        return true;
    }
    
}
