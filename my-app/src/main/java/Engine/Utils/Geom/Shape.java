package Engine.Utils.Geom;

import java.io.Serializable;

public abstract class Shape implements Serializable{
    
    public abstract boolean Contains(Vec2 point);
    public abstract boolean Intersects(Shape other);
}
