package ca.iantria.raid.entity;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

import ca.iantria.raid.util.Constants;

public class Carrier extends Entity {

	public boolean isDestroyed;
	public boolean isSinking;
	public Animation exlosion1;
	public Animation exlosion2;
	public Animation exlosion3;
	public int explosionElapsedTime;
	public int healthElapsedTime;
	public Animation wasHitByMissleAnimation;
	
	public Animation getWasHitByMissleAnimation() {
		return wasHitByMissleAnimation;
	}
	public void setWasHitByMissleAnimation(Animation wasHitByMissleAnimation) {
		this.wasHitByMissleAnimation = wasHitByMissleAnimation;
	}
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
	
	public Carrier(String id, float scale, Vector2f position, float rotation) {
		super(id, scale, position, rotation);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() {
		this.image = main.carrierImage.copy();
		this.exlosion1 = main.explosionAnimations[7].copy();
		this.exlosion2 = main.explosionAnimations[6].copy();
		this.exlosion3 = main.explosionAnimations[7].copy();
		this.exlosion2.setCurrentFrame(10);
		this.exlosion3.setCurrentFrame(5);
		this.health = Constants.MAX_HIT_POINTS_CARRIER;
		this.isDestroyed = false;
		this.isSinking = false;
		this.speed = Constants.CARRIER_SPEED;
		this.wasHitByMissleAnimation = main.explosionAnimations[1].copy();
		this.wasHitByMissleAnimation.stopAt(15);
	}

	@Override
	public void reset() {
		this.health = Constants.MAX_HIT_POINTS_CARRIER;
		this.isDestroyed = false;
		this.isSinking = false;
		this.position = this.startingPosition.copy();
		image.setAlpha(1f);
	}

	@Override
	public void update(GameContainer gc, int delta) {
		
    	if (health < 1 && !isSinking) setSinking(true);
    	if (isDestroyed && main.plane.livesCount > 1) {
    		main.statistics.numberOfLivesLost= main.statistics.numberOfLivesLost + (main.plane.livesCount - 1) ; 
    		main.plane.livesCount = 1;
    	}

    	if (isSinking && main.plane.isLanded) {
        	main.plane.setLanded(false);
    		main.gameMap.speed = Constants.MIN_PLANE_SPEED;
    		main.afterBurnerSpeedEffect.play();
    	}
		
        float hip = delta * speed;

    	position.x = position.x - main.map_dx;
    	position.y = position.y - main.map_dy;
        
        position.y -= hip;
        
        if (position.y < -(main.MAP_HEIGHT/2)) position.y = (main.MAP_HEIGHT/2);
        if (position.y > (main.MAP_HEIGHT/2)) position.y = -(main.MAP_HEIGHT/2);

        if (position.x < -(main.MAP_WIDTH/2)) position.x = (main.MAP_WIDTH/2);
        if (position.x > (main.MAP_WIDTH/2)) position.x = -(main.MAP_WIDTH/2);

        if(isSinking()){
		    setExplosionElapsedTime(getExplosionElapsedTime() + delta);
			if(getExplosionElapsedTime() > 6000) {
				setExplosionElapsedTime(0);
				setDestroyed(true);
				setSinking(false);
			}
        }
        
        healthElapsedTime += delta;
        if (!isDestroyed() && !isSinking() && getHealthElapsedTime() > 1000) {
        	setHealthElapsedTime(0);
        	health++;
        	if (health > Constants.MAX_HIT_POINTS_CARRIER) health = Constants.MAX_HIT_POINTS_CARRIER; 
        }
	}

	@Override
	public void render(GameContainer gc, Graphics gr) {
        if (isDestroyed()){
        	// dont draw it
        } else if (isSinking()) {
        	if(getExplosionElapsedTime() > 2000) image.setAlpha(1.2f - (((float)getExplosionElapsedTime()-2000f)/4000f));
        	image.draw(position.x, position.y, scale);
        	getExlosion1().draw(position.x, position.y);
        	getExlosion1().draw(position.x+15, position.y+125);
        	getExlosion1().draw(position.x-10, position.y+250);
        	getExlosion2().draw(position.x+5, position.y+75);
        	getExlosion2().draw(position.x, position.y+200);
        	getExlosion2().draw(position.x-5, position.y+50);
        	getExlosion3().draw(position.x+15, position.y+150);
        	getExlosion3().draw(position.x, position.y+225);
        	getExlosion3().draw(position.x+15, position.y+275);
        } else {
        	image.draw(position.x, position.y, scale);
        }
        
    	if (wasHit){
    		if (getWasHitByMissleAnimation().getFrame() < 15){
    			getWasHitByMissleAnimation().draw(position.getX()+15, position.getY()+image.getHeight()/2*scale);
    		} else {
    			setWasHit(false);
    			getWasHitByMissleAnimation().restart();
    		}
    	}
        
	}
}
