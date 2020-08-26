public class MazeMode {

    public Node[][] nodes;
    public BoardCanvas platno;
    private int n;
    private int width;
    private int height;

    public MazeMode(int width, int height, int n){
        this.width = width;
        this.height = height;
        this.n = n;

        this.platno = new BoardCanvas(this.width, this.height);

        nodes = new Node[n][n];
        for(int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                nodes[i][j] = new Node(i, j);
                nodes[i][j].i = i;
                nodes[i][j].j = j;
             }
        }

    }

}
