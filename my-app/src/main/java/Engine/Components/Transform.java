package Engine.Components;

import Engine.Utils.Geom.Vec2;
import Engine.Entity.Object;

/**
 * The transform component hold information about position rotation and scale
 */
public class Transform extends Component{
    
    public Vec2 position;
    public float angle;
    public Vec2 scale;

    public Transform(){

        position = new Vec2();
        angle = 0;
        scale = new Vec2(1,1);
    }

    public Transform(Vec2 position){

        this.position = position;
    }

    public Transform(Vec2 position, float angle, Vec2 scale){

        this.position = position;
        this.angle = angle;
        this.scale = scale;
    }

    /**
     * Translates taking into account collision between solids
     * @param dir
     * @param collider
     */
    public void translate(Vec2 dir, RectCollider collider){

        if(collider == null) return;

        //Dont move if going to collide
        //Divide axis so we can collide with a single axis and keep moving
        Vec2 newPositionX = position.sumWith(new Vec2(dir.x, 0));
        Vec2 newPositionY = position.sumWith(new Vec2(0, dir.y)); 

        Vec2 newPosition = position.sumWith(dir);

        for(Object o : Object.objects){

            RectCollider c = (RectCollider) o.getComponent("RectCollider");
            if(c == null || c == collider || c.solid == false) continue;

            if(collider.willCollideWith(o, newPositionX))
                newPosition = newPositionY;
            
                if(collider.willCollideWith(o, newPositionY))
                newPosition = newPositionX;
        }

        position = newPosition;
    }

    public void translate(Vec2 dir){

        //Dont move if going to collide
        Vec2 newPosition = position.sumWith(dir);
        position = newPosition;
    }

    //////////////////////////////////////////////////////////////
    ////SETTERS
    /////////////////////////////////////////////////////////////
    public void setPosition(Vec2 pos){

        this.position = pos;
    }
    public void setPosition(int x, int y){

        this.position = new Vec2(x, y);
    }
    
    public void setScale(Vec2 scale){

        this.scale = scale;
    }

    public void setScale(int x, int y){

        this.scale = new Vec2(x, y);
    }

    public void setAngle(int angle){

        this.angle = angle;
    }
}
