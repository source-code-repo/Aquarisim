package aquarisim.plant;

import java.util.ArrayList;
import java.util.List;

import aquarisim.object.ObjectType;

public class PlantFinder {
	private List<Plant> plants = new ArrayList<Plant>();
	private static PlantFinder singleton;
	
	private PlantFinder() { };
	
	public static PlantFinder getInstance() {
		if(singleton == null) {
			reset();
		}
		return singleton;
	}

	public static void reset() {
		singleton = new PlantFinder();
	}
	
	void addPlant(Plant newPlant) {
		plants.add(newPlant);
	}
	
	public synchronized Plant getNext(ObjectType type) {
		for(Plant p : plants) {
			if(p.getFoodType() == type) {
				return p;
			}
		}
		throw new RuntimeException("Can't find producer with type " + type);
	}
}
