package Engine.Physics;

import Engine.Components.RectCollider;
import Engine.Entity.Object;
import Engine.Utils.Geom.Vec2;

public class RayHit {

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

        return null;
    }

    public boolean isEmpty(){

        return (collider == null || colliderComponent == null || point == null);
    }
}
