package ca.iantria.raid.entity;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

import ca.iantria.raid.util.Constants;

public class EnemyFighter extends EnemyPlane {

	public EnemyFighter(String id, float scale, Vector2f position, float rotation) {
		super(id, scale, position, rotation);
	}

	@Override
	public void init() {
		setType(EnemyPlane.ENEMY_FIGHTER);
    	setVector1(position.copy());
    	setVector2(position.copy());
    	setVector3(position.copy());
    	setVector4(position.copy());
    	speed = 0;
		planeExploded = main.explosionAnimations[6].copy();
		planeExploded.stopAt(15);
		planeWasHitAnimation = main.explosionAnimations[3].copy();
		planeWasHitAnimation.stopAt(15);
		random = (float)(main.random.nextInt(10)-5)/100.0f;
		refireInterval = Constants.ENEMY_FIGHTER_FIRING_INTERVAL;
		image = main.enemyFighterImage.copy();
        image.setCenterOfRotation(image.getWidth()/2, image.getHeight()/2);
        health = Constants.ENEMY_FIGHTER_HEALTH;
        isDestroyed = false;
        updateVectorsForMovingObjects();
	}

	@Override
	public void reset() {
		isLanded = true;
		wasHit = false;
		isDestroyed = false;
		setRespawnElapsedTime(0);
		setElapsedTime(0);
		getPlaneExploded().restart();
		setHealth(Constants.ENEMY_FIGHTER_HEALTH);
		setSpeed(0);
		rotation = 270;
		position.x = main.gameMap.position.x + getStartingPosition().x - Constants.WINDOW_WIDTH/2 + 400;
		position.y = main.gameMap.position.y + getStartingPosition().y;
		updateVectorsForMovingObjects();
	}
	

	@Override
	public void update(GameContainer gc, int delta) {
		
    	position.x = position.x - main.map_dx;
    	position.y = position.y - main.map_dy;
		
		updateVectorsForMovingObjects();
		
    	if (isDestroyed) {
    		speed = 0.0f;
    		setRespawnElapsedTime(getRespawnElapsedTime() + delta);
        	if (getRespawnElapsedTime() > Constants.ENEMY_FIGHTER_RESPAWN_TIMER){
        		reset();
        	}
    	} else if (!isLanded) {
        	if (speed >= Constants.ENEMY_FIGHTER_SPEED + random){
	    		float angleToTarget = (float) main.getSignedDegreesToPlane(position);
	    		double diff = main.calculateDifferenceBetweenAngles(angleToTarget, rotation);

	          	setRefireElapsedTime(getRefireElapsedTime() + delta);
	            if (!isDestroyed() && !isReadyToFire() && 
	            		(getRefireElapsedTime() >= getRefireInterval())) {
	            	setRefireElapsedTime(0);
	            	setReadyToFire(true);
	            }
	    		
	    		// Is plane on screen?
				if ((position.getX() <= Constants.WINDOW_WIDTH && position.getX() >= 0) && 
			    	(position.getY() <= Constants.WINDOW_HEIGHT && position.getY() >= 0)) {
					if (!main.enemyCruise.playing()) main.enemyCruise.play(1f + rotation/360,1.0f);
					
					//Fire if you have a good angle, and ready to fire
					if ((diff > -5 && diff < 5) && isReadyToFire()) fireGun();
				} else {
					//main.enemyCruise.stop();
				}
	    		
	    		if (diff < -5) {
					rotation = rotation + 0.15f * delta;
				} else if (diff > 5){
					rotation = rotation - 0.15f * delta;
				}
        	} else {
        		speed = speed + (0.00005f * delta);
        		if (speed > Constants.ENEMY_FIGHTER_SPEED + random) speed = Constants.ENEMY_FIGHTER_SPEED + random ; 
        	}
        } else {
        	setElapsedTime(getElapsedTime() + delta);
        	if (main.gameMode.getRemainingFactories() < 6 && getElapsedTime() > 3000){
        		setLanded(false);
        		setElapsedTime(0);
        	}
        }

	    float hip = delta * speed;

	    position.x += hip * java.lang.Math.sin(java.lang.Math.toRadians(rotation));
	    position.y -= hip *java.lang.Math.cos(java.lang.Math.toRadians(rotation));

	    if (rotation >= 360) rotation = rotation - 360;
	    if (rotation <= 0) rotation = rotation + 360;
    	
	    image.rotate(getRotation() - image.getRotation());
	}

	@Override
	public void render(GameContainer gc, Graphics gr) {
        if (isDestroyed()){
        	if (getPlaneExploded().getFrame() < 15 && wasHitByMissile) renderMissleImpact();
    		getPlaneExploded().draw(getVector1().x, getVector1().y);
    		getPlaneExploded().draw(getVector2().x, getVector2().y);
    		getPlaneExploded().draw(getVector3().x, getVector3().y);
    		getPlaneExploded().draw(getVector4().x, getVector4().y);
	    } else {
	    	image.draw(getVector1().x, getVector1().y, scale);
	    	image.draw(getVector2().x, getVector2().y, scale);
	    	image.draw(getVector3().x, getVector3().y, scale);
	    	image.draw(getVector4().x, getVector4().y, scale);
	        
	    	if (wasHit){
	    		if (getPlaneWasHitAnimation().getFrame() < 15) {
	    			getPlaneWasHitAnimation().draw(getVector1().x+5, getVector1().y-13);
	    			getPlaneWasHitAnimation().draw(getVector2().x+5, getVector2().y-3);
	    			getPlaneWasHitAnimation().draw(getVector3().x+5, getVector3().y-3);
	    			getPlaneWasHitAnimation().draw(getVector4().x+5, getVector4().y-3);
	    		} else {
	    			setWasHit(false);
	    			getPlaneWasHitAnimation().restart();
	    		}
	    	}
	    }    
	}

    private void fireGun() {
		setReadyToFire(false);
		setElapsedTime(0);
		main.statistics.numberOfTimesFighterFired++;
    	Projectile projectile = new Projectile(id + "Bullet" + main.statistics.numberOfTimesFighterFired, 0.4f, 
    			new Vector2f(position.x + image.getWidth()*scale/2, position.y), rotation, 
    			main.enemyProjectileImage.copy(), Projectile.ENEMY_FIGHTER_BULLET);
		projectile.speed = Constants.MISSLE_SPEED - 0.2f;
		projectile.setRotation(getImage().getRotation());
		//if (!main.fireCannonEffect.playing()) 
		main.fireCannonEffect.play(1.0f, 0.65f);
		main.projectiles.add(projectile);
	}
}
