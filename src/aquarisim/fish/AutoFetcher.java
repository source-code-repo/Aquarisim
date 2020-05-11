package aquarisim.fish;

import com.badlogic.gdx.scenes.scene2d.Actor;

import aquarisim.Time;
import aquarisim.creature.Counter;
import aquarisim.creature.Creature;
import aquarisim.object.Object;
import aquarisim.object.ObjectType;

public class AutoFetcher extends Actor {
	private final static boolean ENABLED = false;
	private float lastAct = 0f;
	@Override
	public void act(float delta) {
		lastAct += delta;
		if(ENABLED && lastAct > Time.convert(0.5f)) {
			assignFish();
		}
		super.act(delta);
	}
	
	private synchronized void assignFish() {
		for(Fish f : FishFinder.getInstance().getFishes()) {
			if(f.getState().equals(FishState.WAITING_FOR_INSTRUCTION)) {
				for(Creature c : Counter.getInstance().getQueue().getCreatures()) {
					ObjectType type = c.getType().getObjectType();
					if(!beingFetched(type) || moreThanOne(type)) {
						f.clearActions();
						f.goAndGetObject(type);
						break;
					}
				}
			}
		}
	}
	
	private boolean moreThanOne(ObjectType type) {
		int counter = 0;
		int currentNum = 0;
		for(Creature c : Counter.getInstance().getQueue().getCreatures()) {
			if(c.getType().getObjectType().equals(type)) {
				counter++;
			}
			currentNum++;
			if(currentNum == 3) {
				break;
			}
		}
		if(counter > 1) {
			return true;
		} else {
			return false;
		}
	}

	private boolean beingFetched(ObjectType type) {
		for(Fish f : FishFinder.getInstance().getFishes()) {
			Object object = f.getFood();
			if(object != null && object.getType().equals(type)) {
				return true;
			}
			Object reservedObject = f.getReservedObject();
			if(reservedObject != null && reservedObject.getType().equals(type)) {
				return true;
			}
			
			ObjectType fishType = f.getObjectType();
			if((f.getState().equals(FishState.WAITING_FOR_OBJECT) || 
					f.getState().equals(FishState.TRAVELLING_TO_PLANT))
					&& fishType.equals(type)) {
				return true;
			}
		}
		return false;
	}
}
