package Engine.Map;

import java.io.FileNotFoundException;

public class Room {
    
    public String name;
    public TileSet tileset;

    public RoomData roomData;

    public Room(String name, TileSet tileset, RoomData roomData) {

        this.tileset = tileset;
        this.name = name;
        this.roomData = roomData;

        RoomHandler.addRoom(this);
    }

    public void loadObjects() {

        if(roomData.hasObjectLayer()){
            
            try {
                roomData.unloadObjectLayer();
            } catch (NumberFormatException | FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
