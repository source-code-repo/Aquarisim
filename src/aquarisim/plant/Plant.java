package aquarisim.plant;

import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;

import aquarisim.Main;
import aquarisim.Time;
import aquarisim.object.Object;
import aquarisim.object.ObjectType;
import aquarisim.plant.PlantUpgrader.PlantUpgrade;

public class Plant extends Actor {
	private final Texture texture;
    private float lastFood = 0f;
    private Time productionRate = new Time(10f);
	private final ObjectType objectType;
	private PlantUpgrade upgrade = PlantUpgrade.NONE;
	private final PlantUpgradePermission upgradePermission;
	private final static float SCALE = 0.7f;
    
	public Plant(ObjectType foodType, PlantUpgradePermission upgradePermission) {
		this.objectType = foodType;
		this.texture = foodType.getPlantTexture();
		setBounds(getX(),getY(),texture.getWidth() * SCALE, texture.getHeight() * SCALE);
		// Set the "origin" as the mid-point
		setOrigin(getWidth()/2, getHeight()/2);
		PlantFinder.getInstance().addPlant(this);
		this.upgradePermission = upgradePermission;
		setTouchable(Touchable.disabled);
	}
	
	@Override
    public void draw(Batch batch, float alpha){
        batch.draw(texture,this.getX(),getY(),this.getOriginX(),this.getOriginY(),this.getWidth(),
                this.getHeight(),this.getScaleX(), this.getScaleY(),this.getRotation(),0,0,
                texture.getWidth(),texture.getHeight(),false,false);
    }
	
	@Override
	public void act(float delta) {
		lastFood += delta;
		if(lastFood > (productionRate.getFloat() * upgrade.getMultiplier())) {
			SequenceAction rotateAndProduce = new SequenceAction(
					Actions.rotateBy(-2f, Time.convert(0.5f)), 
					Actions.rotateBy(2f, Time.convert(0.5f)),
					Actions.run(new Runnable() {
						@Override
						public void run() {
							produceFood();
						}
					}));
			this.addAction(rotateAndProduce);
			lastFood = 0;
		}
		super.act(delta);
	}
	
	private void produceFood() {
		float produceX = getX() + (getWidth() / 2);
		float produceY = getY() + (getHeight() / 2);
		Random r = new Random();
		float randomX = produceX + r.nextInt(100);
		float randomY = produceY + r.nextInt(100);
		Object food = new Object(randomX, randomY, objectType);
		Main.getInstance().addToTop(food);
	}
	
	ObjectType getFoodType() {
		return this.objectType;
	}
	
	void setProductionRate(float newRate) {
		this.productionRate.time(newRate);
	}
	
	PlantUpgrade getUpgrade() {
		return upgrade;
	}

	public void setUpgrade(PlantUpgrade upgrade) {
		this.upgrade = upgrade;
	}
	
	PlantUpgradePermission getUpgradePermission() {
		return upgradePermission;
	}
}