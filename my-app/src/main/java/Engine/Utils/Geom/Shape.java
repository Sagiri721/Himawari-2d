package Engine.Utils.Geom;

public abstract class Shape {
    
    public abstract boolean Contains(Vec2 point);
    public abstract boolean Intersects(Shape other);
}
