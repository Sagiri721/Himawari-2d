package Engine.Entity;

import java.util.ArrayList;
import java.util.List;

import Engine.Components.*;

import Engine.Utils.*;

/**
 * Standard class for all the objects that take part in the game
 */
public class Object{

    /**
     * List of all existing objects at a certain point in time
     */
    public static List<StdBehaviour> behaviours = new ArrayList<StdBehaviour>();
    public static List<Object> objects = new ArrayList<Object>();

    public static int maxLayer = 0;

    //Unique identifier of the object
    protected int id = -1;
    protected String name = "";

    private int layer = 0;
    public void setLayer(int layer) { if(layer < 0 || layer > 100) return; if(layer > maxLayer) maxLayer = layer; this.layer = layer; }
    public int getLayer() { return this.layer; }

    /*
     * List of functional components
     */
    protected List<Component> components = new ArrayList<Component>();

    public void addComponent(Component component){ components.add(component); }

    public Component getComponent(String name){
        
        for(Component c : components){

            boolean isComponent = c.getClass().getName().equals("Engine.Components." + name);
            
            if(isComponent) return c;
        }

        return null;
    }

    //Is eligeble to Update?
    public boolean active = true;
    public void setActive(boolean active) { this.active = active; } //Is active on scene?

    private String tag = "";

     /*** A tag identifies a specific group of objects*/
     public void setTag(String tag) { this.tag = tag; }
    public String getTag() { return tag; }
    public boolean hasTag() { return tag != null || tag == ""; }

    //Constructor
    protected Transform transform;
    protected Object(String name){

        Transform transform = new Transform();
        addComponent(transform);

        this.transform = transform;
        id = objects.size();

        this.name = name;
    }

    /** 
     * Find an object by its idetifier
    */
    public static Object FindObject(int index) {

        return objects.get(index);
    }

    /**
     * Find an object by its name
     * @param name
     * @return the object
     */
    public static Object FindObject(String name) {

        for(Object obj : objects)  {

            if(obj.name.equals(name))
            return obj;
        }

        return null;
    }

    public int getId() { return id; }

    /**
     * Returns the first object to be added to the room with the given tag
     * @param oTag name of tag
     * @return the first object to be added
     */
    public static Object GetObjectWithTag(String oTag){

        for(Object obj : objects) {
            
           if(obj.hasTag() && obj.getTag().equals(oTag)) {

                return obj;
           }
        }

        return null;
    }

    /**
     * Returns a list of all objects added to the room with the specified tag
     * @param oTag name of the tag
     * @return list of objects
     */
    public static List<Object> GetObjectsWithTag(String oTag){

        List<Object> outObj = new ArrayList<Object>();

        for(Object obj : objects) {
            
           if(obj.hasTag() && obj.getTag().equals(oTag)) {

                outObj.add(obj);
           }
        }

        return outObj;
    }
}
