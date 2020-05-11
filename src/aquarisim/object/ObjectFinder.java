package aquarisim.object;

import java.util.ArrayList;
import java.util.List;

public class ObjectFinder {
	private List<Object> food = new ArrayList<Object>();
	private static ObjectFinder singleton;
	
	private ObjectFinder() { };
	
	public static ObjectFinder getInstance() {
		if(singleton == null) {
			reset();
		}
		return singleton;
	}

	public static void reset() {
		singleton = new ObjectFinder();
	}
	
	void addFood(Object newFood) {
		food.add(newFood);
	}
	
	void removeFood(Object toRemove) {
		food.remove(toRemove);
	}
	
	public synchronized Object getNext(ObjectType type) {
		for(Object f : food) {
			if(f.getType() == type) {
				if(!f.toBeEaten()) {
					return f;
				}
			}
		}
		return null;
	}
}
