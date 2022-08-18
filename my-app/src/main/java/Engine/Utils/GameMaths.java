package Engine.Utils;

import java.util.Random;
import java.awt.Color;

public class GameMaths {

    public static final float[] NOISEVALUES = { 0, 1 };

    public static float clamp(float value, float min, float max) {

        if (value > max)
            value = max;
        if (value < min)
            value = min;

        return value;
    }

    private static float Noise2D(float x, float y) {

        return 0;
    }

    public static float[][] trueNoise(int side, float[] values) {

        float[][] noise = new float[side][side];
        Random random = new Random();

        for (int i = 0; i < side; i++)
            for (int j = 0; j < side; j++) {

                noise[i][j] = values[random.nextInt(2)];
            }

        return noise;
    }

    public static int randomInteger(int lower, int highest){

        return new Random().nextInt(lower, highest);
    }

    public static Color generateRandomColor(){

        Random random = new Random();

        float r = random.nextFloat();
        float g = random.nextFloat();
        float b = random.nextFloat();

        return new Color(r,g,b);
    }

    public static Color getInBetweenColor(Color c1, Color c2, float ratio){

        if ( ratio > 1f ) ratio = 1f;
        else if ( ratio < 0f ) ratio = 0f;
        float iRatio = 1.0f - ratio;
    
        int i1 = c1.getRGB();
        int i2 = c2.getRGB();
    
        int a1 = (i1 >> 24 & 0xff);
        int r1 = ((i1 & 0xff0000) >> 16);
        int g1 = ((i1 & 0xff00) >> 8);
        int b1 = (i1 & 0xff);
    
        int a2 = (i2 >> 24 & 0xff);
        int r2 = ((i2 & 0xff0000) >> 16);
        int g2 = ((i2 & 0xff00) >> 8);
        int b2 = (i2 & 0xff);
    
        int a = (int)((a1 * iRatio) + (a2 * ratio));
        int r = (int)((r1 * iRatio) + (r2 * ratio));
        int g = (int)((g1 * iRatio) + (g2 * ratio));
        int b = (int)((b1 * iRatio) + (b2 * ratio));
    
        return new Color( a << 24 | r << 16 | g << 8 | b );
    }
}
