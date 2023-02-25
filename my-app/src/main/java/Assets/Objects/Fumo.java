package Assets.Objects;

import java.awt.Color;
import java.awt.Graphics2D;

import Engine.Components.*;
import Engine.Entity.Object;
import Engine.Gfx.Animation;
import Engine.Gfx.Debugging;
import Engine.Gfx.Sprite;
import Engine.Input.Input;
import Engine.Utils.Path;
import Engine.Utils.Renderer;
import Engine.Utils.StdBehaviour;
import Engine.Utils.Geom.Vec2;

public class Fumo extends Object implements StdBehaviour {

    ImageRenderer renderer;
    Path path;

    public Fumo() {
        super("Gumo");
    }

    @Override
    public StdBehaviour getBehaviour() {

        return (StdBehaviour) this;
    }

    // Called once the object is initialized
    @Override
    public void Start() {

        Sprite s = new Sprite("fumo.png").getScaledSprite(new Vec2(64, 64));
        renderer = new ImageRenderer(s, this);
        addComponent(renderer);

        path = new Path(transform.position, new Vec2[] {new Vec2(0, 50), new Vec2(100, 100), new Vec2(150, 3)});
    }

    // Called every frame
    @Override
    public void Update(float deltaTime) {

    }

    @Override
    public void DrawGUI(Graphics2D g) {

        Debugging.setDebugColor(Color.BLACK);
        Debugging.debugPath(path, g);
    }

    @Override
    public void ReceiveMessage(String origin) {
        
    }
}