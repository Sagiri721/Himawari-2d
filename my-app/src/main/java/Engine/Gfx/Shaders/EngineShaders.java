package Engine.Gfx.Shaders;

import Engine.Gfx.ShaderInterface;

public enum EngineShaders {
    
    LIGHT_SHADER(new LightShader()),
    GRADIENT(new Gradient()),
    BLUR(null),
    VIGNETTE(new Vignette());

    ShaderInterface myInterface;
    private EngineShaders(ShaderInterface sh){this.myInterface = sh; }

    public ShaderInterface build() {return myInterface; }
}
