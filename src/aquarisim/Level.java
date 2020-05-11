package aquarisim;

import com.badlogic.gdx.scenes.scene2d.Group;

import aquarisim.fish.FishUpgradePermission;
import aquarisim.fish.FishUpgrader.FishUpgrade;
import aquarisim.plant.PlantUpgradePermission;

public class Level {
	private final static int FISH_COUNT = 3;
	private Group topGroup;
	LevelBuilder builder;
	
	public Group getContents() {
		builder = new LevelBuilder();
		builder.setPlantsCoords(750f, 100f, 400f, 5f, 50f, 70f);
		FishUpgradePermission fup = new FishUpgradePermission(true, true, true);
		PlantUpgradePermission pup = new PlantUpgradePermission(true, true, true);
		builder.setUpgradePermissions(pup, fup).setFishCount(FISH_COUNT);
		builder.setFishUpgrade(FishUpgrade.NONE);
		Group game = builder.getLevel();
		this.topGroup = builder.getTopGroup();
		return game;
	}

	public Group getTopGroup() {
		return topGroup;
	}
}