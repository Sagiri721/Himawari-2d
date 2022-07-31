package Engine.Gfx;

import java.awt.Color;
import java.awt.Graphics2D;

import Engine.Components.ImageRenderer;
import Engine.Gfx.Debugging.type;
import Engine.Utils.Geom.Circle;
import Engine.Utils.Geom.Rectangle;
import Engine.Utils.Geom.Vec2;

import java.awt.image.BufferedImage;

public class Widget {

    static Color debugColor = Color.BLACK;
    static boolean showDebug = true;

    private static type drawType = type.HOLLOW;
    public static void setDrawType(type t) {
        
        Widget.drawType = t;
    }

    public static void setDebugColor(Color col){

        Debugging.debugColor = col;
    }

    public static void drawDebugRectangle(Rectangle rect, Graphics2D g){

        if(showDebug){
            
            g.setColor(debugColor);
            if(drawType==type.HOLLOW) g.drawRect((int)rect.x, (int)rect.y, (int)rect.width, (int)rect.height);
            else g.fillRect((int)rect.x, (int)rect.y, (int)rect.width, (int)rect.height);
        }
    }

    public static void drawDebugCircle(Circle circle, Graphics2D g){

        if(showDebug){
            
            g.setColor(debugColor);
            if(drawType==type.HOLLOW) g.drawOval((int)circle.x, (int)circle.y, (int)circle.radius, (int)circle.radius);
            else g.fillOval((int)circle.x, (int)circle.y, (int)circle.radius, (int)circle.radius);
        }
    }
    
    public static void drawDebugText(String text, int x, int y, Graphics2D g){

        if(showDebug){

            g.setColor(debugColor);
            g.drawString(text, x, y);
        }
    }

    public static void drawDebugLine(Vec2 start, Vec2 end, Graphics2D g){

        if(showDebug){

            g.setColor(debugColor);
            g.drawLine((int)start.x, (int)start.y, (int)end.x, (int)end.y);
        }
    }

    public static void drawDebugGrid(Vec2 start, Vec2 end, int tileWidth, int tileHeight, Graphics2D g){

        if(showDebug){

            g.setColor(debugColor);

            for(int i = 0; i < ((end.x+1) - start.x); i+=tileWidth){
            
                g.drawLine((int)i, (int)start.y, i, (int)(start.y + (tileHeight * ((end.y - start.y) / tileHeight))));
            }
            for
            (int i = 0; i < ((end.x+1) - start.x); i+=tileHeight){

                g.drawLine((int)start.x, i, (int)(int)(start.x + (tileWidth * ((end.x - start.x) / tileWidth))), i);
            }
        }
    }

    public static void drawSprite(Sprite sprite, Vec2 position, Graphics2D g){

        g.drawImage(sprite.sprite, (int)position.x, (int)position.y, null);
    }

    public static void drawSpriteExt(Sprite sprite, Vec2 position, Vec2 scale, Double angle, Graphics2D g){

        BufferedImage img = sprite.sprite;

        img = ImageUtil.toBufferedImage(ImageUtil.resizeImage((int)(img.getWidth() * scale.x), (int)(img.getHeight() * scale.y), ImageRenderer.scaleAlgorithm.SMOOTH, img));
        img = ImageUtil.rotate(img, angle);

        g.drawImage(img, (int)position.x, (int)position.y, null);
    }

}
