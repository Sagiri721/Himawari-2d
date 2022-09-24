import java.awt.Color;

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
        window.getContentPane().setBackground(Color.cyan);

        CreateObject("Ball", new Vec2(Window.width/2,0), 0, new Vec2(1, 1));
        CreateObject("Wall", new Vec2(0, 400), 0, new Vec2(16, 5));
        CreateObject("Wall", new Vec2(100, 300), 0, new Vec2(1,1));
    }
}
