package aquarisim.fish;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

import aquarisim.Main;
import aquarisim.bank.Account;
import aquarisim.object.ObjectType;
import aquarisim.ui.AquariDialog;
import aquarisim.ui.ButtonBackground;
import aquarisim.ui.DialogContents;
import aquarisim.utils.AquariAssets;
import aquarisim.utils.AquariFont;

public class ObjectSwitchDialog implements DialogContents {
	private Fish fish;
	private static final  float PAD = 30f;
	private static final  float BUTTON_WIDTH = 280f;
	private static final Table table = new Table();
	private static Texture backgroundTexture;
	private static SpriteDrawable background;
	private static ObjectSwitchDialog instance;
	
	public static ObjectSwitchDialog getInstance(Fish fish) {
		if(instance == null) {
			throw new RuntimeException("ObjectSwitchDialog not built");
		}
		instance.fish = fish;
		return instance;
	}
	
	public Actor getDialog() {
		return ObjectSwitchDialog.table;
	}
	
	public static void reset() {
		instance = new ObjectSwitchDialog();
	}
	
	private ObjectSwitchDialog() {
		backgroundTexture = AquariAssets.get("object_switch_background.png", Texture.class);
		background = new SpriteDrawable(new Sprite(backgroundTexture));
		table.clear();
		backgroundTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		table.setBackground(background, false);
		Table objectTable = new Table();
		
		// Job list
		objectTable.defaults().left();
		objectTable.row().padTop(PAD);
		for(ObjectType f : ObjectType.values()) {
			Actor objectRow = getObjectRow(f);
			objectTable.add(objectRow).width(BUTTON_WIDTH);
			objectTable.row();
		}
		table.add(objectTable).colspan(2);
		
		// Temp speed upgrade button
		table.row();
		int cost = SpeedBoost.getCost();
		table.add(getTempSpeedUpgradeButton("Boost ($" + cost + ")")).padBottom(PAD).padLeft(PAD);
		
		// Upgrade button
		table.add(getUpgradeButton()).center().padBottom(PAD).padRight(PAD);
		
        table.pack();
        table.setTouchable(Touchable.enabled);
	}

	private TextButton getUpgradeButton() {
		TextButtonStyle style = new TextButtonStyle();
		style.font = AquariFont.generate(48);
        style.up = ButtonBackground.getInstance().getBackground();
        style.down = ButtonBackground.getInstance().getBackground();
        style.checked = ButtonBackground.getInstance().getBackground();
        
        TextButton button = new TextButton("Upgrade", style);
        button.addListener(new FishUpgradeListener());
        button.setUserObject(fish);
        return button;
	}

	private TextButton getTempSpeedUpgradeButton(String string) {
		TextButtonStyle style = new TextButtonStyle();
		style.font = AquariFont.generate(48);
		style.up = ButtonBackground.getInstance().getBackground();
        style.down = ButtonBackground.getInstance().getBackground();
        style.checked = ButtonBackground.getInstance().getBackground();
        
        TextButton button = new TextButton(string, style);
        button.addListener(new SpeedBoostListener());
        button.setUserObject(fish);
        return button;		
	}
	
	private Button getObjectRow(ObjectType t) {		
		ButtonStyle style = new ButtonStyle();
		style.up = ButtonBackground.getInstance().getBackground();
        style.down = ButtonBackground.getInstance().getBackground();
        style.checked = ButtonBackground.getInstance().getBackground();
        
        Sprite objectSprite = new Sprite(t.getTexture());
        float targetWidth = 35f;
        float ratio = targetWidth / objectSprite.getWidth(); 
        objectSprite.setSize(objectSprite.getWidth() * ratio, objectSprite.getHeight() * ratio);
        SpriteDrawable sd = new SpriteDrawable(objectSprite);
        Image objectImage = new Image(sd);
        
        LabelStyle labelStyle = new LabelStyle();
        labelStyle.font = AquariFont.generate(48);
		Label label = new Label(t.toString(), labelStyle);
        
        Button button = new Button(style);
        button.align(Align.left);
        button.add(objectImage).align(Align.left);
        button.add(label).padLeft(PAD).align(Align.left);
        button.addListener(this.new ObjectSelectedListener());
        button.setUserObject(t);
        return button;
	}
	
	class ObjectSelectedListener extends InputListener {
		@Override
		public boolean touchDown(InputEvent event, float x, float y,
				int pointer, int button) {
			return true;
		}
		
		@Override
		public void touchUp(InputEvent event, float x, float y, int pointer,
				int button) {
			Main.getInstance().enableTouch();
			ObjectType type = (ObjectType) event.getListenerActor().getUserObject();
			fish.clearActions();
			fish.goAndGetObject(type);
			table.remove();
		}
	}
	
	class FishUpgradeListener extends InputListener {
		@Override
		public boolean touchDown(InputEvent event, float x, float y,
				int pointer, int button) {
			return true;
		}
		
		@Override
		public void touchUp(InputEvent event, float x, float y, int pointer,
				int button) {
			Main.getInstance().enableTouch();
			table.remove();
			new AquariDialog().show(FishUpgradeDialog.getInstance(fish));
		}
	}
	
	
	
	class SpeedBoostListener extends InputListener {
		@Override
		public boolean touchDown(InputEvent event, float x, float y,
				int pointer, int button) {
			return true;
		}
		
		@Override
		public void touchUp(InputEvent event, float x, float y, int pointer,
				int button) {
			if(Account.getInstance().getBalance() >= SpeedBoost.getCost()) {
				Main.getInstance().enableTouch();
				table.remove();
				SpeedBoost.build(fish);
			} else {
				Main.getInstance().showMessage("Get more cash!");
			}
		}
	}
	
	// TODO remove, it's only for testing
	public void hide() {
		Main.getInstance().enableTouch();
		table.remove();
	}

	
}