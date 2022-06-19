package Engine.Map;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class RoomData {
    
    private File tileLayer;
    private int[][] tiles = new int[1000][1000];

    private int effectiveWidth, effectiveHeight;

    public static final String RelativeResourcePath = "/Users/heldersimoes/Documents/program/pasta sem nome 2/Assets/Rooms/";

    public int getTile(int x, int y){

        return tiles[x][y];
    }

    public int getWidth() { return effectiveWidth; }
    public int getHeight() { return effectiveHeight; }

    public RoomData(String path){

        tileLayer = new File(RelativeResourcePath + path);

        Scanner reader;
        try {
            reader = new Scanner(tileLayer);

            int line = 0;
            while(reader.hasNextLine()){

                String[] data = reader.nextLine().split(" ");
                for(int i = 0; i < data.length; i++) { tiles[i][line] = Integer.parseInt(data[i]); }
                line++;
            
                effectiveWidth = data.length;
            }

            reader.close();

            effectiveHeight = line;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
