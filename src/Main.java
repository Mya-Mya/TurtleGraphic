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

        //モデル
        TurtleBehaviour mTurtleBehaviour = new TurtleBehaviour();

        //ビュー
        StatePanel vStatePanel = new StatePanel(mTurtleBehaviour);
        ControllerPanel vControllerPanel = new ControllerPanel(mTurtleBehaviour);
        TurtleView vTurtleView = new TurtleView(vStatePanel,mTurtleBehaviour);

        add(vStatePanel, BorderLayout.SOUTH);
        add(vTurtleView, BorderLayout.CENTER);
        add(vControllerPanel, BorderLayout.EAST);

        pack();
        setVisible(true);
    }
}
