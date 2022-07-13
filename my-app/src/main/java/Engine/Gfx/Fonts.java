package Engine.Gfx;

import java.io.File;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Graphics2D;

class Fonts{

    Font font = null;
    Animation letters = null;
    FontMap map = null;

    /**
     * Create a font instance of an actual font file
     */
    public Fonts(String fontPath, float size){

        letters=null;
        map=null;

        File f = new File("my-app/src/main/java/Assets/Fonts/"+fontPath);
        if(!f.exist()){

            try{
                    
                font = Font.createFont(Font.TRUETYPE_FONT, f).derive(size);
                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                ge.registerFont();

            }catch(Exception e){

                System.out.println("[ERROR] Unable to register font\n" + e.getStackTrace());
                return;
            }
        }else{

            System.out.println("[ERROR] The font file does not exist");
            return;
        }
    }

    /**
     * Create a font from a spritesheet
     * To draw with it use the method drawText(String, Graphics2D) from this class
     */
    public Fonts(int startX, int startY, boolean horizontal, FontMap map){

        font=null;
        
        letters = Sprite.createAnimation(map.tileset, set.width, set.height, startX, startY, horizontal);
        this.map = map;
    }

    public void drawText(String text, Graphics2D g){

        if(font!=null){

            System.out.println("[ERROR] drawText can't be called in this type of object");
            return;
        }


    }
}