package aquarisim.fish;

import java.util.ArrayList;
import java.util.List;

public class FishFinder {
	private List<Fish> fishes = new ArrayList<Fish>();
	private static FishFinder singleton;
	
	private FishFinder() { };
	
	public static FishFinder getInstance() {
		if(singleton == null) {
			reset();
		}
		return singleton;
	}

	public static void reset() {
		singleton = new FishFinder();
	}
	
	void addFish(Fish newFish) {
		fishes.add(newFish);
	}
	
	public List<Fish> getFishes() {
		return fishes;
	}
}