package Engine.Utils;
import Engine.Entity.Object;
import Engine.Utils.Geom.*;
import Assets.Objects.*;
import Engine.Components.Camera;
public class ObjectLoader {
public static Object LoadObjectOfName(String name, Vec2 position, float angle, Vec2 scale){
			Object obj = null;
			switch (name){
case "Ball" : obj = new Ball(); break;
case "Fumo" : obj = new Fumo(); break;
case "GameCamera" : obj = new GameCamera(); break;
case "Player" : obj = new Player(); break;
case "Wall" : obj = new Wall(); break;
case "WallChild" : obj = new WallChild(); break;

}
			if(obj.getComponent(Camera.class) != null){
						System.out.println("[ERROR] Can't instantiate the Camera");
return null;
}
//Apply the objects properties
obj.transform.setPosition(position);
obj.transform.setAngle(angle);
obj.transform.setScale(scale);
obj.getBehaviour().Start();
			return obj;
}
}