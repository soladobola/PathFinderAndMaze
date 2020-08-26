import java.awt.*;

public class BoardCanvas extends Canvas {

    public BoardCanvas(int width, int height){

        super();
        Dimension dim = new Dimension(width, height);
        setPreferredSize(dim);
        setBackground(Color.BLACK);

    }
}
