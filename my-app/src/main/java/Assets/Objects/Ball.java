package Assets.Objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import Engine.Components.*;
import Engine.Components.ImageRenderer.scaleAlgorithm;
import Engine.Database.GameSerializer;
import Engine.Entity.Object;
import Engine.Gfx.Animation;
import Engine.Gfx.Debugging;
import Engine.Gfx.FontMap;
import Engine.Gfx.Fonts;
import Engine.Gfx.ImageUtil;
import Engine.Gfx.ParticleEmitter;
import Engine.Gfx.Sprite;
import Engine.Gfx.Widget;
import Engine.Input.Input;
import Engine.Input.Input.Keys;
import Engine.Map.Room;
import Engine.Map.RoomData;
import Engine.Map.RoomHandler;
import Engine.Map.TileSet;
import Engine.Networking.Client;
import Engine.Networking.ServerConnection;
import Engine.Physics.Physics;
import Engine.Physics.RayHit;
import Engine.Utils.Renderer;
import Engine.Utils.StdBehaviour;
import Engine.Utils.Window;
import Engine.Utils.Geom.Vec2;

public class Ball extends Object implements StdBehaviour {

    public static String text = ":)";
    Body b;
    RectCollider collider;
    boolean updateable = false;
    Animator animator;
    ImageRenderer renderer;

    FontMap map;
    Fonts f;

    RectCollider shell = null;
    ParticleEmitter p;

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

        collider = new RectCollider(transform, new Vec2(64, 58));
        addComponent(collider);
        addComponent(animator);

        b = new Body(transform, collider, 1f);
        addComponent(b);

        updateable = true;

        setStatic(true);

        p = new ParticleEmitter(new Sprite(0), new Vec2(100,0), new Vec2(10, 5));
        p.speedRate = 0.05f;
        p.sizeRate = 0.01f;

        shell = (RectCollider) Object.FindObject("Fumo").getComponent(RectCollider.class);
        collider.ignoreTag("fumo");
        
        TileSet tile = new TileSet(new Sprite("tile.png"), 16, 16);
        map = new FontMap(tile);

        f = new Fonts(0, 0, true, map);
    }

    // Called every frame
    @Override
    public void Update(float deltaTime) {

        if(updateable){

            boolean grounded = collider.willCollide(transform.position.sumWith(Vec2.DOWN));
            boolean moving = Input.axisX != 0;

            Vec2 movement = new Vec2(Input.axisX, 0);

            if(Input.isKeyJustPressed(Keys.Z) && grounded){

                //System.out.println("?");
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

            //if(shell !=null && shell.isCollidingWith(collider)) 

            if(Input.isKeyJustPressed(Keys.H)) GameSerializer.serializeObjects();
            if(Input.isKeyJustPressed(Keys.J)) GameSerializer.deserializeData();

            if(Input.mousePressed(0)) {

                //ServerConnection.closeConnection();
                //Client.sendMessageToAllClients("OLAAAA");

                //renderer.setVisible(false);
            }

            transform.translate(movement.times(300).times(deltaTime), collider);
        }
    }

    @Override
    public void DrawGUI(Graphics2D g) {

        //Debugging.drawDebugGrid(new Vec2(5, 5), new Vec2(100, 100), 5, 5, g);
        Widget.drawText(String.valueOf(Renderer.getFPS()) + "fps", 5, 10, g);
        Widget.drawText(text, 5, 30, g);
        //Debugging.drawRay(Physics.CastRay(transform.position, Vec2.DOWN, 5), g);

        //System.out.println(hit.collider.getName());
        //Debugging.drawDebugLine(transform.position, transform.position.sumWith(Vec2.DOWN.times(408)), g);

        p.render(g);

        f.drawText("Miau auusa, sadhsadad fdsjfdsfg", g, new Vec2(20, 20));

        Debugging.drawDebugText("UAUAUAUA", 50, 50, g);
    }

    @Override
    public void ReceiveMessage(String origin) {
        
    }

    @Override
    public void RoomLoaded(Room room) {
        
    }
}