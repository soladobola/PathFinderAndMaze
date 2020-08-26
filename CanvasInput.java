import com.sun.tools.javac.Main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class CanvasInput {

    // Test adding

    int startI, startJ;
    int endI, endJ;

    // Test adding


    BoardCanvas platno;
    int width;
    int height;
    Node[][] nodes;
    OptionsPanel op;
    int n;

    MainFrame mf;

    public int min(int a, int b){
        if(a < b) return a;
         else return b;

    }

    public int max(int a, int b){
        if(a > b) return a;
        else return b;

    }

    public CanvasInput(BoardCanvas canvas, Node[][] nodes, int n, MainFrame maf, int width, int height, OptionsPanel op){

        this.platno = canvas;
        this.nodes = nodes;
        this.n = n;
        this.mf = maf;
        this.width = width;
        this.height = height;
        this.op = op;


        platno.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int mouseX = e.getX();
                int mouseY = e.getY();


                int squareWidth = width / n;
                int squareHeight = height / n;

                int row = mouseY / squareHeight;
                int col = mouseX / squareWidth;
                if(mf.PLAYMODE == 1){
                    int a = row;
                    row = col;
                    col = a;
                }



                if (e.getButton() == MouseEvent.BUTTON1) {
                    if(!nodes[row][col].start && !nodes[row][col].goal) {
                        nodes[row][col].block = !nodes[row][col].block;
                    }
                } else if (e.getButton() == MouseEvent.BUTTON3) {
                    if (!mf.goal) {
                        if(!nodes[row][col].start && !nodes[row][col].block) {
                            nodes[row][col].goal = true;
                            mf.goal = true;
                            mf.Ngoal = nodes[row][col];
                        }
                    } else {
                        if (nodes[row][col].goal) {
                            nodes[row][col].goal = false;
                            mf.goal = false;
                            mf.Ngoal = null;
                        }
                    }
                } else if(e.getButton() == MouseEvent.BUTTON2)
                {
                    if(!mf.start){
                        if(!nodes[row][col].goal && !nodes[row][col].block) {
                            nodes[row][col].start = true;
                            mf.start = true;
                            mf.Nstart = nodes[row][col];
                        }
                    }
                    else {
                        if(nodes[row][col].start)
                        {
                            nodes[row][col].start = false;
                            mf.start =  false;
                            mf.Nstart = null;
                        }
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                int mouseX = e.getX();
                int mouseY = e.getY();


                int squareWidth = width / n;
                int squareHeight = height / n;

                int row = mouseY / squareHeight;
                int col = mouseX / squareWidth;

                startI = row;
                startJ = col;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                int mouseX = e.getX();
                int mouseY = e.getY();


                int squareWidth = width / n;
                int squareHeight = height / n;

                int row = mouseY / squareHeight;
                int col = mouseX / squareWidth;

                endI = row;
                endJ = col;

                if((startI == endI && startJ != endJ) || (startI != endI && startJ == endJ)){
                    if(startI == endI){
                        for(int j = min(startJ, endJ); j <= max(startJ, endJ); j++){
                            if(startI >= 0 && startI < n && j >=0 && j < n)
                            nodes[startI][j].block = true;
                        }
                    }

                    if(startJ == endJ){
                        for(int i = min(startI, endI); i <= max(startI, endI); i++){
                            if(startI >= 0 && startI < n && i >=0 && i < n)
                            nodes[i][startJ].block = true;
                        }
                    }
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });


        platno.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }


            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_SPACE)
                {

                    if(mf.Nstart != null && mf.Ngoal != null)
                    {
                        if(op.izgAlg.getSelectedItem() == "A Star") {
                            Utility.clearNeibs(nodes);
                            Thread thread = new Thread(new AStar(nodes, mf.Nstart, mf.Ngoal, mf));
                            //  w Dijkstra(nodes, mf.NsThread thread = new Thread(netart,mf.Ngoal));
                            thread.start();
                        }

                        if(op.izgAlg.getSelectedItem() == "Dijkstra"){
                            Utility.clearNeibs(nodes);
                            Thread thread = new Thread(new Dijkstra(nodes, mf.Nstart, mf.Ngoal, mf));
                            thread.start();
                        }

                    } else
                    {
                        System.out.println("Choose starting and ending node!");
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

    }
}
