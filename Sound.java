package gameSystem;

import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
public class Sound {
    Clip clip;
    public void play() {
    	clip.setMicrosecondPosition(0);
    	clip.start();
    }
    public Sound(String soundString) {
    	try {
	         clip = AudioSystem.getClip();
	         clip.open(AudioSystem.getAudioInputStream(this.getClass().getClassLoader().getResource("resources/" + soundString)));
	      } catch (UnsupportedAudioFileException e) {
	         e.printStackTrace();
	      } catch (IOException e) {
	         e.printStackTrace();
	      } catch (LineUnavailableException e) {
	         e.printStackTrace();
	      }
    }
    public Sound(String soundString,boolean rootAcs) {
    	if(rootAcs) {
        	try {
   	         clip = AudioSystem.getClip();
   	         clip.open(AudioSystem.getAudioInputStream(this.getClass().getClassLoader().getResource(soundString)));
   	      } catch (UnsupportedAudioFileException e) {
   	         e.printStackTrace();
   	      } catch (IOException e) {
   	         e.printStackTrace();
   	      } catch (LineUnavailableException e) {
   	         e.printStackTrace();
   	      }
    	}else {
        	try {
   	         clip = AudioSystem.getClip();
   	         clip.open(AudioSystem.getAudioInputStream(this.getClass().getClassLoader().getResource("resources/" + soundString)));
   	      } catch (UnsupportedAudioFileException e) {
   	         e.printStackTrace();
   	      } catch (IOException e) {
   	         e.printStackTrace();
   	      } catch (LineUnavailableException e) {
   	         e.printStackTrace();
   	      }
    	}
    }
}
