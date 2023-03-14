package Engine.Networking;

public interface LobbyEventListener {
    
    public void onConnectionEstablished();
    public void onDisconnection();

    public void clientJoined(String id, DynamicObject object);
    public void clientLeft(String id);

    public void receivedMessage(String originID, String message);
}
