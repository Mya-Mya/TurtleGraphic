import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class TurtleView extends JPanel {
    private TurtlePositionListener iTurtlePositionListener;
    private Image iTurtle;
    private Image iBackground;
    private double x = 0;
    private double y = 0;
    private double size = 1;
    private double angle = 0;

    public TurtleView(
            MousePositionListener iMousePositionListener,
            TurtlePositionListener iTurtlePositionListener
    ) {
        super();
        this.iTurtlePositionListener=iTurtlePositionListener;
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

        iTurtle = new ImageIcon("img/turtle.png").getImage();
        moveTo(100, 100);
        setAngle(0);

        setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        if(iBackground!=null){
            g2.drawImage(iBackground,0,0,this);
        }

        AffineTransform transform = g2.getTransform();
        int width = (int) (50 * size);
        int height = (int) (70 * size);
        transform.setToRotation(Math.toRadians(angle + 90),x,y);
        g2.setTransform(transform);
        g2.drawImage(iTurtle, (int) (x - width * .5), (int) (y - height * .5), width, height, this);
    }

    public void move(double deltaX, double deltaY) {
        x += deltaX;
        y += deltaY;
        iTurtlePositionListener.onTurtlePositionChanged(x,y);
    }

    public void moveTo(double x, double y) {
        this.x = x;
        this.y = y;
        iTurtlePositionListener.onTurtlePositionChanged(x,y);
    }

    public void goStraight(double l) {
        move(
                Math.cos(Math.toRadians(angle)) * l,
                Math.sin(Math.toRadians(angle)) * l
        );
    }

    public void turn(double deltaAngle) {
        this.angle += deltaAngle;
        iTurtlePositionListener.onTurtleAngleChanged(angle);
    }

    public void setAngle(double angle) {
        this.angle = angle;
        iTurtlePositionListener.onTurtleAngleChanged(angle);
    }

    public void larger(double ratio) {
        size *= ratio;
    }

    public void smaller(double ratio) {
        size /= ratio;
    }

    public void setSize(double size) {
        this.size = size;
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
        this.iBackground=iBackground;
    }
}
