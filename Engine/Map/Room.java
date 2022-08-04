package Engine.Map;

import java.io.FileNotFoundException;

import Engine.Gfx.Sprite;
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

    public Room(String name, TileSet tileset, RoomData roomData) {

        this.tileset = tileset;
        this.name = name;
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
}
