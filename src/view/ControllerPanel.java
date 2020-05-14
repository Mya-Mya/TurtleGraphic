package view;

import bot.TurtleBot;
import model.TurtleSimulator;
import model.World;
import model.floor.Floor;
import model.floor.FloorColor;
import ui.UiFactory;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class ControllerPanel extends JPanel implements MainViewMouseListener {
    private JPanel pane;
    private TurtleSimulator mTurtleSimulator;
    private Floor mSpecialFloor;
    private World mWorld;
    private Dimension buttonSize = new Dimension(160, 25);

    public ControllerPanel(TurtleSimulator mTurtleSimulator, World mWorld, MainView vMainView) {
        super();
        this.mTurtleSimulator = mTurtleSimulator;
        this.mWorld = mWorld;

        setBackground(UiFactory.back);
        setBorder(UiFactory.smallEmptyBorder());

        setLayout(new BorderLayout());
        pane = new JPanel();
        pane.setBorder(UiFactory.bigEmptyBorder());
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
        pane.setBackground(UiFactory.back);


        addComment("位置指定");
        JTextField fieldX = addInput("x", String.valueOf(mTurtleSimulator.getSimulatedX()));
        JTextField fieldY = addInput("y", String.valueOf(mTurtleSimulator.getSimulatedY()));
        JTextField fieldAngle = addInput("角度", String.valueOf(mTurtleSimulator.getSimulatedAngle()));
        JTextField fieldSize = addInput("大きさ", String.valueOf(mTurtleSimulator.getSimulatedSize()));
        addActionButton("実行(c)", KeyEvent.VK_C, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double x = Double.parseDouble(fieldX.getText());
                double y = Double.parseDouble(fieldY.getText());
                double angle = Double.parseDouble(fieldAngle.getText());
                double size = Double.parseDouble(fieldSize.getText());
                mTurtleSimulator.setPosition(x, y);
                mTurtleSimulator.setAngle(angle);
                mTurtleSimulator.setSize(size);
            }
        });

        addComment("ロボットカメ");

        addActionButton("動かす", null, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        new TurtleBot(mTurtleSimulator);
                    }
                }).start();
            }
        });

        addComment("背景");

        Component parent = this;
        addActionButton("背景画像(b)", KeyEvent.VK_B, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = createFileChooser(new String[]{".png", ".jpg", "jpeg"}, "背景にしたい画像を選んでね");
                if (chooser.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {
                    Image image = new ImageIcon(chooser.getSelectedFile().getAbsolutePath()).getImage();
                    if (image == null) return;
                    mWorld.setBackground(image);

                }
            }
        });

        addActionButton("床データ(f)", KeyEvent.VK_F, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = createFileChooser(new String[]{".txt", ".csv"}, "持ってきたい床のデータを選んでね");
                if (chooser.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {
                    mWorld.loadFloor(chooser.getSelectedFile());
                }
            }
        });

        addComment("カメ");

        addActionButton("すすむ(↑)", KeyEvent.VK_UP, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mTurtleSimulator.goStraight(100);
                    }
                }).start();
            }
        });

        addActionButton("右回り(→)", KeyEvent.VK_RIGHT, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mTurtleSimulator.turn(45.);
                    }
                }).start();
            }
        });

        addActionButton("左回り(←)", KeyEvent.VK_LEFT, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mTurtleSimulator.turn(-45.);
                    }
                }).start();
            }
        });

        addActionButton("大きく", null, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mTurtleSimulator.larger(1.2);
                    }
                }).start();
            }
        });

        addActionButton("小さく", null, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mTurtleSimulator.smaller(1.2);
                    }
                }).start();
            }
        });

        JScrollPane scrollPane = UiFactory.scrollPane();
        scrollPane.setViewportView(pane);
        add(scrollPane, BorderLayout.CENTER);
        vMainView.addMainViewMouseListener(this);
        setVisible(true);
    }

    /**
     * ファイルを選ばせる`JFileChooser`を生成する。
     *
     * @param extensions  一覧に表示させるファイルの拡張子または末尾の文字列
     * @param description 説明文
     * @return
     */
    private JFileChooser createFileChooser(String[] extensions, String description) {
        String currentDirectory = System.getProperty("user.dir");
        JFileChooser chooser = new JFileChooser(currentDirectory);
        FileFilter filter = new FileFilter() {
            @Override
            public boolean accept(File f) {
                if (f.isDirectory()) return true;
                String name = f.getName();
                for (String extension : extensions) {
                    if (name.endsWith(extension)) return true;
                }
                return false;
            }

            @Override
            public String getDescription() {
                return description;
            }
        };
        chooser.setFileFilter(filter);
        chooser.setDialogTitle(description);
        return chooser;
    }

    private JTextField addInput(String description, String initialText) {
        JPanel panel = new JPanel();
        panel.setBackground(UiFactory.back);
        panel.setLayout(new GridLayout(1, 2));
        JLabel label = UiFactory.label();
        label.setText(description);
        JTextField field = UiFactory.textField();
        field.setText(initialText);
        panel.add(label);
        panel.add(field);

        pane.add(panel);
        pane.add(Box.createVerticalStrut(5));
        return field;
    }

    private void addComment(String text) {
        pane.add(Box.createVerticalStrut(5));

        JLabel label = UiFactory.label();
        label.setText(text);
        label.setHorizontalTextPosition(JLabel.CENTER);
        label.setAlignmentX(.5f);
        pane.add(label);

        pane.add(Box.createVerticalStrut(5));
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
        pane.add(button);
        pane.add(Box.createVerticalStrut(5));
    }

    @Override
    public void onMouseMoved(Point point) {

    }

    @Override
    public void onMouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    mTurtleSimulator.setPosition(e.getX(), e.getY());
                }
            }).start();
        }
    }

    @Override
    public void onMousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            Point p1 = e.getPoint();
            Point p2 = new Point(p1);
            int l = 50;
            p1.translate(-l, -l);
            p2.translate(l, l);
            mSpecialFloor = new Floor(p1, p2, FloorColor.BLACK);
            mWorld.addFloor(mSpecialFloor, true);
        }
    }

    @Override
    public void onMouseReleased(MouseEvent e) {
        if (mSpecialFloor != null) {
            mWorld.removeFloor(mSpecialFloor);
        }
    }
}
