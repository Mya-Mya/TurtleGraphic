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
        this.angle = angle;
        this.size = size;
        this.x = x;
        this.y = y;
        setImage(new ImageIcon("img/turtle.png").getImage());
        onTransformChanged(angle, angle, size, size, x, x, y, y);
    }

    public void addTurtleListener(TurtleListener l) {
        mTurtleListenerList.add(l);
    }

    public void onImageChanged(Image image0, Image image1) {
        for (TurtleListener l : mTurtleListenerList) {
            l.onTurtleImageChanged(image0, image1);
        }
    }

    public void onTransformChanged(double angle0, double angle1, double size0, double size1, double x0, double x1, double y0, double y1) {
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
        onTransformChanged(angle0, angle, size, size, x, x, y, y);
    }

    public void setImage(Image image) {
        Image image0 = image;
        this.image = image;
        onImageChanged(image0, image);
    }

    public void setSize(double size) {
        double size0 = size;
        this.size = size;
        onTransformChanged(angle, angle, size0, size, x, x, y, y);
    }


    public void setPosition(double x, double y) {
        double x0 = x;
        double y0 = y;
        this.x = x;
        this.y = y;
        onTransformChanged(angle, angle, size, size, x0, x, y0, y);
    }
}
