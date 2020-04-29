package bot;

import model.TurtleSimulator;
import model.floor.FloorColor;

public class TurtleBot {
    public TurtleBot(TurtleSimulator turtle) {
        turtle.goStraight(100);
        if (turtle.isOnAnyFloor()) {
            if (turtle.getFloorColorOn() == FloorColor.BLACK) {
                turtle.larger(2);
            }
            if (turtle.getFloorColorOn() == FloorColor.RED) {
                turtle.turn(360);
            }
            if (turtle.getFloorColorOn() == FloorColor.BLUE) {
                turtle.larger(10);
            }
            if (turtle.getFloorColorOn() == FloorColor.GREEN) {
                turtle.smaller(2);
            }
            if (turtle.getFloorColorOn() == FloorColor.YELLOW) {
                turtle.goStraight(1000);
            }
        }
    }
}
