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

    public Vec2 pivotPoint = new Vec2();

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
    public int translate(Vec2 dir, RectCollider collider){

        if(collider == null) return 0;
        int res = 0;

        //Dont move if going to collide
        //Divide axis so we can collide with a single axis and keep moving
        Vec2 newPositionX = position.sumWith(new Vec2(dir.x, 0));
        Vec2 newPositionY = position.sumWith(new Vec2(0, dir.y)); 

        Vec2 newPosition = position.sumWith(dir);

        for(Object o : Object.objects){

            RectCollider c = (RectCollider) o.getComponent("RectCollider");
            if(c == null || c == collider || c.solid == false) continue;

            if(collider.willCollideWith(o, newPositionX)){
                res = 2;
                newPosition = newPositionY;
            }
            
            if(collider.willCollideWith(o, newPositionY)){
                newPosition = newPositionX;
                res = 1;
            }
        }

        position = newPosition;
        return res;
    }

    public void translate(Vec2 dir){

        //Dont move if going to collide
        Vec2 newPosition = position.sumWith(dir);
        position = newPosition;
    }

    public void rotate(float angle){

        setAngle(this.angle+angle);
    }

    public void lookAt(Object target){

        Transform targeTransform = (Transform) target.getComponent("Transform");
        double h = targeTransform.position.magnitude(position);
        double x = targeTransform.position.y - position.y;

        double angle = Math.toDegrees(Math.asin(x/h));

        setAngle((float)angle);
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

    public void setScale(float x, float y){

        this.scale = new Vec2(x, y);
    }

    public void setAngle(Float angle){

        this.angle = angle;
        
        /*
        if(this.angle > 360){

            this.angle = 0 + (this.angle-360);
            setAngle(this.angle);
        }else if(this.angle < 0){

            this.angle = 360 - (this.angle-360);
            setAngle(this.angle);
        }*/
    }

    public void setPivotPoint(Vec2 point){

        this.pivotPoint = point;
    }
}
