package aquarisim.object;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

import aquarisim.utils.AquariAssets;

public class ObjectTexturePool {
	public static Texture getPearl() {
		return AquariAssets.get("pearl.png", Texture.class);
	}
	
	public static Texture getLeg() {
		return AquariAssets.get("leg.png", Texture.class);
	}
	
	public static Texture getGlove() {
		return AquariAssets.get("glove.png", Texture.class);
	}
	
	public static Pixmap getPearlPixmap() {
		return AquariAssets.get("pearl_pixmap.png", Pixmap.class);
	}
	
	public static Pixmap getLegPixmap() {
		return AquariAssets.get("leg_pixmap.png", Pixmap.class);
	}
	
	public static Pixmap getGlovesPixmap() {
		return AquariAssets.get("glove_pixmap.png", Pixmap.class);
	}
}
