package Engine.Gfx;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.plaf.LayerUI;

public class ShaderPane extends LayerUI<JComponent> {

    public static List<ShaderInterface> shaderList = new ArrayList<ShaderInterface>();

    public static void LoadShader(ShaderInterface shader){
        shaderList.add(shader);
    }
 
    @Override
    public void paint (Graphics g, JComponent c) {
      Graphics2D g2 = (Graphics2D)g.create();
  
      // Paint the view.
      super.paint (g2, c);

      if(shaderList.size() != 0){

        for (ShaderInterface shaderInterface : shaderList) {
            
            shaderInterface.render(g2, c);
            shaderInterface.graphicsUpdate();
        }
      }

      g2.dispose();
    }
}
