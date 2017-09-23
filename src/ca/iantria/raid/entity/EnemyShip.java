package ca.iantria.raid.entity;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import ca.iantria.raid.util.Constants;

public class EnemyShip extends Enemy {

	public boolean isDestroyed;
	public boolean isSinking;
	public Animation exlosion1;
	public Animation exlosion2;
	public Animation exlosion3;
	public int explosionElapsedTime;
	public int healthElapsedTime;
	private int firedCount = 0;
	private boolean isCannonReadyToFire;
	private int cannonElapsedTime;
	
	public int getHealthElapsedTime() {
		return healthElapsedTime;
	}
	public void setHealthElapsedTime(int healthElapsedTime) {
		this.healthElapsedTime = healthElapsedTime;
	}
	public int getExplosionElapsedTime() {
		return explosionElapsedTime;
	}
	public void setExplosionElapsedTime(int explosionElapsedTime) {
		this.explosionElapsedTime = explosionElapsedTime;
	}
	public boolean isSinking() {
		return isSinking;
	}
	public void setSinking(boolean isSinking) {
		this.isSinking = isSinking;
	}
	public boolean isDestroyed() {
		return isDestroyed;
	}
	public void setDestroyed(boolean isDestroyed) {
		this.isDestroyed = isDestroyed;
	}
	public Animation getExlosion1() {
		return exlosion1;
	}
	public Animation getExlosion2() {
		return exlosion2;
	}
	public Animation getExlosion3() {
		return exlosion3;
	}
	
	public EnemyShip(String id, float scale, Vector2f position, float rotation) {
		super(id, scale, position, rotation);
	}

	@Override
	public void init() {
		this.image = main.enemyShipImage.copy();
		this.exlosion1 = main.explosionAnimations[7].copy();
		this.exlosion2 = main.explosionAnimations[6].copy();
		this.exlosion3 = main.explosionAnimations[7].copy();
		this.exlosion2.setCurrentFrame(10);
		this.exlosion3.setCurrentFrame(5);
		this.health = 1;
		this.isDestroyed = false;
		this.isSinking = false;
		this.isAttacking = false;
		this.speed = 0;
    	setVector1(position.copy());
    	setVector2(position.copy());
    	setVector3(position.copy());
    	setVector4(position.copy());
    	refireInterval = Constants.FIRING_INTERVAL_MISSLE;
    	updateVectorsForMovingObjects();
	}

	@Override
	public void reset() {
		isDestroyed = false;
		isSinking = false;
		isAttacking = false;
		wasHit = false;
		health = 1;
		rotation = 0;
		speed = 0;
		image.setAlpha(1f);
		position.x = main.gameMap.position.x + getStartingPosition().x - Constants.WINDOW_WIDTH/2 + 400;
		position.y = main.gameMap.position.y + getStartingPosition().y;
		updateVectorsForMovingObjects();
	}

