package Engine.Components;

import Engine.Utils.Geom.Vec2;
import Engine.Entity.Node;
import Engine.Entity.Object;

/**
 * The transform component hold information about position rotation and scale
 */
public class Transform extends Component{
    
    public Vec2 position;
    public float angle;
    public Vec2 scale;

    public Vec2 pivotPoint = new Vec2();
    private Object obj;

    public Transform(Object o){

        position = new Vec2();
        angle = 0;
        scale = new Vec2(1,1);
        this.obj = o;
    }

    public Transform(Vec2 position, Object o){

        this.position = position;
        this.obj = o;
    }

    public Transform(Vec2 position, float angle, Vec2 scale, Object o){

        this.position = position;
        this.angle = angle;
        this.scale = scale;
        this.obj = o;
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
        
        //Move children
        for(Node n : obj.node.children){
            
            Transform t = (Transform) n.object.getComponent("Transform");
            RectCollider r = (RectCollider) n.object.getComponent("RectCollider");

            if(r == null){t.translate(newPosition.subtractWith(position));}else
                t.translate(newPosition.subtractWith(position), r);
        }

        position = newPosition;
        return res;
    }

    public void translate(Vec2 dir){

        //Dont move if going to collide
        Vec2 newPosition = position.sumWith(dir);
        position = newPosition;

        //Move children
        for(Node n : obj.node.children){
            
            Transform t = (Transform) n.object.getComponent("Transform");
            t.translate(dir);
        }
    }

    public void rotate(float angle){

        setAngle(this.angle+angle);

        //Rotate children
        for(Node n : obj.node.children){
            
            Transform t = (Transform) n.object.getComponent("Transform");
            t.rotate(angle);
        }
    }

    public void lookAt(Object target){

        Transform targeTransform = (Transform) target.getComponent("Transform");
        double h = targeTransform.position.magnitude(position);
        double x = targeTransform.position.y - position.y;

        double angle = Math.toDegrees(Math.asin(x/h));

        setAngle((float)angle);
        
        //Rotate children
        for(Node n : obj.node.children){
            
            Transform t = (Transform) n.object.getComponent("Transform");
            t.lookAt(target);
        }
    }

    public void updateCollider(){

        RectCollider collider =  (RectCollider) Object.objectOfComponent(this).getComponent("RectCollider");

        if(collider!=null)
            collider.resizeCollider(scale.times((Camera.getInstance() == null ? 1 : Camera.getSize())));
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
        updateCollider();
    }

    public void setScale(float x, float y){

        this.scale = new Vec2(x, y);
        updateCollider();
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
