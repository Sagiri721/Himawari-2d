package Engine.Utils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.AlphaComposite;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import Engine.Components.Animator;
import Engine.Components.Body;
import Engine.Components.Camera;
import Engine.Components.ImageRenderer;
import Engine.Components.RectCollider;
import Engine.Components.Transform;
import Engine.Entity.Object;
import Engine.Gfx.Debugging;
import Engine.Gfx.ImageUtil;
import Engine.Input.Input;
import Engine.Map.RoomHandler;
import Engine.Utils.Geom.Vec2;

public class Renderer extends JPanel implements ActionListener {

    private Timer timer;
    public static int DELAY = 10;
    public static boolean fixedDelta = false; 

    private static JLayeredPane layers = null;

    // Handle framing and delta time
    public static float deltaTime = 0;
    private static int fps = 0;
    private int counter = 0;
    private long startSec = 0, endTime = 0, deltaf = 0, deltai = 0;

    public Renderer() {

        Renderer.layers = Window.window.getLayeredPane();
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
    }

    public void renderImages(Graphics2D g2d) {

        for (int i = 0; i <= Object.maxLayer; i++) {

            //Convert the current objects array to a copy for the rendering time to avoid concurent modifications
            Object[] copyArray = Object.objects.toArray(new Object[Object.objects.size()]);

            for (int j = 0; j < copyArray.length; j++) {
                Object o = copyArray[j];

                if (o.getLayer() == i) {

                    //Run physics updates
                    Body b = (Body) o.getComponent(Body.class);
                    if (b!= null) b.PhysicsUpdate(deltaTime);

                    // Run user graphics code every frame
                    o.getBehaviour().DrawGUI(g2d);
                    g2d.setColor(Color.BLACK);

                    ImageRenderer r = (ImageRenderer) o.getComponent(ImageRenderer.class);

                    // Draw every sprite that needs to be drawn
                    if (r != null && r.hasImage() && r.visible) {
                        /**
                         * If the game has a camera, we want to draw every sprite according to the
                         * camera's perspective
                         * Otherwise we just draw the sprites in their world positions
                         */

                        Transform t = o.transform;
                        boolean camera = Camera.getInstance() != null;
                        int x = camera ? (int) ((t.position.x - Camera.position.position.x + Camera.getOffset().x) * Camera.viewport.x) : (int) t.position.x;
                        int y = camera ? (int) ((t.position.y - Camera.position.position.y + Camera.getOffset().y) * Camera.viewport.y) : (int) t.position.y;

                        if (t != null) {

                            AlphaComposite alcom = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, r.getAlpha());
                            g2d.setComposite(alcom);

                            BufferedImage fnImg = ImageUtil.rotate(r.getImage(), (double) t.angle);
                            if(r.isFlippedX) fnImg = ImageUtil.flipImageHorizontal(fnImg);
                            if(r.isFlippedY) fnImg = ImageUtil.flipImageVertical(fnImg);

                            g2d.drawImage(fnImg, x,  y,
                                    (int)(((r.getImage().getWidth() * t.scale.x)) * Camera.viewport.x),
                                    (int)(((r.getImage().getHeight() * t.scale.y)) * Camera.viewport.y),
                                    null);
                        }
                        
                    }

                    AlphaComposite alcom = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1);
                    g2d.setComposite(alcom);

                    if(Debugging.drawColliders){

                        Transform t = (Transform) o.getComponent(Transform.class);
                        RectCollider c = (RectCollider) o.getComponent(RectCollider.class);

                        if(c != null && t != null){
                            
                            g2d.setColor(Color.RED);
                            g2d.drawRect(
                                (int) ((t.position.x - Camera.position.position.x + Camera.getOffset().x) * Camera.viewport.x),
                                (int) ((t.position.y - Camera.position.position.y + Camera.getOffset().y) * Camera.viewport.y), 
                                (int) (c.bounds.x * Camera.viewport.x), 
                                (int) (c.bounds.y * Camera.viewport.y)
                                );
                        }

                        g2d.setColor(Color.WHITE);
                    }
                }
            }

        }

        // Give the programmer an opportunity to draw it's own graphics
        /*
        for (Iterator<Object> obj = Object.objects.iterator(); obj.hasNext();) {
            Object object = obj.next();

            // Run user code every frame
            object.getBehaviour().DrawGUI(g2d);
            g2d.setColor(Color.BLACK);
        }
        */
    }

    private void globalUpdate() {

        // Calculate FPS
        if (startSec == 0) {

            startSec = System.currentTimeMillis();
            endTime = System.currentTimeMillis();
        } else if ((endTime - startSec) < 1000) {

            endTime = System.currentTimeMillis();
        } else {

            Renderer.fps = counter;
            startSec = 0;
            counter = 0;
        }
        // calculate deltaTime
        if (deltai == 0) {
            deltai = System.currentTimeMillis();
        } else {
            deltaf = System.currentTimeMillis();
            deltaTime = (deltaf - deltai) * 0.001f;
            deltai = 0;
        }

        Object[] copyArray = Object.objects.toArray(new Object[Object.objects.size()]);

        for (int j = 0; j < copyArray.length; j++) {
            Object object = copyArray[j];

            if(object == null) continue;
            
            // Run the necessary component updates
            Animator a = (Animator) object.getComponent(Animator.class);
            if (a != null) {
                a.PlayAnimation();
            }

            object.getBehaviour().Update(deltaTime);
        }

        counter++;

        Input.updateMousePosition();
    }

    public static int getFPS() { return Renderer.fps;  }
    public static float getDeltaTime() { return Renderer.deltaTime;  }

    public static void AddComponent(JComponent component, Vec2 position, Vec2 dimensions){

        component.setBounds((int) position.x, (int) position.y, (int) dimensions.x, (int) dimensions.y);
        layers.add(component, 10);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        new Thread(new Runnable() {

            @Override
            public void run() {
                globalUpdate();
                repaint();
            }
            
        }).start();
    }
}
