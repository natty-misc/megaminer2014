package cz.mm14.src;

import java.io.IOException;

import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Sound {
	public static Audio loadWav(String path)
	{
		  try {
				 return AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("assets/"+path+".wav"));
				} 
				  catch (IOException e) {
					e.printStackTrace();
				}
		return null;
	}
}
