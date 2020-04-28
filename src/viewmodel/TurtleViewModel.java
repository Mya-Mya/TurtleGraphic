package viewmodel;

import model.Turtle;
import model.TurtleListener;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class TurtleViewModel implements TurtleListener {
    interface AnimationFrame {
        void startRunning();

        void run(double time);

        void finalFrame();
    }

    private List<TurtleViewModelListener> mTurtleViewModelListenerList;

    private Queue<AnimationFrame> animationFrameQueue;

    private double angle;
    private Image image;
    private double size;
    private double x;
    private double y;
    private Boolean runningTurtleBotThread = false;

    private final int FREQUENCY = 40;
    private final double ALL_TIME = .3;

    public TurtleViewModel(Turtle mTurtle) {
        angle = mTurtle.getAngle();
        size = mTurtle.getSize();
        image = mTurtle.getImage();
        x = mTurtle.getX();
        y = mTurtle.getY();

        mTurtleViewModelListenerList = new ArrayList<>();
        animationFrameQueue = new ArrayDeque<>();
    }

    public void addTurtleBehaviourListener(TurtleViewModelListener listener) {
        mTurtleViewModelListenerList.add(listener);
        listener.onTurtleViewModelChanged();
    }

    private void fireTurtleBehaviourListener() {
        for (TurtleViewModelListener listener : mTurtleViewModelListenerList) {
            listener.onTurtleViewModelChanged();
        }
    }

    @Override
    public void onTurtleTransformChanged(double angle0, double angle1, double size0, double size1, double x0, double x1, double y0, double y1) {

    }

    @Override
    public void onTurtleImageChanged(Image image0, Image image1) {
        this.image = image1;
        fireTurtleBehaviourListener();
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

    public double getTurtleX() {
        return x;
    }

    public double getTurtleY() {
        return y;
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