	@Override
	public void update(GameContainer gc, int delta) {
    	position.x = position.x - main.map_dx;
    	position.y = position.y - main.map_dy;
		
		updateVectorsForMovingObjects();
		
        healthElapsedTime += delta;
        if (!isAttacking && !isDestroyed() && !isSinking() && getHealthElapsedTime() > 3000) {
        	setHealthElapsedTime(0);
        	health += 1 + (6 - main.gameMode.getRemainingFactories());
        	if (health >= Constants.ENEMY_SHIP_HEALTH) {
        		health = Constants.ENEMY_SHIP_HEALTH;
        		speed = Constants.ENEMY_SHIP_SPEED;
        		main.statistics.enemyShipWasCompleted = true;
        		isAttacking = true;
        		main.combatText.add(new ScrollingCombatText("LandedOnSecretBase", 1f, main.plane.position.copy(), ("Enemy Ship Completed!"), Color.yellow, main.ttfTiny, true));
        	}
        }
		
        if(isSinking()){
		    setExplosionElapsedTime(getExplosionElapsedTime() + delta);
			if(getExplosionElapsedTime() > 6000) {
				setExplosionElapsedTime(0);
				setDestroyed(true);
				setSinking(false);
			}
        }
		
    	setElapsedTime(getElapsedTime() + delta); 
        if (isAttacking && !isSinking && !isDestroyed() && !isReadyToFire() 
        		&& !main.plane.isCrashed && (getElapsedTime() >= getRefireInterval())) {
        	setElapsedTime(0);
        	setReadyToFire(true);
        }

        //if (!isDestroyed && wasHitByMissile) main.missleImpact.update(delta);
        
        if (isDestroyed && getElapsedTime() > Constants.ENEMY_SHIP_RESPAWN_TIMER){
        	setElapsedTime(0);
        	reset();
        }
        
    	cannonElapsedTime = cannonElapsedTime + delta; 
        if (isAttacking && !isSinking && !isDestroyed() && !isReadyToFire() 
        		&& !main.plane.isCrashed && cannonElapsedTime >= 1000) {
        	cannonElapsedTime = 0;
        	isCannonReadyToFire = true;
        }
        
		if ((getVector1().getX() <= Constants.WINDOW_WIDTH && getVector1().getX() >= 0) && 
		    	(getVector1().getY() <= Constants.WINDOW_HEIGHT && getVector1().getY() >= 0) && isAttacking) {
	        	getImage().setRotation((float)main.getAngleToPlane(getVector1()));
				// Refire
	            if (isReadyToFire()) fireCruiseMissle(CruiseMissle.PLAYER_IS_TARGET);
	            if (isCannonReadyToFire) fireCannon();
	    } else if (isAttacking && speed == 0 && !main.carrier.isSinking && !main.carrier.isDestroyed) {
	    	if (isReadyToFire()) {
	    		fireCruiseMissle(CruiseMissle.CARRIER_IS_TARGET);
	    		main.combatText.add(new ScrollingCombatText("DestroyerAttacking", 1f, main.plane.position.copy(), ("Carrier under attack!"), Color.yellow, main.ttfTiny, true));
	    	}
	    }
        
		if (Math.abs((vector1.x - 150) - main.carrier.position.x) < 30 ){
			speed = 0;
		}
		
	    float hip = delta * speed;

	    position.x += hip * java.lang.Math.sin(java.lang.Math.toRadians(rotation+270));
	    position.y -= hip *java.lang.Math.cos(java.lang.Math.toRadians(rotation+270));

	    if (rotation >= 360) rotation = rotation - 360;
	    if (rotation <= 0) rotation = rotation + 360;

	    image.rotate(getRotation() - image.getRotation());
        
	}

