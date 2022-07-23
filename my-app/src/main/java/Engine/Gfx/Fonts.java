package Engine.Gfx;

import java.io.File;

import Engine.Utils.Geom.Vec2;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Graphics2D;

public class Fonts {

    Font font = null;
    Animation letters = null;
    FontMap map = null;

    /**
     * Create a font instance of an actual font file
     */
    public Fonts(String fontPath, float size) {

        letters = null;
        map = null;

        File f = new File("D:/TIAGO/program/himawari/my-app/src/main/java/Assets/Fonts/FontFiles/" + fontPath);
        if (!f.exists()) {

            try {

                font = Font.createFont(Font.TRUETYPE_FONT, f).deriveFont(size);
                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                ge.registerFont(font);

            } catch (Exception e) {

                System.out.println("[ERROR] Unable to register font/n" + e.getStackTrace());
                return;
            }
        } else {

            System.out.println("[ERROR] The font file does not exist");
            return;
        }
    }

    /**
     * Create a font from a spritesheet
     * To draw with it use the method drawText(String, Graphics2D) from this class
     */
    public Fonts(int startX, int startY, boolean horizontal, FontMap map) {

        font = null;

        letters = Sprite.createAnimation(new Sprite(map.tileset.spriteSheet), map.tileset.width, map.tileset.height,
                startX, startY,
                horizontal);
        this.map = map;
    }

    public void drawText(String text, Graphics2D g, Vec2 position) {

        if (font != null) {

            System.out.println("[ERROR] drawText can't be called in this type of object");
            return;
        }

        for (char c : text.toCharArray()) {

            for (int i = 0; i < map.keys.length; i++) {

                if (Character.toLowerCase(map.keys[i]) == Character.toLowerCase(c)) {

                    System.out.println(c);

                    g.drawImage(map.getLetter((int) map.values[i]), (int) position.x, (int) position.y, null);
                    break;
                }
            }
        }

    }
}