package view;

import ui.UiFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class PositionAndAngleSetting extends JDialog {
    private JPanel cCenterPanel;
    private JPanel cSouthPanel;
    private JTextField cXField;
    private JTextField cYField;
    private JTextField cAngleField;
    private boolean approved = false;

    public PositionAndAngleSetting(double defaultTurtleX, double defaultTurtleY, double defaultAngle) {
        super(JOptionPane.getRootFrame(), true);
        setLocationByPlatform(true);

        JLabel label = UiFactory.label();
        label.setText("カメの位置・角度を指定してください");
        label.setHorizontalAlignment(JLabel.CENTER);
        add(label, BorderLayout.NORTH);

        cCenterPanel = new JPanel();
        cCenterPanel.setBackground(UiFactory.back);
        cCenterPanel.setBorder(UiFactory.bigEmptyBorder());
        cCenterPanel.setLayout(new BoxLayout(cCenterPanel, BoxLayout.Y_AXIS));

        (cXField = UiFactory.textField()).setText(Double.toString(defaultTurtleX));
        addField("x座標", cXField);
        (cYField = UiFactory.textField()).setText(Double.toString(defaultTurtleY));
        addField("y座標", cYField);
        (cAngleField = UiFactory.textField()).setText(Double.toString(defaultAngle));
        addField("角度", cAngleField);

        add(cCenterPanel, BorderLayout.CENTER);

        cSouthPanel = new JPanel();
        cSouthPanel.setBackground(UiFactory.back);
        cSouthPanel.setBorder(UiFactory.bigEmptyBorder());
        cSouthPanel.setLayout(new BoxLayout(cSouthPanel, BoxLayout.Y_AXIS));

        addActionButton("OK(Enter)", KeyEvent.VK_ENTER, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                approved = true;
                setVisible(false);
            }
        });

        addActionButton("キャンセル(Esc)",KeyEvent.VK_ESCAPE, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                approved = false;
                setVisible(false);
            }
        });

        add(cSouthPanel, BorderLayout.SOUTH);

        setResizable(false);
        setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        pack();
        setVisible(true);
    }

    private void addField(String description, JTextField textField) {
        JPanel container = new JPanel();
        container.setBackground(UiFactory.back);
        container.setBorder(UiFactory.smallEmptyBorder());
        container.setLayout(new GridLayout(1, 2, 30, 0));

        JLabel label = UiFactory.label();
        label.setText(description);

        container.add(label);
        container.add(textField);

        cCenterPanel.add(container);
    }

    private void addActionButton(String text,Integer mnemonic, ActionListener action) {
        cSouthPanel.add(Box.createVerticalStrut(10));
        JButton button = UiFactory.button();
        button.setAlignmentX(.5f);
        button.setBackground(UiFactory.black);
        button.setText(text);
        button.setHorizontalAlignment(JButton.CENTER);
        button.setMaximumSize(new Dimension(200, 50));
        if(mnemonic!=null){
            button.setMnemonic(mnemonic);
        }
        button.addActionListener(action);
        cSouthPanel.add(button);
    }

    public double getTurtleX() {
        return Double.parseDouble(cXField.getText());
    }

    public double getTurtleY() {
        return Double.parseDouble(cYField.getText());
    }

    public double getTurtleAngle() {
        return Double.parseDouble(cAngleField.getText());
    }

    public boolean wasApproved() {
        return approved;
    }
}
