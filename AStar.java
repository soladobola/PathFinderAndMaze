import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;


public class AStar implements Runnable{

    private Node[][] nodes;
    private Node start;
    private Node goal;
    private int n;
    private int playmode;

    // za backtrack
    Map<Node, Node> map;

    private ArrayList<Node> openSet;
    private ArrayList<Node> closedSet;

    MainFrame mf;



    public AStar(Node[][] nodes, Node start, Node goal, MainFrame mf)
    {
        this.nodes = nodes;
        this.start = start;
        this.goal = goal;
        n = nodes.length;
        openSet = new ArrayList<>();
        closedSet = new ArrayList<>();
        map = new HashMap<Node, Node>();
        this.mf = mf;
        this.playmode = mf.PLAYMODE;



    }

    private void addNeibMode1(int i, int j){
        // add left
        if(i - 1 >= 0 && !nodes[i][j].left && !nodes[i - 1][j].right){
            nodes[i][j].neib.add(nodes[i - 1][j]);
        }

        // add right
        if( i + 1 < n  && !nodes[i][j].right && !nodes[i + 1][j].left ){
            nodes[i][j].neib.add(nodes[i + 1][j]);
        }

        // add top
        if( j - 1 >= 0  && !nodes[i][j].top && !nodes[i][j - 1].bottom ){
            nodes[i][j].neib.add(nodes[i][j - 1]);
        }

        // add bottom
        if( j + 1 < n  && !nodes[i][j].bottom && !nodes[i][j + 1].top ){
            nodes[i][j].neib.add(nodes[i][j + 1]);
        }

    }

    private void addNeibMode0(int i, int j)
    {
        // add left
        if( i - 1 >= 0 && !nodes[i - 1][j].block){
            nodes[i][j].neib.add(nodes[i - 1][j]);
        }

        // add right
        if( i + 1 < n && !nodes[i + 1][j].block){
            nodes[i][j].neib.add(nodes[i + 1][j]);
        }

        // add top
        if( j - 1 >= 0 && !nodes[i][j - 1].block){
            nodes[i][j].neib.add(nodes[i][j - 1]);
        }

        // add bottom
        if( j + 1 < n && !nodes[i][j + 1].block){
            nodes[i][j].neib.add(nodes[i][j + 1]);
        }

        // adding..........................................................................
    /*
        // add up right
        if(j - 1 >= 0 && i + 1 < n && !nodes[i + 1][j - 1].block ){

            nodes[i][j].neib.add(nodes[i + 1][j - 1]);
        }

        // add up left
        if(j - 1 >= 0 && i - 1 >= 0 && !nodes[i - 1][j - 1].block){

            nodes[i][j].neib.add(nodes[i - 1][j - 1]);
        }

        // add down right
        if(j + 1 < n && i + 1 < n && !nodes[i + 1][j + 1].block ){

            nodes[i][j].neib.add(nodes[i + 1][j + 1]);
        }

        // add down left
        if(j + 1 < n && i - 1 >= 0 && !nodes[i - 1][j + 1].block ){

            nodes[i][j].neib.add(nodes[i - 1][j + 1]);
        }

     */

        // adding end.........................................................................
    }

    public void reconstructPath(Node current)
    {
        while(map.containsKey(current))
        {
            (map.get(current)).route = true;
            current = (map.get(current));
        }

    }


    //implement A* algorithm
    @Override
    public void run() {
        //Add neib
        //not optimally, but oke, we gonna change it later (yea... right)
        for(int i = 0; i < n; i++)
        {
            for(int j = 0; j < n; j++)
            {
                if(playmode == 0) {
                    if (!nodes[i][j].block) {
                        addNeibMode0(i, j);
                    }
                } else if(playmode == 1){
                    nodes[i][j].neib.clear();
                    addNeibMode1(i, j);
                }
            }
        }

        openSet.add(start);
        boolean solution = false;

        while(openSet.size() > 0)
        {
            //Find smallest among Nodes in open set!
            Node winner = openSet.get(0);
            for(int i = 1; i < openSet.size(); i++)
            {
                if(openSet.get(i).costF < winner.costF){
                    winner = openSet.get(i);
                }
            }

            //check if is in end

            if(winner == goal)
            {
                solution = true;
                reconstructPath(winner);
                break;
            }

            //!!!! for animating
            winner.open = true;

            openSet.remove(winner);
            closedSet.add(winner);

            for(Object n : winner.neib){

                int tempG;
                if(!closedSet.contains(n)) {
                    tempG = winner.costG + 1;


                    if (openSet.contains(n)) {
                        if (tempG < ((Node) n).costG){
                            ((Node)n).costG = tempG;
                            map.put((Node)n, winner);
                        }
                    }
                    else
                    {
                        ((Node)n).costG = tempG;
                        openSet.add(((Node)n));
                        map.put((Node)n, winner);

                    }

                    //evaluate heuristics
                    ((Node)n).costH = Math.abs(((Node)n).i - goal.i) + Math.abs(((Node)n).j - goal.j);
                    ((Node)n).costF = ((Node)n).costH + ((Node)n).costG;

                }
            }


            try{
                Thread.sleep(mf.speed);

            } catch(Exception e){}

        }
        if(solution){
            System.out.println("Done :')");
        } else
        {
            System.out.println("Solution doesn't exist!");
        }



    }


}
