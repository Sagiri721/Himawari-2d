package Engine.Utils;

import Engine.Entity.Object;
import Engine.Utils.Geom.*;
import Assets.Objects.*;
import Engine.Components.Camera;

public class ObjectLoader {
	public static Object LoadObjectOfName(String name, Vec2 position, float angle, Vec2 scale) {
		Object obj = null;
		switch (name) {
		}
		if (obj.getComponent(Camera.class) != null) {
			System.out.println("[ERROR] Can't instantiate the Camera");
			return null;
		}
		// Apply the objects properties
		obj.transform.setPosition(position);
		obj.transform.setAngle(angle);
		obj.transform.setScale(scale);
		obj.getBehaviour().Start();
		return obj;
	}
}