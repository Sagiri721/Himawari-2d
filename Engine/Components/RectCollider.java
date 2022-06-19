package Engine.Components;

import Engine.Utils.Geom.Rectangle;
import Engine.Utils.Geom.Vec2;
import Engine.Entity.Object;

public class RectCollider extends Component{

    public Transform transform;
    public Vec2 bounds;

    public RectCollider(Transform transform, Vec2 bounds) {

        this.transform = transform;
        this.bounds = bounds;
    }

    public boolean isCollidingWith(Object obj) {

        for(Object o : Object.objects){

            if(o == obj){

                RectCollider r = (RectCollider) obj.getComponent("RectCollider");
                if(r != null){

                    Transform t = (Transform) obj.getComponent("Transform");
                    
                    Rectangle rect = new Rectangle(t.position.x, t.position.y, r.bounds.x, r.bounds.y);
                    Rectangle myRect = new Rectangle(transform.position.x, transform.position.y, bounds.x, bounds.y);

                    if(myRect.Intersects(rect))
                        return true;

                }else
                    return false; 

            }
        }

        return false;
    }

    public boolean willCollideWith(Object obj, Vec2 position){

        for(Object o : Object.objects){

            if(o == obj){

                RectCollider r = (RectCollider) obj.getComponent("RectCollider");
                if(r != null){

                    Transform t = (Transform) obj.getComponent("Transform");
                    
                    Rectangle rect = new Rectangle(t.position.x, t.position.y, r.bounds.x, r.bounds.y);
                    Rectangle myRect = new Rectangle(position.x, position.y, bounds.x, bounds.y);

                    if(myRect.Intersects(rect))
                        return true;

                }else
                    return false; 

            }
        }

        return false;
    }

    public boolean isCollidingWith(Vec2 point){

        Rectangle myRect = new Rectangle(transform.position.x, transform.position.y, bounds.x, bounds.y);
        return myRect.Contains(point);
    }
}
