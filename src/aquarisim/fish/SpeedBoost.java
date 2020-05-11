package aquarisim.fish;

import com.badlogic.gdx.scenes.scene2d.Actor;

import aquarisim.Main;
import aquarisim.Time;
import aquarisim.bank.Account;

public class SpeedBoost extends Actor {
	// 45 = ~30 seconds at 0.65 rate
	private final static float TIME = 45;
	private final static int COST = 5;
	private float currentTime = 0f;
	private final Fish fish;
	
	private SpeedBoost(Fish fish) {
		Account.getInstance().remove(COST);
		this.fish = fish;
		fish.startBoost();
	}
	
	@Override
	public void act(float delta) {
		currentTime += delta;
		if(currentTime > Time.convert(TIME)) {
			fish.stopBoost();
			remove();
		}
		super.act(delta);
	}
	
	static void build(Fish fish) {
		Main.getInstance().addActor(new SpeedBoost(fish));
	}
	
	static int getCost() {
		return COST;
	}
}