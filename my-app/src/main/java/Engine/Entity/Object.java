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

    //Unique identifier of the object
    protected int id = -1;
    protected String name = "";

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
     * @return
     */
    public static Object FindObject(String name) {

        for(Object obj : objects)  {

            if(obj.name.equals(name))
            return obj;
        }

        return null;
    }

    public int getId() { return id; }
}
