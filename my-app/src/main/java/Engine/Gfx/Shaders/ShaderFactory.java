package Engine.Gfx.Shaders;

import java.awt.*;

public class ShaderFactory {
    
    public static LightShader createLightShader(float intensity, float radius, String target){

        LightShader.intensity = intensity;
        LightShader.intensity = radius;
        LightShader.target = target;

        return new LightShader();
    }

    public static Gradient createGradientShader(Color color1, Color color2){ return new Gradient(color1, color2); }
    public static Vignette createVignetteShader(){ return new Vignette(); }
}
