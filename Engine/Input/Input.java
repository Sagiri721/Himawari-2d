package Engine.Input;

import java.awt.MouseInfo;

public class Input {
    
    public static int axisX = 0;
    public static int axisY = 0;

    public static int mouseX = 0;
    public static int mouseY = 0;

    public static boolean[] mousePressed = new boolean[3];

    public static void updateMousePosition(){

        mouseX = MouseInfo.getPointerInfo().getLocation().x;
        mouseY = MouseInfo.getPointerInfo().getLocation().y;
    }

    public static boolean mousePressed(int button) { return mousePressed[button];}
    
}
