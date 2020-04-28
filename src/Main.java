import model.Turtle;
import model.TurtleSimulator;
import model.World;
import viewmodel.TurtleViewModel;
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
        Turtle mTurtle = new Turtle(0, 1, 100, 100);
        World mWorld = new World();
        TurtleSimulator mTurtleSimulator=new TurtleSimulator(mTurtle,mWorld);

        //ビューモデル
        TurtleViewModel mTurtleViewModel = new TurtleViewModel(mTurtle);

        //ビュー
        StatePanel vStatePanel = new StatePanel(mTurtleViewModel);
        ControllerPanel vControllerPanel = new ControllerPanel(mTurtleSimulator,mWorld);
        MainView vMainView = new MainView(vStatePanel, mTurtleViewModel,mWorld);


        add(vStatePanel, BorderLayout.SOUTH);
        add(vMainView, BorderLayout.CENTER);
        add(vControllerPanel, BorderLayout.EAST);

        pack();
        setVisible(true);
    }
}
