package Engine;

import Engine.Map.Room;
import Engine.Map.RoomHandler;
import Engine.Utils.Window;

public class HimawariCore {

    private static Window window;
    
    public static void CreateWindow(int width, int height, String name){

        window = new Window();
        window.initWindow(width, height, name);
    }

    public static void LoadRoom(Room room){

        if(RoomHandler.startRoom == null){
            RoomHandler.startRoom = room;
            RoomHandler.currentRoom = room;
        }else{

            //Move to room
        }
        
        if(room.hasObjects())
            room.loadObjects();
    }

    public static void CloseWindow(){

        window.dispose();
        window = null;
    }

    public static void EndProgram(){

        window.dispose();
        window = null;

        System.out.println("[Execution Finished]");
        System.exit(0);
    }
}
