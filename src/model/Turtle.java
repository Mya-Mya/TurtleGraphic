package model;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * カメの座標、角度、大きさ、画像を管理する。
 */
public class Turtle {
    private List<TurtleListener> mTurtleListenerList = new ArrayList<>();
    private double angle;
    private Image image;
    private double size;
    private double x;
    private double y;

    public Turtle(double angle, double size, double x, double y) {
        setAngle(angle);
        setX(x);
        setY(y);
        setImage(new ImageIcon("img/turtle.png").getImage());
    }

    public void addTurtleListener(TurtleListener l) {
        mTurtleListenerList.add(l);
    }

    public void fireTurtleListeners(double angle0, double angle1, double size0, double size1, double x0, double x1, double y0, double y1) {
        for (TurtleListener l : mTurtleListenerList) {
            l.onTurtleTransformChanged(angle0, angle1, size0, size1, x0, x1, y0, y1);
        }
    }

    public double getAngle() {
        return angle;
    }

    public Image getImage() {
        return image;
    }

    public double getSize() {
        return size;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setAngle(double angle) {
        double angle0 = angle;
        this.angle = (angle + 360.) % 360.;
        fireTurtleListeners(angle0, angle, size, size, x, x, y, y);
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setSize(double size) {
        double size0 = size;
        this.size = size;
        fireTurtleListeners(angle, angle, size0, size, x, x, y, y);
    }

    public void setX(double x) {
        double x0 = x;
        this.x = x;
        fireTurtleListeners(angle, angle, size, size, x0, x, y, y);
    }

    public void setY(double y) {
        double y0 = y;
        this.y = y;
        fireTurtleListeners(angle, angle, size, size, x, x, y0, y);
    }
}
