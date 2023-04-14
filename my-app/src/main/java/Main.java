
import Engine.HimawariCore;
import Engine.Components.Camera;
import Engine.Components.RectCollider;
import Engine.Entity.Object;
import Engine.Gfx.Debugging;
import Engine.Map.Room;
import Engine.Map.RoomData;
import Engine.Sound.Sound;
import Engine.Utils.Renderer;
import Engine.Utils.Geom.Vec2;

public class Main extends HimawariCore{

    public static void main(String[] args) {

        CreateWindow(800, 600, "Nice Game");

        //CreateObject("Wall", new Vec2(-200, 400), 0, new Vec2(40, 7));
        //CreateObject("Wall", new Vec2(100, 300), 0, new Vec2(1,1));

        //TileSet set = new TileSet(new Sprite("Grass.png"), 16, 16);
        Room r = new Room(null,  new RoomData("room1"));
        LoadRoom(r);
        
        CreateObject("Fumo", new Vec2(30, 30), 0, new Vec2(1,1));
        Object player= CreateObject("Ball", new Vec2(0, 0), 0, new Vec2(1, 1));
        CreateObject("GameCamera", Vec2.ZERO, 0, Vec2.ZERO);

        //Object objOther = CreateObject("WallChild", new Vec2(50, -100), 0, new Vec2(1,1));
        //obj.node.addChild(objOther);
        //player.getComponent(RectCollider.class);


        //Object.sendMessageTo("Paredezinha", null);

        // Sound s = new Sound("mp3", false);
        // s.play();

        //Storage.put("Pontos", String.valueOf(1));

        //Storage.CreateCluster("newCluster", (byte) 4);
        //ShaderPane.LoadShader(ShaderFactory.createLightShader(1, 180, "Ball"));

        Debugging.drawColliders = true;
        Renderer.colliderInterest = (RectCollider) player.getComponent(RectCollider.class);

        Camera.setViewport(1f, 1f);

        //Clock c = new Clock(100, ClockType.COUNTDOWN, ClockPrecision.SECONDS);
        //c.startClock();

    }
}
