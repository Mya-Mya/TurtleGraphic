package model;

import java.awt.*;

/**
 * Turtleの値に関するリスナー。
 */
public interface TurtleListener {
    /**
     * カメの座標、角度、大きさの変更を通知する。
     */
    void onTurtleAngleChanged(double angle);
    void onTurtleSizeChanged(double size);
    void onTurtlePositionChanged(double x,double y);
    /**
     * カメの画像の変更を通知する。
     */
    void onTurtleImageChanged(Image image0,Image image1);
}
