import javax.swing.*;
import java.awt.*;

public class StatePanel extends JPanel implements MousePositionListener,TurtlePositionListener {
    private JLabel cMousePositionLabel;
    private JLabel cTurtlePositionLabel;
    private JLabel cTurtleAngleLabel;

    public StatePanel() {
        super();
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
    }

    @Override
    public void onMouseMoved(Point point) {
        cMousePositionLabel.setText("マウス座標:"+point.x + ", " + point.y);
    }

    @Override
    public void onTurtlePositionChanged(double x, double y) {
        cTurtlePositionLabel.setText("カメ座標:"+(int)x+","+(int)y);
    }

    @Override
    public void onTurtleAngleChanged(double angle) {
        cTurtleAngleLabel.setText("カメ角度:"+(int)angle%360);
    }
}
