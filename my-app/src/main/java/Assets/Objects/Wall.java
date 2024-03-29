package Assets.Objects;

import Engine.Entity.Object;
import Engine.Gfx.*;
import Engine.Input.Input;
import Engine.Map.Room;
import Engine.Map.TileSet;
import Engine.Utils.StdBehaviour;
import Engine.Utils.Geom.Vec2;
import Engine.Components.*;

import java.awt.Graphics2D;

public class Wall extends Object implements StdBehaviour {

    public Wall() {
        super("Wall");
    }

    @Override
    public StdBehaviour getBehaviour() {
        return this;
    }

    RectCollider collider;
    boolean updateable = false;
    ImageRenderer rend;

    Fonts f, other;

    // Called once the object is initialized
    @Override
    public void Start() {
        Sprite sprite = new Sprite("square.png");

        rend = new ImageRenderer(sprite, this);

        collider = new RectCollider(transform, rend.getDimensions());

        setLayer(1);
        addComponent(rend);
        addComponent(collider);

        updateable = true;
    }

    @Override
    public void Update(float deltaTime) {

        if (updateable) {

        }
    }

    @Override
    public void DrawGUI(Graphics2D g) {

        // g.drawRect((int) collider.transform.position.x, (int)
        // collider.transform.position.y, (int) collider.bounds.x, (int)
        // collider.bounds.y);

    }

    @Override
    public void ReceiveMessage(String message) { 

        transform.rotate(45);
    }

    @Override
    public void RoomLoaded(Room room) {
     
    }
}
