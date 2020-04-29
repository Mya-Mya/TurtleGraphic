package view;

import model.MousePositionListener;
import model.Turtle;
import model.TurtleListener;
import model.TurtleSimulator;
import model.floor.FloorColor;
import viewmodel.TurtleViewModel;
import viewmodel.TurtleViewModelListener;
import ui.UiFactory;

import javax.swing.*;
import java.awt.*;

public class StatePanel extends JPanel implements MousePositionListener, TurtleViewModelListener, TurtleListener {
    private TurtleViewModel mTurtleViewModel;
    private TurtleSimulator mTurtleSimulator;
    private JLabel cMousePositionLabel;
    private JLabel cTurtleTransformLabel;
    private JLabel cTurtleSimulatedTransformLabel;

    public StatePanel(TurtleViewModel mTurtleViewModel, Turtle mTurtle, TurtleSimulator mTurtleSimulator) {
        super();
        this.mTurtleViewModel = mTurtleViewModel;
        this.mTurtleSimulator = mTurtleSimulator;

        setBackground(UiFactory.back);
        setForeground(UiFactory.white);
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));

        cMousePositionLabel = UiFactory.label();
        add(cMousePositionLabel);

        cTurtleTransformLabel = UiFactory.label();
        add(cTurtleTransformLabel);

        cTurtleSimulatedTransformLabel = UiFactory.label();
        add(cTurtleSimulatedTransformLabel);

        setVisible(true);
        mTurtle.addTurtleListener(this);
        mTurtleViewModel.addTurtleViewListener(this);
    }

    @Override
    public void onMouseMoved(Point point) {
        cMousePositionLabel.setText("マウス:" + point.x + ", " + point.y);
    }

    @Override
    public void onTurtleViewModelChanged() {
        int x = (int) mTurtleViewModel.getTurtleX();
        int y = (int) mTurtleViewModel.getTurtleY();
        int angle = (((int) mTurtleViewModel.getAngle()) + 360) % 360;
        cTurtleTransformLabel.setText("カメ:" + x + "," + y + "," + angle);
    }

    @Override
    public void onTurtleTransformChanged(double angle0, double angle1, double size0, double size1, double x0, double x1, double y0, double y1) {
        int x = (int) x1;
        int y = (int) y1;
        int angle = (int) angle1;
        FloorColor floorColor = mTurtleSimulator.getFloorColorOn();
        String onFloorText = "";
        if (floorColor != null) {
            onFloorText = floorColor.toString() + "の上";
        }
        cTurtleSimulatedTransformLabel.setText("カメ(シミュレーション):" + x + "," + y + "," + angle + " " + onFloorText);
    }

    @Override
    public void onTurtleImageChanged(Image image0, Image image1) {

    }
}
