package Assets.Objects;

import java.awt.Graphics2D;

import Engine.Components.*;
import Engine.Entity.Object;
import Engine.Gfx.Animation;
import Engine.Gfx.Sprite;
import Engine.Input.Input;
import Engine.Utils.Renderer;
import Engine.Utils.StdBehaviour;
import Engine.Utils.Geom.Vec2;

public class Player extends Object implements StdBehaviour {

    public Player() {
        super("Player");
        objects.add(this);
    }

    ImageRenderer renderer;
    RectCollider collider;
    Object wall = Object.FindObject("Wall");
    Animator animator;

    boolean updateable = false;

    int am = 100;

    @Override
    public StdBehaviour getBehaviour() {

        return (StdBehaviour) this;
    }

    // Called once the object is initialized
    @Override
    public void Start() {

        Sprite img = new Sprite("player.png");
        Sprite player = new Sprite("player.png", 0, 0, 64, 64);
        renderer = new ImageRenderer(player, this);
        addComponent(renderer);

        Animation down = Sprite.createAnimation(img, 64, 64, 0, 0, true);
        Animation left = Sprite.createAnimation(img, 64, 64, 0, 64, true);
        Animation right = Sprite.createAnimation(img, 64, 64, 0, 64 + 64, true);
        Animation up = Sprite.createAnimation(img, 64, 64, 0, 64 + 64 + 64, true);

        Animation[] animations = { down, left, up, right };
        animator = new Animator(animations, renderer);
        animator.addTriggerAnimation(up, "up");
        animator.addTriggerAnimation(down, "down");

        animator.play(0, 0);
        addComponent(animator);

        collider = new RectCollider(transform, new Vec2(64, 64));
        addComponent(collider);

        updateable = true;

        setLayer(6);
    }

    // Called every frame
    @Override
    public void Update(float deltaTime) {

        if (updateable) {

            Vec2 dir = new Vec2(Input.axisX, Input.axisY);

            transform.translate(dir.times(100).times(deltaTime), collider);

            if (dir.x == -1) {
                animator.play(1, 0);
            } else if (dir.x == 1) {
                animator.play(3, 0);
            } else if (dir.y == 1) {
                animator.play(0, 0);
            } else {
                animator.play(2, 0);
            }

            if (dir.equals(Vec2.ZERO)) {
                animator.pause();
            }
        }
    }

    @Override
    public void DrawGUI(Graphics2D g) {

        //Widget.drawHealthBar(new Vec2(5,5), new Vec2(120, 20), am, 100, Color.WHITE, Color.black, Color.RED, Color.green, Direction.RIGHT, true, g);
        g.drawString("fps: " + Renderer.getFPS(), 0, 15);
    }

    @Override
    public void ReceiveMessage(String origin) {
        
    }
}
