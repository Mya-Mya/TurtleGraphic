import model.TurtleViewModel;
import view.ControllerPanel;
import view.StatePanel;
import view.MainView;

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
        TurtleViewModel mTurtleViewModel = new TurtleViewModel();

        //ビュー
        StatePanel vStatePanel = new StatePanel(mTurtleViewModel);
        ControllerPanel vControllerPanel = new ControllerPanel(mTurtleViewModel);
        MainView vMainView = new MainView(vStatePanel, mTurtleViewModel);

        add(vStatePanel, BorderLayout.SOUTH);
        add(vMainView, BorderLayout.CENTER);
        add(vControllerPanel, BorderLayout.EAST);

        pack();
        setVisible(true);
    }
}
