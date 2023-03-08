package Engine.Networking;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.util.concurrent.CompletionStage;

public class Client implements WebSocket.Listener {
    
    private MessageHandler handler;
    protected WebSocket ws;

    private String ID;
    protected void setClientID(String ID) {this.ID = ID;}
    public String getClientID() {return ID;}

    public Client(URI endPointURI) throws Exception{

        this.handler = new MessageHandler(this);
        ws = HttpClient.newHttpClient().newWebSocketBuilder().buildAsync(endPointURI, this).join();
    }

    public void onOpen(WebSocket webSocket) {

        System.out.println("[WEBSOCKET] Connection opened");
        WebSocket.Listener.super.onOpen(webSocket);
    }

    public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
        
        if(handler != null) handler.handleMessage(data.toString());
        return WebSocket.Listener.super.onText(webSocket, data, last);
    }

    public CompletionStage<?> onClose(WebSocket webSocket, int statusCode, String reason) {

        System.out.println("[WEBSOCKET] Connection closed | reason: " + reason);
        //Send closed client signal
        webSocket.sendText("closed:" + ID, true);

        return WebSocket.Listener.super.onClose(webSocket, 0, reason);
    }
}
