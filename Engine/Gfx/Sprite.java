package Engine.Gfx;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import java.io.File;

public class Sprite {
    
    public static final String RelativeResourcePath = "/Users/heldersimoes/Documents/program/pasta sem nome 2/Assets/Sprites/";

    //Image data
    public int width, height;
    
    //The image
    public BufferedImage sprite;

    public Sprite(BufferedImage image) { sprite = image; width = image.getWidth(); height = image.getHeight();}

    public Sprite(String path) { sprite = getBufferedImageFromFile(path); width = sprite.getWidth(); height = sprite.getHeight(); }

    public static Sprite getImageFromFile(String path){

        try{

            File src = new File(RelativeResourcePath + "/" + path);
            BufferedImage img = ImageIO.read(src);

            return new Sprite(img);

        }catch(Exception e){ System.out.println(e.toString());}

        return null;
    }

    private BufferedImage getBufferedImageFromFile(String path){

        try{

            File src = new File(RelativeResourcePath + "/" + path);

            BufferedImage img = ImageIO.read(src);

            return img;

        }catch(Exception e){ System.out.println(e.toString());}

        return null;
    }
}
