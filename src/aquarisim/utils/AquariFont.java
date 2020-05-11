package aquarisim.utils;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class AquariFont {
	private static HashMap<Integer, BitmapFont> fonts = new HashMap<Integer, BitmapFont>();
	
	public static BitmapFont generate(int size) {
		if(fonts.containsKey(size)) {
			return fonts.get(size);
		}
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("good_dog.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = size;
		BitmapFont font = generator.generateFont(parameter);
		font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		generator.dispose(); // don't forget to dispose to avoid memory leaks!
		fonts.put(size, font);
		return font;
	}
	
	public static void clearFonts() {
		for(BitmapFont font : fonts.values()) {
			font.dispose();
		}
		fonts.clear();
	}
}
