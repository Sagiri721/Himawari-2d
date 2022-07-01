package Engine.Utils;

import java.util.Random;

public class GameMaths {

    public static final float[] NOISEVALUES = {0, 1};
    
    public static float clamp(float value, float min, float max) {

        if(value > max) value = max;
        if(value < min) value = min;

        return value;
    }

    public static float lerp(float t, float a1, float a2){

        return a1 + t*(a2-a1);
    }

    /**
     * Should not be called on update
     * Generates a perlin noise texture
     * 
     * @param side the size of the texture
     * @return the vector of values between 0 and 1
     */
    public static float[][] perlinNoise(int side){

        float[][] pixels = new float[side][side];

        for(int y = 0; y < 500; y++){
            for(int x = 0; x < 500; x++){
                //Noise2D generally returns a value in the range [-1.0, 1.0]
                float n = Noise2D(x*0.01f, y*0.01f);
                
                //Transform the range to [0.0, 1.0], supposing that the range of Noise2D is [-1.0, 1.0]
                n += 1.0f;
                n /= 2.0f;
		
		        pixels[y][x] = n;
	        }
        }

        return pixels;
    }

    private static float Noise2D(float x, float y){
        
        return 0;
    }

    public static float[][] trueNoise(int side, float[] values){

        float[][] noise = new float[side][side];
        Random random = new Random();

        for(int i = 0; i < side; i++)
            for(int j = 0; j < side; j++){

                noise[i][j] = values[random.nextInt(2)];

                System.out.println(noise[i][j]);
            }
        
        return noise;
    }
}
