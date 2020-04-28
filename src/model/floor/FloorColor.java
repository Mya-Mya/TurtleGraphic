package model.floor;

import ui.UiFactory;

import java.awt.*;

public enum FloorColor {
    BLACK,
    RED,
    BLUE,
    YELLOW,
    GREEN;

    public Color getSwingColor(){
        switch (this) {
            case BLACK:
                return UiFactory.black;
            case RED:
                return UiFactory.red;
            case BLUE:
                return UiFactory.blue;
            case GREEN:
                return UiFactory.green;
            case YELLOW:
                return UiFactory.yellow;
        }
        return UiFactory.back;
    }

}
