import Engine.HimawariCore;
import Engine.Components.RectCollider;
import Engine.Database.Storage;
import Engine.Entity.Object;
import Engine.Gfx.Debugging;
import Engine.Gfx.Sprite;
import Engine.Map.Room;
import Engine.Map.RoomData;
import Engine.Map.TileSet;
import Engine.Sound.Sound;
import Engine.Utils.Alarm;
import Engine.Utils.AlarmPack;
import Engine.Utils.Renderer;
import Engine.Utils.Geom.Vec2;
import javafx.util.Pair;

public class Main extends HimawariCore{

    public static void main(String[] args) {

        CreateWindow(800, 600, "Nice Game");
    }
}
