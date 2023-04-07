package Engine.Gfx;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.Set;
import java.util.stream.IntStream;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.awt.image.BufferedImage;

import Engine.Map.TileSet;

public class FontMap implements Serializable {

    TileSet tileset = null;

    // Map hash
    char[] keys;
    int[] values;

    public FontMap(TileSet tileset) {

        this.tileset = tileset;

        // Create default map
        String letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        this.keys = letters.toCharArray();
        values = IntStream.range(0, keys.length).toArray();
    }

    public FontMap(String letterOrder, TileSet tilesetPath) {

        tileset = tilesetPath;
        this.keys = letterOrder.toCharArray();
        this.values = IntStream.range(0, letterOrder.length()).toArray();
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
            values = new int[keyset.size()];

            int i = 0;
            for (String key : keyset) {

                keys[i] = key.charAt(0);
                values[i] = (int) obj.get(key);
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

        return tileset.spriteSheet.sprite.getSubimage(x * tileset.width, 0, tileset.width, tileset.height);
    }
}