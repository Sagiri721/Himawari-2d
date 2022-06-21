package Engine.Components;

import Engine.Entity.Object;
import Engine.Utils.Geom.Vec2;

public class Camera extends Component{
    
    public static Transform position;
    public static Object target;

    public static Camera gameCamera = null;  
    public static Camera getInstance() { return gameCamera; } 

    private static Vec2 offset = new Vec2();

    public Camera(Transform position, Object target){

        if(getInstance() != null) return;

        Camera.position = position;
        Camera.target = target;

        gameCamera = this;

    }

    public static Vec2 getOffset(){ return offset; }

    public static void setOffset(Vec2 offset){
        
        Camera.offset = offset;
        position.position.sumWith(offset);
    }
}
