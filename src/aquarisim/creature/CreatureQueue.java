package aquarisim.creature;

import java.util.LinkedList;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import aquarisim.Time;

public class CreatureQueue extends Actor {
	private LinkedList<Creature> creatures = new LinkedList<Creature>();
	private int maxLength;
	
	CreatureQueue(float x, float y, int maxLength) {
		this.maxLength = maxLength;
		setPosition(x, y);
		setTouchable(Touchable.disabled);
	}
	
	synchronized void addCreature(Creature creature) {
		float queueWidth = 0;
		for(Creature c : creatures) {
			queueWidth += c.getWidth();
		}
		creatures.add(creature);
		creature.setX(getX() + queueWidth);
		creature.setY(getY());
		creature.setVisible(true);
	}
	
	boolean full() {
		if(creatures.size() > maxLength) {
			return true;
		} else {
			return false;
		}
	}
	
	boolean empty() {
		if(creatures.size() == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	CreatureType getHeadType() {
		return creatures.getFirst().getType();
	}

	public void removeHead() {
		Creature c = creatures.getFirst();
		c.remove();
		creatures.removeFirst();
		moveQueue();
	}
	
	void moveQueue() {
		float queueLength = getX();
		for(Creature c : creatures) {
			float x = queueLength;
			float distance = (float) Math.sqrt(Math.pow((c.getX() - x), 2));
			Time duration = new Time(distance / 100f);
			
			Action move = Actions.moveTo(x, c.getY(), duration.getFloat());
			c.addAction(move);
			queueLength += c.getWidth();
		}
	}
	
	int getSize() {
		return creatures.size();
	}
	
	// TODO: Debugging, remove when done
	public LinkedList<Creature> getCreatures() {
		return creatures;
	}
}