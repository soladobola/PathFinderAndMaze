/*
    For MODE 0
 */

import java.util.ArrayList;
import java.util.Random;

public class RandomizedPrimesMaze implements Runnable {

    Node[][] nodes;
    int n;
    MainFrame mf;

    ArrayList<Node> openSet;

    public RandomizedPrimesMaze(Node[][] nodes, int n, MainFrame mf){
        this.nodes = nodes;
        this.n = n;
        this.openSet = new ArrayList<>();
        Utility.resetCells(nodes);
        Utility.allBlocks(nodes);
        this.mf = mf;

    }


    private ArrayList<Node> getVisitedNeigbs(Node cell){
        ArrayList<Node> result = new ArrayList<>();
        int i = cell.i;
        int j = cell.j;
        //add left
        if( i - 2 >= 0 && !nodes[i - 2][j].block ){
            result.add(nodes[i - 2][j]);
        }

        //add right
        if( i + 2 < n && !nodes[i + 2][j].block ){
            result.add(nodes[i + 2][j]);
        }

        //add top
        if( j - 2 >= 0 && !nodes[i][j - 2].block ){
            result.add(nodes[i][j - 2]);
        }

        //add bottom
        if( j + 2 < n && !nodes[i][j + 2].block ){
            result.add(nodes[i][j + 2]);
        }


        return result;
    }
    private ArrayList<Node> getUnvisitedNeibs(Node cell){
        ArrayList<Node> result = new ArrayList<>();
        int i = cell.i;
        int j = cell.j;
        //add left
        if( i - 2 >= 0 && nodes[i - 2][j].block){
            result.add(nodes[i - 2][j]);
        }

        //add right
        if( i + 2 < n && nodes[i + 2][j].block){
            result.add(nodes[i + 2][j]);
        }

        //add top
        if( j - 2 >= 0 && nodes[i][j - 2].block){
            result.add(nodes[i][j - 2]);
        }

        //add bottom
        if( j + 2 < n && nodes[i][j + 2].block){
            result.add(nodes[i][j + 2]);
        }


        return result;

    }

    public void run(){
        //ok
    // ------------------------------------------------------
        int randX = new Random().nextInt(n);
        int randY = new Random().nextInt(n);
        Node startCell = nodes[randX][randY];
        startCell.block = false;
        for(Node n : getUnvisitedNeibs(startCell))
            openSet.add(n);
    // ------------------------------------------------------

        while(openSet.size() > 0){
            int index = new Random().nextInt(openSet.size());
            Node current = openSet.get(index);
            current.block = false;

            // Get available neighbors from bottom, left, right, top and visited
            // Randomly connect to one
            ArrayList<Node> neibs = getVisitedNeigbs(current);

            if(neibs.size() != 0){
                //mark one as part of maze
                int ind = new Random().nextInt(neibs.size());
               Node winner = neibs.get(ind);
               // winner of frontier
               int midX = (winner.i + current.i)/2;
               int midY = (winner.j + current.j)/2;
               nodes[midX][midY].block = false;

            }

            // Add all unvisited neighbors to the set
            for(Node n : getUnvisitedNeibs(current)){
                if(!openSet.contains(n))
                openSet.add(n);
            }

           // delete frontier
            openSet.remove(current);

            try{
                Thread.sleep(mf.speed);

            } catch(Exception ex){

            }


        }
    }

}
