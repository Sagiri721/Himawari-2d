import Assets.Objects.Ball;
import Assets.Objects.Wall;
import Engine.HimawariCore;
import Engine.Entity.Object;
import Engine.Utils.ObjectLoader;
import Engine.Utils.Window;
import Engine.Utils.Geom.Vec2;

public class Main extends HimawariCore{

    public static void main(String[] args) {

        CreateWindow(500, 500, "Nice Game");

        /*
          new Ball();

        Wall wall = new Wall(), wall2 = new Wall();

        wall.transform.setPosition(0, 350);
        wall.transform.setScale(20, 4);

        wall2.transform.setPosition(0,0);
        wall2.transform.setScale(1,8);
         */

        CreateObject("Wall", new Vec2(0, 400), 0, new Vec2(15, 1));
        CreateObject("Ball", new Vec2(Window.width/2,0), 0, new Vec2(1, 1));
    }
}
