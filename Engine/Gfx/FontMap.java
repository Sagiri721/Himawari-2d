package Engine.Gfx;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.awt.image.BufferedImage;

import Engine.Map.TileSet;

public class FontMap {

    TileSet tileset = null;

    // Map hash
    char[] keys;
    long[] values;

    public FontMap(String mapPath, TileSet tilesetPath) {

        tileset = tilesetPath;

        openFontMap(new File("D:/TIAGO/program/himawari/my-app/src/main/java/Assets/Fonts/FontMaps/map01.json"));
    }

    private void openFontMap(File map) {

        if (!map.exists()) {

            System.out.println("[ERROR] the inputed map does not exist");
            return;
        }

        JSONParser parser = new JSONParser();

        try (FileReader reader = new FileReader(map)) {

            JSONObject obj = (JSONObject) parser.parse(reader);
            Set<String> keyset = obj.keySet();

            keys = new char[keyset.size()];
            values = new long[keyset.size()];

            int i = 0;
            for (String key : keyset) {

                keys[i] = key.charAt(0);
                values[i] = (long) obj.get(key);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            System.out.println("[ERROR] Your map has an invalid format");
        }
    }

    public BufferedImage getLetter(int x) {

        return tileset.spriteSheet.getSubimage(x, 0, tileset.width, tileset.height);
    }
}