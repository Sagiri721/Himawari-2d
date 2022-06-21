package Engine.Components;

import Engine.Gfx.Animation;
import Engine.Gfx.Sprite;

public class Animator extends Component{
    
    Animation[] animations;
    ImageRenderer renderer;

    int index, frame = 0;
    public boolean playing = false;

    public float imageSpeed = 1;
    private float increment = 0.1f, count = 0;

    public Animator(Animation[] animations, ImageRenderer renderer) {

        this.animations = animations;
        this.renderer = renderer;
    }

    public void PlayAnimation() {

        if(playing){

            if(count > imageSpeed){
                Sprite curFrame = new Sprite(animations[index].getFrame(frame));
                renderer.setImage(curFrame);

                if(frame < animations[index].endFrame - 1)
                    frame++;
                else frame = 0;

                count = 0;
            }else{  count += increment; }
        }
    }

    public void play(int index, int startFrame){ 

        this.index = index; 

        if(playing == false)
            frame = startFrame;
            
        playing = true;
    }
    public void pause(){ playing = false;}

    public void setImageIndex(int index){ this.frame = index; }
}
