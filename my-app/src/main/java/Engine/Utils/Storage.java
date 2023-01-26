package Engine.Utils;

import java.sql.Statement;
import java.util.function.Function;

import Engine.Gfx.Sprite;
import javafx.util.Pair;

import java.io.ObjectInputFilter.Status;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Storage {

    public static enum STATUS {

        VALUE,
        EMPTY,
        UNIDENTIFIABLE,
        SUCCESS,
        ERROR
    }
    
    private static Connection conn = null;
    private static String url = "jdbc:sqlite:" + Sprite.RelativeEngineResourcePath + "storage.db";

    private static void connect() {

        try {
            
            conn = DriverManager.getConnection(url);

        } catch (SQLException e) {
            
            System.out.println("[ERROR] SQL Exception error\n"+e.getMessage());
        }
    }

    private static void close() {

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
        
        } catch (SQLException e) {
            e.printStackTrace();
            close();
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

            close();
            return STATUS.SUCCESS;
            
        } catch (SQLException e) {
            
            e.printStackTrace();
            close();
            return STATUS.ERROR;
        }
    }

    public static STATUS clear(){

        String sql = "DELETE FROM localStorage";
        connect();
        try(Statement stmt = conn.createStatement()) {
            stmt.execute(sql);

        }catch (SQLException e) {return STATUS.ERROR;}

        close();
        return STATUS.SUCCESS;
    }
}
