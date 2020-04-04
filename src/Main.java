import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    public static void main(String[] args) {
        new Main();
    }

    public Main() {
        super("TurtleGraphic");
        setPreferredSize(new Dimension(1000, 700));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        StatePanel vStatePanel=new StatePanel();
        add(vStatePanel,BorderLayout.SOUTH);
        TurtleView vTurtleView=new TurtleView(vStatePanel,vStatePanel);
        add(vTurtleView, BorderLayout.CENTER);
        add(new ConrtollerPanel(vTurtleView),BorderLayout.EAST);

        pack();
        setVisible(true);
    }
}
