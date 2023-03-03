package Engine.Utils.Geom;

import java.io.Serializable;
import java.util.Comparator;

public class Vec2 implements Comparator<Vec2>, Serializable {

    public static final Vec2 ZERO = new Vec2();
    public static final Vec2 ONE = new Vec2(1, 1);
    public static final Vec2 LEFT = new Vec2(-1,0);
    public static final Vec2 RIGHT = new Vec2(1,0);
    public static final Vec2 UP = new Vec2(0,-1);
    public static final Vec2 DOWN = new Vec2(0,1);
    
    public float x, y;

    public Vec2(){

        this.x = 0;
        this.y = 0;
    }

    public Vec2(float x, float y){

        this.x = x;
        this.y = y;
    }

    public void setValues(Vec2 newPosition) { x = newPosition.x; y = newPosition.y; }
    public void setValues(float x, float y) { this.x = x; this.y = y; }

    //Arithmetic
    public Vec2 subtractWith(Vec2 other){ return new Vec2(x - other.x, y - other.y); }
    public Vec2 subtractWith(float other){ return new Vec2(x - other, y - other); }
    public Vec2 times(Vec2 other){ return new Vec2(x * other.x, y * other.y); }
    public Vec2 times(float n){ return new Vec2(x * n, y * n); }
    public Vec2 divide(Vec2 other){ return new Vec2(x / other.x, y / other.y); }
    public Vec2 divide(float n){ return new Vec2(x / n, y / n); }
    public Vec2 sumWith(Vec2 other){ return new Vec2(other.x + x, other.y + y); }
    public Vec2 sumWith(float other){ return new Vec2(other + x, other + y); }

    //Logic
    public boolean equals(Vec2 pointB) { return (x == pointB.x && y == pointB.y);}

    public boolean greaterThan(Vec2 vecB) { return (x+y > vecB.x + vecB.y); }
    public boolean greaterOrEqualTo(Vec2 vecB) { return (x+y >= vecB.x + vecB.y); }
    public boolean greaterThan(float vecB) { return (x > vecB && y > vecB); }
    public boolean greaterOrEqualTo(float vecB) { return (x >= vecB && y >= vecB); }

    public boolean lesserThan(Vec2 vecB) { return (x+y < vecB.x + vecB.y); }
    public boolean lesserOrEqualTo(Vec2 vecB) { return (x+y <= vecB.x + vecB.y); }
    public boolean lesserThan(float vecB) { return (x < vecB && y < vecB); }
    public boolean lesserOrEqualTo(float vecB) { return (x <= vecB && y <= vecB); }

    public Vec2 clampX(float min, float max){
        
        Vec2 newVec = new Vec2(x, y);
        if(newVec.x > max) newVec.x = max;
        if(newVec.x < min) newVec.x = min;

        return newVec;
    }

    public Vec2 clampY(float min, float max){
        
        Vec2 newVec = new Vec2(x, y);
        if(newVec.y > max) newVec.y = max;
        if(newVec.y < min) newVec.y = min;

        return newVec;
    }

    public Vec2 clamp(float min, float max){
        
        Vec2 newVec = new Vec2(x, y);
        if(newVec.x > max) newVec.x = max;
        if(newVec.x < min) newVec.x = min;
        if(newVec.x > max) newVec.x = max;
        if(newVec.x < min) newVec.x = min;

        return newVec;
    }

    public void shiftInDirection(Vec2 dir, float scale) {

        Vec2 normalization = dir.normalize().times(scale);
        x += normalization.x;
        y += normalization.y;
    }

    public Vec2 copy() {return new Vec2(x, y);}
    //Mathematics
    public Vec2 abs() {return new Vec2(Math.abs(x), Math.abs(y)); }
    public Vec2 inverse(){ return new Vec2(-x, -y); }

    public float magnitude(Vec2 point) { return (float) Math.sqrt((Math.pow(point.x - x, 2) + Math.pow(point.y - y, 2))); }
    public static float magnitude(Vec2 pointA, Vec2 pointB) { return (float) Math.sqrt((Math.pow(pointB.x - pointA.x, 2) + Math.pow(pointB.y - pointA.y, 2))); }
    public float thisMagnitude() { return (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)); }

    public Vec2 invertX(){ return new Vec2(-x, y); }
    public Vec2 invertY(){ return new Vec2(x, -y); }

    public Vec2 normalize() { return this.divide(thisMagnitude()); }

    public Vec2 getThisMidway(){ return this.divide(2); }
    public Vec2 getMidway(Vec2 other){ return (other.subtractWith(this).getThisMidway()); }
    //Misc
    @Override
    public String toString(){ return "X: " + x + " | Y: " + y; }

    @Override
    public int compare(Vec2 o1, Vec2 o2) {
        
        return Float.compare(o1.thisMagnitude(), o2.thisMagnitude());
    }
}
