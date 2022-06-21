package Engine.Input;

import java.awt.event.KeyListener;              
import java.awt.event.KeyEvent;

public class KeyboardReader implements KeyListener {

    private static char[] keyMap = {'a','d','w','s'};

    public static void mapKeyboard(){

    }

    @Override
    public void keyTyped(KeyEvent e) {


        //Deal with axis X
        if(e.getKeyChar() == keyMap[0]){ Input.axisX = -1;
        } else if(e.getKeyChar() == keyMap[1]){ Input.axisX = 1; }
        
        //Deal with axis Y
        if(e.getKeyChar() == keyMap[2]){ Input.axisY = -1;
        } else if(e.getKeyChar() == keyMap[3]){ Input.axisY = 1; }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
        //Set axis as zero
        if(e.getKeyChar() == keyMap[0] || e.getKeyChar() == keyMap[1]) Input.axisX = 0;

        if(e.getKeyChar() == keyMap[2] || e.getKeyChar() == keyMap[3]) Input.axisY = 0;
    }
    
}
