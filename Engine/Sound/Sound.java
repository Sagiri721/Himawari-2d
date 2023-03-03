package Engine.Sound;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import com.google.common.io.Files;

import Engine.Utils.Window;
import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.BitstreamException;
import javazoom.jl.decoder.Header;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class Sound implements Serializable {

    //MP3 variables
    private Player player;
    private FileInputStream fileInputStream;

    //WAV variables
    private Clip clip;
    private AudioInputStream inputStream;

    public boolean looping = false;

    private Thread playingThread;
    private SupportedFileFormats format;

    private float length = 0f;

    public Sound(String path, boolean looping){

        File file = new File(Window.RelativeResourcePath + "Sounds/" + path);
        getExtensionByStringHandling(file.getName());

        try {

            if(format==SupportedFileFormats.MP3){

                fileInputStream = new FileInputStream(file);
                BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);

                player = new Player(bufferedInputStream);

                //Get audio length
                
                length = getMP3Duration(file);
                System.out.println(length);

            }else if(format==SupportedFileFormats.WAV){

                clip = AudioSystem.getClip();
                inputStream = AudioSystem.getAudioInputStream(file);
                clip.open(inputStream);
    
                length = clip.getMicrosecondLength() / 1000000;
    
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

        if(format == SupportedFileFormats.MP3){

            //Stop mp3 file from playing
            player.close();
        }else if (format == SupportedFileFormats.WAV){

            //Stop wav file from playing
            clip.close();
        }
    }
    
    public float getLength(){

        return length;
    }

    public float getMP3Duration(File filename){

        Header h = null;
        FileInputStream file = null;
        try {
            file = new FileInputStream(filename);
        } catch (FileNotFoundException ex) {}

        Bitstream bitstream = new Bitstream(file);
        try {
            h = bitstream.readFrame();
        } catch (BitstreamException ex) {}
        long tn = 0;
        try {
            tn = file.getChannel().size();
        } catch (IOException ex) {}
        
        return h.total_ms((int) tn)/1000;
    }
}
