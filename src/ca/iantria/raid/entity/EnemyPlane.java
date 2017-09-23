package ca.iantria.raid.entity;

import org.newdawn.slick.Animation;
import org.newdawn.slick.geom.Vector2f;

public abstract class EnemyPlane extends Enemy {

	public Animation planeExploded;
	public Animation planeWasHitAnimation;
	public boolean isLanded = true;
	public float random;
	public static final int ENEMY_FIGHTER = 1;
	public static final int ENEMY_BOMBER = 2;
	public int type;
	
	public EnemyPlane(String id, float scale, Vector2f position, float rotation) {
		super(id, scale, position, rotation);
	}
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public float getRandom() {
		return random;
	}

	public void setRandom(int random) {
		this.random = random;
	}

	public Animation getPlaneExploded() {
		return planeExploded;
	}

	public void setPlaneExploded(Animation planeExploded) {
		this.planeExploded = planeExploded;
	}

	public Animation getPlaneWasHitAnimation() {
		return planeWasHitAnimation;
	}

	public void setPlaneWasHitAnimation(Animation planeWasHitAnimation) {
		this.planeWasHitAnimation = planeWasHitAnimation;
	}

	public boolean isLanded() {
		return isLanded;
	}

	public void setLanded(boolean isLanded) {
		this.isLanded = isLanded;
	}

}
