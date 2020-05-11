package aquarisim.fish;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import aquarisim.Time;
import aquarisim.fish.FishUpgrader.FishUpgrade;
import aquarisim.object.Object;
import aquarisim.object.ObjectFinder;
import aquarisim.object.ObjectType;
import aquarisim.plant.Plant;
import aquarisim.plant.PlantFinder;
import aquarisim.ui.AquariDialog;

public class Fish extends Actor {
	private float lastX = 0;
	private float lastAct = 0;

	private final static float SCALE = 0.3f;
	private FishState state;
	private Facing facing = Facing.RIGHT;
	private Object food = null;
	private Object reservedFood = null;
	private FishUpgrade upgrade = FishUpgrade.NONE;
	private Texture texture = new Texture(PixmapPool.getFastFish());
	private final static float STANDARD_SPEED = 100f;
	private final static float BOOST_SPEED = 200f;
	private float speed = 100f;
	private final FishUpgradePermission upgradePermission;
	private ObjectType objectType;
	private boolean boosting = false;

	private enum Facing {
		LEFT, RIGHT;
	}

	public Fish(FishUpgradePermission upgradePermission) {
		reDrawFish();
		setBounds(getX(), getY(), texture.getWidth() * SCALE,
				texture.getHeight() * SCALE);
		startWiggle();
		// Set the fish's "origin" as it's mid-point
		setOrigin(getWidth() / 2, getHeight() / 2);
		state = FishState.RETRIEVING_OBJECT.nextState(this);
		this.upgradePermission = upgradePermission;
		addListener(new FishListener());
		FishFinder.getInstance().addFish(this);
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

	private class FishListener extends ClickListener {
		@Override
		public void clicked(InputEvent event, float x, float y) {
			new AquariDialog().show(ObjectSwitchDialog.getInstance(Fish.this));
		}
	}

	ObjectType getObjectType() {
		return this.objectType;
	}

	public void setObjectType(ObjectType type) {
		this.objectType = type;
	}

	void setFood(Object food) {
		synchronized (this) {
			this.food = food;
		}
		reDrawFish();
	}

	Object getFood() {
		synchronized (this) {
			return this.food;
		}
	}

	void setState(FishState state) {
		synchronized (this) {
			this.state = state;
		}
	}

	FishState getState() {
		synchronized (this) {
			return this.state;
		}
	}

	void foodEaten() {
		synchronized (this) {
			food.eaten();
		}
		reDrawFish();
	}

	void setSpeed(float newSpeed) {
		this.speed = newSpeed;
	}

	FishUpgrade getUpgrade() {
		return upgrade;
	}

	public void setUpgrade(FishUpgrade upgrade) {
		this.upgrade = upgrade;
		texture = new Texture(upgrade.getPixmap());
	}

	float getSpeed() {
		return speed * upgrade.getMultiplier();
	}

	FishUpgradePermission getUpgradePermission() {
		return upgradePermission;
	}

	public void goToPlant() {
		Plant plant = PlantFinder.getInstance().getNext(objectType);
		FishState.moveAndNotify(plant.getX(), plant.getY(), this);
	}

	public void goAndGetObject(ObjectType type) {
		this.food = null;
		startWiggle();
		setObjectType(type);
		Object f = ObjectFinder.getInstance().getNext(type);
		if (null != f) {
			FishState.eatFood(this, f);
			state = FishState.TRAVELLING_TO_OBJECT;
		} else {
			Plant p = PlantFinder.getInstance().getNext(objectType);
			FishState.moveAndNotify(p.getX(), p.getY(), this);
			state = FishState.TRAVELLING_TO_PLANT;
		}
	}

	void startBoost() {
		boosting = true;
		speed = BOOST_SPEED;
		texture = new Texture(PixmapPool.getFastFish());
		reDrawFish();
		reRunMovement();
	}

	void stopBoost() {
		boosting = false;
		this.speed = STANDARD_SPEED;
		texture = new Texture(PixmapPool.getFastFish());
		reDrawFish();
		reRunMovement();
	}

	synchronized private void reRunMovement() {
		synchronized (this) {
			for (Action a : getActions()) {
				if (a.getClass() == SequenceAction.class) {
					SequenceAction sa = (SequenceAction) a;
					for (Action saAction : sa.getActions()) {
						if (saAction.getClass() == MoveToAction.class) {
							MoveToAction mta = (MoveToAction) saAction;
							float x = mta.getX();
							float y = mta.getY();
							removeAction(sa);
							FishState.moveAndNotify(x, y, this);
						}
					}
				}
			}
		}
	}
	
	private synchronized void reDrawFish() {
		Pixmap p;
		if(boosting) {
			p = PixmapPool.getFastFish();
		} else {
			p = upgrade.getPixmap();
		}

		Pixmap pixMap = new Pixmap(p.getWidth(), p.getHeight(), Pixmap.Format.RGBA8888);
		pixMap.drawPixmap(p, 0, 0);
		
		if(food != null) {
			addObject(pixMap, food.getType().getPixmap());
		}
		texture.dispose();
		texture = new Texture(pixMap);
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		pixMap.dispose();
	}
	
	private void addObject(Pixmap base, Pixmap objectPix) {
		base.drawPixmap(objectPix, 0, base.getHeight() - objectPix.getHeight());
	}

	void setReservedFood(Object object) {
		reservedFood = object;
	}

	void pickUpObject() {
		food = reservedFood;
		reservedFood = null;
		reDrawFish();
	}

	public Object getReservedObject() {
		return reservedFood;
	}
}