package Engine.Utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import javafx.util.Pair;

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
    
    public static float[][] trueNoise(int side, float[] values) {

        float[][] noise = new float[side][side];
        Random random = new Random();

        for (int i = 0; i < side; i++)
            for (int j = 0; j < side; j++) {

                noise[i][j] = values[random.nextInt(2)];
            }

        return noise;
    }

    public static float lerp(float v0, float v1, float t) {
        
        return (1 - t) * v0 + t * v1;
    }

    public static int randomInteger(int lower, int highest){

        return ThreadLocalRandom.current().nextInt(lower, highest + 1);
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

    public static Object[] sortArray(Object[] a){

        if(a.length == 0 || a.length == 1) {System.out.println("[WARNING] The given array is of length 0 or 1, so it won't be ordered");return a;}

        Arrays.sort(a);
        return a;
    }

    public static enum EloScore{

        WIN(1f),
        DRAW(0.5f),
        LOSS(0f);

        private float change = 0;
        private EloScore(float change){

            this.change = change;
        }

        private EloScore flipScore(){
            if(change == 0.5f) return DRAW;
            return change == 0 ? WIN : LOSS;
        }
    }

    public static float K_FACTOR = 32f;

    public static Pair<Double, Double> calculateEloRatingChange(float initialElo, float oponentElo, EloScore score1){

        double R1 = Math.pow(10, initialElo / 400f);
        double R2 = Math.pow(10, oponentElo / 400f);

        double Expected1 = R1 / (R1 + R2);
        double Expected2 = R2 / (R1 + R2);

        EloScore S1 = score1;
        EloScore S2 = score1.flipScore();

        return new Pair<Double, Double>(
            R1 + K_FACTOR*(S1.change - Expected1), 
            R2 + K_FACTOR*(S2.change - Expected2)
        );
    }
}