	@Override
	public void render(GameContainer gc, Graphics gr) {
        if (isDestroyed()){
        	// dont draw it
        } else if (isSinking()) {
        	wasHitByMissile = false;
        	if(getExplosionElapsedTime() > 2000) image.setAlpha(1.2f - (((float)getExplosionElapsedTime()-2000f)/4000f));
        	image.draw(position.x, position.y, scale);
        	getExlosion1().draw(position.x+50, position.y);
        	getExlosion2().draw(position.x+175, position.y+2);
        	getExlosion3().draw(position.x+300, position.y-20);
        	getExlosion3().draw(position.x+100, position.y-5);
        	getExlosion2().draw(position.x+225, position.y+5);
        	getExlosion1().draw(position.x+350, position.y-21);
        	getExlosion2().draw(position.x+125, position.y-15);
        	getExlosion1().draw(position.x+250, position.y-10);
        	getExlosion3().draw(position.x+325, position.y+7);
        } else {
    		image.draw(vector1.x, vector1.y, scale);
    		image.draw(vector2.x, vector2.y, scale);
    		image.draw(vector3.x, vector3.y, scale);
    		image.draw(vector4.x, vector4.y, scale);
    		
        	gr.setColor(Color.green);
        	Rectangle rect = new Rectangle(getVector1().getX() + getImage().getWidth()*scale/2-25, 
        				getVector1().getY() + getImage().getHeight()*getScale() + 3, 
        				getHealth(), 3);
        	gr.fill(rect);
        	rect = new Rectangle(getVector2().getX() + getImage().getWidth()*scale/2-25, 
        				getVector2().getY() + getImage().getHeight()*getScale() + 3, 
        				getHealth(), 3);
        	gr.fill(rect);
        	rect = new Rectangle(getVector3().getX() + getImage().getWidth()*scale/2-25, 
        				getVector3().getY() + getImage().getHeight()*getScale() + 3, 
        				getHealth(), 3);
        	gr.fill(rect);
        	rect = new Rectangle(getVector4().getX() + getImage().getWidth()*scale/2-25, 
        				getVector4().getY() + getImage().getHeight()*getScale() + 3, 
        				getHealth(), 3);
        	gr.fill(rect);
        }
        
    	if (wasHitByMissle()){
    		renderMissleImpact();
    		if (getWasHitByMissleAnimation().getFrame() < 15){
    			getWasHitByMissleAnimation().draw(getVector1().getX()+ getImage().getWidth()*scale/2, getVector1().getY()-5);
    			getWasHitByMissleAnimation().draw(getVector2().getX()+ getImage().getWidth()*scale/2, getVector2().getY()-5);
    			getWasHitByMissleAnimation().draw(getVector3().getX()+ getImage().getWidth()*scale/2, getVector3().getY()-5);
    			getWasHitByMissleAnimation().draw(getVector4().getX()+ getImage().getWidth()*scale/2, getVector4().getY()-5);
    		} else {
    			setWasHitByMissle(false);
    			setWasHit(false);
    			getWasHitByMissleAnimation().restart();
    		}
    	}
        
    	if (wasHitByCannon()){
    		if (getWasHitByCannonAnimation().getFrame() < 15){
    			getWasHitByCannonAnimation().draw(getVector1().getX()+ getImage().getWidth()*scale/2, getVector1().getY()-5);
    			getWasHitByCannonAnimation().draw(getVector2().getX()+ getImage().getWidth()*scale/2, getVector2().getY()-5);
    			getWasHitByCannonAnimation().draw(getVector3().getX()+ getImage().getWidth()*scale/2, getVector3().getY()-5);
    			getWasHitByCannonAnimation().draw(getVector4().getX()+ getImage().getWidth()*scale/2, getVector4().getY()-5);
    		} else {
    			setWasHit(false);
    			setWasHitByCannon(false);
    			getWasHitByCannonAnimation().restart();
    		}
    	}
	}
	
    private void fireCruiseMissle(int target) {
    	if (target == CruiseMissle.CARRIER_IS_TARGET){
    		if (!main.carrierAlarm.playing()) main.carrierAlarm.play();
    	}
		main.statistics.numberOfTimesCruiseMissileFired++;
		CruiseMissle projectile = new CruiseMissle(id + "_CruiseMissle" + firedCount++, 0.1f, 
				new Vector2f(getVector1().getX() + image.getWidth()*scale/2, getVector1().getY()),
				getImage().getRotation(), main.cruiseMissleImage.copy(), Projectile.ENEMY_CRUISE_MISSLE, target);
		main.fireMissleEffect.play(2.2f, 1.0f);
		main.projectiles.add(projectile);
		setReadyToFire(false);
		setElapsedTime(0);
    }
    
    private void fireCannon() {
		Projectile p= new Projectile(id + "_Bullet" + firedCount++, 0.4f, 
			new Vector2f(getVector1().getX() + image.getWidth()*scale/2, getVector1().getY()),
			getImage().getRotation(), main.enemyProjectileImage.copy(), Projectile.AA_GUN_BULLET);
		p.speed = Constants.MISSLE_SPEED - 0.2f;
		main.AAGunFireSound.play(1.0f, 0.65f);
		main.projectiles.add(p);
		isCannonReadyToFire = false;
    }
}
