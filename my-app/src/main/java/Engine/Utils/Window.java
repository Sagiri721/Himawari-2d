package Engine.Utils;

import java.awt.Color;

import javax.swing.JFrame;

import Engine.Input.KeyboardReader;
import Engine.Input.MouseReader;
import Engine.Utils.Geom.Vec2;

public class Window extends JFrame{

    public static String RelativeResourcePath = "/Users/heldersimoes/Documents/program/pasta sem nome 2/my-app/src/main/java/Assets/"; 
    
    //Static data
    public static int width, height;
    public static String name;

    public static boolean WindowExists = false;

    private static Color background = null;

    private static Window window;

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

        setName(name);
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
    }

    public void changeBackground(Color backColor){

        Window.background = backColor;
        setBackground(backColor);
    }

    public static Vec2 getViewportCenter() { return new Vec2(Window.window.getWidth() / 2, Window.window.getHeight() / 2); }
}
