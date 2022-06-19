package Assets.Objects;

import java.awt.Graphics2D;

import Engine.Components.*;
import Engine.Components.ImageRenderer.scaleAlgorithm;
import Engine.Entity.Object;
import Engine.Gfx.Sprite;
import Engine.Input.Input;
import Engine.Utils.StdBehaviour;
import Engine.Utils.Geom.Rectangle;
import Engine.Utils.Geom.Vec2;


public class Player extends Object implements StdBehaviour{

    public Player() { super("Player"); Object.behaviours.add(getBehaviour()); Object.objects.add(this); Start();}
    ImageRenderer renderer;
    RectCollider collider;
    Object wall = Object.FindObject("Wall");

    boolean updateable = false;

    private StdBehaviour getBehaviour(){

        return this;
    }

    //Called once the object is initialized
    @Override
    public void Start() {
        
        Sprite img = new Sprite("image.png");
        renderer = new ImageRenderer(img);
        addComponent(renderer);

        renderer.scaleSprite(90, 90, scaleAlgorithm.SMOOTH);

        transform.setScale(new Vec2(1, 1));

        collider = new RectCollider(transform, renderer.getDimensions());

        updateable = true;
    }

    //Called every frame
    @Override
    public void Update(float deltaTime) {

        if(updateable){

            Vec2 dir = new Vec2(Input.axisX, Input.axisY);

            transform.translate(dir.times(100).times(deltaTime), collider);

            if(dir.x > 0 && !renderer.isFlippedX)
                renderer.flipX();
        
            if(dir.x < 0 && renderer.isFlippedX)
                renderer.flipX();
        }
    }

    @Override
    public void DrawGUI(Graphics2D g) {

        //g.drawRect((int) collider.transform.position.x, (int) collider.transform.position.y, (int) collider.bounds.x, (int) collider.bounds.y);
    }
    
}
