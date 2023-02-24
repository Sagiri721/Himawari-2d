package Engine.Map;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

import Engine.Gfx.Sprite;
import Engine.Utils.Window;

import java.awt.image.BufferedImage;

public class Room {

    public String name;
    public TileSet tileset;

    public RoomData roomData;

    protected Sprite background = null;
    private boolean tileX = false, tileY = false, stretch = false;

    public boolean hasBackground(){

        return background !=null;
    }

    protected BufferedImage getBackground(){return background.sprite;}
    protected boolean[] getTiling(){ if(stretch){return null;} boolean[] values = {tileX, tileY}; return values; }


    public void setBackground(Sprite background, boolean tileX, boolean tileY, boolean stretch) {

        this.tileX = tileX;
        this.tileY = tileY;
        this.background = background;
        this.stretch = stretch;

        if (stretch) {

            this.tileX = false;
            this.tileY = false;
        }
    }

    public void setBackground(BufferedImage background, boolean tileX, boolean tileY, boolean stretch) {

        this.tileX = tileX;
        this.tileY = tileY;
        this.background = new Sprite(background);
        this.stretch = stretch;

        if (stretch) {

            this.tileX = false;
            this.tileY = false;
        }
    }

    public Room(TileSet tileset, RoomData roomData) {

        if(tileset == null){

            //Search fot tileset in folder
            File[] files = new File(Window.RelativeResourcePath + "Rooms/" + roomData.path).listFiles();

            for (File file : files) {
                
                if(file.getName().startsWith("tiles-")){
                    
                    String rest = file.getName().split("tiles-")[1];
                    int size = Integer.valueOf(rest.substring(0, rest.lastIndexOf(".")));
                    try {
                        tileset = new TileSet(new Sprite(ImageIO.read(file)), size, size);
                    } catch (IOException e) {  e.printStackTrace(); }
                }
            }
        }

        this.tileset = tileset;
        this.name = roomData.path;
        this.roomData = roomData;

        RoomHandler.addRoom(this);
    }

    public Room(RoomData roomData) {

        //Search fot tileset in folder
        File[] files = new File(Window.RelativeResourcePath + "Rooms/" + roomData.path).listFiles();

        for (File file : files) {
            
            if(file.getName().startsWith("tiles-")){
                
                String rest = file.getName().split("tiles-")[1];
                int size = Integer.valueOf(rest.substring(0, rest.lastIndexOf(".")));
                try {
                    tileset = new TileSet(new Sprite(ImageIO.read(file)), size, size);
                } catch (IOException e) {  e.printStackTrace(); }
            }
        }

        this.name = roomData.path;
        this.roomData = roomData;

        RoomHandler.addRoom(this);
    }

    public void loadObjects() {

        if (roomData.hasObjectLayer()) {

            try {
                roomData.unloadObjectLayer();
            } catch (NumberFormatException | FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean hasObjects(){

        File objects = new File("my-app\\src\\main\\java\\Assets\\Rooms\\" + name + "\\room-objects.txt");
        return objects.exists();
    }
}
