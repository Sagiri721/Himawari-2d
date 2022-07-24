package Engine.Gfx;

import Engine.Components.ImageRenderer.scaleAlgorithm;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Image;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;

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
        
 
        // Getting Dimensions of image
        int width = bimg.getWidth();
        int height = bimg.getHeight();
 
        // Creating a new buffered image
        BufferedImage newImage = new BufferedImage(
            bimg.getWidth(), bimg.getHeight(), bimg.getType());
 
        // creating Graphics in buffered image
        Graphics2D g2 = newImage.createGraphics();
 
        // Rotating image by degrees using toradians()
        // method
        // and setting new dimension t it
        g2.rotate(Math.toRadians(angle), width / 2,
                  height / 2);
        g2.drawImage(bimg, null, 0, 0);
 
        // Return rotated buffer image
        return newImage;
    }
}
