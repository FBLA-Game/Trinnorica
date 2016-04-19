package res;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import org.fbla.game.utils.Sound;
import org.fbla.game.utils.Utils;

public class Audio {
	
	public static synchronized void playSound(final org.fbla.game.utils.Sound sound) {
		try {
			InputStream audioSrc = Audio.class.getResourceAsStream("sounds/" + sound.getSoundString());
			//add buffer for mark/reset support
			InputStream bufferedIn = new BufferedInputStream(audioSrc);
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(bufferedIn);
			Clip clip = AudioSystem.getClip();
			clip.open(audioStream);
			clip.start();
			
			if(sound.equals(Sound.BACKGROUND)){
				AudioFormat format = audioStream.getFormat();
				long frames = audioStream.getFrameLength();
				double time = (frames+0.0) / format.getFrameRate(); 
				
				Timer timer = new Timer();
				timer.schedule(new TimerTask() {
					
					@Override
					public void run() {
						playSound(sound);
					}
				}, (long) time*1000);
				
				Utils.broadcastMessage(time +"");
			}
		    
		} catch (Exception e) { e.printStackTrace();}
	}
	
	
}
