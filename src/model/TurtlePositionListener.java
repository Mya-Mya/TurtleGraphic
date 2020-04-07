package model;

public interface TurtlePositionListener {
    void onTurtlePositionChanged(double x,double y);
    void onTurtleAngleChanged(double angle);
}
