import java.awt.Color;

import Assets.Objects.GameCamera;
import Assets.Objects.Player;
import Assets.Objects.Wall;
import Engine.Components.ImageRenderer.scaleAlgorithm;
import Engine.Gfx.ImageUtil;
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
        Room room0 = new Room("fields", tileSet, new RoomData("room0"));
        room0.setBackground(ImageUtil.toBufferedImage(ImageUtil.resizeImage(100, 100, scaleAlgorithm.SMOOTH, new Sprite("a.jpg").sprite)), true, true, false);

        new Player();

        new Wall();
        new GameCamera();

        room0.loadObjects();
    }
}
