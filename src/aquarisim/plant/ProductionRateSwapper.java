package aquarisim.plant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.badlogic.gdx.scenes.scene2d.Actor;

import aquarisim.Time;

public class ProductionRateSwapper extends Actor {
	private float lastChange = 25f;
	private Time CHANGE_RATE = new Time(30f);
	boolean done = false;
	enum Rates {
		NONE {
			@Override
			long getRate() {
				return 5;
			}
		},
		MEDIUM {
			@Override
			long getRate() {
				return 4;
			}
		},
		HIGH {
			@Override
			long getRate() {
				return 3;
			}
		};
		abstract long getRate();
	}
	
	private Plant plant1, plant2, plant3; 
	
	public ProductionRateSwapper(Plant plant1, Plant plant2, Plant plant3) {
		this.plant1 = plant1;
		this.plant2 = plant2;
		this.plant3 = plant3;
	}
	
	@Override
	public void act(float delta) {
		lastChange += delta;
		if(lastChange > CHANGE_RATE.getFloat() && !done) {
			// DEBUGGING - REMOVE WHEN DONE
//			done = true;
			lastChange = 0f;
			List<Plant> plants = new ArrayList<Plant>();
			plants.add(plant1);
			plants.add(plant2);
			plants.add(plant3);
			Collections.shuffle(plants);
			
			plants.get(0).setProductionRate(Rates.NONE.getRate());
			plants.get(1).setProductionRate(Rates.MEDIUM.getRate());
			plants.get(2).setProductionRate(Rates.HIGH.getRate());
		}
		super.act(delta);
	}
}