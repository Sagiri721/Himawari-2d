package Engine.Map;

import java.awt.image.BufferedImage;
import java.io.Serializable;

import Engine.Gfx.Sprite;

public class TileSet implements Serializable {

    transient public Sprite spriteSheet;
    public int width, height;

    public int sizeX, sizeY;

    transient Sprite[] sprites;

    public TileSet(Sprite image, int width, int height) {

        this.spriteSheet = image;
        this.width = width;
        this.height = height;

        cropFrames();
    }

    private void cropFrames() {

        sizeX = spriteSheet.sprite.getWidth() / width;
        sizeY = spriteSheet.sprite.getHeight() / height;

        sprites = new Sprite[sizeX * sizeY];

        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {

                sprites[j + (i * sizeY)] = new Sprite(spriteSheet.sprite.getSubimage(i * width, j * height, width, height));
            }
        }
    }

    public BufferedImage getFrame(int index) {

        return sprites[index].sprite;
    }

    public BufferedImage getFrame(int x, int y) {

        return sprites[x + (y * sizeY)].sprite;
    }
}
