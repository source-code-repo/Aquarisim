package aquarisim.fish;

import com.badlogic.gdx.graphics.Pixmap;

import aquarisim.utils.AquariAssets;

class PixmapPool {
	public static Pixmap getFastFish() {
		return AquariAssets.get("fast_fish.png", Pixmap.class);
	}
	
	public static Pixmap getNoneFish() {
		return AquariAssets.get("fish_none.png", Pixmap.class);
	}
	
	public static Pixmap getLowFish() {
		return AquariAssets.get("fish_low.png", Pixmap.class);
	}
	
	public static Pixmap getMediumFish() {
		return AquariAssets.get("fish_medium.png", Pixmap.class);
	}
	
	public static Pixmap getHighFish() {
		return AquariAssets.get("fish_high.png", Pixmap.class);
	}
}
