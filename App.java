import javax.swing.*;

public class App {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainFrame frame = new MainFrame("Path Finder by Mladen and Ana");
                frame.setVisible(true);

            }
        });



    }

}
