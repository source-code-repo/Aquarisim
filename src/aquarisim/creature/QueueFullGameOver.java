package aquarisim.creature;

import com.badlogic.gdx.scenes.scene2d.Actor;

import aquarisim.Aquarisim;
import aquarisim.Time;
import aquarisim.ui.GameOver;
import aquarisim.wave.WaveGenerator;

public class QueueFullGameOver extends Actor {
	private final Counter counter;
	private float lastAct = 0f;
	
	public QueueFullGameOver(Counter counter) {
		this.counter = counter;
	}
	
	@Override
	public void act(float delta) {
		lastAct += delta;
		if(lastAct > Time.convert(1f)) {
			if(counter.getCurrentLength() > counter.getMaxLength()) {
				int waveNumber = WaveGenerator.getInstance().getWave().getNumber();
				Aquarisim.setNewScreen(new GameOver(waveNumber));
			}
		}
	}
}
