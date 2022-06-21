package Engine.Gfx;

import Engine.Components.ImageRenderer.scaleAlgorithm;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Image;

public class ImageUtil {
    
    ////////////////////////////////////////////////////////////////////////////////////////////
    /////////////   IMAGE UTILS
    ////////////////////////////////////////////////////////////////////////////////////////////
    public static Image resizeImage(int targetWidth, int targetHeight, scaleAlgorithm al, BufferedImage sprite) {

        if(al == scaleAlgorithm.FAST)
            return sprite.getScaledInstance(targetWidth, targetHeight, Image.SCALE_DEFAULT);
        else{
            return sprite.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
        }
    }

    public static BufferedImage toBufferedImage(Image img)
    {
        if (img instanceof BufferedImage)
        {
            return (BufferedImage) img;
        }

        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bimage;
    }

    public static BufferedImage flipImageHorizontal(BufferedImage sprite){

        BufferedImage img = new BufferedImage(sprite.getWidth(), sprite.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();

        g.drawImage(sprite, img.getWidth(), 0, -img.getWidth(), img.getHeight(), null);
        return img;
    }

    public static BufferedImage flipImageVertical(BufferedImage sprite){

        BufferedImage img = new BufferedImage(sprite.getWidth(), sprite.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();

        g.drawImage(sprite, 0, img.getHeight(), img.getWidth(), -img.getHeight(), null);
        return img;
    }

    public static BufferedImage rotate(BufferedImage bimg, Double angle) {
        
        double sin = Math.abs(Math.sin(Math.toRadians(angle))),
               cos = Math.abs(Math.cos(Math.toRadians(angle)));
        int w = bimg.getWidth();
        int h = bimg.getHeight();
        int neww = (int) Math.floor(w*cos + h*sin),
            newh = (int) Math.floor(h*cos + w*sin);

        BufferedImage rotated = new BufferedImage(neww, newh, bimg.getType());
        Graphics2D graphic = rotated.createGraphics();
        graphic.translate((neww-w)/2, (newh-h)/2);
        graphic.rotate(Math.toRadians(angle), w/2, h/2);
        graphic.drawRenderedImage(bimg, null);
        graphic.dispose();
        return rotated;
    }
}
