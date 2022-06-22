
import java.awt.Color;

import Assets.Objects.GameCamera;
import Assets.Objects.Player;
import Assets.Objects.Wall;
import Engine.Gfx.Sprite;
import Engine.Map.TileSet;
import Engine.Utils.*;
import Engine.Map.*;

public class Main {
    
    public static void main(String[] args) {

        Window window = new Window();

        window.changeBackground(Color.black);
        window.initWindow(500, 500, "Nice Game!");

        Sprite image = new Sprite("Grass.png");
        TileSet tileSet = new TileSet(image, 16, 16);
        new Room("fields", tileSet, new RoomData("room0.txt"));

        new Wall();

        new Player();
        new GameCamera();
    }
}
