package view;

import model.MousePositionListener;
import model.TurtleBehaviour;
import model.TurtleBehaviourListener;
import ui.UiFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;

public class TurtleView extends JPanel implements TurtleBehaviourListener {
    private TurtleBehaviour mTurtleBehaviour;
    private Image iTurtle;

    public TurtleView(MousePositionListener iMousePositionListener,
                      TurtleBehaviour mTurtleBehaviour) {
        super();
        this.mTurtleBehaviour = mTurtleBehaviour;

        setBackground(UiFactory.white);

        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
            }
            @Override
            public void mouseMoved(MouseEvent e) {
                iMousePositionListener.onMouseMoved(e.getPoint());
            }
        });

        iTurtle = new ImageIcon("img/turtle.png").getImage();
        setVisible(true);
        mTurtleBehaviour.addTurtleBehaviourListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        Image iBackground = mTurtleBehaviour.getBackgroundImage();
        if (iBackground != null) {
            g2.drawImage(iBackground, 0, 0, this);
        }

        AffineTransform transform = g2.getTransform();
        double size=mTurtleBehaviour.getSize();
        int width = (int) (50 * size);
        int height = (int) (70 * size);
        double angle=mTurtleBehaviour.getAngle();
        int x= (int) mTurtleBehaviour.getTurtleX();
        int y= (int) mTurtleBehaviour.getTurtleY();
        transform.setToRotation(Math.toRadians(angle + 90), x, y);
        g2.setTransform(transform);
        g2.drawImage(iTurtle, (int) (x - width * .5), (int) (y - height * .5), width, height, this);
    }


    @Override
    public void onTurtleBehaviourChanged() {
        updateUI();
    }
}
