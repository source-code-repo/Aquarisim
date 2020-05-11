package aquarisim.fish;

import aquarisim.fish.FishUpgrader.FishUpgrade;

public class FishUpgradePermission {
	private boolean level1 = false;
	private boolean level2 = false;
	private boolean level3 = false;
	
	public FishUpgradePermission(boolean level1, boolean level2, boolean level3) {
		this.level1 = level1;
		this.level2 = level2;
		this.level3 = level3;
	}
	
	public static FishUpgradePermission allAllowed() {
		return new FishUpgradePermission(true, true, true);
	}
	
	boolean allowed(FishUpgrade upgrade) {
		switch(upgrade) {
			case LEVEL_1:
				if(level1) { return true; }
				break;
			case LEVEL_2:
				if(level2) { return true; }
				break;
			case LEVEL_3:
				if(level3) { return true; }
				break;
			default:
				return false;
		}
		return false;
	}
}
