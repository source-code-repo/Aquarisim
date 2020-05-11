package aquarisim.ui;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;

import aquarisim.Main;
import aquarisim.utils.AquariAssets;

public class Background extends Actor {
	private static Texture texture;
	private float downX;
	private final float mapLeft = 0;
	private final float mapRight;
	private Camera camera;
	private boolean dragging = false;
	
	public Background(final Camera camera) {
		texture = AquariAssets.get("background.png", Texture.class);
		this.camera = camera;
		setBounds(getX(), getY(), texture.getWidth(), texture.getHeight());
		setTouchable(Touchable.enabled);
		mapRight = 0 + getWidth();
		addListener(new BackgroundListener());			
	}

	@Override
	public void draw(Batch batch, float alpha) {
		batch.draw(texture, getX(), getY());
	}
	
	protected class BackgroundListener extends InputListener {
		public boolean touchDown(InputEvent event, float x, float y,
				int pointer, int button) {
			if(dragging) {
				return false;
			}
			dragging = true;
			downX = x;
			return true;
		}

		// Moves the camera around the background. Stops moving when the
		// camera reaches the edge of the background.
		@Override
		public void touchDragged(InputEvent event, float x, float y,
				int pointer) {
			float translateX = x - downX;

			float cameraHalfWidth = camera.viewportWidth * .5f;
			float cameraLeft = camera.position.x - cameraHalfWidth;
			float cameraRight = camera.position.x + cameraHalfWidth;

			// Don't move the camera beyond the left of the background
			if (cameraLeft - translateX < mapLeft) {
				translateX = cameraLeft - mapLeft;
			}
			
			// Don't move the camera beyond the right of the background
			if (cameraRight - translateX > mapRight) {
				translateX = cameraRight - mapRight;
			}

			Main.getInstance().translateView(-translateX, 0);
			super.touchDragged(event, x, y, pointer);
		}
		
		@Override
		public void touchUp(InputEvent event, float x, float y, int pointer,
				int button) {
			dragging = false;
		}
	};
}
