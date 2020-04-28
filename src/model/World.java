package model;

import model.floor.Floor;
import model.floor.FloorColor;

import java.util.ArrayList;
import java.util.List;

public class World {
    private List<Floor> mFloorList = new ArrayList<>();

    public World() {

    }

    public void addFloor(Floor mFloor) {
        mFloorList.add(mFloor);
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


}
