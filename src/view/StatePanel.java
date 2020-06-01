package view;

import model.Turtle;
import model.TurtleListener;
import model.TurtleSimulator;
import ui.UiFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class StatePanel extends JPanel implements MainViewMouseListener, TurtleListener {
    private Turtle mTurtle;
    private TurtleSimulator mTurtleSimulator;
    private JLabel cMousePositionLabel;
    private JLabel cTurtleTransformLabel;

    public StatePanel(Turtle mTurtle, TurtleSimulator mTurtleSimulator, MainView vMainView) {
        super();
        this.mTurtle = mTurtle;
        this.mTurtleSimulator = mTurtleSimulator;

        setBackground(UiFactory.back);
        setForeground(UiFactory.white);
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));

        cMousePositionLabel = UiFactory.label();
        add(cMousePositionLabel);

        cTurtleTransformLabel = UiFactory.label();
        add(cTurtleTransformLabel);

        setVisible(true);
        mTurtle.addTurtleListener(this);
        vMainView.addMainViewMouseListener(this);
    }

    @Override
    public void onMouseMoved(Point point) {
        cMousePositionLabel.setText("マウス:" + point.x + ", " + point.y);
    }

    private void updateTurtleTransformLabel() {
        cTurtleTransformLabel.setText("カメ: " + (int) mTurtle.getX() + ", " + (int) mTurtle.getY() + ", " + (int) mTurtle.getAngle());
    }

    @Override
    public void onMouseClicked(MouseEvent e) {

    }

    @Override
    public void onMousePressed(MouseEvent e) {

    }

    @Override
    public void onMouseReleased(MouseEvent e) {

    }

    @Override
    public void onTurtleAngleChanged(double angle) {
        updateTurtleTransformLabel();
    }

    @Override
    public void onTurtleSizeChanged(double size) {
        updateTurtleTransformLabel();
    }

    @Override
    public void onTurtlePositionChanged(double x, double y) {
        updateTurtleTransformLabel();
    }

    @Override
    public void onTurtleImageChanged(Image image0, Image image1) {
    }
}
