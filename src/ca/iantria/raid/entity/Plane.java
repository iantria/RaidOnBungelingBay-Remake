package ca.iantria.raid.entity;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.particles.ConfigurableEmitter;

import ca.iantria.raid.modes.Modes;
import ca.iantria.raid.util.Constants;

public class Plane extends Entity {

	public Animation planeExploded;
	public Animation planeWasHitAnimation;
	public boolean isLanded = true;
	public boolean isCrashed = false;
	public boolean isAfterburner = false;
	public Image planeAfterBurnerImage;
	public int missileCount;
	public int cannonCount;
	public int fuelCount;
    public int livesCount;
    public long lastFireMissile;
    public long lastFireCannon;
    public int fuelElapsedTime; // The time that has passed, reset to 0 after +-1 sec.
    public int generalDelayTime = 0;
    public int FUEL_DURATION = Constants.FUEL_DURATION;
    public int YOU_CRASHED_DELAY_DURATION = 2500;
    public float dmg;
    private float percent;
    private Vector2f temp;
	
	public Plane(String id, float scale, Vector2f position, float rotation) {
		super(id, scale, position, rotation);
	}

	public boolean isAfterburner() {
		return isAfterburner;
	}

	public void setAfterburner(boolean isAfterburner) {
		this.isAfterburner = isAfterburner;
	}

	public long getLastFireMissle() {
		return lastFireMissile;
	}

	public void setLastFireMissle(long lastFireMissle) {
		this.lastFireMissile = lastFireMissle;
	}

	public long getLastFireCannon() {
		return lastFireCannon;
	}

