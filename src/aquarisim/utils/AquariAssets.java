package aquarisim.utils;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader.TextureParameter;
import com.badlogic.gdx.graphics.Texture.TextureFilter;

public class AquariAssets {
	private static final AssetManager manager = new AssetManager();
	
	public static <T> T get(String resource, Class<T> type) {
		if(manager.isLoaded(resource, type)) {
			return manager.get(resource, type);
		} else {
			TextureParameter param = new TextureParameter();
			param.minFilter = TextureFilter.Linear;
			param.magFilter = TextureFilter.Linear;
			manager.load(resource, type);
			manager.finishLoading();
			return manager.get(resource, type);
		}
	}
	
	public static void clear() {
		manager.clear();
	}
}
