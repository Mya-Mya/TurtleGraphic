package model;

import model.floor.Floor;
import model.floor.FloorColor;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * カメ以外の要素のモデルを管理する。
 */
public class World {
    private List<WorldListener> mWorldListener=new ArrayList<>();
    private List<Floor> mFloorList = new ArrayList<>();
    private Image background=null;

    public World() {
        //テストコード
        addFloor(new Floor(new Point(10,40),new Point(40,70), FloorColor.BLACK));
        addFloor(new Floor(new Point(100,50),new Point(200,70),FloorColor.BLUE));
        addFloor(new Floor(new Point(50,200),new Point(120,300),FloorColor.GREEN));
        addFloor(new Floor(new Point(200,200),new Point(300,300),FloorColor.RED));
        addFloor(new Floor(new Point(300,300),new Point(400,400),FloorColor.YELLOW));
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
