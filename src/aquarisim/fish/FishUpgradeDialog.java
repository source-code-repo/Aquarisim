package aquarisim.fish;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

import aquarisim.Main;
import aquarisim.bank.Account;
import aquarisim.fish.FishUpgrader.FishUpgrade;
import aquarisim.ui.ButtonBackground;
import aquarisim.ui.DialogContents;
import aquarisim.utils.AquariAssets;
import aquarisim.utils.AquariFont;

public class FishUpgradeDialog implements DialogContents {
	private final static float PAD = 20f;
	private final static Table table = new Table();
	private Texture backgroundTexture;
	private SpriteDrawable background;
	private static final Table upgradeTable = new Table();
	private static FishUpgradeDialog instance;
	private Fish fish;
	
	public static FishUpgradeDialog getInstance(Fish fish) {
		if(instance == null) {
			throw new RuntimeException("ObjectSwitchDialog not built");
		}
		instance.fish = fish;
		return instance;
	}
	
	public static void reset() {
		instance = new FishUpgradeDialog();
	}

	private FishUpgradeDialog() {
		backgroundTexture = AquariAssets.get("object_switch_background.png", Texture.class);
		background = new SpriteDrawable(new Sprite(backgroundTexture));
		table.clear();
		table.defaults().padLeft(PAD);
		table.defaults().padRight(PAD);
		backgroundTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		table.setBackground(background, true);

		// Headers
		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = AquariFont.generate(48);
		Label label = new Label("Upgrade Fish Speed", labelStyle);
		table.add(label).left().padLeft(30f);
		table.row().padTop(0f);

		// Upgrade list
		table.row().padTop(PAD / 2);
		table.add(getUpgradeTable());

		table.pack();
		table.setTouchable(Touchable.enabled);
	}

	private Actor getUpgradeTable() {
		upgradeTable.clear();
		upgradeTable.defaults().pad(5f);
		upgradeTable.add(enabledTable(FishUpgrade.LEVEL_1));
		upgradeTable.add(enabledTable(FishUpgrade.LEVEL_2));
		upgradeTable.add(enabledTable(FishUpgrade.LEVEL_3));		
		return upgradeTable;
	}
	
	private Actor enabledTable(FishUpgrade upgrade) {
		TextButtonStyle style = new TextButtonStyle();
		style.font = AquariFont.generate(48);
		style.up = ButtonBackground.getInstance().getBackground();
        style.down = ButtonBackground.getInstance().getBackground();
        style.checked = ButtonBackground.getInstance().getBackground();
        
        TextButton button = new TextButton(upgrade.getName() + "\n" + "$" + upgrade.getCost(), style);
        button.setUserObject(upgrade);
        
        button.addListener(this.new UpgradeSelectListener());    
        return button;
	}

	@Override
	public Actor getDialog() {
		return table;
	}

	private class UpgradeSelectListener extends InputListener {
		@Override
		public boolean touchDown(InputEvent event, float x, float y,
				int pointer, int button) {
			return true;
		}

		@Override
		public void touchUp(InputEvent event, float x, float y, int pointer,
				int button) {
			FishUpgrade upgrade = (FishUpgrade) event.getListenerActor().getUserObject();
			if(Account.getInstance().getBalance() >= upgrade.getCost()) {
				Main.getInstance().enableTouch();
				FishUpgrader.upgrade(fish, upgrade);
				table.remove();
			} else {
				Main.getInstance().showMessage("Get more cash!");
			}
			
		}
	}

}
