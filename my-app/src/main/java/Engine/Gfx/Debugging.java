package Engine.Gfx;

import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.JOptionPane;

import Engine.Components.Camera;
import Engine.Physics.RayHit;
import Engine.Utils.Path;
import Engine.Utils.Geom.Circle;
import Engine.Utils.Geom.Rectangle;
import Engine.Utils.Geom.Vec2;

public class Debugging {
 
    static Color debugColor = Color.BLACK;
    static boolean showDebug = true;

    public static boolean drawColliders = false;

    public enum type{

        HOLLOW,
        FILLED
    }

    private static type drawType = type.HOLLOW;
    public static void setDrawType(type t) {
        
        Debugging.drawType = t;
    }

    public static void setDebugColor(Color col){

        Debugging.debugColor = col;
    }

    public static void drawDebugRectangle(Rectangle rect, Graphics2D g){

        if(showDebug){
            
            g.setColor(debugColor);
            Vec2 point = Camera.calculateWindowTowindowPoint(new Vec2((int)-rect.x, (int)-rect.y));
            if(drawType==type.HOLLOW) g.drawRect((int)point.x, (int)point.y, (int)rect.width, (int)rect.height);
            else g.fillRect((int)point.x, (int)point.y, (int)rect.width, (int)rect.height);
        }
    }

    public static void drawDebugCircle(Circle circle, Graphics2D g){

        if(showDebug){
            
            g.setColor(debugColor);
            Vec2 point = Camera.calculateWindowTowindowPoint(new Vec2((int)-circle.x, (int)-circle.y));
            if(drawType==type.HOLLOW) g.drawOval((int)point.x, (int)point.y, (int)circle.radius, (int)circle.radius);
            else g.fillOval((int)point.x, (int)point.y, (int)circle.radius, (int)circle.radius);
        }
    }
    
    public static void drawDebugText(String text, int x, int y, Graphics2D g){

        if(showDebug){

            g.setColor(debugColor);
            Vec2 point = Camera.calculateWindowTowindowPoint(new Vec2((int)-x, (int)-y));
            g.drawString(text, point.x, point.y);
        }
    }

    public static void drawDebugLine(Vec2 start, Vec2 end, Graphics2D g){

        if(showDebug){

            g.setColor(debugColor);
            Vec2 point1 = Camera.calculateWorldToWindowPosition(new Vec2((int)-start.x, (int)-start.y));
            Vec2 point2 = Camera.calculateWorldToWindowPosition(new Vec2((int)-end.x, (int)-end.y));
            g.drawLine((int)point1.x, (int)point1.y, (int)point2.x, (int)point2.y);
        }
    }

    public static void drawRay(RayHit hit, Graphics2D g){

        if(showDebug){

            g.setColor(debugColor);
            Vec2 point1 = Camera.calculateWindowTowindowPoint(new Vec2((int)-hit.getOrigin().x, (int)-hit.getOrigin().y));
            Vec2 point2 = Camera.calculateWindowTowindowPoint(new Vec2((int)-hit.point.x, (int)-hit.point.y));
            g.drawLine((int)point1.x, (int)point1.y, (int)point2.x, (int)point2.y);
        }
    }

    public static void drawDebugGrid(Vec2 start, Vec2 end, int tileWidth, int tileHeight, Graphics2D g){

        if(showDebug){

            g.setColor(debugColor);

            Vec2 point1 = Camera.calculateWindowTowindowPoint(start);
            Vec2 point2 = Camera.calculateWindowTowindowPoint(end);

            for(int i = 0; i < ((point2.x+1) - point1.x); i+=tileWidth){
            
                g.drawLine((int)i, (int)point1.y, i, (int)(point1.y + (tileHeight * ((point2.y - point1.y) / tileHeight))));
            }
            for
            (int i = 0; i < ((end.x+1) - point1.x); i+=tileHeight){

                g.drawLine((int)point1.x, i, (int)(int)(point1.x + (tileWidth * ((point2.x - point1.x) / tileWidth))), i);
            }
        }
    }

    public static void debugPath(Path path, Graphics2D g){

        
        if(showDebug){

            g.setColor(debugColor);
            if(path == null) return;

            Vec2 point = Camera.calculateWindowTowindowPoint(path.getKnot(0).inverse()).sumWith(Camera.getOffset());
            Vec2[] points = path.getKnots();
            for (int i = points.length-1; i > 0; i--) {
                
                g.drawLine((int) (point.x + points[i].x), (int) (point.y +points[i].y), (int) (point.x + points[i-1].x), (int) (point.y + points[i-1].y));
            }
        }
    }

    public static void printOut(String text){

        System.out.println("[USER MESSAGE] " + text);
    }

    public static void sendMessage(String title, String content, int messageType){

        JOptionPane.showMessageDialog(null, content, title, messageType);
    }

    public static String retrieveInput(String title, String content){

        return JOptionPane.showInputDialog(null, content, title);
    }
}
