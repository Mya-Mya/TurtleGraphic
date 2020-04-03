import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class TurtleView extends JPanel {
    private Image iTurtle;
    private double x = 0;
    private double y = 0;
    private double size = 1;
    private double angle = 0;

    public TurtleView() {
        super();
        setBackground(UiFactory.white);

        iTurtle = new ImageIcon("img/turtle.png").getImage();
        moveTo(100, 100);
        setAngle(0);

        setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
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
    }

    public void moveTo(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void goStraight(double l) {
        move(
                Math.cos(Math.toRadians(angle)) * l,
                Math.sin(Math.toRadians(angle)) * l
        );
    }

    public void turn(double deltaAngle) {
        this.angle += deltaAngle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
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

}
