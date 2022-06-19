package Engine.Input;

import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputListener;

public class MouseReader implements MouseInputListener{

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

        if(e.getButton() == MouseEvent.BUTTON1) {Input.mousePressed[0] = true; }

        if(e.getButton() == MouseEvent.BUTTON2) {Input.mousePressed[2] = true; }

        if(e.getButton() == MouseEvent.BUTTON3) {Input.mousePressed[1] = true; }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

        if(e.getButton() == MouseEvent.BUTTON1) {Input.mousePressed[0] = false; }

        if(e.getButton() == MouseEvent.BUTTON2) {Input.mousePressed[2] = false; }

        if(e.getButton() == MouseEvent.BUTTON3) {Input.mousePressed[1] = false; }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

        
    }

    @Override
    public void mouseExited(MouseEvent e) {

        
    }

    @Override
    public void mouseDragged(MouseEvent e) {

        
    }

    @Override
    public void mouseMoved(MouseEvent e) {

        
    }
    
}
