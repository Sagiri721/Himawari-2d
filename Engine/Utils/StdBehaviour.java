package Engine.Utils;

import java.awt.Graphics2D;

public interface StdBehaviour {
    
    public void Start();
    public void Update(float deltaTime);

    public void DrawGUI(Graphics2D g);

    public void ReceiveMessage(String message);
}
