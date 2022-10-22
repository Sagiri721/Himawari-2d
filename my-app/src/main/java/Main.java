import java.awt.Color;

import Assets.Objects.Ball;
import Assets.Objects.Wall;
import Engine.HimawariCore;
import Engine.Entity.Object;
import Engine.Gfx.Sprite;
import Engine.Map.Room;
import Engine.Map.RoomData;
import Engine.Map.TileSet;
import Engine.Utils.AlarmPack;
import Engine.Utils.ObjectLoader;
import Engine.Utils.Window;
import Engine.Utils.Geom.Vec2;

public class Main extends HimawariCore{

    public static void main(String[] args) {

        CreateWindow(800, 600, "Nice Game");

        //CreateObject("Wall", new Vec2(-200, 400), 0, new Vec2(40, 7));
        //CreateObject("Wall", new Vec2(100, 300), 0, new Vec2(1,1));

        TileSet set = new TileSet(new Sprite("Grass.png"), 16, 16);
        Room r = new Room(set,  new RoomData("room1"));

        LoadRoom(r);

        CreateObject("Ball", new Vec2(40,0), 0, new Vec2(1, 1));
        CreateObject("GameCamera", Vec2.ZERO, 0, Vec2.ZERO);
    }
}
