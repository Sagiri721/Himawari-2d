package Engine.Networking;

import java.net.URI;

public class ServerConnection {

    public static String connectionURL;
    private static Client connection = null;
    
    protected static LobbyEventListener listener = null;

    public static boolean isConnectionOpen(){
        return connection != null;
    }
    public static String getSessionID(){return connection.getClientID(); }

    public static void openConnection(String url, LobbyEventListener listener){

        if(connection != null){

            System.out.println("[CONNECTION WARNING] Connection already open");
            return;
        }

        String socketURI = "ws://" + url + "/lobby";
        
        try {
            
            final Client clientEndPoint = new Client(new URI(socketURI)) {};
            
            ServerConnection.connection = clientEndPoint;
            ServerConnection.connectionURL = socketURI;

            ServerConnection.listener = listener;

        } catch (Exception e) {
            
            System.out.println("[CONNECTION ERROR] There was a problem connecting to " + socketURI);
            e.printStackTrace();
        }
    }
    
    public static void closeConnection() {

        if(connection == null){

            System.out.println("[CONNECTION WARNING] Connection already closed");
            return;
        }

        listener.onDisconnection();
        connection.ws.sendText("closed:" + connection.getClientID(), true);
        connection.ws.abort();
        connection = null;
    }
}
