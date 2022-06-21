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

    long microLength;

    public Sound(String path){

        try {
            
            clip = AudioSystem.getClip();
            inputStream = AudioSystem.getAudioInputStream(new File(Window.RelativeResourcePath + "Sounds/" + path));
            clip.open(inputStream);

            microLength = clip.getMicrosecondLength();

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
}
