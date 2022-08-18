import java.awt.Color;

import Assets.Objects.GameCamera;
import Assets.Objects.Player;
import Assets.Objects.Wall;
import Engine.HimawariCore;
import Engine.Gfx.Debugging;
import Engine.Gfx.Sprite;
import Engine.Map.TileSet;
import Engine.Map.*;

public class Main extends HimawariCore{

    public static void main(String[] args) {

        CreateWindow(500, 500, "Nice Game");

        Sprite image = new Sprite("Grass.png");
        TileSet tileSet = new TileSet(image, 16, 16);
        Room room0 = new Room(tileSet, new RoomData("room0"));

        LoadRoom(room0);

        new Player();
        new GameCamera();
        new Wall();

        Debugging.drawColliders = true;
    }
}
