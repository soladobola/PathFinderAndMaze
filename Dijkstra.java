import com.sun.tools.javac.Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Dijkstra implements Runnable {


    private Node[][] nodes;
    private Node start;
    private Node goal;
    private int n;
    MainFrame mf;

    // vertex set
    private ArrayList<Node> vSet;
    private HashMap<Node, Double> dist;
    private HashMap<Node, Node> prev;


    public Dijkstra(Node[][] nodes, Node start, Node goal, MainFrame mf){
        this.nodes = nodes;
        this.start = start;
        this.goal = goal;
        // square dim, maybe change that later
        this.n = nodes.length;
        this.vSet = new ArrayList<>();
        this.dist = new HashMap<>();
        this.prev = new HashMap<>();
        this.mf = mf;

    }




    public Node minElem(HashMap<Node, Double> data){
        if(data.size() == 0) return null;
        Node res = null;
        Double distant = Double.POSITIVE_INFINITY;
        for(Node n : vSet) {
            if(data.get(n) < distant){
                distant = data.get(n);
                res = n;
            }
        }

        return res;

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


    //  S ← empty sequence
    //  u ← target
    //  if prev[u] is defined or u = source:          // Do something only if the vertex is reachable
    //      while u is defined:                       // Construct the shortest path with a stack S
    //          insert u at the beginning of S        // Push the vertex onto the stack
    //          u ← prev[u]                           // Traverse from target to source




    public void reconstructPath(Node current)
    {
        current.route = true;
        Node trav = prev.get(current);

        while(trav != null) {
            trav.route = true;
            trav = prev.get(trav);
        }

    }


    public void helper(ArrayList<Node> vSet, int i, int j){
        vSet.add(nodes[i][j]);
        for(Object n : nodes[i][j].neib){
         Node nod = (Node)n;
         if(!vSet.contains(nod)) {
             vSet.add(nod);
             helper(vSet, nod.i, nod.j);
         }
        }
    }

    public void viewNeibSmart(ArrayList<Node> vSet){
        helper(vSet, start.i, start.j);
    }


    // body of algorithm
    public void run(){

        //Add neib
        //not optimally, but oke, we gonna change it later (yea... right)
        for(int i = 0; i < n; i++)
        {
            for(int j = 0; j < n; j++)
            {
                if(mf.PLAYMODE == 0) {
                    if (!nodes[i][j].block) {
                        addNeibMode0(i, j);
                    }
                } else if(mf.PLAYMODE == 1) {
                         nodes[i][j].neib.clear();
                        addNeibMode1(i, j);
                }
            }
        }



        // pseudo code
        //   for each vertex v in Graph:
        //          dist[v] ← INFINITY
        //          prev[v] ← UNDEFINED
        //          add v to Q; -> Q = vSet

        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {

                //maybe not blocked!!!
                if(!nodes[i][j].block) {
                    dist.put(nodes[i][j], Double.MAX_VALUE - 1);
                    prev.put(nodes[i][j], null);
                    // this
                    if(mf.PLAYMODE == 1)
                        vSet.add(nodes[i][j]);
                }
            }
        }
        if(mf.PLAYMODE == 0)
            viewNeibSmart(vSet);

        // dist[source] ← 0
        dist.put(start, (double)0);


        // while Q is not empty:
        //         u ← vertex in Q with min dist[u]

        while(vSet.size() != 0) {
            // not good !!
            Node u = minElem(dist);

            u.open = true;

            // remove u from Q
            vSet.remove(u);

            // only v that are still in Q
            //  for each neighbor v of u:
            //                alt ← dist[u] + length(u, v)
            //                if alt < dist[v]:
            //                    dist[v] ← alt
            //                    prev[v] ← u

            for(Object v : u.neib){
                if(vSet.contains(v)){
                    Double alt = dist.get(u) + 1;
                    if(alt < dist.get(v)) {

                        dist.put((Node)v, alt);
                        prev.put((Node)v, u);

                    }

                }
            }


            try{
                Thread.sleep(mf.speed);

            } catch(Exception e){}

        }

        // traverse path
        reconstructPath(goal);

    }
}

