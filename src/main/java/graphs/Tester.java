/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphs;

/**
 *
 * @author saud
 */
public class Tester {
    
    public static void main(String[] args) {
        GNode[][] grid = GNode.newGNodeGrid(5, 4, 1);
        for(int i=0; i<grid.length; i++) {
            for(int j=0; j<grid[i].length; j++) {
                if(i%2==1) {
                    grid[i][j].setData((i+1)*grid[i].length - j -1);
                } else {
                    grid[i][j].setData(i*grid[i].length + j);
                }
            }
        }
        
        GNodeIterator i = new GNodeIterator(grid[0][0], GNodeIterator.DEPTH_FIRST_SEARCH)/* {
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
