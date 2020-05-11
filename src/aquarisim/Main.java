package aquarisim;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import aquarisim.fish.FishUpgradeDialog;
import aquarisim.fish.ObjectSwitchDialog;
import aquarisim.ui.Background;
import aquarisim.ui.Hud;
import aquarisim.utils.AquariFont;
import aquarisim.wave.WaveGenerator;

public class Main implements Screen {
	private static Main singleton;
	public final static int WIDTH = 800;
	public final static int HEIGHT = 440;
	private final static int MESSAGE_SIZE = 60;
	private final static Color MESSAGE_COLOR = new Color(0.21f, 0.96f, 0.37f, 1f);
	private Group levelContents;
	private Group topGroup;
	private Group backgroundGroup;
	private Group foregroundGroup;
	private Background background;
	private Level level;
	
	private final Stage stage = new Stage(new StretchViewport(WIDTH, HEIGHT));
	private final Stage uiStage = new Stage(new StretchViewport(WIDTH, HEIGHT));
	private boolean started = false;
	
	public static Main getInstance() {
		if(singleton == null) {
			reset();
		}
		return singleton;
	}
	
	public static void reset() {
		singleton = new Main();
	}
	
	private Main() { }
	
	void setLevel(Level level) {
		this.level = level;
	}
	
	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
		background = new Background(stage.getCamera());
		backgroundGroup = new Group();
		backgroundGroup.addActor(background);
		stage.addActor(backgroundGroup);
		levelContents = level.getContents();
		stage.addActor(levelContents);
		foregroundGroup = new Group();
		stage.addActor(foregroundGroup);
		WaveGenerator.getInstance().nextWave();
		this.topGroup = level.getTopGroup();
		uiStage.addActor(Hud.getInstance().build());
		ObjectSwitchDialog.reset();
		FishUpgradeDialog.reset();
	}

	public void addActor(Actor a) {
		levelContents.addActor(a);
	}

	public void removeActor(Actor a) {
		levelContents.removeActor(a);
	}
 
	public void addToTop(Actor actor) {
		topGroup.addActor(actor);
	}
	
	public void ignoreTouch() {
		levelContents.setTouchable(Touchable.disabled);
	}
	
	public void enableTouch() {
		levelContents.setTouchable(Touchable.enabled);
	}
	
	public void setBackgroundListener(EventListener listener) {
		background.addListener(listener);
	}
	
	public void removeBackgroundListener(EventListener listener) {
		background.removeListener(listener);
	}
	
	public void showDialog(Actor a) {
		Camera cam = stage.getCamera();
		Vector3 center = new Vector3(cam.position.x, cam.position.y, 0f);
		a.setPosition(center.x - (a.getWidth() / 2), center.y - (a.getHeight() / 2));
		foregroundGroup.addActor(a);
	}
	
	public void end() {
		backgroundGroup.remove();
		levelContents.remove();
		foregroundGroup.remove();
	}

	public synchronized void translateView(float x, float y) {
		stage.getCamera().translate(x, y, 0);
	}
	
	@Override
	public void render(float delta) {
		if(!started) {
			centerCam();
			started = true;
		}
		Gdx.gl.glClearColor(0.5f, 0.5f, 1f, 0.8f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(delta);
		stage.draw();
		uiStage.act();
		uiStage.draw();
	}
	
	private void centerCam() {
		stage.getCamera().translate(700f, 0f, 0f);
	}
	
	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
		uiStage.getViewport().update(width, height, true);
	}
	
	public Vector2 getUiCenter() {
		return new Vector2(uiStage.getCamera().viewportWidth / 2, uiStage.getCamera().viewportHeight / 2);
	}
	
	public void addToUi(Actor a) {
		uiStage.addActor(a);
	}
	
	
	public void showMessage(String message) {
		Label lbl = new Label(message, new LabelStyle(AquariFont.generate(MESSAGE_SIZE), MESSAGE_COLOR));
		Vector2 center = Main.getInstance().getUiCenter();
        lbl.setPosition(center.x - (lbl.getWidth() / 2), center.y - (lbl.getHeight() / 2)); // set to viewable port
		Main.getInstance().addToUi(lbl);
		lbl.addAction(Actions.sequence(Actions.fadeOut(0.001f), Actions.fadeIn(0.2f), Actions.delay(2f), 
    			Actions.parallel(
						Actions.moveBy(0.0f, 25.0f, 0.3f),
						Actions.fadeOut(0.2f)),
    			Actions.removeActor()));
	}

	@Override
	public void hide() { }
	
	@Override
	public void pause() { }
	
	@Override
	public void resume() { }
	
	@Override
	public void dispose() { }
}