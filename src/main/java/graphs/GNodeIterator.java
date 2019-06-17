
package graphs;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author saud
 */
public class GNodeIterator<T> {
    public static int SEARCH_BREADTH_FIRST = 0;
    public static int SEARCH_DEPTH_FIRST = 1;
    
    private final ArrayList<GNode<T>> nodeList = new ArrayList<>();
    private final LinkedList<GNode<T>> q = new LinkedList<>();
    private final LinkedList<GNode<T>> prev = new LinkedList<>();
    private final int fillType;

    public GNodeIterator(GNode<T> seed, int searchType) {
        seed.setWasScanned(true);
        q.add(seed);
        prev.add(null);
        fillType = searchType;
    }

    public boolean hasNext() {
        return !q.isEmpty();
    }

    public GNode<T> next() {
        GNode<T> curr;
        if(fillType == SEARCH_DEPTH_FIRST) {
            curr = q.removeLast();
        } else {
            curr = q.removeFirst();
        }
        nodeList.add(curr);

        for(GNode<T> node : curr.getNeighbours()) {
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
            for(GNode<T> node : nodeList) {
                node.setWasScanned(false);
            }
        }
    }

    public boolean canAddNextNode(GNode<T> node, GNode<T> parent) {
        return true;
    }
    
}
