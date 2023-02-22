import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class Changer {

	public static void main(String[] args) {

		String spackage = "";

		File loader = new File("my-app\\src\\main\\java\\" + spackage + "\\Engine\\Utils\\ObjectLoader.java");
		File objects = new File("my-app\\src\\main\\java\\" + spackage + "\\Assets\\Objects");

		String cases = "";

		List<String> objectList = new ArrayList<String>();

		for (File f : objects.listFiles()) {

			if (f.isDirectory()) {
				System.out.println("[ERROR] Directory found in Object folder");
				continue;
			}

			try {
				Scanner s = new Scanner(f);

				while (s.hasNextLine()) {

					if (s.nextLine().contains("extends Object implements StdBehaviour")) {

						String name = f.getName().substring(0, f.getName().lastIndexOf("."));
						cases += "case \"" + name + "\" : obj = new " + name + "(); break;\n";
						objectList.add(f.getName());
						break;
					}
				}
				s.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		try {
            FileWriter fw = new FileWriter(loader);
            spackage = spackage.replace("\\", ".") + (spackage == "" ? "" : ("."));

            fw.write("package " + spackage + "Engine.Utils;\nimport " + spackage + "Engine.Entity.Object;\nimport "
                    + spackage + "Engine.Utils.Geom.*;\nimport " + spackage + "Assets.Objects.*;"
                    + "\nimport " + spackage + "Engine.Components.Camera;"
                    + "\npublic class ObjectLoader {\npublic static Object LoadObjectOfName(String name, Vec2 position, float angle, Vec2 scale){\n			Object obj = null;\n			switch (name){\n"
                    + cases
                    + "\n}\n			if(obj.getComponent(Camera.class) != null){\n						System.out.println(\"[ERROR] Can't instantiate the Camera\");\nreturn null;\n}\n//Apply the objects properties\nobj.transform.setPosition(position);\nobj.transform.setAngle(angle);\nobj.transform.setScale(scale);\nobj.getBehaviour().Start();\n			return obj;\n}\n}");
            fw.close();

            System.out.println("-Objects Compiled");
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}
