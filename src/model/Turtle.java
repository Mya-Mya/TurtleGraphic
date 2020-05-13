package model;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


/**
 * カメの座標、角度、大きさ、画像を管理する。
 * このクラスはアニメーションの管理はしないし、各変数は瞬時に変更される。
 */
public class Turtle {
    private List<TurtleListener> mTurtleListenerList = new ArrayList<>();
    private double angle;
    private Image image;
    private double size;
    private double x;
    private double y;

    private interface Animation {
        void frame(double a);//アニメーションの開始ではa=0、終了時にはa=1

        void finalFrame();
    }

    public Turtle(double angle, double size, double x, double y) {
        this.angle = angle;
        this.size = size;
        this.x = x;
        this.y = y;
        setImage(new ImageIcon("img/turtle.png").getImage());
    }

    public void addTurtleListener(TurtleListener l) {
        mTurtleListenerList.add(l);
    }

    public void onImageChanged(Image image0, Image image1) {
        for (TurtleListener l : mTurtleListenerList) {
            l.onTurtleImageChanged(image0, image1);
        }
    }

    public void onTurtleAngleChanged() {
        for (TurtleListener l : mTurtleListenerList) {
            l.onTurtleAngleChanged(this.angle);
        }
    }

    public void onTurtleSizeChanged() {
        for (TurtleListener l : mTurtleListenerList) {
            l.onTurtleSizeChanged(this.size);
        }
    }

    public void onTurtlePositionChanged() {
        for (TurtleListener l : mTurtleListenerList) {
            l.onTurtlePositionChanged(this.x, this.y);
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
        if (this.angle == angle) return;
        double angle0 = this.angle;
        double deltaAngle = angle - angle0;
        startAnimation(new Animation() {
            @Override
            public void frame(double a) {
                Turtle.this.angle = angle0 + deltaAngle * a;
                onTurtleAngleChanged();
            }

            @Override
            public void finalFrame() {
                Turtle.this.angle = angle;
                onTurtleAngleChanged();
            }
        }, 10, 0.8);
    }

    public void setImage(Image image) {
        Image image0 = this.image;
        this.image = image;
        onImageChanged(image0, image);
    }

    public void setSize(double size) {
        if (this.size == size) return;
        double size0 = this.size;
        double deltaSize = size - size0;
        startAnimation(new Animation() {
            @Override
            public void frame(double a) {
                Turtle.this.size = size0 + deltaSize * a;
                onTurtleSizeChanged();
            }

            @Override
            public void finalFrame() {
                Turtle.this.size = size;
                onTurtleSizeChanged();
            }
        }, 10, 0.6);
    }


    public void setPosition(double x, double y) {
        if (this.x == x && this.y == y) return;
        double x0 = this.x;
        double y0 = this.y;
        double deltaX = x - x0;
        double deltaY = y - y0;
        startAnimation(new Animation() {
            @Override
            public void frame(double a) {
                Turtle.this.x = x0 + deltaX * a;
                Turtle.this.y = y0 + deltaY * a;
                onTurtlePositionChanged();
            }

            @Override
            public void finalFrame() {
                Turtle.this.x = x;
                Turtle.this.y = y;
                onTurtlePositionChanged();
            }
        }, 10, 0.8);
    }

    /**
     * 毎秒fフレームのアニメーションをt秒実行させる。この関数はアニメーションが終了するまで制御を戻さない。
     *
     * @param animation アニメーションのモデル
     * @param f         アニメーションをf Hzとする
     * @param t         アニメーションをt sとする
     */
    public void startAnimation(Animation animation, double f, double t) {
        double delta_t = 1. / (f);
        double delta_a = t / delta_t;
        System.out.println("アニメーション " + animation + " 開始");
        try {
            for (double a = 0; a < 1.; a += delta_a) {
                animation.frame(a);
                Thread.sleep((long) delta_t);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        animation.finalFrame();
    }
}
