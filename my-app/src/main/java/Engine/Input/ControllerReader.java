package Engine.Input;

import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import net.java.games.input.Event;
import net.java.games.input.EventQueue;

public class ControllerReader {
    
    private static Event event;

    public static void initControllerEnvironment(){

        System.setProperty("net.java.games.input.useDefaultPlugin", "false");
        
        event = new Event();
        Controller[] controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();
        System.out.println(controllers.length);

        for(int i = 0; i < controllers.length; i++){

            controllers[i].poll();
            System.out.println("[SYSTEM] " + controllers[i].getName() + " detected at port " + controllers[i].getPortNumber());

            EventQueue queue = controllers[i].getEventQueue();

            while(queue.getNextEvent(event)) {

                Component comp = event.getComponent();
                System.out.println(comp.getName());
            }
        }
    }
}
