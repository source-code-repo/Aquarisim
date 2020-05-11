package aquarisim.object;

import com.badlogic.gdx.graphics.Texture;

import aquarisim.utils.AquariAssets;

public class PlantTexturePool {
	public static Texture getTreasureChest() {
		return AquariAssets.get("treasure_chest.png", Texture.class);
	}
}