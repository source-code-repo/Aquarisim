package aquarisim.mainmenu;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;

import aquarisim.utils.AquariAssets;

public class Background extends Actor {
	private static Texture texture = AquariAssets.get("background.png", Texture.class);
	private static Background singleton;
	
	
	public static Background getInstance() {
		if(singleton == null) {
			singleton = new Background();
		}
		texture = AquariAssets.get("background.png", Texture.class);
		return singleton;
	}
	
	private Background() {
		setBounds(getX(), getY(), texture.getWidth(), texture.getHeight());
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		setTouchable(Touchable.enabled);			
	}

	@Override
	public void draw(Batch batch, float alpha) {
		batch.draw(texture, getX(), getY());
	}
}
