import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OptionsPanel extends JPanel {
    // implement later!!

    GridBagConstraints gc;
    Node[][] nodes;
    int distX;
    int distY;
    MainFrame mf;
    public JComboBox<String> izgAlg;

    public OptionsPanel(int width, int height, Node[][] nodes, int distX, int distY, MainFrame mf){

        super();
        this.nodes = nodes;
        this.distX = distX;
        this.distY = distY;
        this.mf = mf;
        this.setPreferredSize(new Dimension(width, height));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.setBorder(BorderFactory.createTitledBorder("Options"));
        this.setBackground(Color.lightGray);
        this.add(Box.createRigidArea(new Dimension(60, 60)));
        izgAlg = new JComboBox<>();
        izgAlg.addItem("A Star");
        izgAlg.addItem("Dijkstra");
        izgAlg.setPreferredSize(new Dimension(180, 20));
        izgAlg.setMaximumSize( izgAlg.getPreferredSize());
        izgAlg.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(izgAlg);
        this.add(Box.createRigidArea(new Dimension(20, 20)));
        JButton startBtn = new JButton("Start");
        startBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        startBtn.setPreferredSize(new Dimension(80, 35));
        startBtn.setMaximumSize( startBtn.getPreferredSize());

       // Container contentPane = getContentPane();
       // contentPane.add(listPane, BorderLayout.CENTER);
       // contentPane.add(buttonPane, BorderLayout.PAGE_END);

        this.add(startBtn);
        this.add(Box.createRigidArea(new Dimension(20, 20)));

        JButton mode0btn = new JButton("Mode 0");
        mode0btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton mode1btn = new JButton("Mode 1");
        mode1btn.setAlignmentX(Component.CENTER_ALIGNMENT);

        //ana//
        JButton mode2btn = new JButton("Mode 2");
        mode2btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        //ana//


        this.add(mode0btn);
        this.add(Box.createRigidArea(new Dimension(20, 20)));
        this.add(mode1btn);
        this.add(Box.createRigidArea(new Dimension(20, 20)));
        this.add(mode2btn);


        this.add(Box.createRigidArea(new Dimension(20, 20)));
        JButton hideEdgesBtn = new JButton("Hide edges");
        hideEdgesBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(hideEdgesBtn);

        this.add(Box.createRigidArea(new Dimension(20, 20)));
        JButton clrAlgs = new JButton("Clear Algs");
        clrAlgs.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(clrAlgs);

        this.add(Box.createRigidArea(new Dimension(20, 20)));
        JSlider speedSlider = new JSlider(JSlider.HORIZONTAL, 0, 300, 1);
        speedSlider.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(speedSlider);

        this.add(Box.createRigidArea(new Dimension(20, 20)));
        JSlider blockSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 20);
        blockSlider.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(blockSlider);

        this.add(Box.createRigidArea(new Dimension(20, 20)));
        JCheckBox dottedChk = new JCheckBox("Dotted?");
        dottedChk.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(dottedChk);

        this.add(Box.createRigidArea(new Dimension(20, 20)));
        JCheckBox showBacktrack = new JCheckBox("Backtrack?");
        showBacktrack.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(showBacktrack);



        dottedChk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(dottedChk.isSelected()){
                    mf.dotted = true;
                } else {
                    mf.dotted = false;
                }
            }
        });

        showBacktrack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(showBacktrack.isSelected()){
                    mf.backtrack = true;
                } else {
                    mf.backtrack = false;
                }
            }
        });

        speedSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                mf.speed = speedSlider.getValue();
            }
        });

        blockSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                mf.percentageBlock = (double)(blockSlider.getValue())/100;
            }
        });

        hideEdgesBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(mf.showBoundery){
                    mf.showBoundery = false;
                    hideEdgesBtn.setText("Show edges");
                } else {
                    mf.showBoundery = true;
                    hideEdgesBtn.setText("Hide edges");
                }
            }
        });

        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(mf.PLAYMODE == 0) {
                    Thread thread = new Thread(new RandomizedPrimesMaze(nodes, nodes.length, mf));
                    mf.Ngoal = null;
                    mf.Nstart = null;
                    mf.goal = false;
                    mf.start = false;
                    thread.start();
                }
                else {
                    // Run maze generator
                    // clear before run!!! in MazeGenerator
                     Thread thread = new Thread(new MazeGenerator(nodes, nodes.length, mf));
                     thread.start();
                }
            }
        });

        mode0btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(mf.PLAYMODE == 1) {
                    mf.PLAYMODE = 0;
                    // generate new board with mode 0

                }

            }
        });

        mode1btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(mf.PLAYMODE == 0) {
                    mf.PLAYMODE = 1;
                    // generate new board with mode 1
                }

            }
        });


        mode2btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                mf.PLAYMODE = 0;
                mf.Ngoal = null;
                mf.Nstart = null;
                mf.goal = false;
                mf.start = false;
                randomSuffle(nodes, mf.percentageBlock);


            }
        });

        clrAlgs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mf.Ngoal = null;
                mf.Nstart = null;
                mf.start = false;
                mf.goal = false;
                Utility.clearAlgs(nodes);

            }
        });

    }

    private void randomSuffle(Node[][] nodes, double percentage){
        // pogledaj Utility klasu, ova funkcija resetuje celije
        // za slucaj da je pre toga bio vec iscrtan lavirint
        // pa onda nakon ovog poziva nece biti nijedan block
        // dakle svi ce biti zuti

        // Obrati paznju da je ovo staticka funkcija (ima keyword static)
        // to znaci da ne treba praviti instancu ove klase da bi se ova funkcija pozvala
        // kao npr: new Utility() ..
        Utility.resetCells(nodes);

        for(int i = 0; i < nodes.length; i++)
        {
            for(int j = 0; j < nodes.length; j++)
            {
                // percentage je broj koji se zadaje funkciji, mora biti izmedju 0 i 1
                // semanticki nam govori koji procenat ce biti block, ako je recimo percentage = 0.2
                // to znaci da ce 20% kvadrata biti blockovi, tj. crni
                if(Math.random() < percentage)
                {
                    // postavljamo kvadratic da bude block
                    nodes[i][j].block = true;
                }
            }
        }

    }

}

