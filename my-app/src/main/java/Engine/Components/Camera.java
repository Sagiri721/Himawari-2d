package Engine.Components;

import java.util.Iterator;

import Engine.Entity.Object;
import Engine.Map.RoomHandler;
import Engine.Utils.GameMaths;
import Engine.Utils.Renderer;
import Engine.Utils.Window;
import Engine.Utils.Geom.Vec2;

import java.awt.image.BufferedImage;

public class Camera extends Component{
    
    public static Transform position;
    public static Object target;

    public static Camera gameCamera = null;  
    public static Camera getInstance() { return gameCamera; } 

    private static Vec2 offset = new Vec2();
    public static Vec2 viewport = new Vec2(1,1);

    public Camera(Transform position, Object target){

        if(getInstance() != null) return;

        Camera.position = position;
        Camera.target = target;

        gameCamera = this;

        calculateOffset();
    }

    public static Vec2 getOffset(){ return offset; }

    public static void setOffset(Vec2 offset){
        
        Camera.offset = offset.divide(viewport);
        position.position = position.position.sumWith(offset);
    }

    public static void calculateOffset(){ 

        if(RoomHandler.getCurrentRoom() == null){

            System.out.println("[ERROR] Camera can't load because there is no room");
            return;
        }

        int w = Window.width;
        int h = Window.height;

        offset = new Vec2(w/2, h/2).times(viewport).times(Vec2.ONE.divide(Renderer.getViewportScale()));
    }

    public static Vec2 calculateIdealCameraOffset(){

        BufferedImage sprite = ((ImageRenderer) target.getComponent(ImageRenderer.class)).getImage();
        Transform tar = (Transform) target.transform;

        float centerX = (Window.width / 2) - (sprite.getWidth() * tar.scale.x);
        float centerY = (Window.height / 2) - (sprite.getHeight() * tar.scale.y);

        return new Vec2(centerX / offset.x, centerY / offset.y);
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

    public static Vec2 calculateWorldToWindowPosition(Vec2 windowPoint){

        if(Camera.getInstance() == null) return windowPoint;

        int x = (int)(((Camera.position.position.x) + windowPoint.x));
        int y = (int)(((Camera.position.position.y) + windowPoint.y));

        return new Vec2(-x, -y);
    }

    public static void setViewport(float x, float y) {

        viewport = viewport.times(new Vec2(x, y));
        offset = offset.divide(new Vec2(x, y));
    }
}
