package Assets.Objects;

import Engine.Entity.Object;
import Engine.Gfx.*;
import Engine.Input.Input;
import Engine.Map.TileSet;
import Engine.Utils.StdBehaviour;
import Engine.Utils.Geom.Vec2;
import Engine.Components.*;

import java.awt.Graphics2D;

public class WallChild extends Object implements StdBehaviour {

    public WallChild() {
        super("WallChild");
    }

    @Override
    public StdBehaviour getBehaviour() {
        return this;
    }
    boolean updateable = false;
    ImageRenderer rend;

    Fonts f, other;

    // Called once the object is initialized
    @Override
    public void Start() {
        Sprite sprite = new Sprite("square.png");
        rend = new ImageRenderer(sprite, this);

        setLayer(1);
        addComponent(rend);

        updateable = true;
    }

    @Override
    public void Update(float deltaTime) {

        if (updateable) {
       
            if(Input.mousePressed(0)){
                transform.rotate(10);
            }
        }
    }

    @Override
    public void DrawGUI(Graphics2D g) {

        // g.drawRect((int) collider.transform.position.x, (int)
        // collider.transform.position.y, (int) collider.bounds.x, (int)
        // collider.bounds.y);

    }

    @Override
    public void ReceiveMessage(String origin) { 

    }
}
