package view;

import model.MousePositionListener;
import model.TurtleBehaviour;
import model.TurtleBehaviourListener;
import ui.UiFactory;

import javax.swing.*;
import java.awt.*;

public class StatePanel extends JPanel implements MousePositionListener, TurtleBehaviourListener {
    private TurtleBehaviour mTurtleBehaviour;
    private JLabel cMousePositionLabel;
    private JLabel cTurtlePositionLabel;
    private JLabel cTurtleAngleLabel;

    public StatePanel(TurtleBehaviour mTurtleBehaviour) {
        super();
        this.mTurtleBehaviour=mTurtleBehaviour;

        setBackground(UiFactory.back);
        setForeground(UiFactory.white);
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
        setBorder(UiFactory.bigEmptyBorder());

        cMousePositionLabel = UiFactory.label();
        add(cMousePositionLabel);

        cTurtlePositionLabel=UiFactory.label();
        add(cTurtlePositionLabel);

        cTurtleAngleLabel=UiFactory.label();
        add(cTurtleAngleLabel);

        setVisible(true);
        mTurtleBehaviour.addTurtleBehaviourListener(this);
    }

    @Override
    public void onMouseMoved(Point point) {
        cMousePositionLabel.setText("マウス座標:"+point.x + ", " + point.y);
    }

    @Override
    public void onTurtleBehaviourChanged() {
        int x=(int)mTurtleBehaviour.getTurtleX();
        int y=(int)mTurtleBehaviour.getTurtleY();
        int angle=(((int)mTurtleBehaviour.getAngle())+360)%360;
        cTurtlePositionLabel.setText("カメ座標:"+x+","+y);
        cTurtleAngleLabel.setText("カメ角度:"+angle);
    }
}
