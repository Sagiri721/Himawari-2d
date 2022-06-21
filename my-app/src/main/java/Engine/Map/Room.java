package Engine.Map;

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
}
