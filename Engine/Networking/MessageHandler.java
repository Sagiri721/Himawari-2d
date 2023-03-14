package Engine.Networking;

import Engine.Entity.Object;

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
                    
                    Object obj = null;
                    DynamicObject dyo = DynamicObject.createObject(obj, contents);

                    ServerConnection.listener.clientJoined(contents, dyo);

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
