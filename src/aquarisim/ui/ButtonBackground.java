package aquarisim.ui;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

import aquarisim.utils.AquariAssets;

public class ButtonBackground {
	private static ButtonBackground buttonBackground;
	private static TextureAtlas buttonAtlas;
	private static NinePatchDrawable buttonBackgroundNinePatch;
	
	public static ButtonBackground getInstance() {
		if(buttonBackground == null) {
			buttonBackground = new ButtonBackground();
		}
		return buttonBackground;
	}
	
	public NinePatchDrawable getBackground() {
		buttonAtlas = AquariAssets.get("secondSkin/button.atlas", TextureAtlas.class);
		buttonBackgroundNinePatch = new NinePatchDrawable(buttonAtlas.createPatch("button"));
		return buttonBackgroundNinePatch;
	}
	
	private ButtonBackground() {
	}
}
