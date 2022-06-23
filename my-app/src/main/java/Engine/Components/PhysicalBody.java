package Engine.Components;

import Engine.Utils.GameMaths;
import Engine.Utils.Geom.Vec2;

public class PhysicalBody extends Component{

    public static float gravity = 9.8f;
    
    public float mass = 0;
    public Vec2 acceleration = new Vec2();

    public float velocity = 0;
    public float terminalVelocity = 2;

    public boolean simulated = true;
    public Vec2 FORCE = new Vec2();

    private RectCollider collider;
    private Transform transform;

    public PhysicalBody(float mass, Transform transform, RectCollider collider){
        
        this.mass = GameMaths.clamp(mass, 0.001f, 9000);
        
        this.transform = transform;
        this.collider = collider;
    }

    public void PhysicsUpdate(float deltaTime){

        //Set gravitical force
        acceleration = new Vec2(acceleration.x, acceleration.y + gravity);

        FORCE = acceleration.times(mass).times(velocity);

        int res = transform.translate(FORCE, collider);

        if(res == 1) acceleration = new Vec2(acceleration.x, 0);

        if(!acceleration.equals(Vec2.ZERO)){

            velocity += deltaTime/100;

            velocity = GameMaths.clamp(velocity, 0, 2);
        }else{velocity = 0;}

    }

    public void ApplyForce(Vec2 force){

        acceleration.sumWith(force);
    }
}
