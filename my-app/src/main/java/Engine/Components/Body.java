package Engine.Components;

import Engine.Entity.Object;
import Engine.Input.Input;
import Engine.Physics.Physics;
import Engine.Utils.Geom.Vec2;

public class Body extends Component{
    
    //Physics variables
    private float mass = 1;
    private Transform transform;
    private RectCollider collider;

    private Vec2 gravity = new Vec2();
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

        ApplyForce(gravity);

        boolean willCollide = collider.willCollide(transform.position.sumWith(totalForce));
        if(willCollide){

            ApplyForce(totalForce.inverse());
        }

        // Cap acceleration
        if(Physics.accelearion_capped) totalForce = totalForce.clampY(-Physics.acceleration_treshold, Physics.acceleration_treshold);

        transform.translate(totalForce.times(deltaTime).times(60), collider);   
        //System.out.println(totalForce.toString());
    }

    public float calculateAcceleration(){

        Vec2 a = totalForce.divide(mass);
        return a.x + a.y;
    }

    public void ApplyForce(Vec2 force){

        totalForce = totalForce.sumWith(force);
    }
}
