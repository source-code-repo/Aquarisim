package aquarisim.wave;

import aquarisim.Main;

public class WaveGenerator {
	private int currentLevel = 1;
	private static WaveGenerator singleton;
	
	private WaveGenerator() { }
	
	public static WaveGenerator getInstance() {
		if(singleton == null) {
			reset();
		}
		return singleton;
	}
	
	public static void reset() {
		singleton = new WaveGenerator();
	}
	
	public void nextWave() {
		IWave wave = Waves.getInstance().get(currentLevel);
		Main.getInstance().showMessage("Wave " + currentLevel + " starting");
		Main.getInstance().addActor(wave.getActor());
		currentLevel++;
	}
	
	public IWave getWave() {
		return Waves.getInstance().get(currentLevel - 1);
	}
}