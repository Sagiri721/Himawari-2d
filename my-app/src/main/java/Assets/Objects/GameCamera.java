package Assets.Objects;

import Engine.Components.Camera;
import Engine.Components.ImageRenderer;
import Engine.Components.Transform;
import Engine.Entity.Object;
import Engine.Utils.StdBehaviour;
import Engine.Utils.Window;
import Engine.Utils.Geom.Vec2;

import java.awt.Graphics2D;

public class GameCamera extends Object implements StdBehaviour{

    public GameCamera() { super("Camera");}
    @Override public StdBehaviour getBehaviour(){ return this; }

    Transform transformCamera;
    Transform targetTransform;

    @Override
    public void Start() {
        transformCamera = new Transform(this);

        Object player = Object.FindObject("Ball");

        Camera camera = new Camera(transformCamera, player);

        addComponent(transform);
        addComponent(camera);

        targetTransform = (Transform) player.getComponent("Transform");

        ImageRenderer sprite = (ImageRenderer) player.getComponent("ImageRenderer");

        if(sprite != null)
            Camera.setOffset(Window.getViewportCenter().subtractWith(new Vec2(sprite.getImage().getWidth()/2, sprite.getImage().getHeight()/2)));
    }

    @Override
    public void Update(float deltaTime) {

        if(targetTransform != null)
            transformCamera.position.setValues(targetTransform.position);
    }
    @Override
    public void DrawGUI(Graphics2D g) {

        
    }
    @Override
    public void ReceiveMessage(String origin) {}
    
}
