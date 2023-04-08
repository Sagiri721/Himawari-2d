package Engine.Utils;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.awt.Color;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLayer;
import javax.swing.plaf.LayerUI;

import com.google.common.io.Files;
import com.google.gson.Gson;

import Engine.Components.Camera;
import Engine.Gfx.ShaderPane;
import Engine.Gfx.Sprite;
import Engine.Input.Input;
import Engine.Input.KeyboardReader;
import Engine.Input.MouseReader;
import Engine.Map.RoomHandler;
import Engine.Networking.ServerConnection;
import Engine.Physics.Physics;
import Engine.Utils.Geom.Vec2;

import java.awt.event.*;;

public class Window extends JFrame implements ComponentListener {

    public static String RelativeResourcePath = System.getProperty("user.dir") + "/src/main/java/Assets/"; 
    
    //Static data
    public static int width, height;
    protected static Vec2 defaultSize = Vec2.ONE;
    protected static float aspectRatio = 1;

    public static String name;

    public static boolean WindowExists = false;

    private static Color background = null;

    public static boolean focus = true;
    protected static Window window;
    protected static ShaderPane myShader;
    protected static JLayer<JComponent> layer;

    //Local class data
    Renderer gameRenderer;
    KeyboardReader reader = new KeyboardReader();
    MouseReader mouseReader = new MouseReader();

    public static EngineData engineSettings = null;

    /**
     * Create a window in where game objects can be placed
     */
    public void initWindow(int width, int height, String name){

        loadSettings();

        if(Window.WindowExists)
            return;
        
        Window.WindowExists = true;

        //Define basic properties of the window
        Window.width = width;
        Window.height = height;

        Window.defaultSize = new Vec2(width, height);
        Window.aspectRatio = width / height;

        Window.name = name;
        Window.window = this;

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
        gameRenderer = new Renderer();
        //Apply shaders
        Window.myShader = new ShaderPane();
        LayerUI<JComponent> layerUI = Window.myShader;
        JLayer<JComponent> jlayer = new JLayer<JComponent>(gameRenderer, layerUI);
        Window.layer = jlayer;

        add(jlayer);

        //Define a background color
        if(Window.background != null) {setBackground(background);}

        //Add closing protocol
        addWindowListener(new WindowAdapter() {
           
            public void windowClosing(WindowEvent e) {
             
                if(ServerConnection.isConnectionOpen()){
                    ServerConnection.closeConnection();
                }
                
                System.out.println("[GAME CLOSED]");
            }
        });

        setVisible(true);

        addComponentListener(this);
    }

    public void changeBackground(Color backColor){

        Window.background = backColor;
        Renderer.clearColor = backColor;
    }

    public void closeWindow(){

        setVisible(false);
        dispose();
    }

    public static Vec2 getViewportCenter() { return new Vec2(Window.window.getWidth() / 2, Window.window.getHeight() / 2); }

    @Override
    public void componentResized(ComponentEvent e) {

        if(Camera.getInstance() != null){
        
            Camera.calculateOffset();
        }
        width = getWidth();
        height = getHeight();
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

    public static class EngineData {

        protected EngineData(){

            this.delay=10;
            this.fixed=false;
            this.grav=1.0f;
            this.cap=true;
            this.capValue=60.0f;
            this.details=1.0f;
            this.ignore=true;
            this.limit=1;
            this.map= new char[]{'A', 'D', 'W', 'S', KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_UP, KeyEvent.VK_DOWN};
        }

        public int delay;
        public boolean fixed;
        public float grav;
        public boolean cap;
        public float capValue;
        public float details;
        public boolean ignore;
        public int limit;
        public char[] map;
    }

    private static void loadSettings(){

        Gson g = new Gson();
        EngineData defaultData = new Window.EngineData();

        File dataFile = new File(Sprite.RelativeEngineResourcePath + "MyGameData.json");
        if(dataFile.exists()){

            Charset c = Charset.forName("US-ASCII");
            try(Reader r = Files.newReader(dataFile, c)){

                defaultData = g.fromJson(r, EngineData.class);

            } catch (IOException e){}

        }

        // Set the settings to the default ones
        Window.engineSettings = defaultData;        

        // Define the actual variables
        Renderer.DELAY = engineSettings.delay;
        Renderer.fixedDelta = engineSettings.fixed;

        Physics.G = engineSettings.grav;
        Physics.accelearion_capped = engineSettings.cap;
        Physics.acceleration_treshold = engineSettings.capValue;
        Physics.raycast_detail = engineSettings.details;
        Physics.ignoreSelf = engineSettings.ignore;

        KeyboardReader.limit = engineSettings.limit;
        Input.keyMap = engineSettings.map;
    }
}
