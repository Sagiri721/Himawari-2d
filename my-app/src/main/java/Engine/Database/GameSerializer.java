package Engine.Database;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Engine.Entity.Object;
import Engine.Gfx.Sprite;
import Engine.Map.RoomHandler;

public class GameSerializer {
 
    public static void serializeObjects(){

        ObjectOutputStream outputStream = null;
        String name = "savestate1";

        try {
            
            outputStream = new ObjectOutputStream(new FileOutputStream(Sprite.RelativeEngineResourcePath + "states/"+name+"-objects.dat"));
            outputStream.writeObject(Object.getObjects());

            outputStream.flush();
            outputStream.close();

            outputStream = new ObjectOutputStream(new FileOutputStream(Sprite.RelativeEngineResourcePath + "states/"+name+"-rooms.dat"));
            outputStream.writeObject(RoomHandler.getRooms());

            outputStream.flush();
            outputStream.close();

        } catch (IOException e) {
            
            e.printStackTrace();
        }
    }

    public static void deserializeData(){

        String name = "savestate1";
        try {
            
            FileInputStream fileInputStream = new FileInputStream(Sprite.RelativeEngineResourcePath + "states/"+name+"-objects.dat");
            ObjectInputStream in = new ObjectInputStream(fileInputStream);

            List<Object> objects = Arrays.asList((Object[]) in.readObject());

        } catch (Exception e) {
            
            e.printStackTrace();
        }
    }
}
