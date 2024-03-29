package Engine.Physics;

import java.io.Serializable;

import Engine.Components.RectCollider;
import Engine.Entity.Object;
import Engine.Utils.Geom.Vec2;

public class RayHit implements Serializable {

    public Object collider = null;
    public RectCollider colliderComponent = null;
    public Vec2 point = null;
    public Vec2 normal = null;
    
    private Vec2 original = null;
    public Vec2 getOrigin() {return original;}

    protected RayHit(){}

    protected RayHit(Object collider, RectCollider component, Vec2 point, Vec2 original) {

        this.original = original;
        this.collider = collider;
        this.colliderComponent = component;
        this.point = point;

        this.normal = calculateNormalVector();
    }

    private Vec2 calculateNormalVector() {

        if(this.colliderComponent == null){

            return this.point.inverse();
        }else{

            return (this.point.sumWith(this.colliderComponent.bounds.divide(2))).subtractWith(this.point).normalize();
        }
    }

    public boolean isEmpty(){ return (collider == null || colliderComponent == null || point == null); }
}
