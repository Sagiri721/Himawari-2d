package Engine.Gfx;

import java.awt.*;
import javax.swing.*;

public interface ShaderInterface {

    public boolean active = true;
    
    public void graphicsUpdate();
    public void render(Graphics g, JComponent c);
}
