package aquarisim.creature;

import com.badlogic.gdx.graphics.Texture;

import aquarisim.object.ObjectType;

public enum CreatureType {
	OYSTER {
		@Override
		public Texture getTexture() {
			return TexturePool.getOyster();
		}

		@Override
		public ObjectType getObjectType() {
			return ObjectType.PEARL;
		}
	},
	OCTOPUS {
		@Override
		public Texture getTexture() {
			return TexturePool.getOctopus();
		}

		@Override
		public ObjectType getObjectType() {
			return ObjectType.GLOVES;
		}
	},
	SHARK {
		@Override
		public Texture getTexture() {
			return TexturePool.getShark();
		}

		@Override
		public ObjectType getObjectType() {
			return ObjectType.LEG;
		}
	};
	public abstract Texture getTexture();
	public abstract ObjectType getObjectType();
}