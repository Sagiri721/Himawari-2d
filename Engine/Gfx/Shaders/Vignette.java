package Engine.Gfx.Shaders;

import java.awt.*;
import javax.swing.JComponent;
import Engine.Gfx.ShaderInterface;
import Engine.Utils.Window;

public class Vignette implements ShaderInterface {

    public Vignette() {

    }

    @Override
    public void graphicsUpdate() {
        
    }

    @Override
    public void render(Graphics g, JComponent c) {

        Graphics2D g2 = (Graphics2D) g.create();

        int w = Window.width;
        int h = Window.height;

        java.awt.geom.Point2D center = new java.awt.geom.Point2D.Float(w / 2, h / 2);
        float[] dist = {0.0f, 1.0f};
        Color[] colors = {new Color(0.0f, 0.0f, 0.0f, 0.0f), Color.BLACK};
        
        RadialGradientPaint p = new RadialGradientPaint(center, w / 2, dist, colors);
        g2.setPaint(p);
        g2.setComposite(AlphaComposite.getInstance(
            AlphaComposite.SRC_OVER, 1));
        g2.fillRect(0, 0, c.getWidth(), c.getHeight());
        
        g2.dispose();
    }
}
