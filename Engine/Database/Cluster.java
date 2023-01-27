package Engine.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Engine.Database.Storage.STATUS;
import javafx.util.Pair;

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

    public STATUS put(String key, String value) {

        STATUS status = STATUS.UNIDENTIFIABLE;
        String sql = "INSERT INTO "+this.name+" VALUES(?, ?)";

        //Before inserting check if already exists
        Pair<STATUS, String> p = get(key);
        if(p.getKey() == STATUS.VALUE){
            
            //Update
            sql = "UPDATE "+this.name+" SET value = ? WHERE key = ?";
        }
        Storage.connect();

        try (PreparedStatement pstmt = Storage.conn.prepareStatement(sql)){
            
            boolean order = sql.startsWith("INSERT");
            pstmt.setString(1, order ? key : value);
            pstmt.setString(2, !(order) ? key : value);

            pstmt.executeUpdate();

            Storage.logData("Added key: " + key + " | " + value + " to table: "+this.name);
        
        } catch (SQLException e) {
            e.printStackTrace();
            Storage.close();

            Storage.logData("Failed to add key: " + key + " | " + value + " to table: "+this.name+", " + e.getMessage());
            return STATUS.ERROR;
        }

        Storage.close();
        return status;
    }

    public Pair<Storage.STATUS, String> get(String key) {

        Pair<STATUS, String> status = new Pair<Storage.STATUS, String>(STATUS.UNIDENTIFIABLE, null);
        String sql = "SELECT value FROM "+this.name+" WHERE key = '" + key + "'";
        Storage.connect();

        try (Statement stmt = Storage.conn.createStatement(); ResultSet rs = stmt.executeQuery(sql);) {

            fetchData: {

                while (rs.next()) { status = new Pair<Storage.STATUS, String>(STATUS.VALUE, rs.getString("value")); break fetchData;};
                status = new Pair<Storage.STATUS,String>(STATUS.EMPTY, "");
            }

        } catch (SQLException e) {
         
            System.out.println("[ERROR] SQL Exception error\n"+e.getMessage());
            Storage.logData("Failed to fetch key: " + key + " in table: "+this.name+", " + e.getMessage());
            status = new Pair<Storage.STATUS,String>(STATUS.ERROR, null);
        }

        Storage.close();
        return status;
    }

    public STATUS flush(String key) {

        String sql = "DELETE FROM "+this.name+" WHERE key = ?";
        Storage.connect();

        try(PreparedStatement pstmt = Storage.conn.prepareStatement(sql)) {

            pstmt.setString(1, key);
            pstmt.executeUpdate();

            Storage.logData("Flushed key: " + key + " in table: "+this.name+"");
            Storage.close();
            return STATUS.SUCCESS;
            
        } catch (SQLException e) {
            
            e.printStackTrace();
            Storage.close();

            Storage.logData("Failed to flush key: " + key + " in table: "+this.name+", " + e.getMessage());
            return STATUS.ERROR;
        }
    }

    public STATUS clear(){

        String sql = "DELETE FROM "+this.name+"";
        Storage.connect();
        try(Statement stmt = Storage.conn.createStatement()) {
            stmt.execute(sql);
            Storage.logData("Cleared localStorage");

        }catch (SQLException e) {Storage.logData("Failed to clear "+this.name+", " + e.getMessage()); return STATUS.ERROR;}

        Storage.close();
        return STATUS.SUCCESS;
    }
}
