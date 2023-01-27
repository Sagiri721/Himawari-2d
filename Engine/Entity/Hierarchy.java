package Engine.Entity;

import java.util.HashSet;
import java.util.Set;

public class Hierarchy {

    protected static Set<Node> hierarchy = new HashSet<Node>();
    
    public static void StartProjectHierarchy(){


    }

    public static void CreateHierarchyNode(Object obj){

        Node node = new Node(obj);
        hierarchy.add(node);
        obj.node = node;
    }

    public static void AddChildNodeTo(String name, Object child){

        for (Node node : hierarchy) {
            
            if(node.object.name == name){

                node.addChild(child);
            }

            node.searchChildren(name, child);
        }
    }

    public static void RemoveNode(Object object){

        hierarchy.remove(object.node);
    }
}
