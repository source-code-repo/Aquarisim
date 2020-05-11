package aquarisim.wave;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

import aquarisim.Main;
import aquarisim.Time;
import aquarisim.creature.Counter;
import aquarisim.creature.Creature;
import aquarisim.creature.CreatureType;

public class LinearWaveBehaviour {
	private IWave wave;
	Queue<Creature> creatures;
	private float lastAct = 0f;
	private float actInterval = 0;
	private boolean done = false;
	private final float length;
	private final int numSharks;
	private final int numOctopuses;
	private final int numOysters;
	private boolean setUp = false;
	
	LinearWaveBehaviour(float length, int numSharks, int numOctopuses, int numOysters) {
		this.length = length;
		this.numSharks = numSharks;
		this.numOctopuses = numOctopuses;
		this.numOysters = numOysters;
		
	}
	
	void setWave(IWave wave) {
		this.wave = wave;
	}
	
	// It acts but it's not an actor!
	public void act(float delta) {
		if(!setUp) {
			setUp();
			setUp = true;
		}
		
		lastAct += delta;
		// At the end of a wave wait for 2 * the act interval 
		if(creatures.size() == 0 && !done) {
			lastAct = 0f;
			done = true;
			actInterval = 2 * actInterval;
		}
		
		// Spread creature creation out evenly
		if(lastAct > Time.convert(actInterval)) {
			if(creatures.size() == 0) {
				wave.completed();
				creatures.clear();
				wave = null;
				creatures = null;
				return;
			}
			lastAct = 0;
			Creature creature = creatures.remove();
			Main.getInstance().addToTop(creature);
			Counter.getInstance().addCreature(creature);
		}
	}
	
	// for debugging
	public float getLength() {
		return actInterval * creatures.size();
	}
	
	private void setUp() {
		LinkedList<Creature> creaturesList = new LinkedList<Creature>();
		for(int i=0;i<numSharks;i++) {
			creaturesList.add(new Creature(0, 0, CreatureType.SHARK));
		}
		
		for(int i=0;i<numOctopuses;i++) {
			creaturesList.add(new Creature(0, 0, CreatureType.OCTOPUS));
		}
		
		for(int i=0;i<numOysters;i++) {
			creaturesList.add(new Creature(0, 0, CreatureType.OYSTER));
		}

		Collections.shuffle(creaturesList);
		creatures = creaturesList;

		actInterval = length / creaturesList.size();
	}
}
