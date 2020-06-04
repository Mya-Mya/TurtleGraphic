package view;

import model.*;
import model.floor.Floor;
import ui.UiFactory;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

public class MainView extends JPanel implements WorldListener, TurtleListener {
    private List<MainViewMouseListener> mMainViewMouseListenerList = new ArrayList<>();
    private Turtle mTurtle;
    private double g2PositionRatio = 1;
    private World mWorld;

    public MainView(Turtle mTurtle, World mWorld) {
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
                fireOnMouseMoved(e.getPoint());
            }
        });
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                fireOnMouseClicked(mouseEvent);
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                fireOnMousePressed(mouseEvent);
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
                fireOnMouseReleased(mouseEvent);
            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {

            }
        });
        addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent mouseWheelEvent) {
                g2PositionRatio *= (1. + mouseWheelEvent.getWheelRotation() * 0.01);
                updateUI();
            }
        });

        setVisible(true);
        mTurtle.addTurtleListener(this);
        mWorld.addWorldListener(this);
    }

    public void addMainViewMouseListener(MainViewMouseListener l) {
        mMainViewMouseListenerList.add(l);
    }

    private void fireOnMouseMoved(Point p) {
        for (MainViewMouseListener l : mMainViewMouseListenerList) {
            l.onMouseMoved(p);
        }
    }

    private void fireOnMouseClicked(MouseEvent e) {
        for (MainViewMouseListener l : mMainViewMouseListenerList) {
            l.onMouseClicked(e);
        }
    }

    private void fireOnMousePressed(MouseEvent e) {
        for (MainViewMouseListener l : mMainViewMouseListenerList) {
            l.onMousePressed(e);
        }
    }

    private void fireOnMouseReleased(MouseEvent e) {
        for (MainViewMouseListener l : mMainViewMouseListenerList) {
            l.onMouseReleased(e);
        }
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
        double size = mTurtle.getSize();
        int width = (int) (50 * size);
        int height = (int) (70 * size);
        double angle = mTurtle.getAngle();
        int x = (int) (mTurtle.getX() * g2PositionRatio);
        int y = (int) (mTurtle.getY() * g2PositionRatio);
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

    @Override
    public void onTurtleRemarkChanged(String remark) {
    }
}
