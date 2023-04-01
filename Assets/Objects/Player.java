package Assets.Objects;

import java.awt.Graphics2D;

import Engine.Utils.StdBehaviour;
import Engine.Entity.Object;
import java.awt.Graphics2D;
import Engine.Components.*;
import Engine.Gfx.*;
import Engine.Input.*;
import Engine.Map.*;
import Engine.Physics.*;
import Engine.Sound.*;
import Engine.Utils.*;
import Engine.Utils.Geom.*;

public class Player extends Object implements StdBehaviour{

    public Player() {
        super("Player");
    }

    /**
     * Called once the object is initialized
     */
    @Override
    public void Start() {
  
    }

    /**
     * Called once every frame
     */
    @Override
    public void Update(float deltaTime) {
        
        
    }

    /**
     * Draw your own graphics here
     */
    @Override
    public void DrawGUI(Graphics2D g) {
        
        
    }

    /**
     * Called from a different object, receives the object that sent the message
     */
    @Override
    public void ReceiveMessage(String origin) {
        
    }

    /**
     * DO NOT ERASE THIS METHOD
     */
    @Override
    public StdBehaviour getBehaviour() {
        return (StdBehaviour) this;
    }
    
    @Override
    public void RoomLoaded(Room room) {
    
    }
}
