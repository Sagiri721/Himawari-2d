package Engine.Gfx;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
 
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

class FontMap{

    TileSet tileset = null;
    
    //Map hash
    String[] keys;
    Integer[] values;

    public FontMap(String mapPath, TileSet tilesetPath){

        tileset = tilesetPath;

        openFontMap(new File("my-app/src/main/java/Assets/Fonts/FontMaps" + mapPath));
    }

    private void openFontMap(File map){

        if(!map.exists()){

            System.out.println("[ERROR] the inputed map does not exist");
            return;
        }

        JSONParser parser = new JSONParser();

        try(FileReader reader = new FileReader(map)){

            JSONArray array = (JSONArray) parser.parse(reader);

            int i = 0;
            //Map everything
            array.forEach( keypair -> {

                JSONObject o = (JSONObject) keypair;
                System.out.println(o.toString());
            });

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            System.out.println("[ERROR] Your map has an invalid format");
        }
    }
}