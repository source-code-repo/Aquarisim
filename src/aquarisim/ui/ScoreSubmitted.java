package aquarisim.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import aquarisim.Aquarisim;
import aquarisim.Main;
import aquarisim.mainmenu.Background;
import aquarisim.mainmenu.MainMenuContent;
import aquarisim.utils.AquariAssets;
import aquarisim.utils.AquariFont;

public class ScoreSubmitted implements Screen {
	private Stage stage;
	private static final Table table = new Table();
	private Texture backgroundTexture;
	private SpriteDrawable background;
	private final Color TITLE_COLOUR = new Color(0f, 0.09f, 0.55f, 1f);
	private TextureAtlas buttonAtlas;

	@Override
	public void show() {
		setUp();
	}
	
	private void setUp() {
		AquariAssets.clear();
		AquariFont.clearFonts();
		backgroundTexture = AquariAssets.get("object_switch_background.png", Texture.class);
		background = new SpriteDrawable(new Sprite(backgroundTexture));
		buttonAtlas = AquariAssets.get("secondSkin/button.atlas", TextureAtlas.class);
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
	    table.pack();
	    center(table);
	}
	
	private void addContents(Table table) {
		table.add(getTitle()).width(600f).padTop(70f);
	    table.row().padTop(130f).padBottom(70f);
	    table.add(getMenuItem("Main Menu", new MainMenuInputListener()));
	}

	private Actor getTitle() {
		final String title = "High score submitted successfully!";
		final int fontSize = 48;
		return getLabel(fontSize, title, TITLE_COLOUR);
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
        label.setAlignment(Align.center);
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
	
	private class MainMenuInputListener extends InputListener {
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
