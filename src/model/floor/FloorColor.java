package model.floor;

import ui.UiFactory;

import java.awt.*;

public enum FloorColor {
    BLACK,
    RED,
    BLUE,
    YELLOW,
    GREEN;

    public Color getSwingColor() {
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

    @Override
    public String toString() {
        switch (this) {
            case BLACK:
                return "黒";
            case RED:
                return "赤";
            case BLUE:
                return "青";
            case GREEN:
                return "緑";
            case YELLOW:
                return "黄";
        }
        return super.toString();
    }

    static FloorColor fromString(String s) {
        switch (s.toLowerCase()) {
            case "黒":
            case "black":
                return BLACK;
            case "赤":
            case "red":
                return RED;
            case "青":
            case "blue":
                return BLUE;
            case "緑":
            case "green":
                return GREEN;
            case "黄":
            case "yellow":
                return YELLOW;
        }
        return null;
    }
}
