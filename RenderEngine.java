import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;

public class RenderEngine  implements ActionListener {
    private Timer timer;
    private int delay = 8;
    private BufferStrategy bs;
    Graphics g;
    private BoardCanvas platno;
    Node[][] nodes;
    int width;
    int height;
    int n;
    MainFrame mf;


    public RenderEngine(BoardCanvas platno, Node[][] nodes, int width, int height, int n, MainFrame mf){

        this.platno = platno;
        this.nodes = nodes;
        this.width = width;
        this.height = height;
        this.n = n;
        this.mf = mf;
        timer = new Timer(delay, this);

    }

    public void start(){
        timer.start();
    }

    public void stop(){
        timer.stop();
    }


    public void render()
    {
        bs = platno.getBufferStrategy();
        if(bs == null){
            platno.createBufferStrategy(3);
            return;
        }

        g = bs.getDrawGraphics();
        g.clearRect(0, 0, width, height);
        // draw here

        // Note: Randomized maze in this Category
        if(mf.PLAYMODE == 0) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {

                    if (!nodes[i][j].block) g.setColor(Color.YELLOW);
                    if (nodes[i][j].open) g.setColor(Color.blue);
                    if (nodes[i][j].route) g.setColor(Color.red);

                    if (nodes[i][j].start) g.setColor(Color.RED);
                    if (nodes[i][j].goal) g.setColor(Color.GREEN);

                    if (nodes[i][j].block) g.setColor(Color.BLACK);


                    g.fillRect(width / n * j, height / n * i, width / n, width / n);
                    if(mf.showBoundery) {
                        g.setColor(Color.blue);
                        g.drawRect(width / n * j, height / n * i, width / n, width / n);
                    }
                }
            }
        }

        if(mf.PLAYMODE == 1){
            for(int i = 0; i < n; i++){
                for(int j = 0; j < n; j++){


                    g.setColor(Color.white);

                    if (nodes[j][i].highlight && mf.backtrack) g.setColor(Color.PINK);
                    if(nodes[j][i].open) g.setColor(Color.blue);

                    if(nodes[j][i].route) g.setColor(Color.yellow);
                    if(nodes[j][i].start) g.setColor(Color.RED);
                    if (nodes[j][i].goal) g.setColor(Color.GREEN);

                    g.fillRect(width / n * j, height / n * i, width / n, width / n);
                    if(mf.dotted) {
                        if (nodes[j][i].route && !nodes[j][i].start && !nodes[j][i].goal) {
                            g.setColor(Color.red);
                            g.fillOval(width / n * j + (int) (width / (6 * n)), height / n * i + (int) (height / (6 * n)), (int) (width / (3 * n)), (int) (height / (3 * n)));
                        }
                    }


                    g.setColor(Color.black);
                    Stroke stroke = new BasicStroke(2f);
                    ((Graphics2D)g).setStroke(stroke);
                    int distX = width/n;
                    int distY = height/n;
                    if(nodes[j][i].top)
                    {
                        g.drawLine(distX*j, distY*i, distX*j + distX, distY*i);
                    }
                    if(nodes[j][i].right){
                        g.drawLine(distX*j + distX, distY*i, distX*j + distX, distY*i + distY);
                    }
                    if(nodes[j][i].bottom){
                        g.drawLine(distX*j, distY*i + distY, distX*j + distX, distY*i + distY);
                    }
                    if(nodes[j][i].left){
                        g.drawLine(distX*j, distY*i, distX*j, distY*i + distY);

                    }

                }
            }

        }

        //end drawin
        bs.show();
        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        render();
    }


}

