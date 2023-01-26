package Engine.Database;

public class Cluster {
    
    private String name;
    private byte capacity;

    public String getName() {return name;}
    public byte getCapacity() {return capacity;}

    protected Cluster(String name, int capacity){

        this.name = name;
        this.capacity = (byte) capacity;
    }

    public boolean exists(){

        return true;
    }
}
