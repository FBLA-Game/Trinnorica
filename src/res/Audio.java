package res;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.sampled.AudioFileFormat.Type;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import org.fbla.game.utils.Sound;
import org.fbla.game.utils.Utils;

public class Audio {
	
	static Clip background = null;
	
	
	public static synchronized void playSound(final org.fbla.game.utils.Sound sound) {
		try {
			
			
			
			final String name = sound.getSoundString();
			
			
			
			final Clip clip = AudioSystem.getClip();
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(new BufferedInputStream(Audio.class.getResourceAsStream("sounds/" + name)));
			
			clip.open(audioStream);
				
			
			
			
			clip.start();
			
			
			
			if(sound.equals(Sound.BACKGROUND)){
				background = clip;
				AudioFormat format = background.getFormat();
				long frames = background.getFrameLength();
				double time = (frames+0.0) / format.getFrameRate(); 
				
				Timer timer = new Timer();
				timer.schedule(new TimerTask() {
					
					@Override
					public void run() {
						playSound(sound);
					}
				}, (long) time*1000);
				
				Utils.broadcastMessage(name);
			}
			
		    
		} catch (Exception e) { e.printStackTrace();}
	}

	public static void nextSong() {
		background.stop();
		playSound(Sound.BACKGROUND);
	}
	
	
}
