import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;


public class MainFrame extends JFrame{
    public static int width = 900;
    public static int height = 670;
    public static int n = 20;
    public double percentageBlock = 0.2;

    public int canvasWidth = 600;
    public int canvasHeight = 600;


    public boolean backtrack = false;
    public int speed;

    // 0 -> playground, 1 -> maze
    public int PLAYMODE = 0;

    public boolean dotted = false;

    public boolean showBoundery = true;

    public PlaygroundMode pgMode = new PlaygroundMode(canvasWidth, canvasHeight, n, percentageBlock);
    public MazeMode mMode = new MazeMode(canvasWidth, canvasHeight, n);

   // Node[][] nodes = pgMode.nodes;
    Node[][] nodes = mMode.nodes;
    public Node Nstart = pgMode.Nstart;
    public Node Ngoal = pgMode.Ngoal;

    // Common for both modes!
    Graphics g;
    private BufferStrategy bs;

    public boolean goal = pgMode.goal;
    public boolean start = pgMode.start;


    private BoardCanvas platno = pgMode.platno;
   // private BoardCanvas platno = mMode.platno;



    public MainFrame(String title)
    {
        super(title);
        setSize(MainFrame.width, MainFrame.height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //gonna use only center

        setLayout(new FlowLayout());
        OptionsPanel op = new OptionsPanel(200, 600, nodes, canvasWidth/n, canvasHeight/n, this);
        add(op);
        // mod
        add(platno);

       // pack();

        //safe for rendering here!! this object maybe will be destroyed
        RenderEngine re = new RenderEngine(platno, nodes, canvasWidth, canvasHeight, n, this);
        re.start();

        // Special for playground mode
        CanvasInput cInput = new CanvasInput(platno, nodes, n, this, canvasWidth, canvasHeight, op);


    }



}
