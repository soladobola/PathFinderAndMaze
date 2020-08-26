public class PlaygroundMode {
    private int width;
    private int height;
    private int n;
    private double percentageBlock;

    public Node[][] nodes;

    public Node Nstart;
    public Node Ngoal;
    public boolean goal = false;
    public boolean start = false;
    public BoardCanvas platno;

    public PlaygroundMode(int width, int height, int n, double percentageBlock){
        this.width = width;
        this.height = height;
        this.n = n;
        this.percentageBlock = percentageBlock;

        // Initialization of nodes
        nodes = new Node[n][n];
        for(int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                nodes[i][j] = new Node(i, j);
            }
        }

        randomSuffle(percentageBlock);

        this.platno = new BoardCanvas(600, 600);
        /*
        nodes[0][0].start = true;
        nodes[0][0].block = false;
        Nstart = nodes[0][0];
        start = true;
        nodes[(n - 1)/2][n - 1].goal = true;
        nodes[(n - 1)/2][n - 1].block = false;
        Ngoal = nodes[(n - 1)/2][n - 1];
        goal = true; */

    }


    // bad name...
    private void randomSuffle(double percentage){
        for(int i = 0; i < n; i++)
        {
            for(int j = 0; j < n; j++)
            {
                if(Math.random() < percentage)
                {
                    nodes[i][j].block = true;
                }
            }
        }

    }

}
