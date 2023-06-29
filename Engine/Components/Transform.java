package Engine.Components;

import Engine.Utils.Geom.Vec2;
import Engine.Entity.Node;
import Engine.Entity.Object;
import Engine.Map.RoomHandler;

/**
 * The transform component hold information about position rotation and scale
 */
public class Transform extends Component{
    
    public Vec2 position;
    public float angle;
    public Vec2 scale;

    protected Object obj;

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
    public int translate(Vec2 dir, RectCollider collider) {

        if (collider == null)
            return 0;
        int res = 0;

        // Dont move if going to collide
        // Divide axis so we can collide with a single axis and keep moving
        Vec2 newPositionX = position.sumWith(new Vec2(dir.x, 0));
        Vec2 newPositionY = position.sumWith(new Vec2(0, dir.y));

        Vec2 newPosition = position.sumWith(dir);

        Object[] objs = Object.objects.toArray(new Object[Object.objects.size()]);
        for (Object o : objs) {

            RectCollider c = (RectCollider) o.getComponent(RectCollider.class);
            if (c == null || c == collider || c.solid == false)
                continue;

            if (collider.willCollideWith(o, newPositionX)) {
                res = 2;
                newPosition = newPositionY;
            }

            if (collider.willCollideWith(o, newPositionY)) {
                newPosition = newPositionX;
                res = 1;
            }
        }

        // Move children
        for (Node n : obj.node.children) {

            if (!n.isConnected())
                continue;

            Transform t = n.object.transform;
            RectCollider r = (RectCollider) n.object.getComponent(RectCollider.class);

            if (r == null) {
                t.translate(newPosition.subtractWith(position));
            } else
                t.translate(newPosition.subtractWith(position), r);
        }

        position = newPosition;
        collider.updateInterestStack();
        return res;
    }

    public void translate(Vec2 dir) {

        // Dont move if going to collide
        Vec2 newPosition = position.sumWith(dir);

        // Move children
        for (Node n : obj.node.children) {

            if (!n.isConnected())
                continue;

            RectCollider c = (RectCollider) n.object.getComponent(RectCollider.class);
            Transform t = n.object.transform;

            if (c == null)
                t.translate(dir);
            else
                t.translate(dir, c);
        }

        position = newPosition;
    }

    public void rotate(float angle) {

        setAngle(this.angle + angle);

        // Rotate children
        for (Node n : obj.node.children) {

            if (!n.isConnected())
                continue;

            Transform t = n.object.transform;
            t.rotate(angle);
        }
    }

    public void lookAt(Object target) {

        Transform targeTransform = target.transform;
        double h = targeTransform.position.magnitude(position);
        double x = targeTransform.position.y - position.y;

        double angle = Math.toDegrees(Math.asin(x / h));

        setAngle((float) angle);

        // Rotate children
        for (Node n : obj.node.children) {

            if (!n.isConnected())
                continue;

            Transform t = n.object.transform;
            t.lookAt(target);
        }
    }

    public void updateCollider() {

        RectCollider collider = (RectCollider) obj.getComponent(RectCollider.class);

        if (collider != null) {
            
            collider.resizeColliderSpecifics(scale.times(collider.bounds));
        }
    }

    public Vec2 getDeadCenter() {

        ImageRenderer renderer = (ImageRenderer) obj.getComponent(ImageRenderer.class);
        if (renderer == null) {

            RectCollider collider = (RectCollider) obj.getComponent(RectCollider.class);
            return collider == null ? position : position.sumWith(collider.bounds.divide(2));
        }

        return renderer == null ? position : position.sumWith(renderer.getDimensions().divide(2));
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
        
        
        if(this.angle > 360){

            this.angle = 0 + (this.angle-360);
            setAngle(this.angle);
        }else if(this.angle < 0){

            this.angle = 360 - (this.angle-360);
            setAngle(this.angle);
        }
    }
}
