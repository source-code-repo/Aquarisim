package aquarisim.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import aquarisim.Main;

public class AquariDialog {
	Actor dialog;
	
	public void show(DialogContents contents) {
		this.dialog = contents.getDialog();
		Main.getInstance().ignoreTouch();
        Main.getInstance().setBackgroundListener(new AquariDialog.DialogCancelListener());
        Main.getInstance().showDialog(dialog);
	}
	
	private class DialogCancelListener extends InputListener {
		@Override
		public boolean touchDown(InputEvent event, float x, float y,
				int pointer, int button) {
			dialog.remove();
			Main.getInstance().removeBackgroundListener(this);
			Main.getInstance().enableTouch();
			return false;
		}
	}

}
