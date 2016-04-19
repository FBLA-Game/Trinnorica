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
	
//	static Clip background = null;
	
	static Map<String, Clip> sounds = new HashMap<>();
	
	public static synchronized void playSound(final org.fbla.game.utils.Sound sound) {
		try {
			
			
			
			final String name = sound.getSoundString();
			
			
			
			final Clip clip = AudioSystem.getClip();

			
			if(!sounds.containsKey(name)){
				AudioInputStream audioStream = AudioSystem.getAudioInputStream(new BufferedInputStream(Audio.class.getResourceAsStream("sounds/" + name)));
//				AudioSystem.write
//				Utils.broadcastMessage("saved: " + name);
				clip.open(audioStream);
				sounds.put(name, clip);
				
			} else {
				Utils.broadcastMessage("opened: " + name);
				sounds.get(name).open();
			}
			
			
			clip.start();
			
			
			Timer timer = new Timer();
			timer.schedule(new TimerTask() {
				
				@Override
				public void run() {
					Utils.broadcastMessage("stopped: "  + name);
					
//					clip.setFramePosition(-1);
//					clip.setMicrosecondPosition(0);
//					clip.flush();
//					clip.stop();
				}
			}, (long) ((sounds.get(name).getFrameLength()+0.0) / sounds.get(name).getFormat().getFrameRate())*1000);
			
//			if(sound.equals(Sound.BACKGROUND)){
//				background = clip;
//				AudioFormat format = sounds.get(name).getFormat();
//				long frames = sounds.get(name).getFrameLength();
//				double time = (frames+0.0) / format.getFrameRate(); 
//				
//				Timer timer = new Timer();
//				timer.schedule(new TimerTask() {
//					
//					@Override
//					public void run() {
//						playSound(sound);
//					}
//				}, (long) time*1000);
//				
//				Utils.broadcastMessage(name);
//			}
			
		    
		} catch (Exception e) { e.printStackTrace();}
	}

	public static void nextSong() {
//		background.stop();
//		playSound(Sound.BACKGROUND);
	}
	
	
}
