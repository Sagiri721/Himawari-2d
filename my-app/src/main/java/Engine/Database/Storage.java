package Engine.Database;

import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import Engine.Gfx.Sprite;
import javafx.util.Pair;

import java.io.ObjectInputFilter.Status;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Storage {

    public static Cluster localCluster = new Cluster("localStorage", 2);

    public static enum STATUS {

        VALUE,
        EMPTY,
        UNIDENTIFIABLE,
        SUCCESS,
        ERROR
    }
    
    protected static Connection conn = null;
    private static String url = "jdbc:sqlite:" + Sprite.RelativeEngineResourcePath + "storage.db";

    protected static void connect() {

        try {
            
            conn = DriverManager.getConnection(url);

        } catch (SQLException e) {
            
            System.out.println("[ERROR] SQL Exception error\n"+e.getMessage());
        }
    }

    protected static void close() {

        try {

            if(conn != null) {conn.close();}
            
        } catch (SQLException e) {
            
            System.out.println("[ERROR] SQL Exception error\n"+e.getMessage());
        }
    }

    public static STATUS put(String key, String value) {

        STATUS status = STATUS.UNIDENTIFIABLE;
        String sql = "INSERT INTO localStorage VALUES(?, ?)";

        //Before inserting check if already exists
        Pair<STATUS, String> p = get(key);
        if(p.getKey() == STATUS.VALUE){
            
            //Update
            sql = "UPDATE localStorage SET value = ? WHERE key = ?";
        }
        connect();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)){
            
            boolean order = sql.startsWith("INSERT");
            pstmt.setString(1, order ? key : value);
            pstmt.setString(2, !(order) ? key : value);

            pstmt.executeUpdate();

            logData("Added key: " + key + " | " + value + " to table: localStorage");
        
        } catch (SQLException e) {
            e.printStackTrace();
            close();

            logData("Failed to add key: " + key + " | " + value + " to table: localStorage, " + e.getMessage());
            return STATUS.ERROR;
        }

        close();
        return status;
    }

    public static Pair<Storage.STATUS, String> get(String key) {

        Pair<STATUS, String> status = new Pair<Storage.STATUS, String>(STATUS.UNIDENTIFIABLE, null);
        String sql = "SELECT value FROM localStorage WHERE key = '" + key + "'";
        connect();

        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql);) {

            fetchData: {

                while (rs.next()) { status = new Pair<Storage.STATUS, String>(STATUS.VALUE, rs.getString("value")); break fetchData;};
                status = new Pair<Storage.STATUS,String>(STATUS.EMPTY, "");
            }

        } catch (SQLException e) {
         
            System.out.println("[ERROR] SQL Exception error\n"+e.getMessage());
            logData("Failed to fetch key: " + key + " in table: localStorage, " + e.getMessage());
            status = new Pair<Storage.STATUS,String>(STATUS.ERROR, null);
        }

        close();
        return status;
    }

    public static STATUS flush(String key) {

        String sql = "DELETE FROM localStorage WHERE key = ?";
        connect();

        try(PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, key);
            pstmt.executeUpdate();

            logData("Flushed key: " + key + " in table: localStorage");
            close();
            return STATUS.SUCCESS;
            
        } catch (SQLException e) {
            
            e.printStackTrace();
            close();

            logData("Failed to flush key: " + key + " in table: localStorage, " + e.getMessage());
            return STATUS.ERROR;
        }
    }

    public static STATUS clear(){

        String sql = "DELETE FROM localStorage";
        connect();
        try(Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            logData("Cleared localStorage");

        }catch (SQLException e) {logData("Failed to clear localStorage, " + e.getMessage()); return STATUS.ERROR;}

        close();
        return STATUS.SUCCESS;
    }

    protected static void logData(String data) {

        String sql = "INSERT INTO logs(description, log_date) VALUES(?, ?)";
        connect();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)){
            
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();  

            pstmt.setString(1, data);
            pstmt.setString(2, dtf.format(now));

            pstmt.executeUpdate();
        
        } catch (SQLException e) {
            System.out.println("[ERROR] Error outputing to logs");
            e.printStackTrace();
        }

        close();
    }

    public static Cluster CreateCluster(String name, byte capacity){
        
        if(capacity <= 1) return null;

        Cluster cl = new Cluster(name, capacity);
        String sql = "CREATE TABLE " + name + " (?);";

        String values = "";
        for (int i = 0; i < capacity; i++) { values += ("value" + i) + " VARCHAR(200)" + (i+1 == capacity ? "" : ","); }

        sql = sql.replace("?", values);

        connect();
        try(Statement stmt = conn.createStatement()) {
            
            stmt.execute(sql);
            logData("Created cluster: " + name);

        }catch (SQLException e) {logData("Failed to create cluster: " + name + ", " + e.getMessage()); e.printStackTrace(); return null; }

        close();
        return cl;
    }

    public static void RunSQL(String sql) throws SQLException {

        connect();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.executeUpdate();
        close();
    }

    public static Cluster GetCluster(String name){

        //Find the capacity by selecting
        connect();

        int count = 0;
        try(Statement stmt = conn.createStatement()){

            ResultSet rs = stmt.executeQuery("SELECT * FROM name");
            //Get result set size
            count = rs.getMetaData().getColumnCount();

        }catch (SQLException e) {

            return null;
        }
        close();

        return new Cluster(name, count-1);
    }
}
