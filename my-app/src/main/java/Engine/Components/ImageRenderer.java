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
    private Sprite currentSprite = null;

    public boolean hasImage() { return sprite != null; }
    public BufferedImage getImage() {return currentSprite.sprite; }

    public void setImage(Sprite img) { currentSprite = img; }

    public ImageRenderer() {visible = false;}

    public ImageRenderer(Sprite img) { this.sprite = img; this.currentSprite = this.sprite; }

    public Vec2 getDimensions() { return new Vec2(sprite.width, sprite.height); }

    public void scaleSprite(int width, int height, scaleAlgorithm al){
        
        Image scaled = ImageUtil.resizeImage(width, height, al, sprite.sprite);
        BufferedImage img = ImageUtil.toBufferedImage(scaled);

        sprite.sprite = img;
        this.currentSprite = sprite;

        this.sprite.width = width;
        this.sprite.height = height;

        this.currentSprite.width = width;
        this.currentSprite.height = height;
    }

    public void flipX(){

        Sprite newSprite = new Sprite(ImageUtil.flipImageHorizontal(getImage()));

        setImage(newSprite);
        isFlippedX = !isFlippedX;
    }

    public void flipY(){

        Sprite newSprite = new Sprite(ImageUtil.flipImageVertical(getImage()));

        setImage(newSprite);
        isFlippedY = !isFlippedY;
    }
}
