package aquarisim.wave;

import com.badlogic.gdx.scenes.scene2d.Actor;

public interface IWave {
	// Must notify the WaveGenerator of completion in here
	void completed();
	int getNumber();
	Actor getActor();
}
