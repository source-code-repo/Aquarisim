package aquarisim.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import aquarisim.Aquarisim;
import aquarisim.Main;
import aquarisim.fish.FishUpgrader.FishUpgrade;
import aquarisim.fish.MenuFish;
import aquarisim.mainmenu.Background;
import aquarisim.mainmenu.MainMenuContent;
import aquarisim.utils.AquariAssets;
import aquarisim.utils.AquariFont;

public class GameOver implements Screen {
	private final static boolean AUTO_RESTART = false;
	private final static Color TITLE_COLOR = new Color(0.21f, 0.96f, 0.37f, 1f);
	private final static float BUTTON_WIDTH = 350f;
	private Stage stage;
	private final int wave;
	private static TextureAtlas buttonAtlas;
	// HAAACKY
	public static boolean showScoreSubmittedSuccess = false;
	public static boolean showScoreSubmittedFailed;
	
	public GameOver(int wave) {
		buttonAtlas = AquariAssets.get("secondSkin/button.atlas", TextureAtlas.class);
		this.wave = wave;
	}

	@Override
	public void show() {		
		setUp();
	}
	
	private void setUp() {
		if(AUTO_RESTART) {
			Aquarisim.reset();
		}
		stage = new Stage(new StretchViewport(Main.WIDTH, Main.HEIGHT));
		Gdx.input.setInputProcessor(stage);
		Table table = getContent();
		
		stage.addActor(Background.getInstance());
		stage.addActor(new MenuFish(FishUpgrade.LEVEL_1));
		stage.addActor(new MenuFish(FishUpgrade.LEVEL_1));
		stage.addActor(new MenuFish(FishUpgrade.LEVEL_1));
		stage.addActor(table);
	}
	
	private Table getContent() {
		final Table table = new Table();
		
		table.clear();
		table.setFillParent(true);
		table.defaults().pad(5f).expandY().expandX();
		
		table.add(getTitle("Game Over"));
		table.row();
		table.add(getScore());
		table.row();
		table.add(getMenuItem("Try Again", new RestartListener())).width(BUTTON_WIDTH);
		table.row();
		table.add(getMenuItem("Send High Score", new HighScoreListener())).width(BUTTON_WIDTH);
		table.row();
		table.add(getMenuItem("Main Menu", new MainMenuListener())).width(BUTTON_WIDTH);
		table.pack();
        table.setTouchable(Touchable.enabled);
        
        return table;
	}
	
	private Label getTitle(String title) {
		Label.LabelStyle style = new Label.LabelStyle();
		style.font = AquariFont.generate(60);
		Label label = new Label(title, style);
		label.setColor(TITLE_COLOR);
		return label;
	}
	
	private Label getScore() {
		Label.LabelStyle style = new Label.LabelStyle();
		style.font = AquariFont.generate(40);
		Label label = new Label("You got to wave " + wave, style);
		return label;
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
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.24f, 0.655f, 1f, 0.73f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(delta);
		stage.draw();
		Table.drawDebug(stage);
	}

	@Override
	public void resize(int width, int height) { 
		stage.getViewport().update(width, height, true);
	}
	
	@Override
	public void hide() { }

	@Override
	public void pause() { }

	@Override
	public void resume() { }

	@Override
	public void dispose() { }
	
	class RestartListener extends InputListener {
		@Override
		public boolean touchDown(InputEvent event, float x, float y,
				int pointer, int button) {
			return true;
		}
		
		@Override
		public void touchUp(InputEvent event, float x, float y, int pointer,
				int button) {
			Aquarisim.reset();
			Aquarisim.restart();
		}
	}
	
	class HighScoreListener extends InputListener {
		@Override
		public boolean touchDown(InputEvent event, float x, float y,
				int pointer, int button) {
			return true;
		}
		
		@Override
		public void touchUp(InputEvent event, float x, float y, int pointer,
				int button) {
			Aquarisim.getInstance().getSubmitHighScoreListener().submitHighScore(wave);
		}
	}
	
	class MainMenuListener extends InputListener {
		@Override
		public boolean touchDown(InputEvent event, float x, float y,
				int pointer, int button) {
			return true;
		}
		
		@Override
		public void touchUp(InputEvent event, float x, float y, int pointer,
				int button) {
			Aquarisim.setNewScreen(MainMenuContent.getInstance());
		}
	}
}