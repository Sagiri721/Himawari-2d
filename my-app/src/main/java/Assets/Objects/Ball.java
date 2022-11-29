package Assets.Objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import Engine.Components.*;
import Engine.Components.ImageRenderer.scaleAlgorithm;
import Engine.Entity.Object;
import Engine.Gfx.Animation;
import Engine.Gfx.Debugging;
import Engine.Gfx.ImageUtil;
import Engine.Gfx.Sprite;
import Engine.Gfx.Widget;
import Engine.Input.Input;
import Engine.Input.Input.Keys;
import Engine.Utils.Renderer;
import Engine.Utils.StdBehaviour;
import Engine.Utils.Window;
import Engine.Utils.Geom.Vec2;

public class Ball extends Object implements StdBehaviour {

    Body b;
    RectCollider collider;
    boolean updateable = false;
    Animator animator;
    ImageRenderer renderer;

    public Ball() {
        super("Ball");
    }

    @Override
    public StdBehaviour getBehaviour() {

        return (StdBehaviour) this;
    }

    // Called once the object is initialized
    @Override
    public void Start() {

        Sprite image = new Sprite("mini idle.png", 0, 0, 32, 32);
        renderer = new ImageRenderer(image, this);
        renderer.scaleSprite(38, 59, scaleAlgorithm.SMOOTH);
        
        BufferedImage bimg = ImageUtil.resizeImage(640, 64, scaleAlgorithm.SMOOTH, Sprite.getBufferedImageFromFile("mini idle.png"));
        BufferedImage bimg2 = ImageUtil.resizeImage(640, 64, scaleAlgorithm.SMOOTH, Sprite.getBufferedImageFromFile("mini walk.png"));

        Animation idle = Sprite.createAnimation( new Sprite(bimg), 64, 64, 0, 0, true);
        Animation walk = Sprite.createAnimation(new Sprite(bimg2), 64, 64, 0, 0, true);
        Animation[] anims = {idle, walk};

        animator = new Animator(anims, renderer);
        animator.play(0, 0);
        animator.imageSpeed = 0.5f;

        addComponent(renderer);

        transform.setPosition(new Vec2(Window.width/2 - renderer.getImage().getWidth(), Window.height/2 - renderer.getImage().getHeight()));

        collider = new RectCollider(transform, new Vec2(64, 58));
        addComponent(collider);
        addComponent(animator);

        b = new Body(transform, collider, 1);
        addComponent(b);

        updateable = true;
    }

    // Called every frame
    @Override
    public void Update(float deltaTime) {

        if(updateable){

            boolean grounded = collider.willCollide(transform.position.sumWith(Vec2.DOWN));
            boolean moving = Input.axisX != 0;

            Vec2 movement = new Vec2(Input.axisX, 0);

            if(Input.isKeyPressed(Keys.Z) && grounded){
                b.ApplyForce(new Vec2(0, -20));
            }

            if(moving){

                animator.play(1, 0);
            }else{
                animator.play(0, 0);
            }

            if(Input.axisX < 0 && !renderer.isFlippedX){
                renderer.flipX();
            }
            if(Input.axisX > 0 && renderer.isFlippedX){
                renderer.flipX();
            }

            transform.translate(movement.times(4), collider);

            
            if(Input.mousePressed(0)){

                renderer.setAlpha(renderer.getAlpha() - 0.01f);
            }
        }
    }

    @Override
    public void DrawGUI(Graphics2D g) {

        //Debugging.drawDebugGrid(new Vec2(5, 5), new Vec2(100, 100), 5, 5, g);
        Debugging.setDebugColor(Color.black);
        Widget.drawText(String.valueOf(Renderer.getFPS()) + "fps", 5, 10, g);

        Widget.setColor(Color.PINK);
    }

    @Override
    public void ReceiveMessage(String origin) {
        
    }
}