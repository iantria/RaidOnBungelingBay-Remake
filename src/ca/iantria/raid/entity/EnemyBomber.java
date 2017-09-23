package ca.iantria.raid.entity;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

import ca.iantria.raid.util.Constants;

public class EnemyBomber extends EnemyPlane {

	public Animation bombsDroppingAnimation;
	
	public EnemyBomber(String id, float scale, Vector2f position, float rotation) {
		super(id, scale, position, rotation);
	}

	public void setRandom() {
		this.random = main.random.nextInt(100);
	}
	
	@Override
	public void init() {
		setType(EnemyPlane.ENEMY_BOMBER);
    	setVector1(position.copy());
    	setVector2(position.copy());
    	setVector3(position.copy());
    	setVector4(position.copy());
    	speed = 0;
		planeExploded = main.explosionAnimations[6].copy();
		planeExploded.stopAt(15);
		planeWasHitAnimation = main.explosionAnimations[3].copy();
		planeWasHitAnimation.stopAt(15);
		image = main.enemyBomberImage.copy();
        image.setCenterOfRotation(image.getWidth()/2, image.getHeight()/2);
		bombsDroppingAnimation = main.explosionAnimations[5].copy();
		bombsDroppingAnimation.stopAt(15);
        health = Constants.ENEMY_BOMBER_HEALTH;
        isDestroyed = false;
        isAttacking = false;
        updateVectorsForMovingObjects();
	}

	@Override
	public void reset() {
		isLanded = true;
		wasHit = false;
		isDestroyed = false;
        isAttacking = false;
		setRespawnElapsedTime(0);
		setElapsedTime(0);
		getPlaneExploded().restart();
        health = Constants.ENEMY_BOMBER_HEALTH;
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
		
        if (collidesWith(main.carrier) && !isDestroyed() && !isLanded()) {
        	setAttacking(true);
        }
        
        if(isAttacking() && !main.carrierAlarm.playing() && !main.carrier.isDestroyed()) {
        	main.combatText.add(new ScrollingCombatText("BombersAttacking" + main.carrier.health, 1f, main.plane.position.copy(), ("Carrier under attack!"), Color.yellow, main.ttfTiny, true));
        	main.carrierAlarm.play(1.0f, 0.5f);
        }
        
    	if (isDestroyed) {
    		isAttacking = false;
    		speed = 0.0f;
    		setRespawnElapsedTime(getRespawnElapsedTime() + delta);
        	if (getRespawnElapsedTime()> (Constants.ENEMY_BOMBER_RESPAWN_TIMER + (main.gameMode.getRemainingFactories()*10000))){
        		reset();
        	}
    	} else if (main.carrier.isDestroyed()) {
    		
    	} else if (!isLanded) {
        	if (speed == Constants.MIN_PLANE_SPEED){
	    		float angleToTarget = (float) main.getSignedDegreesToCarrier(position);
	    		double diff = main.calculateDifferenceBetweenAngles(angleToTarget, rotation);
	    		if (diff < -5) {
					rotation = rotation + 0.1f * delta;
				} else if (diff > 5){
					rotation = rotation - 0.1f * delta;
				}
        	} else {
        		speed = speed + (0.00005f * delta);
        		if (speed > Constants.MIN_PLANE_SPEED) speed = Constants.MIN_PLANE_SPEED; 
        	}
        } else {
        	setElapsedTime(getElapsedTime() + delta);
        	if (main.gameMode.getRemainingFactories() < 5 && getElapsedTime() > 5000){
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

		if (isDestroyed()) {
			if (getPlaneExploded().getFrame() < 15 && wasHitByMissile) renderMissleImpact();
			getPlaneExploded().draw(getVector1().x, getVector1().y);
			getPlaneExploded().draw(getVector2().x, getVector2().y);
			getPlaneExploded().draw(getVector3().x, getVector3().y);
			getPlaneExploded().draw(getVector4().x, getVector4().y);
		} else {
			if (collidesWith(main.carrier)) {
				if (bombsDroppingAnimation.getFrame() < 15) {
					bombsDroppingAnimation.draw(main.carrier.position.x,
							main.carrier.position.y + 100 + getRandom());
					if (!main.bombsDroppingSound.playing())
						main.bombsDroppingSound.play();
				} else {
					main.carrier.health = main.carrier.health - Constants.ENEMY_BOMBER_BOMB_DMG;
					main.statistics.amountOfCarrierDamageTaken += Constants.ENEMY_BOMBER_BOMB_DMG;
					bombsDroppingAnimation.restart();
					setRandom();
				}
			}

			image.draw(getVector1().x, getVector1().y, scale);
			image.draw(getVector2().x, getVector2().y, scale);
			image.draw(getVector3().x, getVector3().y, scale);
			image.draw(getVector4().x, getVector4().y, scale);

			if (wasHit) {
				if (getPlaneWasHitAnimation().getFrame() < 15) {
					getPlaneWasHitAnimation().draw(getVector1().x + 33,
							getVector1().y + 5);
					getPlaneWasHitAnimation().draw(getVector2().x + 33,
							getVector2().y + 5);
					getPlaneWasHitAnimation().draw(getVector3().x + 33,
							getVector3().y + 5);
					getPlaneWasHitAnimation().draw(getVector4().x + 33,
							getVector4().y + 5);

				} else {
					setWasHit(false);
					getPlaneWasHitAnimation().restart();
				}
			}
		}
	}
}
