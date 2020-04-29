package model;

import model.floor.Floor;
import model.floor.FloorColor;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * カメ以外の要素のモデルを管理する。
 */
public class World {
    private List<WorldListener> mWorldListener = new ArrayList<>();
    private List<Floor> mFloorList = new ArrayList<>();
    private Image background = null;

    public World() {
    }

    public void addFloor(Floor mFloor, boolean fireListener) {
        mFloorList.add(mFloor);
        if (fireListener) {
            fireWorldListener();
        }
    }

    public void addWorldListener(WorldListener l) {
        mWorldListener.add(l);
    }

    public void fireWorldListener() {
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

    public void loadFloor(File file) {
        mFloorList.clear();
        try {
            FileInputStream stream = new FileInputStream(file);
            InputStreamReader reader = new InputStreamReader(stream);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] splittedLine = line.split(",");
                int x0 = Integer.parseInt(splittedLine[0]);
                int y0 = Integer.parseInt(splittedLine[1]);
                int x1 = Integer.parseInt(splittedLine[2]);
                int y1 = Integer.parseInt(splittedLine[3]);
                String name = splittedLine[4];
                FloorColor color = FloorColor.fromString(name);

                addFloor(new Floor(new Point(x0, y0), new Point(x1, y1), color), false);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            fireWorldListener();
        }
    }

    public void setBackground(Image background) {
        this.background = background;
        fireWorldListener();
    }
}
