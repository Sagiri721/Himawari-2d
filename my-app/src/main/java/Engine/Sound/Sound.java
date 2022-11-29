package Engine.Sound;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import com.google.common.io.Files;

import Engine.Utils.Window;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class Sound {

    private Player player;
    private FileInputStream fileInputStream;

    private Clip clip;
    private AudioInputStream inputStream;

    public boolean looping = false;
    public long microLength;

    private Thread playingThread;
    private SupportedFileFormats format;

    public Sound(String path, boolean looping){

        File file = new File(Window.RelativeResourcePath + "Sounds/" + path);
        getExtensionByStringHandling(file.getName());

        try {

            if(format==SupportedFileFormats.MP3){

                fileInputStream = new FileInputStream(file);
                BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);

                player = new Player(bufferedInputStream);

            }else if(format==SupportedFileFormats.WAV){

                clip = AudioSystem.getClip();
                inputStream = AudioSystem.getAudioInputStream(file);
                clip.open(inputStream);
    
                microLength = clip.getMicrosecondLength();
    
                if(looping)
                    clip.loop(Clip.LOOP_CONTINUOUSLY);    
            }else{

            }
            
            this.looping = looping;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getExtensionByStringHandling(String filename) {
        
        String ex = Files.getFileExtension(filename);

        switch(ex){

            case "wav":
                format = SupportedFileFormats.WAV;
                break;
            case "mp3":
                format = SupportedFileFormats.MP3;
                break;
            case "ogg":
                format = SupportedFileFormats.OGG;
                break;
        }
    }

    public void play(){

        playingThread = new Thread(playsound);
        playingThread.start();
    }

    Runnable playsound = new Runnable() {

        @Override
        public void run() {

            if(format==SupportedFileFormats.MP3){
            
                //Play mp3 sound
                try {
                    player.play();
                } catch (JavaLayerException e) {
                    e.printStackTrace();
                }
            }else if(format==SupportedFileFormats.WAV){

                //Play wav sound
                clip.start();
            }else{

            }
        }
    };

    public void stop() {

        //Stop playing thread
        playingThread.interrupt();
    }
}
