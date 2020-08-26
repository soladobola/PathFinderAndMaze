import java.util.LinkedList;


public class Node {

    public int i;
    public int j;

    //needed for animating
    public boolean block = false;
    public boolean open = false;
    public boolean goal = false;
    public boolean start = false;
    public LinkedList neib;

    public boolean highlight = false;

    //needed for evaluating
    public int costG;
    public int costH;
    public int costF;
    public boolean route = false;


    // MazeMode, check if edge is a block?
    public boolean top = false;
    public boolean right = false;
    public boolean bottom = false;
    public boolean left = false;

    // Its actually passage
    // For Randomized maze algorithm
    public boolean mazePart = false;


    //universal
    public boolean visited = false;





    public Node(){
    }

    public Node(int x, int y){
        this.i = x;
        this.j = y;
        neib = new LinkedList();

    }



}
