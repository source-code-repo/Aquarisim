package aquarisim.mainmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
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
import com.badlogic.gdx.utils.viewport.StretchViewport;

import aquarisim.Aquarisim;
import aquarisim.HighScoreListener;
import aquarisim.Main;
import aquarisim.fish.FishUpgrader.FishUpgrade;
import aquarisim.fish.MenuFish;
import aquarisim.ui.Tutorial;
import aquarisim.utils.AquariAssets;
import aquarisim.utils.AquariFont;

public class MainMenuContent implements Screen {
	private static MainMenuContent instance;
	private Stage stage;
	private HighScoreListener mainMenuListener;
	private Aquarisim aquarisim;
	private final static float PAD = 25f;
	private final static float BUTTON_WIDTH = 350f;
	private Texture logo;
	private TextureAtlas buttonAtlas;
	
	public static void build(HighScoreListener mainMenuListener, Aquarisim aquarisim) {
		if(instance != null) {
			instance.mainMenuListener = mainMenuListener;
			instance.aquarisim = aquarisim;
		}
		instance = new MainMenuContent(mainMenuListener, aquarisim);
	}

	public static MainMenuContent getInstance() {
		if(instance == null) {
			throw new RuntimeException("MainMenuContent must be built before use");
		}
		return instance;
	}
	
	private MainMenuContent(HighScoreListener mainMenuListener, Aquarisim aquarisim) {
		this.mainMenuListener = mainMenuListener;
		this.aquarisim = aquarisim;
	}

	@Override
	public void show() {
		logo = AquariAssets.get("logo.png", Texture.class);
		buttonAtlas = AquariAssets.get("secondSkin/button.atlas", TextureAtlas.class);
		setUp();
	}
	
	private void setUp() {
		stage = new Stage(new StretchViewport(Main.WIDTH, Main.HEIGHT));
		Gdx.input.setInputProcessor(stage);
		Table table = getContent();
		stage.addActor(Background.getInstance());
		stage.addActor(new MenuFish(FishUpgrade.LEVEL_1));
		stage.addActor(new MenuFish(FishUpgrade.LEVEL_2));
		stage.addActor(new MenuFish(FishUpgrade.LEVEL_3));
		stage.addActor(table);
		Aquarisim.reset();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.24f, 0.655f, 1f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(delta);
		stage.draw();
		Table.drawDebug(stage);
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}
	
	private Table getContent() {
		final Table table = new Table();
		
		table.clear();
		table.setFillParent(true);
		table.defaults().pad(5f).expandX();

		table.add(getTitle()).padTop(PAD).height(175f);
		table.row();
		table.add(getMenuItem("Start Game!", new StartTutorialInputListener())).width(BUTTON_WIDTH);
		table.row();
		table.add(getMenuItem("High Scores", new HighScoresInputListener())).width(BUTTON_WIDTH);
		table.row();
		table.add(getCopyright()).left().padLeft(185f);
        table.setTouchable(Touchable.enabled);
        
        return table;
	}
	
	private Image getTitle() {
		logo.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		return new Image(logo);
	}
	
	private TextButton getMenuItem(String text, InputListener listener) {
		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.font = AquariFont.generate(48);
        textButtonStyle.up = new NinePatchDrawable(buttonAtlas.createPatch("button"));
        textButtonStyle.down = new NinePatchDrawable(buttonAtlas.createPatch("button"));
        textButtonStyle.checked = new NinePatchDrawable(buttonAtlas.createPatch("button"));
        TextButton button = new TextButton(text, textButtonStyle);
        if(listener != null) {
        	button.addListener(listener);
        }
        return button;
	}

	private Label getCopyright() {
		final String description = "(c) 2014";
		final int fontSize = 36;
		return getLabel(fontSize, description, null);
	}

	private Label getLabel(int fontSize, String text, Color colour) {
		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = AquariFont.generate(fontSize);
		if (colour != null) {
			labelStyle.fontColor = colour;
		}
		Label label = new Label(text, labelStyle);
		label.setWrap(true);
		label.setWidth(10f);
		label.setAlignment(Align.left);
		return label;
	}
	
	private class StartTutorialInputListener extends InputListener {
		@Override
		public boolean touchDown(InputEvent event, float x, float y,
				int pointer, int button) {
			return true;
		}
		
		@Override
		public void touchUp(InputEvent event, float x, float y, int pointer,
				int button) {
			Aquarisim.setNewScreen(new Tutorial(mainMenuListener, aquarisim));
		}
	}
	
	private class HighScoresInputListener extends InputListener {
		@Override
		public boolean touchDown(InputEvent event, float x, float y,
				int pointer, int button) {
			return true;
		}
		
		@Override
		public void touchUp(InputEvent event, float x, float y, int pointer,
				int button) {
			mainMenuListener.showHighScores();
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
