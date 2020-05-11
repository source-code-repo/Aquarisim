package aquarisim.creature;

import com.badlogic.gdx.graphics.Texture;

import aquarisim.utils.AquariAssets;

public class TexturePool {
	public static Texture getOyster() {
		return AquariAssets.get("oyster.png", Texture.class);
	}
	
	public static Texture getShark() {
		return AquariAssets.get("shark.png", Texture.class);
	}
	
	public static Texture getOctopus() {
		return AquariAssets.get("octopus.png", Texture.class);
	}
}