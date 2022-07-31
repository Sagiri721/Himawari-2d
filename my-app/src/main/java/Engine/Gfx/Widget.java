package Engine.Gfx;

import java.awt.Color;
import java.awt.Graphics2D;

import Engine.Components.ImageRenderer;
import Engine.Gfx.Debugging.type;
import Engine.Utils.GameMaths;
import Engine.Utils.Geom.Circle;
import Engine.Utils.Geom.Rectangle;
import Engine.Utils.Geom.Vec2;

import java.awt.image.BufferedImage;

public class Widget {

    static Color color = Color.BLACK;
    static boolean show = true;

    public static enum Direction{

        LEFT,
        RIGHT,
        UP,
        DOWN
    }

    private static type drawType = type.HOLLOW;
    public static void setDrawType(type t) {
        
        Widget.drawType = t;
    }

    public static void setColor(Color col){

        color = col;
    }

    public static void drawRectangle(Rectangle rect, Graphics2D g){

        if(show){
            
            g.setColor(color);
            if(drawType==type.HOLLOW) g.drawRect((int)rect.x, (int)rect.y, (int)rect.width, (int)rect.height);
            else g.fillRect((int)rect.x, (int)rect.y, (int)rect.width, (int)rect.height);
        }
    }

    public static void drawRoundRectangle(Rectangle rect, int roundness, Graphics2D g){

        if(show){
            
            g.setColor(color);
            if(drawType==type.HOLLOW) g.drawRoundRect((int)rect.x, (int)rect.y, (int)rect.width, (int)rect.height, roundness, roundness);
            else g.fillRoundRect((int)rect.x, (int)rect.y, (int)rect.width, (int)rect.height, roundness, roundness);
        }
    }

    public static void drawCircle(Circle circle, Graphics2D g){

        if(show){
            
            g.setColor(color);
            if(drawType==type.HOLLOW) g.drawOval((int)circle.x, (int)circle.y, (int)circle.radius, (int)circle.radius);
            else g.fillOval((int)circle.x, (int)circle.y, (int)circle.radius, (int)circle.radius);
        }
    }
    
    public static void drawText(String text, int x, int y, Graphics2D g){

        if(show){

            g.setColor(color);
            g.drawString(text, x, y);
        }
    }

    public static void drawLine(Vec2 start, Vec2 end, Graphics2D g){

        if(show){

            g.setColor(color);
            g.drawLine((int)start.x, (int)start.y, (int)end.x, (int)end.y);
        }
    }

    public static void drawGrid(Vec2 start, Vec2 end, int tileWidth, int tileHeight, Graphics2D g){

        if(show){

            g.setColor(color);

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

        if(show)
            g.drawImage(sprite.sprite, (int)position.x, (int)position.y, null);
    }

    public static void drawSpriteExt(Sprite sprite, Vec2 position, Vec2 scale, Double angle, Graphics2D g){

        if(show){

            BufferedImage img = sprite.sprite;

            img = ImageUtil.toBufferedImage(ImageUtil.resizeImage((int)(img.getWidth() * scale.x), (int)(img.getHeight() * scale.y), ImageRenderer.scaleAlgorithm.SMOOTH, img));
            img = ImageUtil.rotate(img, angle);
    
            g.drawImage(img, (int)position.x, (int)position.y, null);
        }
    }

    public static void drawHealthBar(Vec2 start, Vec2 dimensions, float amount, float maxAmount, Color background, Color borderColor, Color minColor, Color maxColor, Direction direction, boolean border, Graphics2D g){

        if(show){
            
            //Draw the back
            g.setColor(background);
            g.fillRect((int)start.x, (int)start.y, (int)dimensions.x, (int)dimensions.y);

            if(border){
                
                //Draw the border
                g.setColor(borderColor);
                g.drawRect((int)start.x - 1, (int)start.y - 1, (int)dimensions.x + 1, (int)dimensions.y + 1);
            }

            //Draw the health
            float filledPercentage = (int)((amount / maxAmount) * 100);
            Color betweenColor = GameMaths.getInBetweenColor(minColor, maxColor, filledPercentage/100);

            g.setColor(betweenColor);

            switch(direction){

                case RIGHT:

                g.fillRect((int)start.x, (int)start.y, (int)(dimensions.x * (filledPercentage/100)), (int)dimensions.y);
                    break;
                case DOWN:

                g.fillRect((int)start.x, (int)start.x + (int)(dimensions.y * (1 - filledPercentage/100)), (int)(dimensions.x), (int)(dimensions.y) - (int)(dimensions.y * (1 - filledPercentage/100)));
                    break;
                case LEFT:

                g.fillRect((int)start.x + (int)(dimensions.x * (1 - filledPercentage/100)), (int)start.y, (int)(dimensions.x) - (int)(dimensions.x * (1 - filledPercentage/100)), (int)dimensions.y);
                    break;
                case UP:

                g.fillRect((int)start.x, (int)start.y, (int)dimensions.x, (int)(dimensions.y * (filledPercentage/100)));
                    break;
                default:
                    break;
            }
        }
    }
}
