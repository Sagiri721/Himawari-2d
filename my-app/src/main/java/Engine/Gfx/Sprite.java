package Engine.Gfx;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import java.io.File;

public class Sprite {
    
    public static final String RelativeResourcePath = "/Users/heldersimoes/Documents/program/pasta sem nome 2/my-app/src/main/java/Assets/Sprites";
    public static final String RelativeEngineResourcePath = "/Users/heldersimoes/Documents/program/pasta sem nome 2/my-app/src/main/java/Engine/Assets";
    //Image data
    public int width, height;
    
    //The image
    public BufferedImage sprite;

    public Sprite(BufferedImage image) { sprite = image; width = image.getWidth(); height = image.getHeight();}

    public Sprite(String path, int x, int y, int w, int h) {BufferedImage image = getBufferedImageFromFile(path); sprite = image.getSubimage(x, y, w, h); }

    public Sprite(String path) { sprite = getBufferedImageFromFile(path); width = sprite.getWidth(); height = sprite.getHeight(); }

    public Sprite(int i) { sprite = getBufferedImageFromEngineFile("default-sprites.png").getSubimage(i * 32, 0, 32, 32);}

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

    private BufferedImage getBufferedImageFromEngineFile(String path){

        try{

            File src = new File(RelativeEngineResourcePath + "/" + path);

            BufferedImage img = ImageIO.read(src);

            return img;

        }catch(Exception e){ System.out.println(e.toString());}

        return null;
    }

    public static BufferedImage[] getFramesOf(BufferedImage image, int width, int height, int x, int y) {

        BufferedImage[] frames = new BufferedImage[(image.getWidth() / width) - (x * width)];

        for(int i = 0; i < frames.length; i++){

            frames[i] = image.getSubimage(x + (i * width), y, width, height);
        }

        return frames;
    }

    public static Animation createAnimation(Sprite spriteSheet, int width, int height, int startX, int startY){

        BufferedImage[] frames = getFramesOf(spriteSheet.sprite, width, height, startX, startY);
        return new Animation(frames, startX, startY, width, height);
    }
}
