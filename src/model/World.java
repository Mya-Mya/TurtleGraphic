package model;

import model.floor.Floor;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class World {
    private List<WorldListener> mWorldListener=new ArrayList<>();
    private List<Floor> mFloorList = new ArrayList<>();
    private Image background=null;

    public World() {
    }

    public void addFloor(Floor mFloor) {
        mFloorList.add(mFloor);
    }

    public void addWorldListener(WorldListener l){
        mWorldListener.add(l);
    }

    public void fireWorldListener(){
        for (WorldListener l : mWorldListener) {
            l.onWorldChanged();
        }
    }

    public Image getBackground() {
        return background;
    }

    public List<Floor> getFloorList() {
        return mFloorList;
    }

    public Floor getFloorOn(double x, double y) {
        for (Floor mFloor : mFloorList) {
            if (mFloor.isOn(x, y)) {
                return mFloor;
            }
        }
        return null;
    }

    public void setBackground(Image background) {
        this.background = background;
    }
}
