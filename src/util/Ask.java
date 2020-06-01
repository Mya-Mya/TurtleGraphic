package util;

import ui.UiFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Ask extends JDialog {
    public static double doubleNumber() {
        Ask inst = new Ask();
        double ans = 0;
        try {
            ans = Double.parseDouble(inst.vAnswerTextField.getText());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return ans;
    }

    public static int intNumber() {
        Ask inst = new Ask();
        int ans = 0;
        try {
            ans = Integer.parseInt(inst.vAnswerTextField.getText());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return ans;
    }

    public static String text() {
        Ask inst = new Ask();
        String ans = inst.vAnswerTextField.getText();
        return ans;
    }

    private JTextField vAnswerTextField;

    public Ask() {
        super(JOptionPane.getRootFrame(), true);
        setTitle("Ask");
        setPreferredSize(new Dimension(500, 200));
        setLayout(new BorderLayout());
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {
                if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
                    ok();
                }
            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {

            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {

            }
        });

        vAnswerTextField = UiFactory.textField();
        vAnswerTextField.setBackground(UiFactory.back);
        add(vAnswerTextField, BorderLayout.CENTER);
        JButton button = UiFactory.button();
        button.setBackground(UiFactory.black);
        button.setText("OK");
        button.setMnemonic(KeyEvent.VK_ENTER);
        button.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Ask.this.ok();
            }
        });
        add(button, BorderLayout.EAST);

        pack();
        setVisible(true);
    }

    private void ok() {
        setVisible(false);
    }
}
