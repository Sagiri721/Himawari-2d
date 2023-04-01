import java.awt.Color;

import Assets.Objects.Alarm1;
import Assets.Objects.Ball;
import Engine.HimawariCore;
import Engine.Database.Storage;
import Engine.Entity.Object;
import Engine.Gfx.Debugging;
import Engine.Gfx.ShaderPane;
import Engine.Gfx.Shaders.ShaderFactory;
import Engine.Map.Room;
import Engine.Map.RoomData;
import Engine.Networking.Client;
import Engine.Networking.LobbyEventListener;
import Engine.Networking.ServerConnection;
import Engine.Utils.Alarm;
import Engine.Utils.AlarmPack;
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

        Object obj = CreateObject("Wall", new Vec2(50, -50), 0, new Vec2(1,1));
        //Object objOther = CreateObject("WallChild", new Vec2(50, -100), 0, new Vec2(1,1));
        player.node.addChild(obj);
        //obj.node.addChild(objOther);
        //player.getComponent(RectCollider.class);


        //Object.sendMessageTo("Paredezinha", null);

        // Sound s = new Sound("mp3", false);
        // s.play();

        //Storage.put("Pontos", String.valueOf(1));

        //Storage.CreateCluster("newCluster", (byte) 4);
        //ShaderPane.LoadShader(ShaderFactory.createLightShader(1, 180, "Ball"));

        AlarmPack p = new AlarmPack(new Alarm1(), 1, true);
        Alarm.runAlarm(p);
    }
}
