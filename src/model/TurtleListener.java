package model;

/**
 * Turtleの値に関するリスナー。
 */
public interface TurtleListener {
    /**
     * カメの座標、角度、大きさの変更を通知する。
     */
    void onTurtleTransformChanged(double angle0, double angle1, double size0, double size1, double x0, double x1, double y0, double y1);
}
