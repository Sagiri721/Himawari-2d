package Engine.Map;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import Engine.Gfx.Sprite;
import Engine.Utils.Window;
import Engine.Utils.Geom.Vec2;

import java.awt.image.BufferedImage;

public class Room implements Serializable{

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

    public List<Vec2> getAllPositionsWhere(int index, Space capture){

        List<Vec2> positions = new ArrayList<Vec2>();
        Vec2 multiplier = capture == Space.WORLD ? new Vec2(tileset.width, tileset.height) : Vec2.ONE;
        
        for (int i = 0; i < roomData.getWidth(); i++) {
            for (int j = 0; j < roomData.getHeight(); j++) {

                if(roomData.getTile(i, j) == index){

                    Vec2 position = new Vec2(i, j).times(multiplier);
                    positions.add(position);
                }
            }   
        }

        return positions;
    }

    public int getTileInPosition(Vec2 position){

        Vec2 realPosition = position.divide(new Vec2(tileset.width, tileset.height));
        return roomData.getTile(
            Math.round(realPosition.x),
            Math.round(realPosition.y)
        );
    }

    public Vec2 convertWorldToTilePosition(Vec2 wordPosition) {

        Vec2 transformer = new Vec2(tileset.width, tileset.height);
        return wordPosition.divide(transformer);
    }

    public int getIdFromTileset(int x, int y){ return y + (x * tileset.sizeY); }
    public void changeTileAt(int x, int y, int newTile){ roomData.setTile(x, y, newTile); }
}
