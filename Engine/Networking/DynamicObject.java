package Engine.Networking;

import java.util.List;

public class DynamicObject {
    
    public static DynamicObject selfPointer;
    public static List<DynamicObject> dynamicObjectObserver;

    private Object myObj;
    private String pointer;

    private DynamicObject(){}

    public static  DynamicObject createObject(Object object, String id) {

        DynamicObject d = new DynamicObject();
        d.myObj = object;
        d.pointer = id;

        dynamicObjectObserver.add(d);
        return d;
    }
}
