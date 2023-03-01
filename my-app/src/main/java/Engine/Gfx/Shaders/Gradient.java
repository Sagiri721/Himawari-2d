package Engine.Gfx.Shaders;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;
import java.awt.*;
import Engine.Gfx.ShaderInterface;

public class Gradient implements ShaderInterface {

    private Color c1 = Color.YELLOW, c2 = Color.RED;
    public Gradient(Color c1, Color c2) {

        this.c1 = c1;
        this.c2 = c2;
    }

    @Override
    public void graphicsUpdate() {

    }

    @Override
    public void render(Graphics g, JComponent c) {

        Graphics2D g2 = (Graphics2D) g.create();

        int w = c.getWidth();
        int h = c.getHeight();
        g2.setComposite(AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER, .5f));
        g2.setPaint(new GradientPaint(0, 0, c1, 0, h, c2));
        g2.fillRect(0, 0, w, h);

        g2.dispose();
    }
    
}
