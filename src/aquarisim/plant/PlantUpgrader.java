package aquarisim.plant;

import aquarisim.bank.Account;

public class PlantUpgrader {
	public enum PlantUpgrade {
		NONE {
			@Override
			public long getCost() { return 0; }
			@Override
			public String getName() { return "None"; }
			@Override
			public float getMultiplier() { return 2f; };
		},
		LEVEL_1 {
			@Override
			public long getCost() { return 100; }
			@Override
			public String getName() { return "Level 1"; }
			@Override
			public float getMultiplier() { return 1f; }
		},
		LEVEL_2 {
			@Override
			public long getCost() { return 200; }
			@Override
			public String getName() { return "Level 2"; }
			@Override
			public float getMultiplier() { return 0.5f; }
		},
		LEVEL_3 {
			@Override
			public long getCost() { return 300; }
			@Override
			public String getName() { return "Level 3"; }
			@Override
			public float getMultiplier() { return 0.25f; }
		};
		abstract long getCost();
		abstract String getName();
		abstract float getMultiplier();
	}
	
	static void upgrade(Plant plant, PlantUpgrade upgrade) {
		if(!canAfford(upgrade)) {
			return;
		}
		Account.getInstance().remove(upgrade.getCost());
		plant.setUpgrade(upgrade);
	}
	
	static boolean enabled(Plant plant, PlantUpgrade upgrade) {
		if(validUpgrade(plant, upgrade) && canAfford(upgrade)) {
			return true;
		} else {
			return false;
		}
	}
	
	private static boolean canAfford(PlantUpgrade upgrade) {
		if(Account.getInstance().getBalance() >= upgrade.getCost()) {
			return true;
		} else {
			return false;
		}
	}
	
	private static boolean validUpgrade(Plant plant, PlantUpgrade upgrade) {
		if(plant.getUpgrade().equals(PlantUpgrade.NONE)) {
			return true;
		}
		
		if(plant.getUpgrade().equals(PlantUpgrade.LEVEL_1)) {
			if(upgrade.equals(PlantUpgrade.LEVEL_2) || upgrade.equals(PlantUpgrade.LEVEL_3)) {
				return true;
			}
		}
		
		if(plant.getUpgrade().equals(PlantUpgrade.LEVEL_2)) {
			if(upgrade.equals(PlantUpgrade.LEVEL_3)) {
				return true;
			}
		}
		
		return false;
	}
}
