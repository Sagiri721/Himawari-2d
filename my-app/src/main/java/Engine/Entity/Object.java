package Engine.Entity;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import Engine.Components.*;

import Engine.Utils.*;
import Engine.Utils.Geom.Vec2;

/**
 * Standard class for all the objects that take part in the game
 */
public class Object implements Serializable{

    public Node node = null;

    /**
     * List of all existing objects at a certain point in time
     */
    transient public static List<Object> objects = new ArrayList<Object>();

    public static int maxLayer = 0;

    //Unique identifier of the object
    protected int id = -1;
    protected String name = "";
    private boolean staticObject = false;

    private int layer = 0;
    public void setLayer(int layer) { if(layer < 0 || layer > 100) return; if(layer > maxLayer) maxLayer = layer; this.layer = layer; }
    public int getLayer() { return this.layer; }

    public String getName() {return name;}
    public boolean isStatic() {return staticObject;}
    public void setStatic(boolean isStatic) {this.staticObject = isStatic; }

    /*
     * List of functional components
     */
    protected List<Component> components = new ArrayList<Component>();

    public int componentCount() {return components.size();}

    public void addComponent(Component component){ components.add(component); }

    @Deprecated
    public Component getComponent(String name){
        
        for(Component c : components){

            boolean isComponent = c.getClass().getName().equals("Engine.Components." + name);
            
            if(isComponent) return c;
        }

        return null;
    }

    public Component[] getComponents(){

        return components.toArray(new Component[components.size()]);
    }

    public Component getComponent(Class comp){

        for(Component c : components){

            if(c.getClass() == comp) return c;
        }

        return null;
    }

    public void setName(String name){
        this.name = name;
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
    public Transform transform;
    protected Object(String name){

        Hierarchy.CreateHierarchyNode(this);

        Transform transform = new Transform(this);
        addComponent(transform);

        this.transform = transform;
        id = objects.size();

        this.name = name.replace(" ", "-");

        if(nameExists(name)){

            autoNameChangeProtocol();
           System.out.println("[WARNING] The name '" + name + "' was already atributed to a different object, The new name of this object will be: " + this.name); 
        }

        objects.add(this);
    }

    private void autoNameChangeProtocol(){

        int index = 0;
        while (true) {
            
            String tempName = this.name + String.valueOf(index);

            if(!nameExists(tempName)){
                this.name = tempName;
                break;
            }
            index++;
        }
    }

    private boolean nameExists(String name){

        for(Iterator<Object> inter = objects.iterator(); inter.hasNext();)  {
            Object obj = inter.next();

            if(obj.name.equals(name)) return true;
        }

        return false;
    }

    public static Object[] getObjects() {return objects.toArray(new Object[objects.size()]); }

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

        name = name.replace(" ", "-");
        for(Iterator<Object> inter = objects.iterator(); inter.hasNext();)  {
            Object obj = inter.next();

            if(obj.name.equals(name))
            return obj;
        }

        return null;
    }

    /**
     * Given a certain component returns it's original object, mostly used in engine features and not of use to the average developer
     * 
     * @param component
     * @return
     */
    public static Object objectOfComponent(Component component){

        for(Iterator<Object> inter = objects.iterator(); inter.hasNext();)  {
            Object obj = inter.next();

            for(Component c : obj.components){

                if(c==component){
                    return obj;
                }
            }
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

        for(Iterator<Object> inter = objects.iterator(); inter.hasNext();)  {
            Object obj = inter.next();
            
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

        for(Iterator<Object> inter = objects.iterator(); inter.hasNext();)  {
            Object obj = inter.next();
            
           if(obj.hasTag() && obj.getTag().equals(oTag)) {

                outObj.add(obj);
           }
        }

        return outObj;
    }

    public StdBehaviour getBehaviour(){
        return null;
    }

    public static Object Instantiate(String name, Vec2 position, float angle, Vec2 scale){

        Object newObj = ObjectLoader.LoadObjectOfName(name, position, angle, scale);

        return newObj;
    }

    public static Object CreateObject(Class c, Vec2 position, float angle, Vec2 scale){

        if(c.getSuperclass() == Object.class){

            Object o;
            try {

                o = (Object) c.getDeclaredConstructor().newInstance();
                o.transform.position.setValues(position);
                o.transform.angle = angle;
                o.transform.scale.setValues(scale);

                return o;

            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException | NoSuchMethodException | SecurityException e) {
                
                System.out.println("[ERROR] The input class does not have an accessible constructor");
                e.printStackTrace();
                return null;
            }
        }   

        System.out.println("[ERROR] The input class does not match an object type");
        return null;
    }
    
    
    public static Object Instantiate(String name){

        Object newObj = ObjectLoader.LoadObjectOfName(name, new Vec2(0, 0), 0, new Vec2(0,0));
        return newObj;
    }

    public void DestroyInstance(){

        objects.remove(this);
    }

    public static void DestroyObject(Object obj){

        objects.remove(obj);
    }

    /**
     * Sends a call to the ReceiveMessage() function of the first object with the specified name
     * @param name
     */
    public static void sendMessageTo(String name, String message){

        for(Iterator<Object> inter = objects.iterator(); inter.hasNext();)  {
            Object obj = inter.next();
        
            if(obj.name.equals(name)){

                obj.getBehaviour().ReceiveMessage(message);
                return;
            }
        }
    }

    public static void sendMessageToAll(String className){

        for(Iterator<Object> inter = objects.iterator(); inter.hasNext();)  {
            Object obj = inter.next();
        
            /*if(obj.getClass().toString()==){

                obj.getBehaviour().ReceiveMessage(this.name);
            }*/
        }
    }

    public static void clearNonStatic(){

        Object[] copy = objects.toArray(new Object[objects.size()]);
        objects.clear();
        try {
         
            List<Object> toSave = Arrays.stream(copy).filter(obj -> obj.isStatic()).toList();
            objects.addAll(toSave);

        } catch (Exception e) {
            
            //Repair array
            objects.addAll(Arrays.asList(copy));
        }
    }
}
