package ca.iantria.raid.entity;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import ca.iantria.raid.util.Constants;

public class AAGun extends Enemy {

	public Animation explodeAnimation;
	private int firedCount = 0;
	
	public AAGun(String id, float scale, Vector2f position, float rotation) {
		super(id, scale, position, rotation);
	}

	@Override
	public void init() {
		this.image = main.AAGunImage.copy();
		scale = 1f;
		health = Constants.ENEMY_AA_GUN_HEALTH;
		refireInterval = 1800 + main.random.nextInt(700);
    	setVector1(position.copy());
    	setVector2(position.copy());
    	setVector3(position.copy());
    	setVector4(position.copy());
    	updateVectorsForStationaryObjects();
		explodeAnimation = main.explosionAnimations[1].copy();
		explodeAnimation.stopAt(15);
		elapsedTime = 0;
		respawnTime = 0;
		isReadyToFire = false;
	}

	@Override
	public void reset() {
		health = Constants.ENEMY_AA_GUN_HEALTH;
		isDestroyed = false;
		isReadyToFire = false;
		setWasHit(false);
		setWasHitByCannon(false);
		setWasHitByMissle(false);
		updateVectorsForStationaryObjects();
		this.elapsedTime = 0;
		this.respawnTime = 0;
		this.explodeAnimation.restart();
		this.explodeAnimation.stopAt(15);
	}

	@Override
	public void update(GameContainer gc, int delta) {
		updateVectorsForStationaryObjects();		
		// Ready to Fire?
    	setElapsedTime(getElapsedTime() + delta); 
        if (!isDestroyed() && !isReadyToFire() && !main.plane.isCrashed && (getElapsedTime() >= getRefireInterval())) {
        	setElapsedTime(0);
        	setReadyToFire(true);
        }
    	
    	// Rotation
		if ((getVector1().getX() <= Constants.WINDOW_WIDTH && getVector1().getX() >= 0) && 
	    	(getVector1().getY() <= Constants.WINDOW_HEIGHT && getVector1().getY() >= 0) ||
	    	(getVector2().getX() <= Constants.WINDOW_WIDTH && getVector2().getX() >= 0) && 
	    	(getVector2().getY() <= Constants.WINDOW_HEIGHT && getVector2().getY() >= 0) ||
	    	(getVector3().getX() <= Constants.WINDOW_WIDTH && getVector3().getX() >= 0) && 
	    	(getVector3().getY() <= Constants.WINDOW_HEIGHT && getVector3().getY() >= 0) ||
	    	(getVector4().getX() <= Constants.WINDOW_WIDTH && getVector4().getX() >= 0) && 
	    	(getVector4().getY() <= Constants.WINDOW_HEIGHT && getVector4().getY() >= 0)) {
        	getImage().setRotation((float)main.getAngleToPlane(getVector1()));
			// Refire
            if (isReadyToFire()) fireAAGun();
	    } else {
	    	setReadyToFire(false);
	    }
		
		//Respawn AA
		if (isDestroyed()) {
			setRespawnTime(getRespawnTime() + delta);
			if (getRespawnTime() >= (3000 + 3000 * main.gameMode.getRemainingFactories())) {
            	setRespawnTime(0);
            	reset();
			}
		}
	}

	@Override
	public void render(GameContainer gc, Graphics gr) {
    	if (isDestroyed()) {
    		if (explodeAnimation.getFrame() < 15){
            	if (wasHitByMissile) renderMissleImpact();
        		explodeAnimation.draw(getVector1().getX(), getVector1().getY(), explodeAnimation.getWidth(), explodeAnimation.getHeight());
        		explodeAnimation.draw(getVector2().getX(), getVector2().getY(), explodeAnimation.getWidth(), explodeAnimation.getHeight());
        		explodeAnimation.draw(getVector3().getX(), getVector3().getY(), explodeAnimation.getWidth(), explodeAnimation.getHeight());
        		explodeAnimation.draw(getVector4().getX(), getVector4().getY(), explodeAnimation.getWidth(), explodeAnimation.getHeight());
    		} else {

    		}
    	} else {
    		image.draw(vector1.x, vector1.y, scale);
    		image.draw(vector2.x, vector2.y, scale);
    		image.draw(vector3.x, vector3.y, scale);
    		image.draw(vector4.x, vector4.y, scale);
        	
        	if (wasHit){
        		if (getWasHitByCannonAnimation().getFrame() < 15){
        			getWasHitByCannonAnimation().draw(getVector1().getX()-5, getVector1().getY()-5);
        		} else {
        			setWasHit(false);
        			getWasHitByCannonAnimation().restart();
        		}
        	}
        	
        	
        	gr.setColor(Color.green);
        	Rectangle rect = new Rectangle(getVector1().getX() - 3, 
        				getVector1().getY() + getImage().getHeight()*getScale(), 
        				getHealth()*5, 3);
        	gr.fill(rect);
        	rect = new Rectangle(getVector2().getX() - 3, 
        				getVector2().getY() + getImage().getHeight()*getScale(), 
        				getHealth()*5, 3);
        	gr.fill(rect);
        	rect = new Rectangle(getVector3().getX() - 3, 
        				getVector3().getY() + getImage().getHeight()*getScale(), 
        				getHealth()*5, 3);
        	gr.fill(rect);
        	rect = new Rectangle(getVector4().getX() - 3 , 
        				getVector4().getY() + getImage().getHeight()*getScale(), 
        				getHealth()*5, 3);
        	gr.fill(rect);
    	}
	}

	
    private void fireAAGun() {
    	if (main.gameMode.isReadyToFireCruiseMissle){
    		main.statistics.numberOfTimesCruiseMissileFired++;
    		CruiseMissle projectile = new CruiseMissle(id + "_CruiseMissle" + firedCount, 0.1f, 
    				new Vector2f(getVector1().getX() + image.getWidth()*scale/2, getVector1().getY()),
    				getImage().getRotation(), main.cruiseMissleImage.copy(), Projectile.ENEMY_CRUISE_MISSLE, CruiseMissle.PLAYER_IS_TARGET);
			main.fireMissleEffect.play(2.2f, 1.0f);
			main.projectiles.add(projectile);
    		main.gameMode.isReadyToFireCruiseMissle = false;
    		setReadyToFire(false);
    	} else { 
    		firedCount++;
    		main.statistics.numberOfTimesAAGunFired++;
			Projectile p= new Projectile(id + "_Bullet" + firedCount, 0.4f, 
				new Vector2f(getVector1().getX() + image.getWidth()*scale/2, getVector1().getY()),
				getImage().getRotation(), main.enemyProjectileImage.copy(), Projectile.AA_GUN_BULLET);
			p.speed = Constants.MISSLE_SPEED - 0.2f;
			main.AAGunFireSound.play(1.0f, 0.65f);
			
			main.projectiles.add(p);
			setReadyToFire(false);
    	}
	}
}
