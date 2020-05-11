package aquarisim;

import com.badlogic.gdx.scenes.scene2d.Group;

import aquarisim.creature.Counter;
import aquarisim.creature.QueueFullGameOver;
import aquarisim.fish.AutoFetcher;
import aquarisim.fish.Fish;
import aquarisim.fish.FishUpgradePermission;
import aquarisim.fish.FishUpgrader.FishUpgrade;
import aquarisim.object.ObjectType;
import aquarisim.plant.Plant;
import aquarisim.plant.PlantUpgradePermission;
import aquarisim.plant.PlantUpgrader.PlantUpgrade;
import aquarisim.plant.ProductionRateSwapper;
import aquarisim.ui.Hud;

public class LevelBuilder {
	private Group game = new Group();
	private Group topGroup = new Group();

	private PlantUpgradePermission pup = new PlantUpgradePermission(true, true, true);
	private Plant pearlPlant;
	private Plant legPlant;
	private Plant glovesPlant;
	
	private PlantUpgrade startingPlantUpgrade = PlantUpgrade.NONE;
	private FishUpgrade startingFishUpgrade = FishUpgrade.NONE;
	
	private int fishCount;	
	private FishUpgradePermission fup = new FishUpgradePermission(true, true, true);
	private float legX;
	private float legY;
	private float pearlX;
	private float pearlY;
	private float glovesX;
	private float glovesY;
	
	private Counter counter;
	private QueueFullGameOver gameOver;
	
	private final static float FISH_START_X = 600f;
	private final static float FISH_START_Y = 5f;

	private void create() {
		glovesPlant = new Plant(ObjectType.GLOVES, pup);
		legPlant = new Plant(ObjectType.LEG, pup);
		pearlPlant = new Plant(ObjectType.PEARL, pup);
		
		counter = Counter.getInstance();
		counter.setUp(1000f, 100f);
		gameOver = new QueueFullGameOver(counter);
	}

	Group getLevel() {
		create();

		glovesPlant.setPosition(glovesX, glovesY);
		legPlant.setPosition(legX, legY);
		pearlPlant.setPosition(pearlX, pearlY);
		
		glovesPlant.setUpgrade(startingPlantUpgrade);
		legPlant.setUpgrade(startingPlantUpgrade);
		pearlPlant.setUpgrade(startingPlantUpgrade);
		
		ProductionRateSwapper prs = new ProductionRateSwapper(glovesPlant,
				legPlant, pearlPlant);

		game.addActor(prs);

		game.addActor(pearlPlant);
		game.addActor(glovesPlant);
		game.addActor(legPlant);
		game.addActor(counter);
		game.addActor(counter.getQueue());
		game.addActor(gameOver);
		
		game.addActor(new AutoFetcher());
		

		// Anything put in topGroup will always be on top of everything because
		// this was added last
		game.addActor(this.topGroup);

		for(int i=0;i<fishCount;i++) {
			game.addActor(addFish(fup, startingFishUpgrade));
		}
		
		game.addActor(Hud.getInstance());
		return game;
	}
	
	Group getTopGroup() {
		return this.topGroup;
	}
	
	LevelBuilder setPlantsCoords(float legX, float legY, float pearlX, float pearlY, 
			float glovesX, float glovesY) {
		this.legX = legX;
		this.legY = legY;
		this.pearlX = pearlX;
		this.pearlY = pearlY;
		this.glovesX = glovesX;
		this.glovesY = glovesY;
		return this;
	}
	
	LevelBuilder setUpgradePermissions(PlantUpgradePermission pup, FishUpgradePermission fup) {
		this.pup = pup;
		this.fup = fup;
		return this;
	}
	
	LevelBuilder setFishCount(int fishCount) {
		this.fishCount = fishCount;
		return this;
	}
	
	LevelBuilder setPlantUpgrade(PlantUpgrade upgrade) {
		this.startingPlantUpgrade = upgrade;
		return this;
	}

	LevelBuilder setFishUpgrade(FishUpgrade upgrade) {
		this.startingFishUpgrade = upgrade;
		return this;
	}
	
	private Fish addFish(FishUpgradePermission upgradePermission, FishUpgrade upgrade) {
		Fish fish = new Fish(upgradePermission);
		fish.setPosition(FISH_START_X, FISH_START_Y);
		fish.setUpgrade(upgrade);
		return fish;
	}
}