package aquarisim;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class AquariMusic {
	private static AquariMusic singleton;
	private Music music;	
	
	public static AquariMusic getInstance() {
		if(singleton == null) {
			singleton = new AquariMusic();
		}
		return singleton;
	}
	
	public void start() {
		if(music != null) {
			music.stop();
			music.dispose();
		}
		music = Gdx.audio.newMusic(Gdx.files.internal("BackgroundMusic.mp3"));
		music.setLooping(true);
		music.play();
	}
}