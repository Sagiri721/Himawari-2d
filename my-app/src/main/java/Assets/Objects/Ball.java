package Assets.Objects;

import java.awt.Graphics2D;

import Engine.Components.*;
import Engine.Entity.Object;
import Engine.Gfx.Debugging;
import Engine.Gfx.Sprite;
import Engine.Input.Input;
import Engine.Input.Input.Keys;
import Engine.Utils.StdBehaviour;
import Engine.Utils.Window;
import Engine.Utils.Geom.Vec2;

public class Ball extends Object implements StdBehaviour {

    Body b;
    RectCollider collider;
    boolean updateable = false;

    public Ball() {
        super("Ball");
        Object.objects.add(this);
    }

    @Override
    public StdBehaviour getBehaviour() {

        return (StdBehaviour) this;
    }

    // Called once the object is initialized
    @Override
    public void Start() {

        Sprite image = new Sprite(1);
        ImageRenderer renderer = new ImageRenderer(image);

        addComponent(renderer);

        transform.setPosition(new Vec2(Window.width/2 - renderer.getImage().getWidth(), Window.height/2 - renderer.getImage().getHeight()));

        collider = new RectCollider(transform, renderer.getDimensions());
        addComponent(collider);

        b = new Body(transform, collider, 1);
        addComponent(b);

        updateable = true;
    }

    // Called every frame
    @Override
    public void Update(float deltaTime) {

        if(updateable){

            b.PhysicsUpdate(deltaTime);

            Vec2 movement = new Vec2(Input.axisX, 0);

            if(Input.mousePressed(0)){
                b.ApplyForce(new Vec2(0, -5));
            }

            transform.translate(movement.times(4), collider);
        }
    }

    @Override
    public void DrawGUI(Graphics2D g) {


        //Debugging.drawDebugGrid(new Vec2(5, 5), new Vec2(100, 100), 5, 5, g);
    }

    @Override
    public void ReceiveMessage(String origin) {
        
    }
}