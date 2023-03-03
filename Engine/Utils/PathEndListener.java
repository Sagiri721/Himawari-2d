package Engine.Utils;

import Engine.Utils.Geom.Vec2;

public interface PathEndListener {
    
    public void pathStart();
    public void pathEnd();
    public void pathMoved(Vec2 position);
}
