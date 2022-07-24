package Engine.Utils;

import java.util.Random;

public class GameMaths {

    public static final float[] NOISEVALUES = { 0, 1 };

    public static float clamp(float value, float min, float max) {

        if (value > max)
            value = max;
        if (value < min)
            value = min;

        return value;
    }

    public static float lerp(float t, float a1, float a2) {

        return a1 + t * (a2 - a1);
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

                System.out.println(noise[i][j]);
            }

        return noise;
    }

    public static int randomInteger(int lower, int highest){

        return new Random().nextInt(lower, highest);
    }
}
