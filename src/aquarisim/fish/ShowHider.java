package aquarisim.fish;

import com.badlogic.gdx.scenes.scene2d.Actor;

import aquarisim.Time;
import aquarisim.ui.AquariDialog;


// TODO: Remove after testing is complete.
public class ShowHider extends Actor {
	private float lastAct = 0f;
	private boolean shown = false;
	private Fish fish;
	private ObjectSwitchDialog dialog;
	
	public ShowHider(Fish fish) {
		this.fish = fish;
	}
	
	@Override
	public void act(float delta) {
		lastAct += delta;
		if(lastAct > Time.convert(1f) && !shown) {
			dialog = ObjectSwitchDialog.getInstance(fish);
			new AquariDialog().show(dialog);
			shown = true;
		}
		if(lastAct > Time.convert(2f) && shown) {
			dialog.hide();
			lastAct = 0f;
			shown = false;
		}
		super.act(delta);
	}
}
