package Engine.Components;

import javax.lang.model.type.ExecutableType;

import Engine.Physics.Physics;
import Engine.Utils.Geom.Vec2;

public class Body extends Component{
    
    //Physics variables
    private float mass = 1;
    private Transform transform;
    private RectCollider collider;

    private Vec2 gravity = new Vec2(), externalForce = new Vec2(), normalForce = new Vec2();
    private Vec2 totalForce = new Vec2();

    public float drag = 0.1f;

    public Body(Transform transform, RectCollider collider, float mass){

        this.transform = transform;
        this.mass = mass;

        this.collider = collider;
    }

    public void PhysicsUpdate(float deltaTime){

        if(transform == null || mass <= 0 || collider == null ) return;

        gravity = Vec2.DOWN.times(Physics.G);

        boolean willCollide = collider.willCollide(transform.position.sumWith(gravity));
        normalForce = Vec2.UP.times(willCollide ? 1 : 0);
        normalForce = normalForce.times(Physics.G);

        totalForce = gravity.sumWith(normalForce).sumWith(externalForce);

        transform.translate(totalForce, collider);

        ApplyDrag();        

        if(externalForce.y < 0 && willCollide && calculateAcceleration() > 0)
        System.out.println("a");
    }

    private void ApplyDrag(){

        //System.out.println(externalForce.toString());

        if(externalForce.x > 0){
            externalForce = externalForce.subtractWith(new Vec2(drag, 0));
            if(externalForce.x < 0) externalForce.x = 0;
        }
        if(externalForce.x < 0){
            externalForce = externalForce.sumWith(new Vec2(drag, 0));
            if(externalForce.x > 0) externalForce.x = 0;
        }

        if(externalForce.y > 0){
            externalForce = externalForce.subtractWith(new Vec2(0, drag));
            if(externalForce.y < 0) externalForce.y = 0;
        }
        if(externalForce.y < 0){
            externalForce = externalForce.sumWith(new Vec2(0, drag));
            if(externalForce.y > 0) externalForce.y = 0;
        }
    }

    public float calculateAcceleration(){

        Vec2 a = totalForce.divide(mass);
        return a.x + a.y;
    }

    public void ApplyForce(Vec2 force){

        externalForce = externalForce.sumWith(force);
    }
}
