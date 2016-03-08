package res;

import java.io.BufferedInputStream;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

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
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
}
