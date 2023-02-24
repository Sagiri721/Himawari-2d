package Engine.Map;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

import com.google.common.io.Files;

import Engine.Components.Transform;
import Engine.Utils.ObjectLoader;
import Engine.Utils.Window;
import Engine.Utils.Geom.Vec2;
import Engine.Entity.Object;

public class RoomData {
    
    public String path;

    //The layer with information on tile placement
    private File tileLayer = null;
    //The layer with information on object placement
    private File objectLayer = null;
    public boolean hasObjectLayer() { return objectLayer != null; }

    //Tile map
    private int[][] tiles;

    private int effectiveWidth, effectiveHeight;

    public int getTile(int x, int y){

        try {
            
            return tiles[x][y];
        } catch (Exception e) {
            
            System.out.println("[INTERNAL ERROR] Tile out of bounds no such tile of position (" + x + ", " + y + ")");
            return 0;
        }
    }

    public int getWidth() { return effectiveWidth; }
    public int getHeight() { return effectiveHeight; }

    public RoomData(String path){

        this.path = path;

        tileLayer = new File(Window.RelativeResourcePath + "Rooms/" + path + "/room-tiles.txt");
        objectLayer = new File(Window.RelativeResourcePath + "Rooms/" + path + "/room-objects.txt");

        // Calculate the tile matrix size
        try {
            
            List<String> tileLines = Files.readLines(tileLayer, StandardCharsets.UTF_8);
            effectiveWidth = tileLines.get(0).split(" ").length;
            effectiveHeight = tileLines.size();

            tiles = new int[effectiveWidth+2][effectiveHeight+2];

            int index = 0;
            for (String line : tileLines) {
                
                String[] data = line.split(" ");
                for(int i = 0; i < data.length; i++) { tiles[i][index] = Integer.parseInt(data[i]); }
                index++;
            }

        } catch (IOException e ) {
            
            System.out.println("[ERROR] Room file is unreadable");
        }
    }

    public void unloadObjectLayer() throws FileNotFoundException, NumberFormatException{

        Scanner reader = new Scanner(objectLayer);

        while(reader.hasNextLine())
        {
            String[] data = reader.nextLine().split(" ");
            //name - position - rotation - scale 

            String[] positions = data[1].split("-");
            String[] scaleFactor = data[3].split("-");

            Vec2 newPosition = new Vec2(Integer.valueOf(positions[0]), Integer.valueOf(positions[1]));
            Vec2 scale = new Vec2(Integer.valueOf(scaleFactor[0]), Integer.valueOf(scaleFactor[1]));

            Object obj = ObjectLoader.LoadObjectOfName(data[0], newPosition, Float.valueOf(data[2]), scale);

            if(data.length >= 5){

                obj.setName(data[4]);
            }

            Transform transform = (Transform) obj.getComponent(Transform.class);
            transform.updateCollider();
        }

        reader.close();
    }
}
