package aquarisim.fish;

import java.util.Random;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;

import aquarisim.Main;
import aquarisim.Time;
import aquarisim.fish.FishUpgrader.FishUpgrade;

public class MenuFish extends Actor {
	private final float MAX_X = Main.WIDTH;
	private final float MAX_Y = Main.HEIGHT;
	private float lastX = 0;
	private float lastAct = 0;

	private final static float SCALE = 0.3f;
	private Facing facing = Facing.RIGHT;
	private FishUpgrade upgrade = FishUpgrade.NONE;
	private Texture texture;
	
	private enum Facing {
		LEFT, RIGHT;
	}

	public MenuFish(FishUpgrade upgrade) {
		texture = new Texture(PixmapPool.getFastFish());
		this.upgrade = upgrade;
		reDrawFish();
		setBounds(getX(), getY(), texture.getWidth() * SCALE,
				texture.getHeight() * SCALE);
		startWiggle();
		// Set the fish's "origin" as it's mid-point
		setOrigin(getWidth() / 2, getHeight() / 2);
		Vector2 position = randomCoOrd();
		setX(position.x);
		setY(position.y);
		randomMove();
	}
	
	private Vector2 randomCoOrd() {
		Random r = new Random();
		float x = r.nextFloat() * MAX_X;
		float y = r.nextFloat() * MAX_Y;
		return new Vector2(x, y);
	}

	void randomMove() {
		Vector2 coOrd = randomCoOrd();
		float distance = (float) Math.sqrt(Math.pow((getX() - coOrd.x), 2) + Math.pow((getY() - coOrd.y), 2));
		final float SPEED = 75f;
		Time duration = new Time(distance / SPEED);
		
		Action move = Actions.moveTo(coOrd.x, coOrd.y, duration.getFloat());
		SequenceAction moveNotify = Actions.sequence(move, Actions.run(new RandomMove()));
		addAction(moveNotify);
	}
	
	private class RandomMove implements Runnable {
		@Override
		public void run() {
			MenuFish.this.randomMove();
		}
	}

	@Override
	public void draw(Batch batch, float alpha) {
		batch.draw(texture, this.getX(), getY(), this.getOriginX(),
				this.getOriginY(), this.getWidth(), this.getHeight(),
				this.getScaleX(), this.getScaleY(), this.getRotation(), 0, 0,
				texture.getWidth(), texture.getHeight(), facing == Facing.LEFT,
				false);
	}

	@Override
	public void act(float delta) {
		lastAct += delta;
		if (lastAct > new Time(0.1f).getFloat()) {
			lastAct = 0;
			if (getX() >= lastX) {
				this.facing = Facing.RIGHT;
			} else {
				this.facing = Facing.LEFT;
			}
			this.lastX = getX();
		}

		super.act(delta);
	}

	private void startWiggle() {
		// Wiggle the fish
		SequenceAction rotate = new SequenceAction(
				Actions.rotateTo(-3f, 0.25f), Actions.rotateTo(3f, 0.25f));
		addAction(Actions.forever(rotate));
	}

	private synchronized void reDrawFish() {
		Pixmap p;
		p = upgrade.getPixmap();

		Pixmap pixMap = new Pixmap(p.getWidth(), p.getHeight(), Pixmap.Format.RGBA8888);
		pixMap.drawPixmap(p, 0, 0);
		
		texture.dispose();
		texture = new Texture(pixMap);
		pixMap.dispose();
	}
}