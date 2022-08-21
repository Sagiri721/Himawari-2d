import Assets.Objects.Ball;
import Assets.Objects.Wall;
import Engine.HimawariCore;

public class Main extends HimawariCore{

    public static void main(String[] args) {

        CreateWindow(500, 500, "Nice Game");

        new Ball();

        Wall wall = new Wall();

        wall.transform.setPosition(0, 350);
        wall.transform.setScale(20, 4);
    }
}
