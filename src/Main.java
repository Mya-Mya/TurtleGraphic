import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    public static void main(String[] args) {
        new Main();
    }

    public Main() {
        super("TurtleGraphic");
        setPreferredSize(new Dimension(600, 500));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        TurtleView vTurtleView=new TurtleView();
        add(vTurtleView, BorderLayout.CENTER);
        add(new ConrtollerPanel(vTurtleView),BorderLayout.EAST);

        pack();
        setVisible(true);
    }
}
