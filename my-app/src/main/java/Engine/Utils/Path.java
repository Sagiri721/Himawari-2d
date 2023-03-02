package Engine.Utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import Engine.Components.Transform;
import Engine.Utils.Geom.Vec2;

public class Path implements Serializable {
 
    private Vec2 myPathPoint;
    private int myPointIndex = 0;
    private float distanceTraveled = 0;
    private boolean stopped = false;
    
    public boolean loop = true;
    public float speed = 1;
    
    private PathEndListener pathListener = null;
    private List<Vec2> knots;

    public Path(Vec2 origin, Vec2[] knots){
        this.knots = new ArrayList<Vec2>(Arrays.asList(knots)); 
        appendStartKnot(origin.copy());

        myPathPoint = origin;
    }

    public Path(Vec2[] knots){ this.knots = new ArrayList<Vec2>(Arrays.asList(knots)); myPathPoint = knots[0]; }
    public Path() { this.knots = new ArrayList<Vec2>(); }

    public void redefineKnots(Vec2[] knots){ this.knots = new ArrayList<Vec2>(Arrays.asList(knots)); }
    public List<Vec2> getKnots() {return knots;}

    public int pathSize(){ return knots.size(); }
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

    public void addPathEndListener(PathEndListener listener){ pathListener = listener; }
    public void removePathEndListener(){ pathListener = null; }

    public void invertPath(){

        Collections.reverse(knots);
        System.out.println(knots);
    }

    float segmentSize = -1;
    public void followPath(Transform t) {

        if(stopped) return;
        if(segmentSize==-1) {segmentSize = knots.get(myPointIndex).subtractWith(knots.get(myPointIndex+1)).thisMagnitude(); if(pathListener!=null)pathListener.pathStart(); }

        t.position = myPathPoint;

        Vec2 direction = knots.get(myPointIndex+1).subtractWith(knots.get(myPointIndex));
        myPathPoint.shiftInDirection(direction, speed);
        distanceTraveled += direction.normalize().times(speed).thisMagnitude();

        if(t.position.subtractWith(knots.get(myPointIndex)).thisMagnitude() >= segmentSize){ 
            
            if(myPointIndex == knots.size()-2){

                if(!loop){

                    stopped = true;
                    if(pathListener!=null) pathListener.pathEnd();
                    return;
                }
                myPathPoint = knots.get(0).copy();
                myPointIndex = 0;
                distanceTraveled = 0;
                segmentSize = knots.get(myPointIndex).subtractWith(knots.get(myPointIndex+1)).thisMagnitude();
                if(pathListener!=null) pathListener.pathEnd();
                return;
            }

            myPointIndex++;
            distanceTraveled = 0;
            myPathPoint = knots.get(myPointIndex).copy();
            segmentSize = knots.get(myPointIndex).subtractWith(knots.get(myPointIndex+1)).thisMagnitude();
            if(pathListener!=null) pathListener.pathMoved(myPathPoint);
        }
    }

    public void restartPath(){

        myPointIndex = 0;
        distanceTraveled = 0;
        myPathPoint = knots.get(0).copy();
        stopped = false;

        segmentSize = knots.get(myPointIndex).subtractWith(knots.get(myPointIndex+1)).thisMagnitude();
        if(pathListener!=null) pathListener.pathStart();
    }

    public float getRelativeDistanceTraveled(){return distanceTraveled; }
    public float getRelativeTotalDistanceTraveled(){

        if(myPointIndex==0) return distanceTraveled;
        float distance = distanceTraveled;
        for (int i = 1; i <= myPointIndex; i++) {
            
            distance += knots.get(i).subtractWith(knots.get(i-1)).thisMagnitude();
        }        

        return distance;
    }

    public void mergePaths(Path newPath, MergePathMethod method){

        switch (method) {
            case APPEND: knots.addAll(newPath.getKnots()); break;
            case INTERCALATE:

            try {
            
                Vec2[] vecs = new Vec2[newPath.pathSize() + pathSize()];

                boolean turn = false;
                Iterator<Vec2> iter1 = knots.iterator(), iter2 = newPath.getKnots().iterator();
                for (int i = 0; i < vecs.length; i++) {
                    
                    Vec2 value = Vec2.ZERO;
                    if(!turn){
                        
                        if(iter1.hasNext()) value = iter1.next();
                        else value = iter2.next();
                    }else {

                        if(iter2.hasNext()) value = iter2.next();
                        else value = iter1.next();
                    }

                    turn = !turn;
                    vecs[i] = value;
                }

                redefineKnots(vecs);

            } catch (Exception e) {
            
                e.printStackTrace();
            }
                break;
            case ORDER:

                knots.addAll(newPath.getKnots());
                for(int i = 0; i < pathSize() - 1; ++i) {
                    // swapping of element occurs here
                    if (knots.get(i).greaterThan(knots.get(i+1))) {
                        Vec2 temp = knots.get(i);
                        knots.set(i, knots.get(i + 1));
                        knots.set(i+1, temp);
                    }
                }
                
                break;
            case SUFFIX: knots.addAll(0, newPath.getKnots()); break;
            case JOINGREATER:
                
                for(int i = 0; i < Math.min(pathSize(), newPath.pathSize()); i++){
                    
                    if(newPath.getKnots().get(i).greaterThan(knots.get(i)))
                    knots.set(i, newPath.getKnots().get(i));
                }
            break;
            case JOINLESSER:

                for(int i = 0; i < Math.min(pathSize(), newPath.pathSize()); i++){
                        
                    if(newPath.getKnots().get(i).lesserThan(knots.get(i)))
                    knots.set(i, newPath.getKnots().get(i));
                }
            break;
            case INTERCEPTION:

                knots = knots.stream().filter(two -> newPath.getKnots().stream().anyMatch(one -> one.equals(two))).collect(Collectors.toList());
            break;
            default: mergePaths(newPath, MergePathMethod.APPEND); break;
        }

        restartPath();
    }
}
