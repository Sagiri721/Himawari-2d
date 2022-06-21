package Engine.Utils.Geom;

public class Circle extends Shape{
    
    public float x, y;
    public float radius;

    public Circle(float x, float y, float radius) {

        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    @Override
    public boolean Contains(Vec2 point) {

        Vec2 center = new Vec2(x, y);
        float mag = Vec2.magnitude(point, center);

        return mag <= radius;
    }

    @Override
    public boolean Intersects(Shape other) {

        if(other instanceof Circle) {

            //Circle
            Circle circ = (Circle) other;

            float sumR = (circ.radius + radius);
            float dist = Vec2.magnitude(new Vec2(x, y), new Vec2(circ.x, circ.y));

            return dist <= sumR;

        }else{

            //Rectangle
        }

        return false;
    }
}
