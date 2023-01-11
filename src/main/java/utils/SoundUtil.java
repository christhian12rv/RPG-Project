package utils;

import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundUtil {
	private String fileName;
	private static Clip clip;
    private static javax.sound.sampled.AudioInputStream audioIn;

	public synchronized void playSound(String sound) {
		try {
				fileName = sound;
				audioIn = AudioSystem.getAudioInputStream(getClass().getResource("/" + sound + ".wav"));
				clip = AudioSystem.getClip();
				clip.open(audioIn);
				clip.loop(Clip.LOOP_CONTINUOUSLY);
				clip.start();
		} catch (Exception e) {
				e.printStackTrace();
		}
	}

	public synchronized void stopSound() {
			try {
					clip.stop();
					audioIn.close();
					clip.close();
			} catch (Exception e) {
					e.printStackTrace();
			}
	}

	public String getFileName() {
		return fileName;
	}

	public void setFClip(String fileName) {
		this.fileName = fileName;
	}
}
