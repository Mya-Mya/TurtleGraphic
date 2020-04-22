package model;

import view.TurtleView;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class TurtleBehaviour {

    interface AnimationFrame {
        void startRunning();

        void run(double time);

        void finalFrame();
    }

    private List<TurtleBehaviourListener> behaviourListenerList;

    private Queue<AnimationFrame> animationFrameQueue;

    private double x = 50;
    private double y = 50;
    private double angle = 0;
    private double size = 1;
    private Image iBackground;
    private Boolean runningTurtleBotThread = false;

    private final int FREQUENCY = 40;
    private final double ALL_TIME = .3;

    public TurtleBehaviour() {
        behaviourListenerList = new ArrayList<>();
        animationFrameQueue = new ArrayDeque<>();
    }

    public void addTurtleBehaviourListener(TurtleBehaviourListener listener) {
        behaviourListenerList.add(listener);
        listener.onTurtleBehaviourChanged();
    }

    private void fireTurtleBehaviourListener() {
        for (TurtleBehaviourListener listener : behaviourListenerList) {
            listener.onTurtleBehaviourChanged();
        }
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
            }

            @Override
            public void finalFrame() {
                x = startX + deltaX;
                y = startY + deltaY;
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
            }

            @Override
            public void finalFrame() {
                x = startX + cosAngle * l;
                y = startY + sinAngle * l;
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
            }

            @Override
            public void finalFrame() {
                angle = startAngle + deltaAngle;
            }
        });

    }

    public void turnRight() {
        turn(90);
    }

    public void turnLeft() {
        turn(-90);
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
                size = startSize * Math.pow(ratio, time);
            }

            @Override
            public void finalFrame() {
                size = startSize * ratio;
            }
        });
    }

    public void smaller(double ratio) {
        larger(1. / ratio);
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
        this.iBackground = iBackground;
        fireTurtleBehaviourListener();
    }

    public Image getBackgroundImage() {
        return iBackground;
    }

    public double getSize() {
        return size;
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
                synchronized (runningTurtleBotThread) {
                    if (runningTurtleBotThread) return;
                    runningTurtleBotThread = true;
                }
                System.out.println("カメロボットスレッド開始");

                while (true) {
                    AnimationFrame frame;
                    synchronized (animationFrameQueue) {
                        frame = animationFrameQueue.poll();
                    }
                    if (frame == null) break;

                    frame.startRunning();
                    System.out.println(frame + "のアニメーション開始");
                    for (int i = 0; i < numFrame; i++) {
                        sleepFor(deltaTime);
                        frame.run((double) i / (double) numFrame);
                        fireTurtleBehaviourListener();
                    }
                    frame.finalFrame();
                    fireTurtleBehaviourListener();
                    sleepFor(100);
                    System.out.println(frame + "のアニメーション終了");
                }

                synchronized (runningTurtleBotThread) {
                    runningTurtleBotThread = false;
                }
                System.out.println("カメロボットスレッド終了");
            }
        });
        thread.start();
    }

    private void sleepFor(long t) {
        try {
            Thread.sleep(t);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
