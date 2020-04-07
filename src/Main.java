import model.TurtleBehaviour;
import view.ControllerPanel;
import view.StatePanel;
import view.TurtleView;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main();
            }
        });

    }

    public Main() {
        super("TurtleGraphic");
        setPreferredSize(new Dimension(1000, 700));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        StatePanel vStatePanel = new StatePanel();
        TurtleView vTurtleView = new TurtleView(vStatePanel);
        TurtleBehaviour mTurtleBehaviour = new TurtleBehaviour(vTurtleView, vStatePanel);
        ControllerPanel vControllerPanel = new ControllerPanel(mTurtleBehaviour);

        add(vStatePanel, BorderLayout.SOUTH);
        add(vTurtleView, BorderLayout.CENTER);
        add(vControllerPanel, BorderLayout.EAST);

        pack();
        setVisible(true);
    }
}
