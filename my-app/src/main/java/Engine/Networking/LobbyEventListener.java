package Engine.Networking;

public interface LobbyEventListener {
    
    public void onConnectionEstablished();
    public void onDisconnection();

    public void clientJoined(String id);
    public void clientLeft(String id);
}
