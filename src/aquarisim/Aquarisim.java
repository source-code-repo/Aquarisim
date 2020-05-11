package aquarisim;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

import aquarisim.bank.Account;
import aquarisim.creature.Counter;
import aquarisim.fish.FishFinder;
import aquarisim.mainmenu.MainMenuContent;
import aquarisim.plant.PlantFinder;
import aquarisim.ui.GameOver;
import aquarisim.ui.Hud;
import aquarisim.ui.ScoreFailed;
import aquarisim.ui.ScoreSubmitted;
import aquarisim.utils.AquariFont;
import aquarisim.wave.WaveGenerator;
import aquarisim.wave.Waves;

public class Aquarisim extends Game {
	public static Aquarisim singleton;
	private HighScoreListener highScoreListener;
	private SubmitHighScoreListener submitListener;
	
	private Aquarisim() { }
	
	public static Aquarisim build(HighScoreListener highScoreListener, SubmitHighScoreListener submitListener) {
		if(singleton == null) {
			singleton = new Aquarisim();
		}
		singleton.highScoreListener = highScoreListener;
		singleton.submitListener = submitListener;	
		return singleton;
	}
	
	public static Aquarisim getInstance() {
		if(singleton == null) {
			throw new RuntimeException("Aquarisim class must be built before getting an instance");
		}
		return singleton;
	}
	
	@Override
	public void create() {
		MainMenuContent.build(highScoreListener, this);
		Aquarisim.setNewScreen(MainMenuContent.getInstance());
		AquariMusic.getInstance().start();
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
	}
	
	@Override
	public void resize(int width, int height) {
	}
	
	public static void setNewScreen(Screen screen) {
		AquariFont.clearFonts();
		Aquarisim.singleton.setScreen(screen);
	}
	
	public void startGame() {
		Level level = new Level();
		Main main = Main.getInstance();
		main.setLevel(level);
		Aquarisim.setNewScreen(main);
	}
	
	public static void reset() {
		Main.reset();
		Hud.reset();
		Counter.reset();
		Waves.reset();
		Account.reset();
		FishFinder.reset();
		PlantFinder.reset();
		WaveGenerator.reset();
		
	}
	
	public static void restart() {
		Main main = Main.getInstance();
		Level level = new Level();
		main.setLevel(level);
		Aquarisim.setNewScreen(main);
	}
	
	public SubmitHighScoreListener getSubmitHighScoreListener() {
		return this.submitListener;
	}
	
	@Override
	public void resume() {
		super.resume();		
		if(GameOver.showScoreSubmittedSuccess) {
			GameOver.showScoreSubmittedSuccess = false;
			setNewScreen(new ScoreSubmitted());
		}
		
		if(GameOver.showScoreSubmittedFailed) {
			GameOver.showScoreSubmittedFailed = false;
			setNewScreen(new ScoreFailed());
		}
	}
}