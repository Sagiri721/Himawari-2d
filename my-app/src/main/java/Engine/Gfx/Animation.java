package Engine.Gfx;

import java.awt.image.BufferedImage;
import java.io.Serializable;

public class Animation implements Serializable{
    
    Sprite[] frames;
    int startX, startY, width, height;

    public int endFrame = 0;

    protected Animation(Sprite[] frames, int startX, int startY, int width, int height) {

        this.frames = frames;
        this.startX = startX;
        this.startY = startY;
        this.width = width;
        this.height = height;

        endFrame = frames.length;
    }
    
    public BufferedImage getFrame(int index){ return frames[index].sprite; }
}
