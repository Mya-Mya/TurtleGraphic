package bot;

import model.TurtleBehaviour;

public class TurtleBot {
    public TurtleBot(TurtleBehaviour turtle) {
        turtle.goStraight(200);
        turtle.turn(90);
        turtle.goStraight(50);
        turtle.larger(5);
        turtle.turn(90);
        turtle.smaller(5);
    }
}
