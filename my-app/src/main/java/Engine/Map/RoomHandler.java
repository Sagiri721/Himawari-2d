package Engine.Map;

import java.util.ArrayList;
import java.util.List;

import Engine.Components.Camera;
import Engine.Utils.Geom.Vec2;

import java.awt.Graphics2D;

public class RoomHandler {
    
    public static Room currentRoom;
    public static Room startRoom = null;

    //Room list
    private static List<Room> rooms = new ArrayList<Room>();

    public static void addRoom(Room room) { rooms.add(room); if(rooms.size() == 1) startRoom = room; currentRoom = room; }
    public static boolean hasRooms(){return startRoom != null;}

    public static Room getCurrentRoom(){return currentRoom;}
    public static Room getRoom(int index){return rooms.get(index);}

    public static int roomCount(){return rooms.size();}

    public static Vec2 viewportOffset = new Vec2(2,0);

    public static void render(Graphics2D g) {

        if(hasRooms()){

            if(Camera.getInstance() == null){

                for(int i = 0; i < currentRoom.roomData.getHeight(); i++){
                    for(int j = 0; j < currentRoom.roomData.getWidth(); j++) {

                        g.drawImage(currentRoom.tileset.getFrame(currentRoom.roomData.getTile(j, i)), 
                        j * currentRoom.tileset.width, 
                        i * currentRoom.tileset.height, 
                        null);

                    }
                }
            }else{

                /**
                 * To save a loot of memory, we need to just draw the camera viewport and not the entire map
                 */

                for(int i = (int) Camera.getViewPortOffset().x; i < Camera.ViewPort.x + (int) Camera.getViewPortOffset().x + viewportOffset.y; i++){
                    for(int j = (int) Camera.getViewPortOffset().y; j < Camera.ViewPort.y + (int) Camera.getViewPortOffset().y + viewportOffset.x; j++) {

                        if(j > currentRoom.roomData.getWidth()){
                            break;
                        }

                        g.drawImage(currentRoom.tileset.getFrame(currentRoom.roomData.getTile(j, i)), 
                        (int) (j * currentRoom.tileset.width - Camera.position.position.x + Camera.getOffset().x), 
                        (int) (i * currentRoom.tileset.height - Camera.position.position.y + Camera.getOffset().y), 
                null);
                    }

                    if(i > currentRoom.roomData.getHeight()){
                        break;
                    }
                }
            }
        }
    }
}
