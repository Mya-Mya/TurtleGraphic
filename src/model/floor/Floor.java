package model.floor;

import java.awt.*;

public class Floor {
    private FloorColor color;
    private Point p1;
    private Point p2;

    public Floor(Point p1, Point p2,FloorColor color) {
        setFloorColor(color);
        setP1(p1);
        setP2(p2);

    }

    public FloorColor getFloorColor(){
        return color;
    }

    public Point getP1() {
        return p1;
    }
    public Point getP2() {
        return p2;
    }

    public boolean isOn(double x, double y) {
        return (p1.x < x && x < p2.x) && (p1.y < y && y < p2.y);
    }

    private void setFloorColor(FloorColor color) {
        this.color = color;
    }

    public void setP1(Point p1) {
        this.p1 = p1;
    }

    public void setP2(Point p2) {
        this.p2 = p2;
    }
}
