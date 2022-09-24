package Engine.Components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import Engine.Gfx.Animation;
import Engine.Gfx.Sprite;

public class Animator extends Component{
    
    private List<Animation> animations = new ArrayList<>();
    private ImageRenderer renderer;

    private int index /* The index is the currently playing animation */, frame = 0;
    public boolean playing = false;

    public float imageSpeed = 1;
    private float increment = 0.1f, count = 0;

    private Map<String, Boolean> triggers = new HashMap<>();
    private List<Animation> triggerAnimations = new ArrayList<>();

    /**
     * The animator receives all the animations and plays them once the play() function is called
     * 
     * @param animations
     * @param renderer
     */
    public Animator(Animation[] animations, ImageRenderer renderer) {

        this.animations = Arrays.asList(animations);
        this.renderer = renderer;
    }

    public void addAnimation(Animation anim){

        this.animations.add(anim);
    }
    /**
     * A trigger is connected to an animation, once it goes true the animation will play
     * 
     * @param anim
     * @param trigger
     */
    public void addTriggerAnimation(Animation anim, String triggerName){

        if(triggerNameExists(triggerName) != null){

            System.out.println("[ERROR] This trigger name already exists");
        }

        triggerAnimations.add(anim);
        triggers.put(triggerName, false);
    }

    public Boolean getTriggerValue(String name){

        if(triggerNameExists(name) == null){

            System.out.println("[ERROR] Can't get this trigger as '" +name+"' was not found on the map");
            return null;
        }

        return triggers.get(name);
    }

    public void setTriggerValue(String name, boolean value){

        if(triggerNameExists(name) == null){

            System.out.println("[ERROR] Can't set this trigger as '" +name+"' was not found on the map");
            return;
        }

        triggers.replace(name, value);
    }

    private Integer triggerNameExists(String name){

        for (int i = 0; i < triggers.size(); i++){

            if(triggers.get(name) != null){

                return i;
            }
        }

        return null;
    }

    public void PlayAnimation() {

        //Play the current animation
        if(playing){

            List<Animation> source = animations;

            if(count > imageSpeed){

                Object[] keyset = triggers.keySet().toArray();
                for (int i = 0; i < triggers.size(); i++){
    
                    if(triggers.get(keyset[i].toString())){

                        source = triggerAnimations;
                        index = i;
                        
                        break;
                    }
                }

                Sprite curFrame = new Sprite(source.get(index).getFrame(frame));
                renderer.setImage(curFrame);

                if(frame < animations.get(index).endFrame - 1)
                    frame++;
                else frame = 0;

                count = 0;
            }else{  count += increment; }
        }
    }

    public void play(int index, int startFrame){
        
        if(index >= animations.size() || index < 0){

            System.out.println("[ERROR] The inputed animation does not exist");
            return;
        }

        this.index = index; 

        if(playing == false)
            frame = startFrame;
            
        playing = true;
    }
    public void pause(){ playing = false;}

    public void setImageIndex(int index){ this.frame = index; }
}
