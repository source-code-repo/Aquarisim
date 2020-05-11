package aquarisim.object;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;

import aquarisim.Time;
import aquarisim.fish.Fish;

public class Object extends Actor {
	private enum State {
		SITTING, TO_BE_EATEN, EATEN;
	}
	
	private final static float SCALE = 0.5f;
	private float age = 0f;
	private final Texture texture;
	private final static Time MAX_AGE = new Time(5f);
	private State state = State.SITTING;
	private ObjectType type;
	
	public Object(float x, float y, ObjectType type) {
		setPosition(x, y);
		this.type = type;
		texture = type.getTexture();
		setBounds(getX(), getY(), texture.getWidth() * SCALE, texture.getHeight() * SCALE);
		// Set the "origin" as it's mid-point
		setOrigin(getWidth()/2, getHeight()/2);
		ObjectFinder.getInstance().addFood(this);
		setTouchable(Touchable.disabled);
	}
	
	@Override
    public void draw(Batch batch, float alpha){
        batch.draw(texture,this.getX(),getY(),this.getOriginX(),this.getOriginY(),this.getWidth(),
                this.getHeight(),this.getScaleX(), this.getScaleY(),this.getRotation(),0,0,
                texture.getWidth(),texture.getHeight(), false,false);
    }
	
	@Override
	public void act(float delta) {
		age = age + delta;
		if(state == State.SITTING) {
			if(age > MAX_AGE.getFloat()) {
				ObjectFinder.getInstance().removeFood(this);
				remove();
			}
		}
		
		if(state == State.EATEN) {
			ObjectFinder.getInstance().removeFood(this);
			remove();
		}
		super.act(delta);
	}
	
	public ObjectType getType() {
		return type;
	}
	
	public void toBeEaten(Fish fish) {
		this.state = State.TO_BE_EATEN;
	}
	
	public void eaten() {
		this.state = State.EATEN;
	}
	
	public void sitting() {
		this.state = State.SITTING;
	}
	
	boolean toBeEaten() {
		if(this.state == State.TO_BE_EATEN) {
			return true;
		} else {
			return false;
		}
	}
}