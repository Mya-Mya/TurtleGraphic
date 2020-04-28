package bot;

import model.TurtleSimulator;

public class TurtleBot {
    public TurtleBot(TurtleSimulator turtle) {
        turtle.goStraight(200);
        turtle.turnRight();
        turtle.goStraight(50);
        turtle.larger(5);
        turtle.turnRight();
        turtle.smaller(5);
    }
}
