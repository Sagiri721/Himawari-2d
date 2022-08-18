package Engine.Utils.Geom;

public class Vec2 {

    public static final Vec2 ZERO = new Vec2();
    public static final Vec2 LEFT = new Vec2(-1,0);
    public static final Vec2 RIGHT = new Vec2(1,0);
    public static final Vec2 UP = new Vec2(0,1);
    public static final Vec2 DOWN = new Vec2(0,-1);
    
    public float x, y;

    public Vec2(){

        this.x = 0;
        this.y = 0;
    }

    public Vec2(float x, float y){

        this.x = x;
        this.y = y;
    }

    public void setPosition(Vec2 newPosition) { x = newPosition.x; y = newPosition.y; }

    public Vec2 sumWith(Vec2 other){

        return new Vec2(other.x + x, other.y + y);
    }

    public Vec2 times(float n){

        return new Vec2(x * n, y * n);
    }

    public Vec2 times(Vec2 other){

        return new Vec2(x * other.x, y * other.y);
    }

    public Vec2 subtractWith(Vec2 other){

        return new Vec2(x - other.x, y - other.y);
    }

    public static float magnitude(Vec2 pointA, Vec2 pointB) {

        return (float) Math.sqrt((Math.pow(pointB.x - pointA.x, 2) + Math.pow(pointB.y - pointA.y, 2)));
    }

    public float magnitude(Vec2 point) {

        return (float) Math.sqrt((Math.pow(point.x - x, 2) + Math.pow(point.y - y, 2)));
    }

    public boolean equals(Vec2 pointB) { return (x == pointB.x && y == pointB.y);}

    public Vec2 abs() {return new Vec2(Math.abs(x), Math.abs(y)); }
}
