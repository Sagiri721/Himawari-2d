package Engine.Entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Node implements Serializable{
    
    public Set<Node> children = new HashSet<Node>();
    public Node parent = null;
    protected boolean connected = true;

    public Object object;
    protected Node(Object object){

        this.object = object;
        object.node = this;
    }

    protected Node(Object object, Node parent){

        this.object = object;
        this.parent = parent;
        object.node = this;
    }

    public void addChild(Object obj){

        Hierarchy.RemoveNode(obj);
        children.add(obj.node);
    }

    public void searchChildren(String name, Object child){

        for (Node node : children) {
            
            if(node.object.name == name){

                node.addChild(child);
            }
        }
    }

    public boolean isSupremeParent(){
        return parent == null;
    }

    public boolean isConnected(){

        return connected;
    }

    public void setConnected(boolean connected){

        this.connected = connected;
    }
}
