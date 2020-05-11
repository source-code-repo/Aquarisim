package aquarisim.wave;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomWaves {
	private static RandomWaves singleton;
	
	private enum WaveType {
		STANDARD {
			@Override
			List<IWave> getFourWaves(int startWaveNumber) {
				List<IWave> waves = new ArrayList<IWave>();
				for(int i=0;i<4;i++) {
					int waveNumber = startWaveNumber + i;
					waves.add(getStandardWave(waveNumber));
				}
				return waves;
			}
		}, RUSH {
			@Override
			List<IWave> getFourWaves(int startWaveNumber) {
				List<IWave> waves = new ArrayList<IWave>();
				for(int i=0;i<4;i++) {
					int waveNumber = startWaveNumber + i;
					float delay = (30 / (float) waveNumber) * 25;
					IWave wave = new WaveThenDelay(waveNumber, new LinearWaveBehaviour(5f, 3, 2, 2), delay);
					waves.add(wave);
				}
				return waves;
			}
		}, RUSH_WAIT {
			@Override
			List<IWave> getFourWaves(int startWaveNumber) {
				List<IWave> waves = new ArrayList<IWave>();
				for(int i=0;i<4;i+=2) {
					int waveNumber = startWaveNumber + i;
					float rushDelay = (30 / (float) waveNumber) * 25;
					IWave rush = new WaveThenDelay(waveNumber, new LinearWaveBehaviour(5f, 3, 2, 2), rushDelay);
					IWave wait = getStandardWave(waveNumber + 1);
					waves.add(rush);
					waves.add(wait);
				}
				return waves;
			}
		};
		abstract List<IWave> getFourWaves(int levelNumber);
	}
	
	private static IWave getStandardWave(int waveNumber) {
		float length = (24 / (float) waveNumber) * 70;
		float waitDelay = length / 6;
		return new WaveThenDelay(waveNumber, new LinearWaveBehaviour(length , 5, 5, 5), waitDelay);
	}

	public static RandomWaves getInstance() {
		if(singleton == null) {
			singleton = new RandomWaves();
		}
		return singleton;
	}
	
	private RandomWaves() {}
	
	public List<IWave> getFourWaves(int levelNumber) {
		WaveType type = randomType();
		return type.getFourWaves(levelNumber);
	}
	
	private WaveType randomType() {
	    int pick = new Random().nextInt(WaveType.values().length);
	    return WaveType.values()[pick];
	}
}
