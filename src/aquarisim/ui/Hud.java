package aquarisim.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import aquarisim.bank.Account;
import aquarisim.utils.AquariFont;

public class Hud extends Actor {
	private long balance = Account.getInstance().getBalance();
	private final Table table = new Table();
	private Label cashLabel;
	private Label waveTextLabel;
	private Label waveCountLabel;
	private int maxQueueLength;
	private int queueLength;
	private static Hud singleton;
	private final static Color GREEN = new Color(0.21f, 0.96f, 0.37f, 1f);
	private final static Color RED = new Color(1f, 0.24f, 0.25f, 1f);
	private final static int SIZE = 40;
	
	private Hud() { }
	
	public static Hud getInstance() {
		if(singleton == null) {
			reset();
		}
		return singleton;
	}
	
	public static void reset() {
		singleton = new Hud();
	}

	public Table build() {
		Label.LabelStyle style = new Label.LabelStyle(AquariFont.generate(SIZE), GREEN);
		Label.LabelStyle countStyle = new Label.LabelStyle(AquariFont.generate(SIZE), GREEN);
		cashLabel = new Label("", style);
		waveTextLabel = new Label("", style);
		waveCountLabel = new Label("", countStyle);
		table.setFillParent(true);
		table.top().left();
		table.padLeft(7f).padRight(13f);
		table.add(cashLabel).expandX().left();
		table.add(waveTextLabel).right();
		table.add(waveCountLabel).right();
		waveTextLabel.setText("Queue: ");
        table.setTouchable(Touchable.disabled);
        table.debug();
        return table;
	}
	
	@Override
	public void act(float delta) {
		cashLabel.setText("$" + balance);
		waveCountLabel.setText(queueLength + "/" + maxQueueLength);
		if(warning()) {
			waveCountLabel.getStyle().fontColor = RED;
		} else {
			waveCountLabel.getStyle().fontColor = GREEN;
		}
		table.pack();
		super.act(delta);
	}
	
	private boolean warning() {
		if(queueLength >= (0.9 * maxQueueLength)) {
			return true;
		} else {
			return false;
		}
	}
	
	public void setBalance(long newBalance) {
		balance = newBalance;
	}
	
	public void setQueueLength(int queueLength) {
		this.queueLength = queueLength;
	}
	
	public void setMaxQueueLength(int maxQueueLength) {
		this.maxQueueLength = maxQueueLength;
	}
}