	public void setLastFireCannon(long lastFireCannon) {
		this.lastFireCannon = lastFireCannon;
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

	public boolean isCrashed() {
		return isCrashed;
	}

	public void setCrashed(boolean isCrashed) {
		this.isCrashed = isCrashed;
	}

	public Image getPlaneAfterBurnerImage() {
		return planeAfterBurnerImage;
	}

	public void setPlaneAfterBurnerImage(Image planeAfterBurnerImage) {
		this.planeAfterBurnerImage = planeAfterBurnerImage;
	}

	public int getMissleCount() {
		return missileCount;
	}

	public void setMissleCount(int missleCount) {
		this.missileCount = missleCount;
	}

	public int getCannonCount() {
		return cannonCount;
	}

	public void setCannonCount(int cannonCount) {
		this.cannonCount = cannonCount;
	}

	public int getFuelCount() {
		return fuelCount;
	}

	public void setFuelCount(int fuelCount) {
		this.fuelCount = fuelCount;
	}

	public int getLivesCount() {
		return livesCount;
	}

	public void setLivesCount(int livesCount) {
		this.livesCount = livesCount;
	}

	@Override
	public void init() {
		// Images
		this.image = main.planeImage.copy();
		this.image = main.planeImage.copy();
		this.planeExploded = main.explosionAnimations[6].copy();
		this.planeExploded.stopAt(15);
		this.planeWasHitAnimation = main.explosionAnimations[3].copy();
		this.planeWasHitAnimation.stopAt(15);
		
		this.livesCount = Constants.NUMBER_OF_LIVES;
		this.health = Constants.MAX_HIT_POINTS_PLANE;
		this.fuelCount = Constants.FUEL_CAPACITY;
		this.missileCount = Constants.MISSLES_PER_PLANE;
		this.cannonCount = Constants.CANNON_ROUNDS;
		this.relativePositionToMap = new Vector2f(Constants.WINDOW_WIDTH/2 + 400, Constants.WINDOW_HEIGHT/2);
		setCrashed(false);
		setLanded(true);
	}

	@Override
	public void reset() {
		//this.livesCount = Constants.NUMBER_OF_LIVES;
		this.health = Constants.MAX_HIT_POINTS_PLANE;
		this.fuelCount = Constants.FUEL_CAPACITY;
		this.missileCount = Constants.MISSLES_PER_PLANE;
		this.cannonCount = Constants.CANNON_ROUNDS;
		rotation = 0;
		setCrashed(false);
		setLanded(true);
		planeExploded.restart();
	}
	
	@Override
	public void update(GameContainer gc, int delta) {
		
		main.engine.update(delta);
		
    	percent = ((float)health/(float)Constants.MAX_HIT_POINTS_PLANE)*100;
    	dmg = 0.0f;
    	if (percent <= 70 && percent  >= 50) dmg = 0.1f;
    	else if (percent < 50 && percent  >= 30) dmg = 0.2f;
    	else if (percent < 30 && percent  >= 0) dmg = 0.3f;
    	
    	if (health <= 70) main.playerDamage.update(delta);
		
		fuelElapsedTime += delta;
		if (fuelElapsedTime >= FUEL_DURATION && !isLanded && !isCrashed) {
			fuelElapsedTime = 0;
			fuelCount--;
			main.statistics.amountOfFuelUsed++;
		}

		if (fuelCount < 1 || health < 1) {
			if (!main.outOfFuelCrashSound.playing())
				main.outOfFuelCrashSound.play();
			main.cruiseSpeedEffect.stop();
			main.afterBurnerSpeedEffect.stop();
			main.projectileImpact.stop();
			setCrashed(true);

			generalDelayTime += delta;
			if (generalDelayTime >= YOU_CRASHED_DELAY_DURATION) {
				generalDelayTime = 0;
				livesCount--;
				main.statistics.numberOfLivesLost++;
				if (fuelCount < 1)
					main.statistics.numberOfRanOutFuel++;
				if (livesCount == 0) {
					main.statistics.carrierSurvived = !main.carrier.isDestroyed
							&& !main.carrier.isSinking;
					main.stopAllSounds();
					main.drums.loop();
					if (!main.plane.isPlayer) main.requestMode(Modes.MAINMENU, gc);
					else main.requestMode(Modes.OUTCOME, gc);
				}
				main.gameMode.updatePositionsAfterCrash();
				return;
			}
		}
		
		// Unstable plane
//		if ((!isLanded && !(isCrashed) && percent <= 25){
//			generalDelayTime += delta;
//			rotation += dmg_roation * delta;
//			if (generalDelayTime > 750){
//				generalDelayTime = 0;
//				if (main.random.nextBoolean()) dmg_roation = -main.random.nextFloat()*0.05f;
//				else dmg_roation = main.random.nextFloat()*0.05f;
//			}
//		}
		
		if (isPlayer) {
			if(main.input.isKeyDown(Input.KEY_SPACE)) tryToFire("fireCannon");
	        if(main.input.isKeyDown(Input.KEY_LCONTROL)) tryToFire("fireMissle");
			
	        if(main.input.isKeyDown(Input.KEY_LEFT) && (!isLanded && !(isCrashed))) {
	            rotation += -0.2f * delta;
	        }
	        if(main.input.isKeyDown(Input.KEY_RIGHT) && (!isLanded && !(isCrashed))) {
	            rotation += 0.2f * delta;
	        }
	        if (main.input.isKeyDown(Input.KEY_DOWN)) checkIfYouCanLand();
		} else {
			doAIFlying(gc, delta);
		}

        rotation = rotation % 360;
        
//        relativePositionToMap.x = relativePositionToMap.x + main.map_dx;
//        relativePositionToMap.y = relativePositionToMap.y + main.map_dy;
//        
//        if (relativePositionToMap.x > main.MAP_WIDTH) relativePositionToMap.x = relativePositionToMap.x - main.MAP_WIDTH;
//        if (relativePositionToMap.y > main.MAP_HEIGHT) relativePositionToMap.y = relativePositionToMap.y - main.MAP_HEIGHT;
//        if (relativePositionToMap.x < 0) relativePositionToMap.x = relativePositionToMap.x + main.MAP_WIDTH;
//        if (relativePositionToMap.y < 0) relativePositionToMap.y = relativePositionToMap.y + main.MAP_HEIGHT;
	}
	

	@Override
	public void render(GameContainer gc, Graphics g) {
        if (isCrashed){
        	// find center first (cx, cy)
        	float cx = position.x + getPlaneExploded().getWidth() / 2;
        	float cy = position.y + getPlaneExploded().getHeight() / 2;

        	// get scaled draw coordinates (sx, sy)
        	float sx = cx - getPlaneExploded().getWidth() / 2 * 2;
        	float sy = cy - getPlaneExploded().getHeight() / 2 * 2;
        	
        	getPlaneExploded().draw(sx, sy, getPlaneExploded().getWidth()* 2, getPlaneExploded().getHeight() * 2);
        } else {
    		image.rotate(getRotation() - image.getRotation());
        	if (isAfterburner){
        		main.engine.setPosition(position.x+ getImageCenterX(), position.y+getImageCenterY());
        		((ConfigurableEmitter)main.engine.getEmitter(0)).angularOffset.setValue(getRotation()-180);
        		main.engine.render();
        	}
       		image.draw(position.x, position.y, scale);

        	if (wasHit){
        		if (getPlaneWasHitAnimation().getFrame() < 15) {
        			getPlaneWasHitAnimation().draw(position.x-6, position.y-5);
        		} else {
        			setWasHit(false);
        			getPlaneWasHitAnimation().restart();
        		}
        	}
        	
        	if (health <= 70) {
        		main.playerDamage.setPosition(position.x+ getImageCenterX(), position.y+getImageCenterY());
        		((ConfigurableEmitter)main.playerDamage.getEmitter(0)).angularOffset.setValue(getRotation()-180);
        		((ConfigurableEmitter)main.playerDamage.getEmitter(0)).speed.setMax(120f*(main.gameMap.speed)/Constants.MIN_PLANE_SPEED);
        		((ConfigurableEmitter)main.playerDamage.getEmitter(0)).speed.setMin(120f*(main.gameMap.speed)/Constants.MIN_PLANE_SPEED);
        		((ConfigurableEmitter)main.playerDamage.getEmitter(0)).initialSize.setMax(10f + (70f-(float)health)/3f);
        		((ConfigurableEmitter)main.playerDamage.getEmitter(0)).initialSize.setMin(10f + (70f-(float)health)/3f);
        		main.playerDamage.render();
        	}
        	
        }
	}
	
	public void checkIfYouCanLand() {
		//Can you land on Carrier?
    	if (!isCrashed && !main.carrier.isDestroyed && !isLanded && collidesWith(main.carrier)){
			float angle = getRotation();
			angle = Math.abs(angle % 360);
			
			if ((main.gameMap.speed == Constants.MIN_PLANE_SPEED) && ((angle > 350 || angle < 10) || (angle > 170 && angle < 190))){
				setLanded(true);
				setAfterburner(false);
				main.landEffect1.play();
				main.cruiseSpeedEffect.stop();
				main.statistics.numberOfLandings++;
				fuelCount = Constants.FUEL_CAPACITY;
				missileCount = Constants.MISSLES_PER_PLANE;
				cannonCount = Constants.CANNON_ROUNDS;
				health = Constants.MAX_HIT_POINTS_PLANE;
				main.combatText.add(new ScrollingCombatText("LandedOnCarrier" + main.statistics.numberOfLandings, 1f, main.plane.position.copy(), ("Repaired, Refueled, Reloaded!"), Color.green, main.ttfTiny, true));
			}
		}
    	
		//Can you land on SecretBase?
    	if (!isCrashed && !isLanded && main.secretBase.collidesWith(this)){
			float angle = getRotation();
			angle = Math.abs(angle % 360);
			
			if (main.gameMap.speed == Constants.MIN_PLANE_SPEED){
				setLanded(true);
				setAfterburner(false);
				main.landEffect1.play();
				main.cruiseSpeedEffect.stop();
				missileCount = Constants.MISSLES_PER_PLANE;
				cannonCount = Constants.CANNON_ROUNDS;
				main.combatText.add(new ScrollingCombatText("LandedOnSecretBase" + main.statistics.numberOfLandings, 1f, main.plane.position.copy(), ("Reloaded!"), Color.green, main.ttfTiny, true));
			}
		}
	}
	
	public void tryToFire(String weapon) {
		if (isLanded() || isCrashed()) return;
		if (weapon.equals("fireMissle")){
			if (System.currentTimeMillis() - lastFireMissile < Constants.FIRING_INTERVAL_MISSLE) {
				return;
			}
			if (missileCount > 0) {
				lastFireMissile = System.currentTimeMillis();
				main.fireMissleEffect.play(1.0f,0.6f);
				missileCount--;
				main.statistics.numberOfMissilesFired++;
				Projectile missle = main.myMissileShots[main.gameMode.missileIndex++ % main.myMissileShots.length];
				missle.setRotation(getRotation());
				missle.setPosition(main.gameMode.missleCenter.copy());
				main.projectiles.add(missle);
			}
		}

		if (weapon.equals("fireCannon")){
			if (System.currentTimeMillis() - lastFireCannon < Constants.FIRING_INTERVAL_CANNON) {
				return;
			}
			if (cannonCount > 0 ) {
				lastFireCannon = System.currentTimeMillis();
				main.m61Sound.play(1.0f,0.6f);;
				cannonCount--;
				main.statistics.numberOfCannonRoundsFired++;
				Projectile missle = main.myCannonShots[main.gameMode.bulletIndex++ % main.myCannonShots.length];;
				missle.setRotation(getRotation());
				missle.setPosition(main.gameMode.bulletCenter.copy());
				main.projectiles.add(missle);
			}
		}
	}
	
	public void doAIFlying(GameContainer gc, int delta) {
		
		// If landed... take off
    	if (isLanded && !isCrashed) {
			generalDelayTime += delta;
			if (generalDelayTime >= 1100) {
				generalDelayTime = 0;
	        	setLanded(false);
	        	setAfterburner(true);
	    		main.afterBurnerSpeedEffect.play();
			}
			return;
    	}

    	// Go back to carrier is low on fuel, weapons, or low health
    	if ((fuelCount < 30 || missileCount == 0 || cannonCount == 0 || health < 40) && !main.carrier.isDestroyed) {
    		// Go back to carrier
    		setPrimaryTarget(main.carrier);
    		reduceSpeedIfTargetVisible(delta);
    		checkIfYouCanLand();
    	} else {
    		// Attack bombers
			 if (main.enemyPlanes[2].isAttacking()){
				setPrimaryTarget(main.enemyPlanes[2]);
				reduceSpeedIfTargetVisible(delta);
			} else if (main.enemyPlanes[3].isAttacking()) {
				setPrimaryTarget(main.enemyPlanes[3]);
				reduceSpeedIfTargetVisible(delta);
			} else {
				// Attack factory
				main.gameMap.speed =  main.gameMap.speed + (0.0005f * delta);
				if (main.gameMap.speed > (Constants.MAX_PLANE_SPEED - dmg )) main.gameMap.speed = Constants.MAX_PLANE_SPEED - dmg;
				setAfterburner(true);
				
		    	for (Factory f: main.factories){
		    		if (!f.isDestroyed) {
		    			setPrimaryTarget(f);
		    			break;
		    		}
		    	}
			}
    	}

        temp = new Vector2f(getPrimaryTarget().position.x + getPrimaryTarget().getImageCenterX(),
							getPrimaryTarget().position.y + getPrimaryTarget().getImageCenterY());
    	
    	// Directions
		float angleToTarget = (float) main.getSignedDegrees(position, temp);
		double diff = main.calculateDifferenceBetweenAngles(angleToTarget, rotation);

		if (diff < -5) {
			rotation += 0.2f * delta;
		} else if (diff > 5){
			rotation -= 0.2f * delta;
		}
    	
		if ((Factory.class.isInstance(getPrimaryTarget()) || EnemyBomber.class.isInstance(getPrimaryTarget())) && 
			(getPrimaryTarget().position.getX() <= Constants.WINDOW_WIDTH+10 && getPrimaryTarget().position.getX() >= -10) && 
	    	(getPrimaryTarget().position.getY() <= Constants.WINDOW_HEIGHT+10 && getPrimaryTarget().position.getY() >= -10)) {
			if (diff > -10 && diff < 10) {
		        tryToFire("fireMissle");
				tryToFire("fireCannon");
			}
		} else {
			tryToFire("fireCannon");
		}
	}
	
	
	public void reduceSpeedIfTargetVisible(int delta) {
		if ((getPrimaryTarget().position.getX() <= Constants.WINDOW_WIDTH && getPrimaryTarget().position.getX() >= 0) && 
    		(getPrimaryTarget().position.getY() <= Constants.WINDOW_HEIGHT && getPrimaryTarget().position.getY() >= 0)){
   			main.gameMap.speed = main.gameMap.speed - (0.00025f * delta);
        	if (main.gameMap.speed < Constants.MIN_PLANE_SPEED) main.gameMap.speed = Constants.MIN_PLANE_SPEED;
   			setAfterburner(false);
		} else {
			main.gameMap.speed =  main.gameMap.speed + (0.0005f * delta);
			if (main.gameMap.speed > (Constants.MAX_PLANE_SPEED - dmg )) main.gameMap.speed = Constants.MAX_PLANE_SPEED - dmg;
			setAfterburner(true);
		}
	}
	
}
