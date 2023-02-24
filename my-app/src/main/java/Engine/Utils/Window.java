package Engine.Utils;

import java.awt.Color;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JFrame;

import Engine.Components.Camera;
import Engine.Input.KeyboardReader;
import Engine.Input.MouseReader;
import Engine.Utils.Geom.Vec2;

public class Window extends JFrame implements ComponentListener{

    public static String RelativeResourcePath = System.getProperty("user.dir") + "/src/main/java/Assets/"; 
    
    //Static data
    public static int width, height;
    public static String name;

    public static boolean WindowExists = false;

    private static Color background = null;

    public static boolean focus = true;
    protected static Window window;

    //Local class data
    Renderer gameRenderer = new Renderer();
    KeyboardReader reader = new KeyboardReader();
    MouseReader mouseReader = new MouseReader();

    /**
     * Create a window in where game objects can be placed
     */
    public void initWindow(int width, int height, String name){

        if(Window.WindowExists)
            return;
        
        Window.WindowExists = true;

        //Define basic properties of the window
        Window.width = width;
        Window.height = height;

        Window.name = name;

        setTitle(name);
        setSize(width, height);

        //Define window related operations
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        //Add input readers
        addKeyListener(reader);
        addMouseListener(mouseReader);
        requestFocus();
        //Add a renderer
        add(gameRenderer);

        //Define a background color
        if(Window.background != null) {setBackground(background);}

        setVisible(true);

        Window.window = this;

        addComponentListener(this);
    }

    public void changeBackground(Color backColor){

        Window.background = backColor;
        getContentPane().setBackground(backColor);
    }

    public void closeWindow(){

        setVisible(false);
        dispose();
    }

    public static Vec2 getViewportCenter() { return new Vec2(Window.window.getWidth() / 2, Window.window.getHeight() / 2); }

    @Override
    public void componentResized(ComponentEvent e) {

        if(Camera.getInstance() != null)
        {
            width = getWidth();
            height = getHeight();

            Camera.calculateViewPort();
        }
    }

    @Override
    public void componentMoved(ComponentEvent e) {

    }

    @Override
    public void componentShown(ComponentEvent e) {

        focus = true;
    }

    @Override
    public void componentHidden(ComponentEvent e) {

        focus = false;
    }
}
