package aquarisim.fish;

import java.util.Random;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;

import aquarisim.Time;
import aquarisim.creature.Counter;
import aquarisim.object.Object;
import aquarisim.object.ObjectFinder;

enum FishState {
	WAITING_FOR_INSTRUCTION {
		@Override
		protected FishState nextState(Fish fish) {
			waitAndNotify(fish, new Time(0.5f));
			return WAITING_FOR_INSTRUCTION;
		}
	},
	WAITING_TO_DELIVER {
		@Override
		protected FishState nextState(Fish fish) {
			Counter c = Counter.getInstance();
			if(c.deliver(fish.getFood())) {
				fish.setFood(null);
				Random r = new Random();
				moveAndNotify(fish.getX() - (r.nextFloat() * 50), fish.getY() + 150f + (r.nextFloat() * 25), fish);
				return WAITING_FOR_INSTRUCTION.nextState(fish);
			} else {
				waitAndNotify(fish, new Time(1f));
				return WAITING_TO_DELIVER;
			}
		}
	},
	TRAVELLING_TO_COUNTER {
		@Override
		protected FishState nextState(Fish fish) {
			if(fish.getFood() != null) {
				return WAITING_TO_DELIVER.nextState(fish);
			} else {
				return WAITING_FOR_INSTRUCTION.nextState(fish);
			}
			
		}
	},
	RETRIEVING_OBJECT {
		@Override
		public FishState nextState(final Fish fish) {
			if(fish.getFood() != null) {
				fish.foodEaten();
			}
			Random r = new Random();
			Counter counter = Counter.getInstance();
			moveAndNotify(counter.getX() + 50f, counter.getY() + r.nextInt(100), fish);
			return TRAVELLING_TO_COUNTER;
		}
	},
	TRAVELLING_TO_OBJECT {
		// Object is already reserved for fish
		@Override
		public FishState nextState(final Fish fish) {
			waitAndNotify(fish, new Time(2f));
			fish.pickUpObject();
			return RETRIEVING_OBJECT;
		}
	},
	WAITING_FOR_OBJECT {
		@Override
		protected FishState nextState(Fish fish) {
			Object food = isFoodAvailable(fish);
			if(null != food) {
				eatFood(fish, food);
				return TRAVELLING_TO_OBJECT;
			} else {
				// no food available - go to plant
				waitAndNotify(fish, new Time(1f));
				return WAITING_FOR_OBJECT;
			}
		}
	},
	TRAVELLING_TO_PLANT {
		@Override
		protected FishState nextState(Fish fish) {
			return WAITING_FOR_OBJECT.nextState(fish);
		}
	};
	
	static Object isFoodAvailable(Fish fish) {
		Object food = ObjectFinder.getInstance().getNext(fish.getObjectType());
		return food;
	}
	
	static void moveAndNotify(float x, float y, Fish fish) {
		float distance = (float) Math.sqrt(Math.pow((fish.getX() - x), 2) + Math.pow((fish.getY() - y), 2));
		Time duration = new Time(distance / fish.getSpeed());
		
		Action move = Actions.moveTo(x, y, duration.getFloat());
		SequenceAction moveNotify = Actions.sequence(move, Actions.run(new NextState(fish)));
		fish.addAction(moveNotify);
	}
	
	private static void waitAndNotify(Fish fish, Time duration) {
		Random r = new Random();
		float waitTime = duration.getFloat() + new Time(r.nextFloat()).getFloat();
		
		Action wait = Actions.delay(waitTime);
		SequenceAction moveNotify = Actions.sequence(wait, Actions.run(new NextState(fish)));
		fish.addAction(moveNotify);
	}
	
	private static class NextState implements Runnable {
		private Fish fish;
		public NextState(Fish fish) {
			this.fish = fish;
		}
		@Override
		public void run() {
			fish.setState(fish.getState().nextState(fish));
		}
	}
	
	static void eatFood(Fish fish, Object f) {
		// food available - goto and eat
		f.toBeEaten(fish);
		fish.setReservedFood(f);
		moveAndNotify(f.getX(), f.getY(), fish);
	}
	
	protected abstract FishState nextState(Fish fish);
}