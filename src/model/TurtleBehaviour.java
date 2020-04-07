package model;

import view.TurtleView;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

public class TurtleBehaviour {
    interface AnimationFrame {
        void startRunning();

        void run(double time);

        void finalFrame();
    }

    private TurtleView vTurtleView;
    private TurtlePositionListener iTurtlePositionListener;
    private java.util.List<TurtleMotionListener> turtleMotionListenerList;
    private Queue<AnimationFrame> animationFrameQueue;

    private double x = 50;
    private double y = 50;
    private double angle = 0;
    private double size = 1;
    private Boolean runningTurtleBotThread = false;

    private final int FREQUENCY = 40;
    private final double ALL_TIME = .3;

    public TurtleBehaviour(
            TurtleView vTurtleView,
            TurtlePositionListener iTurtlePositionListener) {
        this.vTurtleView = vTurtleView;
        this.iTurtlePositionListener = iTurtlePositionListener;
        turtleMotionListenerList = new ArrayList<>();
        animationFrameQueue = new ArrayDeque<>();
        vTurtleView.moveTo(x, y);
        iTurtlePositionListener.onTurtlePositionChanged(x, y);
        vTurtleView.setAngle(angle);
        iTurtlePositionListener.onTurtleAngleChanged(angle);
        vTurtleView.setSize(size);
    }

    public void addTurtleMotionListener(TurtleMotionListener listener) {
        turtleMotionListenerList.add(listener);
    }

    public void move(double deltaX, double deltaY) {
        double halfPi = Math.PI / 2.;

        addAnimation(new AnimationFrame() {
            double startX;
            double startY;

            @Override
            public void startRunning() {
                startX = x;
                startY = y;
            }

            @Override
            public void run(double time) {
                double sinTime = Math.sin(time * halfPi);
                x = startX + sinTime * deltaX;
                y = startY + sinTime * deltaY;
                vTurtleView.moveTo(x, y);
                iTurtlePositionListener.onTurtlePositionChanged(x, y);
            }

            @Override
            public void finalFrame() {
                x = startX + deltaX;
                y = startY + deltaY;
                vTurtleView.moveTo(x, y);
                iTurtlePositionListener.onTurtlePositionChanged(x, y);
            }
        });

    }

    public void moveTo(double x, double y) {
        move(x - this.x, y - this.y);
    }

    public void goStraight(double l) {
        addAnimation(new AnimationFrame() {
            double startX;
            double startY;
            double cosAngle;
            double sinAngle;

            @Override
            public void startRunning() {
                startX = x;
                startY = y;
                double radianAngle = Math.toRadians(angle);
                cosAngle = Math.cos(radianAngle);
                sinAngle = Math.sin(radianAngle);
            }

            @Override
            public void run(double time) {
                x = startX + cosAngle * l * time;
                y = startY + sinAngle * l * time;
                vTurtleView.moveTo(x, y);
                iTurtlePositionListener.onTurtlePositionChanged(x, y);
            }

            @Override
            public void finalFrame() {
                x = startX + cosAngle * l;
                y = startY + sinAngle * l;
                vTurtleView.moveTo(x, y);
                iTurtlePositionListener.onTurtlePositionChanged(x, y);
            }
        });
    }

    public void turn(double deltaAngle) {
        double halfPi = Math.PI / 2.;
        addAnimation(new AnimationFrame() {
            double startAngle;

            @Override
            public void startRunning() {
                startAngle = angle;
            }

            @Override
            public void run(double time) {
                angle = startAngle + deltaAngle * Math.sin(time);
                vTurtleView.setAngle(angle);
                iTurtlePositionListener.onTurtleAngleChanged(angle);
            }

            @Override
            public void finalFrame() {
                angle = startAngle + deltaAngle;
                vTurtleView.setAngle(angle);
                iTurtlePositionListener.onTurtleAngleChanged(angle);
            }
        });

    }

    public void setAngle(double angle) {
        turn(angle - this.angle);
    }

    public void larger(double ratio) {
        addAnimation(new AnimationFrame() {
            double startSize;

            @Override
            public void startRunning() {
                startSize = size;
            }

            @Override
            public void run(double time) {
                vTurtleView.setSize(startSize * Math.pow(ratio, time));
            }

            @Override
            public void finalFrame() {
                size = startSize * ratio;
                vTurtleView.setSize(size);
            }
        });
    }

    public void smaller(double ratio) {
        larger(1. / ratio);
    }

    public void setSize(double size) {
        vTurtleView.setSize(size);
    }

    public double getTurtleX() {
        return x;
    }

    public double getTurtleY() {
        return y;
    }

    public double getAngle() {
        return angle;
    }

    public void setBackgroundImage(Image iBackground) {
        vTurtleView.setBackgroundImage(iBackground);
        vTurtleView.updateUI();
    }

    public boolean isRunningTurtleBotThread() {
        return runningTurtleBotThread;
    }

    private void addAnimation(AnimationFrame newAnimationFrame) {
        synchronized (animationFrameQueue) {
            animationFrameQueue.add(newAnimationFrame);
            System.out.println(animationFrameQueue + "のアニメーションがキューにある");
        }
        launchTurtleBotThread();
    }

    private void launchTurtleBotThread() {
        long deltaTime = (long) (1000 / (double) FREQUENCY);
        int numFrame = (int) (FREQUENCY * ALL_TIME);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (TurtleMotionListener listener : turtleMotionListenerList) listener.onStartedTurtleMoving();
                synchronized (runningTurtleBotThread){
                    if(runningTurtleBotThread)return;
                    runningTurtleBotThread = true;
                }
                System.out.println("カメロボットスレッド開始");

                while (true) {
                    AnimationFrame frame;
                    synchronized (animationFrameQueue){
                        frame = animationFrameQueue.poll();
                    }
                    if (frame == null) break;

                    frame.startRunning();
                    System.out.println(frame + "のアニメーション開始");
                    for (int i = 0; i < numFrame; i++) {
                        try {
                            Thread.sleep(deltaTime);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        frame.run((double) i / (double) numFrame);
                    }
                    frame.finalFrame();
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(frame + "のアニメーション終了");
                }

                synchronized (runningTurtleBotThread){
                    runningTurtleBotThread = false;
                }
                for (TurtleMotionListener listener : turtleMotionListenerList) listener.onFinishedTurtleMoving();
                System.out.println("カメロボットスレッド終了");
            }
        });
        thread.start();

    }
}
