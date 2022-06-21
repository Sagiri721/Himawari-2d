package Engine.Utils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.Timer;

import Engine.Components.Camera;
import Engine.Components.ImageRenderer;
import Engine.Components.Transform;
import Engine.Entity.Object;
import Engine.Gfx.ImageUtil;
import Engine.Input.Input;
import Engine.Map.RoomHandler;

public class Renderer extends JPanel implements ActionListener{

    private Timer timer;
    public static int DELAY = 10;
    
    //Handle framing and delta time
    public static float deltaTime = 0;
    private static int fps = 0;
    private int counter = 0;
    private long startSec = 0, endTime = 0, deltaf = 0, deltai = 0;

    public Renderer() {

        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        /*
         * Draw the graphics of the game
         */
        Graphics2D g2d = (Graphics2D) g;

        RoomHandler.render(g2d);
        renderImages(g2d);

        g2d.drawString("fps: " + getFPS(), 0, 15);
    }

    public void renderImages(Graphics2D g2d){

        for (Object o : Object.objects){

            ImageRenderer r = (ImageRenderer) o.getComponent("ImageRenderer");

            //Draw every sprite that needs to be drawn
            if(r != null && r.visible && r.hasImage())
            {
                /**
                 * If the game has a camera, we want to draw every sprite according to the cameras perspective
                 * Otherwise we just draw the sprites in their world positions
                 */

                if(Camera.getInstance() == null)
                {

                    //Normal rendering
                    Transform t = (Transform) o.getComponent("Transform");

                    if(t != null){

                        BufferedImage fnImg = ImageUtil.rotate(r.getImage(), (double) t.angle);

                        g2d.drawImage(fnImg, (int) t.position.x, (int) t.position.y, 
                        r.getImage().getWidth() * (int) t.scale.x, r.getImage().getHeight() * (int) t.scale.y, 
                        null);
                    }

                }else{
                    //Combined with camera
                    //Normal rendering
                    Transform t = (Transform) o.getComponent("Transform");

                    if(t != null){

                        BufferedImage fnImg = ImageUtil.rotate(r.getImage(), (double) t.angle);

                        g2d.drawImage(fnImg, (int) (t.position.x - Camera.position.position.x + Camera.getOffset().x), (int) (t.position.y - Camera.position.position.y + Camera.getOffset().y), 
                        r.getImage().getWidth() * (int) t.scale.x, r.getImage().getHeight() * (int) t.scale.y, 
                        null);
                    }
                }
            }
        }

        //Give the programmer an opportunity to draw it's own graphics
        for (StdBehaviour object : Object.behaviours) {
            
            //Run user code every frame
            object.DrawGUI(g2d);
            g2d.setColor(Color.BLACK);
        }
    }

    private void globalUpdate(){

        //Calculate FPS
        if(startSec == 0){
            
            startSec = System.currentTimeMillis();
            endTime = System.currentTimeMillis();
        }else if((endTime - startSec) < 1000){
            
            endTime = System.currentTimeMillis();
        } else {

            Renderer.fps = counter;
            startSec = 0;
            counter = 0;
        }
        //calculate deltaTime
        if(deltai == 0) {deltai = System.currentTimeMillis(); } else {deltaf = System.currentTimeMillis(); deltaTime =(deltaf-deltai) * 0.001f; deltai = 0; }

        for (StdBehaviour object : Object.behaviours) {
            
            //Run user code every frame
            object.Update(deltaTime);
        }

        counter++;

        Input.updateMousePosition();
    }

    public static int getFPS(){ return Renderer.fps; }
    public static float getDeltaTime(){ return Renderer.deltaTime; }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        globalUpdate();
        repaint();
    }
}
