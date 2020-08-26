// Well this failed :')
// i'll come back later, for now let go on


import com.sun.tools.javac.Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Stack;

public class MazeGenerator implements Runnable {

    // Maze generator algorithm
    // Recursive backtracker


    private Node[][] nodes;
    private int n;
    private Stack<Node> stack;
    private MainFrame mf;

    public MazeGenerator(Node[][] nodes, int n, MainFrame mf){

        this.nodes = nodes;
        this.n = n;
        Utility.resetCells(nodes);
        Utility.allWalls(nodes);
        this.stack = new Stack<>();
        //nodes[5][5].start = true;
        //nodes[15][15].goal = true;
        this.mf = mf;
        mf.Nstart = null;
        mf.Ngoal = null;
        mf.start = false;
        mf.goal = false;

    }


    private void addNeib(int i, int j)
    {
        //add left
        if( i - 1 >= 0 && !nodes[i - 1][j].block){
            nodes[i][j].neib.add(nodes[i - 1][j]);
        }

        //add right
        if( i + 1 < n && !nodes[i + 1][j].block){
            nodes[i][j].neib.add(nodes[i + 1][j]);
        }

        //add top
        if( j - 1 >= 0 && !nodes[i][j - 1].block){
            nodes[i][j].neib.add(nodes[i][j - 1]);
        }

        //add bottom
        if( j + 1 < n && !nodes[i][j + 1].block){
            nodes[i][j].neib.add(nodes[i][j + 1]);
        }
    }


    public void run(){

        // Connect neibs
        for(int i = 0; i < n; i++)
        {
            for(int j = 0; j < n; j++)
            {
                if(!nodes[i][j].block){
                    addNeib(i, j);
                }
            }
        }



        // Given a current cell as a parameter
        // Start with top left corner
        Node initialCell = nodes[0][0];

        // Mark the initial cell as visited
        initialCell.visited = true;

        // push it to the stack
        stack.push(initialCell);

        // While the stack is not empty
        while(!stack.isEmpty()){
            // Pop a cell from the stack and make it a current cell
            Node current = stack.pop();
            current.highlight = true;

            // Gather unvisited neibs
            ArrayList<Node> unvisitedNeib = new ArrayList<>();
            for(Object n1 : current.neib){
                if(!(((Node)n1).visited)){
                    unvisitedNeib.add((Node)n1);
                }
            }

            // If the current cell has any neighbours which have not been visited
            if(!unvisitedNeib.isEmpty()){
                // Push the current cell to the stack
                stack.push(current);
               // current.highlight = false;

                // Choose one of the unvisited neighbours
                int index = new Random().nextInt(unvisitedNeib.size());
                Node chosen = unvisitedNeib.get(index);
                chosen.highlight = true;

                // Remove the wall between the current cell and the chosen cell
                if(current.i < chosen.i){
                    // left and right neighbours
                    current.right = false;
                    chosen.left = false;
                }

                else if(current.i > chosen.i){
                    // right and left neighbours
                    current.left = false;
                    chosen.right = false;
                }
               else  if(current.j < chosen.j){
                    // top and bottom neighbours
                    current.bottom = false;
                    chosen.top = false;
                }
               else  if(current.j > chosen.j) {
                    // bottom and top neighbours
                    current.top = false;
                    chosen.bottom = false;
                }

               // Mark the chosen cell as visited and push it to the stack
               chosen.visited = true;
             //  chosen.highlight = false;
               current.highlight = false;
               stack.push(chosen);


            } else {
                //current.highlight = false;
            }

            try{
                Thread.sleep(mf.speed);

            } catch(Exception e){}
        }

        // probably working, but render not
    }

}
