import java.util.LinkedList;

public class Utility {

    public static void resetCells(Node[][] nodes){
        int n = nodes.length;
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                nodes[i][j].block = false;
                nodes[i][j].neib = new LinkedList<Node>();
                nodes[i][j].start = false;
                nodes[i][j].goal = false;
                nodes[i][j].top = false;
                nodes[i][j].right = false;
                nodes[i][j].bottom = false;
                nodes[i][j].left = false;
                nodes[i][j].mazePart = false;
                nodes[i][j].route = false;
                nodes[i][j].open = false;
                nodes[i][j].visited = false;
                nodes[i][j].highlight = false;

            }
        }

    }


    public static void allBlocks(Node[][] nodes){
        int n = nodes.length;
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                nodes[i][j].block = true;
            }
        }
    }

    public static void allWalls(Node[][] nodes){
        int n = nodes.length;
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                nodes[i][j].top = true;
                nodes[i][j].right = true;
                nodes[i][j].bottom = true;
                nodes[i][j].left = true;
            }
        }
    }

    public static void clearAlgs(Node[][] nodes){
        int n = nodes.length;
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                nodes[i][j].start = false;
                nodes[i][j].goal = false;
                nodes[i][j].route = false;
                nodes[i][j].open = false;
                nodes[i][j].visited = false;
            }
        }
    }

    public static void clearNeibs(Node[][] nodes){
        int n = nodes.length;
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                nodes[i][j].neib = new LinkedList<Node>();
            }
        }
    }
}
