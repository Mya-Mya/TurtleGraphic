package view;

import bot.TurtleBot;
import model.TurtleSimulator;
import model.World;
import ui.UiFactory;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;

public class ControllerPanel extends JPanel {
    private TurtleSimulator mTurtleSimulator;
    private World mWorld;
    private Dimension buttonSize = new Dimension(160, 30);

    public ControllerPanel(TurtleSimulator mTurtleSimulator, World mWorld) {
        super();
        this.mTurtleSimulator = mTurtleSimulator;
        setBorder(UiFactory.bigEmptyBorder());

        setBackground(UiFactory.back);
        setForeground(UiFactory.white);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        addComment("カメ");

        addActionButton("すすむ(↑)", KeyEvent.VK_UP, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mTurtleSimulator.goStraight(20);
            }
        });

        addActionButton("右回り(→)", KeyEvent.VK_RIGHT, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mTurtleSimulator.turn(45.);
            }
        });

        addActionButton("左回り(←)", KeyEvent.VK_LEFT, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mTurtleSimulator.turn(-45.);
            }
        });

        addActionButton("大きく", null, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mTurtleSimulator.larger(1.2);
            }
        });

        addActionButton("小さく", null, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mTurtleSimulator.smaller(1.2);

            }
        });

        addActionButton("位置指定(c)", KeyEvent.VK_C, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PositionAndAngleSetting setting = new PositionAndAngleSetting(
                        mTurtleSimulator.getSimulatedX(),
                        mTurtleSimulator.getSimulatedY(),
                        mTurtleSimulator.getSimulatedAngle()
                );
                if (setting.wasApproved()) {
                    mTurtleSimulator.setPosition(setting.getTurtleX(), setting.getTurtleY());
                    mTurtleSimulator.setAngle(setting.getTurtleAngle());

                }
            }
        });

        addComment("ロボットカメ");

        addActionButton("動かす", null, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TurtleBot(mTurtleSimulator);
            }
        });

        addComment("背景");

        Component parent = this;
        addActionButton("読み込み", null, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"));
                chooser.setFileFilter(new FileFilter() {
                    @Override
                    public boolean accept(File f) {
                        return f.isDirectory() ||
                                f.getName().endsWith(".png") ||
                                f.getName().endsWith(".jpg") ||
                                f.getName().endsWith(".jpeg");
                    }

                    @Override
                    public String getDescription() {
                        return "背景にしたい写真を選んでね";
                    }
                });
                if (chooser.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {
                    Image image = new ImageIcon(chooser.getSelectedFile().getAbsolutePath()).getImage();
                    if (image == null) return;
                    mWorld.setBackground(image);

                }
            }
        });

        setVisible(true);
    }

    private void addComment(String text) {
        add(Box.createVerticalStrut(5));

        JLabel label = UiFactory.label();
        label.setText(text);
        label.setHorizontalTextPosition(JLabel.CENTER);
        label.setAlignmentX(.5f);
        add(label);

        add(Box.createVerticalStrut(5));
    }

    private void addActionButton(String text, Integer mnemonic, ActionListener action) {
        JButton button = UiFactory.button();
        button.setText(text);
        button.setAlignmentX(.5f);
        button.setMaximumSize(buttonSize);//BoxLayoutの時はMaximSizeで大きさを指定するんだって
        button.addActionListener(action);
        button.setBorderPainted(false);
        if (mnemonic != null) {
            button.setMnemonic(mnemonic);
        }
        add(button);
        add(Box.createVerticalStrut(5));
    }

}
