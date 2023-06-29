package Engine.Utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import Engine.Components.Body;
import Engine.Components.ImageRenderer;
import Engine.Components.RectCollider;
import Engine.Entity.Object;
import Engine.Input.Input;
import Engine.Input.KeyboardReader;
import Engine.Physics.Physics;
import Engine.Utils.Geom.Vec2;

public class Exporter {

    public static void main(String[] args) {

        String pack = "com/com/com/";
        String art = "";

        List<String> objects = new ArrayList<String>();

        File objectFolder = new File(
                System.getProperty("user.dir") + "/" + art + "src/main/java/" + pack + "Assets/Objects");
        if (!objectFolder.exists() && !objectFolder.isDirectory()) {

            System.out.println("Export ERROR, no such folder " + objectFolder.getAbsolutePath());
            return;
        }

        for (File f : objectFolder.listFiles()) {

            if (isAlert(f))
                continue;

            String name = f.getName().substring(0, f.getName().lastIndexOf("."));
            objects.add(name);

            try {
                Object.Instantiate(name);
            } catch (Exception e) {
            }
        }

        Gson g = new GsonBuilder().setPrettyPrinting().create();
        Object[] copy = Object.objects.toArray(new Object[Object.objects.size()]);
        ObjectAdaptation[] models = new ObjectAdaptation[copy.length];

        assignmentLoop: for (int i = 0; i < models.length; i++) {

            models[i] = new ObjectAdaptation();

            Object obj = copy[i];
            if (i >= 1)
                for (ObjectAdaptation objectAdaptation : models)
                    if (objectAdaptation != null && objectAdaptation.id == obj.getId())
                        continue assignmentLoop;

            models[i].active = obj.active;
            models[i].angle = obj.transform.angle;
            models[i].id = obj.getId();
            models[i].layer = (byte) obj.getLayer();
            models[i].name = obj.getName();
            models[i].parent = obj.node.isSupremeParent() ? null : obj.node.parent.object.getName();
            models[i].position = obj.transform.position;
            models[i].tag = obj.getTag();
            models[i].scale = obj.transform.scale;
            models[i].isStatic = obj.isStatic();
            models[i].className = obj.getClass().getName();

            // Components
            models[i].components = Arrays.stream(obj.getComponents())
                    .map(c -> c.getClass().getName().substring(c.getClass().getName().lastIndexOf(".") + 1,
                            c.getClass().getName().length()))
                    .collect(Collectors.toList()).toArray(new String[obj.componentCount()]);
            Set<String> set = new HashSet<String>(Arrays.asList(models[i].components));
            models[i].components = set.toArray(new String[set.size()]);

            ImageRenderer renderer = (ImageRenderer) obj.getComponent(ImageRenderer.class);
            if (renderer != null && renderer.getSprite() != null) {

                models[i].image = renderer.getSprite().imageFile.getAbsolutePath();
                models[i].visible = renderer.visible;
                models[i].spriteDimensions = renderer.getDimensions();
            }

            RectCollider collider = (RectCollider) obj.getComponent(RectCollider.class);
            if (collider != null) {
                models[i].colliderDimensions = collider.bounds;
            }

            Body body = (Body) obj.getComponent(Body.class);
            if (body != null) {
                models[i].mass = body.mass;
                models[i].drag = body.drag;
            }
        }

        try (FileWriter fw = new FileWriter(
                new File(System.getProperty("user.dir") + "/" + art + "src/main/java/" + pack
                        + "Engine/Assets/MyObjectDataDump.json"))) {

            fw.write(g.toJson(models));

        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileWriter fw = new FileWriter(
                new File(
                        System.getProperty("user.dir") + "/" + art + "src/main/java/" + pack
                                + "Engine/Assets/MyGameData.json"))) {

            fw.write(g.toJson(new EngineData()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean isAlert(File f) {

        try (Scanner s = new Scanner(f)) {

            while (s.hasNextLine()) {

                if (s.nextLine().contains("implements AlarmFunctionality"))
                    return true;
            }

            return false;

        } catch (Exception e) {
            return false;
        }
    }

    static class ObjectAdaptation {

        public ObjectAdaptation() {
        }

        public Integer id = 0;
        public String name = null, className = null;
        public String[] components = null;
        public Vec2 position = null, scale = null, spriteDimensions = null, colliderDimensions = null;
        public Float angle = null, mass = null, drag = null;

        public String tag = null;
        public String image = null;
        public Byte layer = null;
        public String parent = null;
        public Boolean active = null, visible = null, isStatic = null;
    }

    static class EngineData {

        public EngineData() {
        }

        int delay = Renderer.DELAY;
        boolean fixed = Renderer.fixedDelta;
        float grav = Physics.G;
        boolean cap = Physics.accelearion_capped;
        float capValue = Physics.acceleration_treshold;
        float details = Physics.raycast_detail;
        boolean ignore = Physics.ignoreSelf;
        int limit = KeyboardReader.limit;
        char[] map = Input.keyMap;
    }
}
