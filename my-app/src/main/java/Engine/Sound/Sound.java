package Engine.Sound;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import Engine.Utils.Window;

public class Sound {

    private Clip clip;
    private AudioInputStream inputStream;

    public boolean looping = false;
    public long microLength;

    public Sound(String path, boolean looping){

        try {
            
            clip = AudioSystem.getClip();
            inputStream = AudioSystem.getAudioInputStream(new File(Window.RelativeResourcePath + "Sounds/" + path));
            clip.open(inputStream);

            microLength = clip.getMicrosecondLength();

            if(looping)
                clip.loop(Clip.LOOP_CONTINUOUSLY);

            this.looping = looping;

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void play(){ 
    
        new Thread(new Runnable() {

            @Override
            public void run() {
                clip.start();       
            }
         
            
        }).start();
    }

    public void stop() {

        if(!looping)
            return;

        clip.stop();
    }
}
