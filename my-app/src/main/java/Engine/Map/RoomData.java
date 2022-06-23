package Engine.Map;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import Engine.Components.Transform;
import Engine.Utils.Window;
import Engine.Utils.Geom.Vec2;
import Engine.Entity.Object;

public class RoomData {
    
    //The layer with information on tile placement
    private File tileLayer = null;
    //The layer with information on object placement
    private File objectLayer = null;
    public boolean hasObjectLayer() { return objectLayer != null; }

    //Tile map
    private int[][] tiles = new int[9999][9999];

    private int effectiveWidth, effectiveHeight;

    public int getTile(int x, int y){

        return tiles[x][y];
    }

    public int getWidth() { return effectiveWidth; }
    public int getHeight() { return effectiveHeight; }

    public RoomData(String path){

        tileLayer = new File(Window.RelativeResourcePath + "Rooms/" + path + "/room-tiles.txt");
        objectLayer = new File(Window.RelativeResourcePath + "Rooms/" + path + "/room-objects.txt");

        //reader of files
        Scanner reader;
        try {
            reader = new Scanner(tileLayer);

            //Read and output the layer file to the matrix
            int line = 0;
            while(reader.hasNextLine()){

                String[] data = reader.nextLine().split(" ");

                for(int i = 0; i < data.length; i++) { tiles[i][line] = Integer.parseInt(data[i]); }
                line++;
            
                effectiveWidth = data.length;
            }
            effectiveHeight = line;

            reader.close();

        } catch (FileNotFoundException e ) {
            e.printStackTrace();
        }
    }

    public void unloadObjectLayer() throws FileNotFoundException, NumberFormatException{

        Scanner reader = new Scanner(objectLayer);

        while(reader.hasNextLine())
        {
            String[] data = reader.nextLine().split(" ");
            //name - position - rotation - scale 

            Object obj = Object.FindObject(data[0]);
            if(obj == null){ System.out.println("[Object Instantiating Process] Object: " + data[0] + " was not found"); continue; }

            obj = Object.Instantiate(obj);
            Transform transform = (Transform) obj.getComponent("Transform");

            String[] positions = data[1].split("-");
            String[] scaleFactor = data[3].split("-");

            Vec2 newPosition = new Vec2(Integer.valueOf(positions[0]), Integer.valueOf(positions[1]));
            Vec2 scale = new Vec2(Integer.valueOf(scaleFactor[0]), Integer.valueOf(scaleFactor[1]));

            transform.setPosition(newPosition);
            transform.setAngle(Integer.valueOf(data[2]));
            transform.setScale(scale);
        }

        reader.close();
    }
}
