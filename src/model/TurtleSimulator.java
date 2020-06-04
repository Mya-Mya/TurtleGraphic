package model;

import model.floor.Floor;
import model.floor.FloorColor;

/**
 * 授業で使用するカメへの命令を受け付ける。
 */
public class TurtleSimulator {
    private Turtle mTurtle;
    private World mWorld;

    public TurtleSimulator(Turtle mTurtle, World mWorld) {
        this.mTurtle = mTurtle;
        this.mWorld = mWorld;
    }

    public double getSimulatedAngle() {
        return mTurtle.getAngle();
    }

    public double getSimulatedSize() {
        return mTurtle.getSize();
    }

    public double getX() {
        return mTurtle.getX();
    }

    public double getY() {
        return mTurtle.getY();
    }

    public Floor getFloorOn() {
        return mWorld.getFloorOn(getX(), getY());
    }

    public FloorColor getFloorColorOn() {
        Floor floor = getFloorOn();
        if (floor != null) {
            return floor.getFloorColor();
        }
        return null;
    }

    public void goStraight(double l) {
        double theta = Math.toRadians(mTurtle.getAngle());
        double x1 = mTurtle.getX() + l * Math.cos(theta);
        double y1 = mTurtle.getY() + l * Math.sin(theta);
        mTurtle.setPosition(x1, y1);
    }

    public boolean isOnAnyFloor() {
        return getFloorColorOn() != null;
    }


    public void larger(double ratio) {
        setSize(mTurtle.getSize() * ratio);
    }

    public void say(String text) {
        mTurtle.setRemark(text);
    }

    public void setAngle(double angle) {
        mTurtle.setAngle(angle);
    }

    public void setPosition(double x, double y) {
        mTurtle.setPosition(x, y);
    }

    public void setSize(double size) {
        mTurtle.setSize(size);
    }

    public void silent() {
        mTurtle.setRemark("");
    }

    public void smaller(double ratio) {
        larger(1. / ratio);
    }

    public void turn(double deltaAngle) {
        mTurtle.setAngle(mTurtle.getAngle() + deltaAngle);
    }

    public void turnLeft() {
        turn(-90);
    }

    public void turnRight() {
        turn(90);
    }

}
