package aquarisim.wave;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class Wave extends Actor implements IWave {
	private int number;
	private LinearWaveBehaviour behaviour;

	Wave(int number, LinearWaveBehaviour beheviour) {
		this.number = number;
		this.behaviour = beheviour;
		behaviour.setWave(this);
	}
	
	@Override
	public void act(float delta) {
		behaviour.act(delta);
	}

	@Override
	public void completed() {
		remove();
		WaveGenerator.getInstance().nextWave();
	}
	
	public int getNumber() {
		return this.number;
	}
	
	@Override
	public Actor getActor() {
		return this;
	}
}
