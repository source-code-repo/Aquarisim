package aquarisim.creature;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;

public class Creature extends Actor {
	private final float SCALE = 0.5f;
	private final Texture texture;
	private final CreatureType creatureType;
	
	public Creature(float x, float y, CreatureType type) {
		setPosition(x, y);
		this.creatureType = type;
		texture = type.getTexture();
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		setBounds(getX(), getY(), texture.getWidth() * SCALE, texture.getHeight() * SCALE);
		// Set the "origin" as it's mid-point
		setOrigin(getWidth()/2, getHeight()/2);
		setVisible(false);
		setTouchable(Touchable.disabled);
	}
	
	@Override
    public void draw(Batch batch, float alpha){
        batch.draw(texture,this.getX(),getY(),this.getOriginX(),this.getOriginY(),this.getWidth(),
                this.getHeight(),this.getScaleX(), this.getScaleY(),this.getRotation(),0,0,
                texture.getWidth(),texture.getHeight(), false,false);
    }
	
	public CreatureType getType() {
		return creatureType;
	}
	
	@Override
	public boolean remove() {
		return super.remove();
	}
}