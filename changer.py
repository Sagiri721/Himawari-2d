# Find Main file
import os

loader = 'my-app\\src\\main\\java\\Engine\\Utils\\ObjectLoader.java'
objects = 'my-app\\src\\main\\java\\Assets\\Objects'

obj_list = [];

files = os.listdir(objects);
for file in files:
    if(len(file.split(".")) > 2):
        print("[ERROR] Your class name has a '.' in it's name, you can't run the program")

        print("OBJECT COMPILATION: unsuccessful");
        exit()

    with open(objects + "\\" + file, "r") as c:

        if("public class " + file.split(".")[0] + " extends Object implements StdBehaviour" in c.read()):
                obj_list.append(file.split(".")[0]);


with open(loader, "w") as l:

    cases = "";
    for o in obj_list:
        cases += "case \"" + o + "\": obj = new " + o + "(); break;" + "\n"

    code = """package Engine.Utils;

import Engine.Entity.Object;
import Engine.Utils.Geom.Vec2;
import Assets.Objects.*;

public class ObjectLoader {
    
        public static Object LoadObjectOfName(String name, Vec2 position, float angle, Vec2 scale){

            Object obj = null;

        switch (name){
            """+ cases +"""
        }

        if(obj.getComponent("Camera") != null){
            
            System.out.println("[ERROR] Can't instantiate the Camera");
            return null;
        }

        obj.getBehaviour().Start();

        //Apply the objects properties
        obj.transform.setPosition(position);
        obj.transform.setAngle(angle);
        obj.transform.setScale(scale);

        return obj;
    }
}
""" ;
    l.write(code);

    print("-Objects compiled");