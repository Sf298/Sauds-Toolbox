
package sauds.toolbox.graphs;

import java.util.ArrayList;

/**
 *
 * @author saud
 */
public class Tester {
    
    public static void main(String[] args) {
        ArrayList<ArrayList<GNode<Integer>>> grid = GNode.newGNodeGrid(5, 4, (Integer)1);
        for(int i=0; i<grid.size(); i++) {
            for(int j=0; j<grid.get(i).size(); j++) {
                if(i%2==1) {
                    grid.get(i).get(j).setData((i+1)*grid.get(i).size() - j -1);
                } else {
                    grid.get(i).get(j).setData(i*grid.get(i).size() + j);
                }
            }
        }
        
        GNodeIterator i = new GNodeIterator(grid.get(0).get(0), GNodeIterator.SEARCH_DEPTH_FIRST)/* {
            @Override
            public boolean canAddNextNode(GNode node, GNode parent) {
                return ((int)node.getData()) == ((int)parent.getData()+1);
            }
        }*/;
        while(i.hasNext()) {
            Integer d = (Integer) i.next().getData();
            System.out.println(d);
        }
    }
    
}
