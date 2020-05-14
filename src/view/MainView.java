package view;

import model.*;
import model.floor.Floor;
import ui.UiFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;

public class MainView extends JPanel implements WorldListener, TurtleListener {
    private Turtle mTurtle;
    private World mWorld;

    public MainView(Turtle mTurtle,
                    MousePositionListener iMousePositionListener,
                    World mWorld) {
        super();
        this.mTurtle = mTurtle;
        this.mWorld = mWorld;

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

        setVisible(true);
        mTurtle.addTurtleListener(this);
        mWorld.addWorldListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        Image iBackground = mWorld.getBackground();
        if (iBackground != null) {
            g2.drawImage(iBackground, 0, 0, this);
        }

        for (Floor floor : mWorld.getFloorList()) {
            Color color = floor.getFloorColor().getSwingColor();
            g2.setColor(color);
            int x = floor.getP1().x;
            int y = floor.getP1().y;
            int width = floor.getP2().x - x;
            int height = floor.getP2().y - y;
            g2.fillRect(x, y, width, height);
        }

        AffineTransform transform = g2.getTransform();
        double size= mTurtle.getSize();
        int width = (int) (50 * size);
        int height = (int) (70 * size);
        double angle= mTurtle.getAngle();
        int x= (int) mTurtle.getX();
        int y= (int) mTurtle.getY();
        transform.setToRotation(Math.toRadians(angle + 90), x, y);
        g2.setTransform(transform);
        g2.drawImage(mTurtle.getImage(), (int) (x - width * .5), (int) (y - height * .5), width, height, this);

    }


    @Override
    public void onWorldChanged() {
        updateUI();
    }

    @Override
    public void onTurtleAngleChanged(double angle) {
        updateUI();
    }

    @Override
    public void onTurtleSizeChanged(double size) {
        updateUI();
    }

    @Override
    public void onTurtlePositionChanged(double x, double y) {
        updateUI();
    }

    @Override
    public void onTurtleImageChanged(Image image0, Image image1) {
        updateUI();
    }
}
