package ui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class UiFactory {
    public static final Font mainFont = new Font("メイリオ", Font.BOLD, 18);
    public static final Color white = new Color(240, 240, 240);
    public static final Color black = new Color(52, 56, 60);
    public static final Color red = new Color(213, 58, 47, 230);
    public static final Color blue = new Color(48, 124, 208, 230);
    public static final Color yellow = new Color(221, 195, 48, 230);
    public static final Color green = new Color(66, 181, 11, 230);
    public static final Color back = new Color(34, 34, 40);

    public static final Border smallEmptyBorder() {
        return BorderFactory.createEmptyBorder(10, 10, 10, 10);
    }

    public static final Border bigEmptyBorder() {
        return BorderFactory.createEmptyBorder(20, 20, 20, 20);
    }

    public static final JButton button() {
        JButton b = new JButton();
        b.setFont(mainFont);
        b.setBorder(smallEmptyBorder());
        b.setBackground(black);
        b.setForeground(white);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        return b;
    }

    public static final JLabel label() {
        JLabel l = new JLabel();
        l.setFont(mainFont);
        l.setOpaque(true);
        l.setBorder(smallEmptyBorder());
        l.setBackground(back);
        l.setForeground(white);
        return l;
    }

    public static final JTextField textField() {
        JTextField t = new JTextField();
        t.setFont(mainFont);
        t.setBorder(smallEmptyBorder());
        t.setBackground(black);
        t.setForeground(white);
        return t;
    }
}
