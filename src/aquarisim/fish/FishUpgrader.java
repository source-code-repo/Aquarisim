package aquarisim.fish;

import com.badlogic.gdx.graphics.Pixmap;

import aquarisim.bank.Account;

public class FishUpgrader {
	public enum FishUpgrade {
		NONE {
			@Override
			public long getCost() { return 0; }
			@Override
			public String getName() { return "None"; }
			@Override
			public float getMultiplier() { return 1f; }
			@Override
			Pixmap getPixmap() {
				return PixmapPool.getNoneFish();
			}
		},
		LEVEL_1 {
			@Override
			public long getCost() { return 50; }
			@Override
			public String getName() { return "Level 1"; }
			@Override
			public float getMultiplier() { return 1.5f; }
			@Override
			Pixmap getPixmap() {
				return PixmapPool.getLowFish();
			}
		},
		LEVEL_2 {
			@Override
			public long getCost() { return 100; }
			@Override
			public String getName() { return "Level 2"; }
			@Override
			public float getMultiplier() { return 2f; }
			@Override
			Pixmap getPixmap() {
				return PixmapPool.getMediumFish();
			}
		},
		LEVEL_3 {
			@Override
			public long getCost() { return 300; }
			@Override
			public String getName() { return "Level 3"; }
			@Override
			public float getMultiplier() { return 3f; }
			@Override
			Pixmap getPixmap() {
				return PixmapPool.getHighFish();
			}
		};
		abstract long getCost();
		abstract String getName();
		abstract float getMultiplier();
		abstract Pixmap getPixmap();
	}
	
	static void upgrade(Fish fish, FishUpgrade upgrade) {
		if(!canAfford(upgrade)) {
			return;
		}
		Account.getInstance().remove(upgrade.getCost());
		fish.setUpgrade(upgrade);
	}
	
	static boolean enabled(Fish fish, FishUpgrade upgrade) {
		if(validUpgrade(fish, upgrade) && canAfford(upgrade)) {
			return true;
		} else {
			return false;
		}
	}
	
	private static boolean canAfford(FishUpgrade upgrade) {
		if(Account.getInstance().getBalance() >= upgrade.getCost()) {
			return true;
		} else {
			return false;
		}
	}
	
	private static boolean validUpgrade(Fish fish, FishUpgrade upgrade) {
		if(fish.getUpgrade().equals(FishUpgrade.NONE)) {
			return true;
		}
		
		if(fish.getUpgrade().equals(FishUpgrade.LEVEL_1)) {
			if(upgrade.equals(FishUpgrade.LEVEL_2) || upgrade.equals(FishUpgrade.LEVEL_3)) {
				return true;
			}
		}
		
		if(fish.getUpgrade().equals(FishUpgrade.LEVEL_2)) {
			if(upgrade.equals(FishUpgrade.LEVEL_3)) {
				return true;
			}
		}
		
		return false;
	}
}
