package aquarisim.creature;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;

import aquarisim.bank.Account;
import aquarisim.object.Object;
import aquarisim.ui.Hud;

public class Counter extends Actor {
	private static Counter singleton;
	private CreatureQueue queue;
	private final static int MAX_LENGTH = 10;
	
	private Counter() {
	}
	
	public static Counter getInstance() {
		if(singleton == null) {
			reset();
		}
		return singleton;
	}
	
	public static void reset() {
		singleton = new Counter();
	}
	
	@Override
	public void act(float delta) {
		Hud.getInstance().setQueueLength(queue.getSize());
		Hud.getInstance().setMaxQueueLength(MAX_LENGTH);
	}
	
	public void setUp(float x, float y) {
		singleton.setPosition(x, y);
		queue = new CreatureQueue(getX() + 200f, 
				getY() + 40f,
				MAX_LENGTH);
		singleton.setTouchable(Touchable.disabled);
	}
	
	public synchronized boolean deliver(Object food) {
		if(queue.empty()) {
			return false;
		}
		
		if(queue.getHeadType().getObjectType() == food.getType()) {
			Account.getInstance().add(1);
			queue.removeHead();
			return true;
		} else {
			return false;
		}
	}

	public void addCreature(Creature creature) {
		getQueue().addCreature(creature);
	}

	public CreatureQueue getQueue() {
		return queue;
	}

	public boolean full() {
		return getQueue().full();
	}

	public int getMaxLength() {
		return MAX_LENGTH;
	}

	public int getCurrentLength() {
		return queue.getSize();
	}
}
