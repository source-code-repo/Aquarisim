package aquarisim.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import aquarisim.Aquarisim;
import aquarisim.HighScoreListener;
import aquarisim.Main;
import aquarisim.creature.TexturePool;
import aquarisim.mainmenu.Background;
import aquarisim.utils.AquariAssets;
import aquarisim.utils.AquariFont;

public class Tutorial implements Screen {
	private Stage stage;
	private Aquarisim aquarisim;
	private static final Table table = new Table();
	private static Texture backgroundTexture;
	private static SpriteDrawable background;
	private static final Color TITLE_COLOUR = new Color(0f, 0.09f, 0.55f, 1f);
	private static TextureAtlas buttonAtlas;

	public Tutorial(HighScoreListener mainMenuListener, Aquarisim aquarisim) {
		this.aquarisim = aquarisim;
	}

	@Override
	public void show() {
		backgroundTexture = AquariAssets.get("object_switch_background.png", Texture.class);
		background = new SpriteDrawable(new Sprite(backgroundTexture));
		buttonAtlas = AquariAssets.get("secondSkin/button.atlas", TextureAtlas.class);
		setUp();
	}
	
	private void setUp() {
		stage = new Stage(new StretchViewport(Main.WIDTH, Main.HEIGHT));
		setUpTable();
		Gdx.input.setInputProcessor(stage);
		stage.addActor(Background.getInstance());
		stage.addActor(table);
	}
	
	private void center(Actor a) {
		Camera cam = stage.getCamera();
		Vector3 center = new Vector3(cam.position.x, cam.position.y, 0f);
		a.setPosition(center.x - (a.getWidth() / 2), center.y - (a.getHeight() / 2));
	}

	private void setUpTable() {
		table.clear();
		backgroundTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		table.setBackground(background, true);
	    table.setTouchable(Touchable.enabled);
	    table.defaults().padLeft(40f).padRight(40f);
	    table.row().padTop(10f);
	    addContents(table);
	    table.debug();
	    table.pack();
	    center(table);
	}
	
	private void addContents(Table table) {
		table.add(getTitle()).left();
	    table.row();
	    table.add(getDescription()).width(700f);
	    table.row();
	    table.add(getCreatures());
	    table.row();
	    table.add(getMenuItem("Start Game", new StartGameInputListener())).padBottom(20f);
	}
	
	private Actor getCreatures() {
		final float PAD = 50f;
		Table table = new Table();
		
		Image oyster = new Image(filter(TexturePool.getOyster()));
		Image pearl = new Image(filter(aquarisim.object.ObjectTexturePool.getPearl()));
		Image octopus = new Image(filter(TexturePool.getOctopus()));
		Image gloves = new Image(filter(aquarisim.object.ObjectTexturePool.getGlove()));
		Image shark = new Image(filter(TexturePool.getShark()));
		Image leg = new Image(filter(aquarisim.object.ObjectTexturePool.getLeg()));
		dontStretch(oyster);
		dontStretch(pearl);
		dontStretch(octopus);
		dontStretch(gloves);
		dontStretch(shark);
		dontStretch(leg);
		gloves.setAlign(Align.left);
		
		table.add(oyster).padLeft(10f).width(100f).padRight(10f);
		table.add(pearl).height(40f).padTop(20f).padRight(PAD);
		table.add(octopus).height(125f).width(80f).padRight(10f);
		table.add(gloves).padRight(PAD).width(60f).padTop(30f);
		table.add(shark).width(200f).padRight(15f);
		table.add(leg).width(50f);
		table.debug();
		return table;
	}
	
	private Image dontStretch(Image image) {
		image.setScaling(Scaling.fit);
		return image;
	}

	private Texture filter(Texture texture) {
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		return texture;
	}

	private Actor getTitle() {
		final String title = "Welcome to Aquarisim!";
		final int fontSize = 48;
		return getLabel(fontSize, title, TITLE_COLOUR);
	}
	
	private Actor getDescription() {
		final String description = 
				"When a sea creature arrives, " +
				"use a fish to fetch the item they want. " +
				"When the creature queue reaches 10 it's game over.";
		final int fontSize = 36;
		return getLabel(fontSize, description, null);
	}
	
	private Label getLabel(int fontSize, String text, Color colour) {
		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = AquariFont.generate(fontSize);
		if(colour != null) {
			labelStyle.fontColor = colour;
		}
        Label label = new Label(text, labelStyle);
        label.setWrap(true);
        label.setWidth(10f);
        return label;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.24f, 0.655f, 1f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}
	
	private TextButton getMenuItem(String text, InputListener listener) {
		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.font = AquariFont.generate(35);		
        textButtonStyle.up = new NinePatchDrawable(buttonAtlas.createPatch("button"));
        textButtonStyle.down = new NinePatchDrawable(buttonAtlas.createPatch("button"));
        textButtonStyle.checked = new NinePatchDrawable(buttonAtlas.createPatch("button"));
        TextButton button = new TextButton(text, textButtonStyle);
        if(listener != null) {
        	button.addListener(listener);
        }
        return button;
	}
	
	private class StartGameInputListener extends InputListener {
		@Override
		public boolean touchDown(InputEvent event, float x, float y,
				int pointer, int button) {
			return true;
		}
		
		@Override
		public void touchUp(InputEvent event, float x, float y, int pointer,
				int button) {
			aquarisim.startGame();
		}
	}

	@Override
	public void resume() {
		// Need to set up every time to overcome
		// https://github.com/libgdx/libgdx/issues/870
		setUp();
	}
	
	@Override
	public void hide() { }

	@Override
	public void pause() { }


	@Override
	public void dispose() { }
}
