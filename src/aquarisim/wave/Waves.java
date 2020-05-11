package aquarisim.wave;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Waves {
	private static Waves singleton;

	private IWave[] staticWaves = {
		new Wave(1, new LinearWaveBehaviour(60f, 3, 2, 2)), // 46 seconds, $7
		new Wave(2, new LinearWaveBehaviour(55f, 4, 3, 1)), // 54 seconds, $8, 15
		new Wave(3, new LinearWaveBehaviour(50f, 4, 4, 1)), // 54 seconds, $9, 24
		// All players should have $24 and have cleared the queue
		
		// Longer queues. Boost needed?
		new WaveThenDelay(4, new LinearWaveBehaviour(60f, 6, 6, 4), 20f), //
		new Wave(5, new LinearWaveBehaviour(150f, 7, 7, 6)), // 
		new Wave(6, new LinearWaveBehaviour(120f, 7, 7, 7)), // 
		// 2 auto fish just about clear the queue
		
		// Rush waves to panic the player into using some boosts
		new WaveThenDelay(7, new LinearWaveBehaviour(10f, 2, 1, 2), 30f), // 34 seconds, $5
		new WaveThenDelay(8, new LinearWaveBehaviour(10f, 3, 2, 2), 40f), // 50 seconds, $7
		new WaveThenDelay(9, new LinearWaveBehaviour(10f, 3, 3, 2), 50f), // 62 seconds, $8
		// 2 auto fish *just* make it through but queue is full. Too hard?
		
		// Some easier waves to calm things down, then some faster waves again, 101 total by this point
		new Wave(10, new LinearWaveBehaviour(100f, 5, 5, 5)), // 116 seconds, $15
		new Wave(11, new LinearWaveBehaviour(100f, 5, 5, 5)), // 116 seconds, $15
		// At this point assume player has one L1 upgraded fish (costing $50)
		
		new WaveThenDelay(12, new LinearWaveBehaviour(10f, 3, 3, 3), 25f), // 34 seconds, $5
		new WaveThenDelay(13, new LinearWaveBehaviour(10f, 3, 2, 2), 40f), // 50 seconds, $7
		new WaveThenDelay(14, new LinearWaveBehaviour(10f, 3, 3, 2), 40f), // 62 seconds, $8
		new WaveThenDelay(15, new LinearWaveBehaviour(10f, 3, 3, 2), 40f), // 62 seconds, $8
		
		// Calm after second rush
		new Wave(16, new LinearWaveBehaviour(100f, 5, 5, 5)), // 116 seconds, $15
		new Wave(17, new LinearWaveBehaviour(100f, 5, 5, 5)), // 116 seconds, $15
		
		// Now has enough for two fish upgraded + ~$20
		new WaveThenDelay(18, new LinearWaveBehaviour(10f, 3, 3, 2), 35f), // 35 seconds, $8
		new WaveThenDelay(19, new LinearWaveBehaviour(40f, 4, 4, 4), 25f), // 65 seconds, $15
		new WaveThenDelay(20, new LinearWaveBehaviour(10f, 3, 3, 2), 35f), // 35 seconds, $8
		new WaveThenDelay(21, new LinearWaveBehaviour(65f, 5, 5, 5), 25f), // 65 seconds, $15
		new WaveThenDelay(22, new LinearWaveBehaviour(10f, 2, 2, 2), 35f), // 35 seconds, $8
		new Wave(23, new LinearWaveBehaviour(65f, 5, 5, 5)), // 65 seconds, $15
	};
	
	private List<IWave> waveList; 
	
	private Waves() {
		waveList = new ArrayList<IWave>(Arrays.asList(staticWaves));
		int highestLevel = highestWave(waveList).getNumber();
		// generate more levels up to 1000
		int target = 1000 - highestLevel;
		for(int i=highestLevel + 1;i<target;i+=4) {
			List<IWave> randomWaves = RandomWaves.getInstance().getFourWaves(i);
			waveList.addAll(randomWaves);
		}
	}
	
	private IWave highestWave(List<IWave> waves) {
		IWave highest = waves.get(0);
		for(IWave w : waves) {
			if(w.getNumber() > highest.getNumber()) { highest = w; }
		}
		return highest;
	}
	
	public IWave get(int number) {
		try {
			IWave w = waveList.get(number - 1);
			return w;
		} catch(IndexOutOfBoundsException e) {
			throw new RuntimeException("Can't find wave " + number);
		}
	}

	public static Waves getInstance() {
		if(singleton == null) {
			reset();
		}
		return singleton;
	}

	public static void reset() {
		singleton = new Waves();
	}
}