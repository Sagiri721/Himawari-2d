package Engine.Networking;

public class MessageHandler {

    private Client myCLient;
    protected MessageHandler(Client client) { this.myCLient = client; }
    
    public void handleMessage(String message){

        if(message.contains(":")){

            String prefix = message.split(":")[0];
            String contents = message.substring(message.indexOf(":"));

            switch(prefix) {

                case "cid":{

                    //Set client id
                    myCLient.setClientID(contents);
                }
            }
        }else {

            System.out.println("[SERVER SIDE MESSAGE] could not interpret: " + message);
        }
    }
}
