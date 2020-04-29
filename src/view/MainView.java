package view;

import model.MousePositionListener;
import model.World;
import model.WorldListener;
import model.floor.Floor;
import viewmodel.TurtleViewModel;
import viewmodel.TurtleViewModelListener;
import ui.UiFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;

public class MainView extends JPanel implements TurtleViewModelListener, WorldListener {
    private TurtleViewModel mTurtleViewModel;
    private World mWorld;

    public MainView(MousePositionListener iMousePositionListener,
                    TurtleViewModel mTurtleViewModel,
                    World mWorld) {
        super();
        this.mTurtleViewModel = mTurtleViewModel;
        this.mWorld=mWorld;

        setBackground(UiFactory.white);

        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
            }
            @Override
            public void mouseMoved(MouseEvent e) {
                iMousePositionListener.onMouseMoved(e.getPoint());
            }
        });

        setVisible(true);
        mTurtleViewModel.addTurtleViewListener(this);
        mWorld.addWorldListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        Image iBackground = mWorld.getBackground();
        if (iBackground != null) {
            g2.drawImage(iBackground, 0, 0, this);
        }

        for (Floor floor : mWorld.getFloorList()) {
            Color color=floor.getFloorColor().getSwingColor();
            g2.setColor(color);
            int x=floor.getP1().x;
            int y=floor.getP1().y;
            int width=floor.getP2().x-x;
            int height=floor.getP2().y-y;
            g2.fillRoundRect(x,y,width,height,1,1);
        }

        AffineTransform transform = g2.getTransform();
        double size= mTurtleViewModel.getSize();
        int width = (int) (50 * size);
        int height = (int) (70 * size);
        double angle= mTurtleViewModel.getAngle();
        int x= (int) mTurtleViewModel.getTurtleX();
        int y= (int) mTurtleViewModel.getTurtleY();
        transform.setToRotation(Math.toRadians(angle + 90), x, y);
        g2.setTransform(transform);
        g2.drawImage(mTurtleViewModel.getImage(), (int) (x - width * .5), (int) (y - height * .5), width, height, this);
    }


    @Override
    public void onTurtleViewModelChanged() {
        updateUI();
    }

    @Override
    public void onWorldChanged() {
        updateUI();
    }
}
