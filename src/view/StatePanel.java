package view;

import model.MousePositionListener;
import model.Turtle;
import model.TurtleListener;
import model.TurtleSimulator;
import model.floor.FloorColor;
import ui.UiFactory;

import javax.swing.*;
import java.awt.*;

public class StatePanel extends JPanel implements MousePositionListener, TurtleListener {
    private TurtleSimulator mTurtleSimulator;
    private JLabel cMousePositionLabel;
    private JLabel cTurtleTransformLabel;
    private JLabel cTurtleSimulatedTransformLabel;

    public StatePanel(Turtle mTurtle, TurtleSimulator mTurtleSimulator) {
        super();
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
    }

    @Override
    public void onMouseMoved(Point point) {
        cMousePositionLabel.setText("マウス:" + point.x + ", " + point.y);
    }

/*
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
 */

    @Override
    public void onTurtleAngleChanged(double angle) {

    }

    @Override
    public void onTurtleSizeChanged(double size) {

    }

    @Override
    public void onTurtlePositionChanged(double x, double y) {

    }

    @Override
    public void onTurtleImageChanged(Image image0, Image image1) {

    }
}
