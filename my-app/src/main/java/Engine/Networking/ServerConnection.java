package Engine.Networking;

import java.net.URI;

public class ServerConnection {

    private static String connectionURL;
    private static Client connection;

    public static void openConnection(String url){

        String socketURI = "ws://" + url + "/lobby";
        
        try {
            
            final Client clientEndPoint = new Client(new URI(socketURI)) {};
            ServerConnection.connection = clientEndPoint;
            ServerConnection.connectionURL = socketURI;

        } catch (Exception e) {
            
            System.out.println("[CONNECTION ERROR] There was a problem connecting to " + socketURI);
            e.printStackTrace();
        }
    }
    
}
