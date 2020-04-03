import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConrtollerPanel extends JPanel {
    private TurtleView vTurtleView;
    private Dimension buttonSize = new Dimension(100, 40);

    public ConrtollerPanel(TurtleView vTurtleView) {
        super();
        this.vTurtleView = vTurtleView;
        setBorder(UiFactory.bigEmptyBorder());

        setBackground(UiFactory.back);
        setForeground(UiFactory.white);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel label = UiFactory.label();
        label.setText("コントローラー");
        label.setHorizontalTextPosition(JLabel.CENTER);
        label.setAlignmentX(.5f);
        add(label);

        addAction("直進", new Runnable() {
            @Override
            public void run() {
                vTurtleView.goStraight(20);
            }
        });

        addAction("右回り", new Runnable() {
            @Override
            public void run() {
                vTurtleView.turn(45.);
            }
        });

        addAction("左回り", new Runnable() {
            @Override
            public void run() {
                vTurtleView.turn(-45.);
            }
        });

        addAction("大きく", new Runnable() {
            @Override
            public void run() {
                vTurtleView.larger(1.2);
            }
        });

        addAction("小さく", new Runnable() {
            @Override
            public void run() {
                vTurtleView.smaller(1.2);
            }
        });

        setVisible(true);
    }

    private void addAction(String text, Runnable action) {

        add(Box.createVerticalStrut(10));

        JButton button = UiFactory.button();
        button.setText(text);
        button.setAlignmentX(.5f);
        button.setMaximumSize(buttonSize);//BoxLayoutの時はMaximSizeで大きさを指定するんだって
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                action.run();
                vTurtleView.updateUI();
            }
        });
        add(button);
    }

}
