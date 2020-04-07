package view;

import model.MousePositionListener;
import model.TurtlePositionListener;
import ui.UiFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;

public class TurtleView extends JPanel {
    private Image iTurtle;
    private Image iBackground;
    private double x = 0;
    private double y = 0;
    private double size = 1;
    private double angle = 0;

    public TurtleView(MousePositionListener iMousePositionListener) {
        super();
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
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        if (iBackground != null) {
            g2.drawImage(iBackground, 0, 0, this);
        }

        AffineTransform transform = g2.getTransform();
        int width = (int) (50 * size);
        int height = (int) (70 * size);
        transform.setToRotation(Math.toRadians(angle + 90), x, y);
        g2.setTransform(transform);
        g2.drawImage(iTurtle, (int) (x - width * .5), (int) (y - height * .5), width, height, this);
    }


    public void moveTo(double x, double y) {
        this.x = x;
        this.y = y;
        updateUI();
    }


    public void setAngle(double angle) {
        this.angle = angle;
        updateUI();
    }

    public void setSize(double size) {
        this.size = size;
        updateUI();
    }

    public void setBackgroundImage(Image iBackground) {
        this.iBackground = iBackground;
        updateUI();
    }
}
