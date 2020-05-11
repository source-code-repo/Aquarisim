package aquarisim.object;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

public enum ObjectType {
	PEARL {
		@Override
		public Texture getTexture() {
			return ObjectTexturePool.getPearl();
		}

		@Override
		public Texture getPlantTexture() {
			return PlantTexturePool.getTreasureChest();
		}

		@Override
		public Pixmap getPixmap() {
			return ObjectTexturePool.getPearlPixmap();
		}
	},
	GLOVES {
		@Override
		public Texture getTexture() {
			return ObjectTexturePool.getGlove();
		}

		@Override
		public Texture getPlantTexture() {
			return PlantTexturePool.getTreasureChest();
		}

		@Override
		public Pixmap getPixmap() {
			return ObjectTexturePool.getGlovesPixmap();
		}
	},
	LEG {
		@Override
		public Texture getTexture() {
			return ObjectTexturePool.getLeg();
		}

		@Override
		public Texture getPlantTexture() {
			return PlantTexturePool.getTreasureChest();
		}

		@Override
		public Pixmap getPixmap() {
			return ObjectTexturePool.getLegPixmap();
		}

	};
	public abstract Texture getTexture();
	public abstract Texture getPlantTexture();
	public abstract Pixmap getPixmap();
}