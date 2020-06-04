package model;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;


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
    private String remark;

    private Semaphore animationSemaphore;

    private interface Animation {
        void onStart();

        void frame(double a);//アニメーションの開始ではa=0、終了時にはa=1

        void finalFrame();
    }

    public Turtle(double angle, double size, double x, double y) {
        this.angle = angle;
        this.size = size;
        this.x = x;
        this.y = y;
        this.remark = "";
        setImage(new ImageIcon("img/turtle.png").getImage());
        animationSemaphore = new Semaphore(1);
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

    public void onTurtleRemarkChanged(){
        for (TurtleListener l : mTurtleListenerList) {
            l.onTurtleRemarkChanged(this.remark);
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

    public String getRemark() {
        return remark;
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

        startAnimation(new Animation() {
            double angle0;
            double deltaAngle;

            @Override
            public void onStart() {
                angle0 = Turtle.this.angle;
                deltaAngle = angle - angle0;
            }

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
        }, 30, 0.4);
    }

    public void setImage(Image image) {
        Image image0 = this.image;
        this.image = image;
        onImageChanged(image0, image);
    }

    public void setRemark(String remark) {
        this.remark = remark;
        onTurtleRemarkChanged();
    }

    public void setSize(double size) {
        if (this.size == size) return;
        startAnimation(new Animation() {
            double size0;
            double deltaSize;

            @Override
            public void onStart() {
                size0 = Turtle.this.size;
                deltaSize = size - size0;
            }

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
        }, 30, 0.3);
    }


    public void setPosition(double x, double y) {
        if (this.x == x && this.y == y) return;
        startAnimation(new Animation() {
            double x0;
            double y0;
            double deltaX;
            double deltaY;

            @Override
            public void onStart() {
                x0 = Turtle.this.x;
                y0 = Turtle.this.y;
                deltaX = x - x0;
                deltaY = y - y0;
            }

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
        }, 30, 0.4);
    }

    /**
     * 毎秒fフレームのアニメーションをt秒実行させる。この関数はアニメーションが終了するまで制御を戻さない。
     *
     * @param animation アニメーションのモデル
     * @param f         アニメーションをf Hzとする
     * @param t         アニメーションをt sとする
     */
    public void startAnimation(Animation animation, double f, double t) {
        if (!animationSemaphore.tryAcquire()) {
            System.out.println("アニメーション " + animation + " 棄却");
            return;
        }
        double delta_t = 1. / f;
        long delta_t_ms = (long) (delta_t * 1000.);
        double delta_a = delta_t / t;
        System.out.println("アニメーション " + animation + " 開始");
        try {
            animation.onStart();
            for (double a = 0; a < 1.; a += delta_a) {
                animation.frame(a);
                Thread.sleep(delta_t_ms);
            }
            animation.finalFrame();
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("アニメーション " + animation + " 終了");
        animationSemaphore.release();
    }
}
