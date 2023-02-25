package Engine.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Engine.Utils.Geom.Vec2;

public class Path {
 
    private List<Vec2> knots;

    public Path(Vec2 origin, Vec2[] knots){ this.knots = new ArrayList<Vec2>(Arrays.asList(knots)); appendStartKnot(origin); }

    public Path(Vec2[] knots){ this.knots = new ArrayList<>(Arrays.asList(knots)); }
    public Path() { this.knots = new ArrayList<Vec2>(); }

    public void redefineKnots(Vec2[] knots){ this.knots = new ArrayList<Vec2>(Arrays.asList(knots)); }
    public Vec2[] getKnots() {return knots.toArray(new Vec2[knots.size()]);}

    public double getFullPathDistance() {

        if(knots==null || knots.size() == 0) return 0;

        double size = 0;
        for (int i = knots.size()-1; i > 0; i--) {
            
            size += knots.get(i).magnitude(knots.get(i-1));
        }

        return size;
    }

    public Vec2 getKnot(int index) {

        if(index >= knots.size() || index < 0) return null;
        return knots.get(index);
    }

    public void appendEndKnot(Vec2 knot) {knots.add(knot);}
    public void appendEndVertices(Vec2[] knot) {knots.addAll(Arrays.asList(knot));}

    public void appendStartKnot(Vec2 knot) {knots.add(0, knot);}
    public void appendStartVertices(Vec2[] knot) {knots.addAll(0, Arrays.asList(knot));}

    public void invertPath(){

        for (int i = knots.size()-1; i >= 0; i--) {
            
            knots.set((knots.size()-1) - i, knots.get(i));
        }
    }
}
