package aquarisim.wave;

import com.badlogic.gdx.scenes.scene2d.Actor;

import aquarisim.Time;

public class WaveThenDelay extends Actor implements IWave {
	private int number;
	private LinearWaveBehaviour behaviour;
	private final float delayTime;
	private boolean delaying = false;
	private float waitCounter = 0f;

	WaveThenDelay(int number, LinearWaveBehaviour behaviour, float delayTime) {
		this.number = number;
		this.behaviour = behaviour;
		this.delayTime = delayTime;
		behaviour.setWave(this);
	}
	
	@Override
	public void act(float delta) {
		if(!delaying) {
			behaviour.act(delta);
		} else {
			waitCounter += delta;
			if(waitCounter > Time.convert(delayTime)) {
				finish();
			}
		}
	}

	@Override
	public void completed() {
		delaying = true;		
	}
	
	public int getNumber() {
		return this.number;
	}
	
	private void finish() {
		remove();
		behaviour = null;
		WaveGenerator.getInstance().nextWave();
	}
	@Override
	public Actor getActor() {
		return this;
	}
}