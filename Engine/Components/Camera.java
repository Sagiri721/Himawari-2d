package Engine.Components;

import java.util.Iterator;

import Engine.Entity.Object;
import Engine.Map.RoomHandler;
import Engine.Utils.GameMaths;
import Engine.Utils.Window;
import Engine.Utils.Geom.Vec2;

import java.awt.image.BufferedImage;

public class Camera extends Component{
    
    public static Transform position;
    public static Object target;

    private static int size = 1;

    public static void setSize(int size){ 
        
        if(size <= 0){System.out.println("[ERROR] Invalid camera size");return;} 
        Camera.size = size; 

        for(Iterator<Object> objec = Object.objects.iterator(); objec.hasNext();){
            Transform t = (Transform) objec.next().getComponent("Transform");

            if(t!=null) t.updateCollider();
        }

        setOffset(Camera.calculateIdealCameraOffset());
    }
    public static int getSize() {return Camera.size; }

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

        if(RoomHandler.getCurrentRoom() == null){

            System.out.println("[ERROR] Camera can't load because there is no room");
            return;
        }

        int w = Window.width;
        int h = Window.height;

        int cRoomTileSizeX = RoomHandler.getCurrentRoom().tileset.sizeX;
        int cRoomTileSizeY = RoomHandler.getCurrentRoom().tileset.sizeY;

        ViewPort = new Vec2((int)(w / cRoomTileSizeX), (int)(h / cRoomTileSizeY));
    }

    public static Vec2 calculateIdealCameraOffset(){

        BufferedImage sprite = ((ImageRenderer) target.getComponent("ImageRenderer")).getImage();
        Transform tar = (Transform) target.getComponent("Transform");

        float centerX = (Window.width / 2) - (sprite.getWidth() * tar.scale.x);
        float centerY = (Window.height / 2) - (sprite.getHeight() * tar.scale.y);

        return new Vec2(centerX / size, centerY / size);
    }

    public static Vec2 getViewPortOffset() {

        if(RoomHandler.getCurrentRoom() == null){

            System.out.println("[ERROR] Camera can't load because there is no room");
            return null;
        }

        int cRoomTileSizeX = RoomHandler.getCurrentRoom().tileset.width;
        int cRoomTileSizeY = RoomHandler.getCurrentRoom().tileset.height;

        Vec2 viewPos = new Vec2(GameMaths.clamp((position.position.y - offset.y) / cRoomTileSizeY, 0, RoomHandler.currentRoom.roomData.getHeight()), 
        GameMaths.clamp((position.position.x - offset.x) / cRoomTileSizeX, 0, RoomHandler.currentRoom.roomData.getWidth()));

        return viewPos;
    }

    public static Vec2 calculateWindowTowindowPoint(Vec2 windowPoint){

        if(Camera.getInstance() == null) return windowPoint;

        int x = (int)((Camera.position.position.x * size) + windowPoint.x);
        int y = (int)((Camera.position.position.y * size) + windowPoint.y);

        return new Vec2(-x, -y);
    }

    public static Vec2 calculateWorldToWindowPosition(Vec2 windowPoint){

        if(Camera.getInstance() == null) return windowPoint;

        int x = (int)(windowPoint.x - Camera.position.position.x);
        int y = (int)(windowPoint.y - Camera.position.position.y);

        return new Vec2(-x, -y);
    }
}
