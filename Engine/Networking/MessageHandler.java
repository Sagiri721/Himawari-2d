package Engine.Networking;

public class MessageHandler {

    private Client myCLient;
    protected MessageHandler(Client client) { this.myCLient = client; }
    
    public void handleMessage(String message){

        if(message.contains(":")){

            String prefix = message.split(":")[0];
            String contents = message.substring(message.indexOf(":")+1);

            switch(prefix) {

                case "cid":{

                    //Set client id
                    myCLient.setClientID(contents);
                    ServerConnection.listener.onConnectionEstablished();
                }

                case "join":

                    ServerConnection.listener.clientJoined(contents);
                break;
                case "left":

                    ServerConnection.listener.clientLeft(contents);
                break;
                default:

                    System.out.println("[SERVER SIDE MESSAGE ERROR] could not parse: " + message);
                break;
            }
        }else {

            System.out.println("[SERVER SIDE MESSAGE ERROR] could not interpret: " + message);
        }
    }
}
