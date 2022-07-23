package Engine.Map;

import java.awt.image.BufferedImage;

import Engine.Gfx.Sprite;

public class TileSet {

    public BufferedImage spriteSheet;
    public int width, height;

    public int sizeX, sizeY;

    BufferedImage[] sprites;

    public TileSet(Sprite image, int width, int height) {

        this.spriteSheet = image.sprite;
        this.width = width;
        this.height = height;

        cropFrames();
    }

    private void cropFrames() {

        sizeX = spriteSheet.getWidth() / width;
        sizeY = spriteSheet.getHeight() / height;

        sprites = new BufferedImage[sizeX * sizeY];

        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {

                sprites[j + (i * sizeY)] = spriteSheet.getSubimage(i * width, j * height, width, height);
            }
        }
    }

    public BufferedImage getFrame(int index) {

        return sprites[index];
    }

    public BufferedImage getFrame(int x, int y) {

        return sprites[x + (y * sizeY)];
    }
}
