package Engine.Components;

import Engine.Entity.Object;
import Engine.Map.RoomHandler;
import Engine.Utils.GameMaths;
import Engine.Utils.Window;
import Engine.Utils.Geom.Vec2;

public class Camera extends Component{
    
    public static Transform position;
    public static Object target;

    public static Camera gameCamera = null;  
    public static Camera getInstance() { return gameCamera; } 

    public static Vec2 ViewPort = null;

    private static Vec2 offset = new Vec2();

    public Camera(Transform position, Object target){

        if(getInstance() != null) return;

        Camera.position = position;
        Camera.target = target;

        gameCamera = this;

        calculateViewPort();
    }

    public static Vec2 getOffset(){ return offset; }

    public static void setOffset(Vec2 offset){
        
        Camera.offset = offset;
        position.position.sumWith(offset);
    }

    public static void calculateViewPort(){ 

        int w = Window.width;
        int h = Window.height;

        int cRoomTileSizeX = RoomHandler.getCurrentRoom().tileset.width;
        int cRoomTileSizeY = RoomHandler.getCurrentRoom().tileset.height;

        ViewPort = new Vec2((int)(w / cRoomTileSizeX), (int)(h / cRoomTileSizeY));
    }

    public static Vec2 getViewPortOffset() {

        int cRoomTileSizeX = RoomHandler.getCurrentRoom().tileset.width;
        int cRoomTileSizeY = RoomHandler.getCurrentRoom().tileset.height;

        Vec2 viewPos = new Vec2(GameMaths.clamp((position.position.y - offset.y) / cRoomTileSizeY, 0, RoomHandler.currentRoom.roomData.getHeight()), 
        GameMaths.clamp((position.position.x - offset.x) / cRoomTileSizeX, 0, RoomHandler.currentRoom.roomData.getWidth()));

        return viewPos;
    }
}
