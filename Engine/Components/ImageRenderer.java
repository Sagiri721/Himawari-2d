package Engine.Components;

import Engine.Gfx.ImageUtil;
import Engine.Gfx.Sprite;
import Engine.Utils.Geom.Rectangle;
import Engine.Utils.Geom.Vec2;

import java.awt.image.BufferedImage;
import java.awt.Image;

public class ImageRenderer extends Component{

    public enum scaleAlgorithm{
        SMOOTH,
        FAST
    }

    public boolean isFlippedX = false, isFlippedY = false;
    public boolean visible = true;
    public void setVisible(boolean visible) { this.visible = visible;}

    private Sprite sprite = null;

    public boolean hasImage() { return sprite != null; }
    public BufferedImage getImage() {return sprite.sprite; }

    public void setImage(Sprite img) { sprite = img; }

    public ImageRenderer() {}

    public ImageRenderer(Sprite img) { this.sprite = img; }

    public Vec2 getDimensions() { return new Vec2(sprite.width, sprite.height); }

    public void scaleSprite(int width, int height, scaleAlgorithm al){
        
        Image scaled = ImageUtil.resizeImage(width, height, al, sprite.sprite);
        BufferedImage img = ImageUtil.toBufferedImage(scaled);

        sprite.sprite = img;

        this.sprite.width = width;
        this.sprite.height = height;
    }

    public void flipX(){

        Sprite newSprite = new Sprite(ImageUtil.flipImageHorizontal(sprite.sprite));

        setImage(newSprite);
        isFlippedX = !isFlippedX;
    }

    public void flipY(){

        Sprite newSprite = new Sprite(ImageUtil.flipImageVertical(sprite.sprite));

        setImage(newSprite);
        isFlippedY = !isFlippedY;
    }
}
