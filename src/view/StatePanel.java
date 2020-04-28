package view;

import model.MousePositionListener;
import model.TurtleViewModel;
import model.TurtleViewModelListener;
import ui.UiFactory;

import javax.swing.*;
import java.awt.*;

public class StatePanel extends JPanel implements MousePositionListener, TurtleViewModelListener {
    private TurtleViewModel mTurtleViewModel;
    private JLabel cMousePositionLabel;
    private JLabel cTurtlePositionLabel;
    private JLabel cTurtleAngleLabel;

    public StatePanel(TurtleViewModel mTurtleViewModel) {
        super();
        this.mTurtleViewModel = mTurtleViewModel;

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
        mTurtleViewModel.addTurtleBehaviourListener(this);
    }

    @Override
    public void onMouseMoved(Point point) {
        cMousePositionLabel.setText("マウス座標:"+point.x + ", " + point.y);
    }

    @Override
    public void onTurtleViewModelChanged() {
        int x=(int) mTurtleViewModel.getTurtleX();
        int y=(int) mTurtleViewModel.getTurtleY();
        int angle=(((int) mTurtleViewModel.getAngle())+360)%360;
        cTurtlePositionLabel.setText("カメ座標:"+x+","+y);
        cTurtleAngleLabel.setText("カメ角度:"+angle);
    }
}
