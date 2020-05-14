package view;

import java.awt.*;
import java.awt.event.MouseEvent;

public interface MainViewMouseListener {
    void onMouseMoved(Point point);
    void onMouseClicked(MouseEvent e);
    void onMousePressed(MouseEvent e);
    void onMouseReleased(MouseEvent e);
}
