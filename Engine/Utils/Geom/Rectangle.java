package Engine.Utils.Geom;

import Engine.Components.RectCollider;

public class Rectangle extends Shape{
    
    public float x, y;
    public float width, height;

    public static Rectangle CreateRectFromCollider(RectCollider collider){

        return new Rectangle(collider.transform.position.x, 
        collider.transform.position.y, 
        collider.bounds.x, 
        collider.bounds.y);
    }

    public Rectangle(){

        this.x = 0;
        this.y = 0;
        this.width = 0;
        this.height = 0;
    }

    public Rectangle(float x, float y, float width, float height){

        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
    }

    @Override
    public boolean Contains(Vec2 point) {

        boolean intX = point.x > x && point.x < x + width;
        boolean intY = point.y > y && point.y < y + height;

        return intY && intX;
    }

    @Override
    public boolean Intersects(Shape other) {

        if(other instanceof Rectangle){
            
            //It's a Rectangle
            Rectangle rect = (Rectangle) other;
            
            Vec2 l2 = new Vec2(rect.x, rect.y), r2 = new Vec2(rect.x + rect.width, rect.y + rect.height);
            Vec2 l1 = new Vec2(x, y), r1 = new Vec2(x + width, y + height);

            if (l1.x > r2.x || l2.x > r1.x) 
                return false;   

            if (r1.y < l2.y || r2.y < l1.y) 
                return false;
     
            return true;

        }else{

            //It's a Circle
        }

        return false;
    }

    @Override
    public String toString(){

        return ("X: " + x + " | Y: " + y + " | W: " + width + " | H: " + height);
    }

    @Override
    public double area() {
     
        return width * height;
    }
}
