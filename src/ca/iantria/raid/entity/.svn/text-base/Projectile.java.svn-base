package ca.iantria.raid.entity;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import ca.iantria.raid.util.Constants;

public class Projectile extends Enemy {

	public Image image;
	public int type;
	public static final int MY_BULLET = 0;
	public static final int MY_MISSLE = 1;
	public static final int AA_GUN_BULLET = 2;
	public static final int ENEMY_FIGHTER_BULLET = 3;
	public static final int ENEMY_CRUISE_MISSLE = 4;
	public static final int PLAYER_IS_TARGET = 0;
	public static final int CARRIER_IS_TARGET = 1;
	public int mainTarget;
	
	public Projectile(String id, float scale, Vector2f position, float rotation, Image image, int type) {
		super(id, scale, position, rotation);
		this.image = image;
		this.type = type;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(GameContainer gc, int delta) {
        float hip = delta * speed;
        
    	position.x = position.x - main.map_dx;
    	position.y = position.y - main.map_dy;

        position.x += hip * java.lang.Math.sin(java.lang.Math.toRadians(rotation));
        position.y -= hip *java.lang.Math.cos(java.lang.Math.toRadians(rotation));
        
    	checkCollisions(gc, delta);
    	
    	if ((position.x < -10 || position.x > Constants.WINDOW_WIDTH + 10) || 
    		(position.y < -10 || position.y > Constants.WINDOW_HEIGHT + 10)){
        	main.removeProjectileList.add(this);
        }
	}

	@Override
	public void render(GameContainer gc, Graphics g) {
		image.rotate(getRotation() - image.getRotation());
		image.draw(position.x, position.y, scale);
	}

	@Override
	public void reset() {
		
	}
	
	@Override
    public boolean collidesWith(Entity other) {
    	me = new Rectangle(	(int)position.x, 
    				 		(int)position.y, 
    				 		(int)(getImage().getWidth()*scale), 
    				 		(int)(getImage().getHeight()*scale));
    	him = new Rectangle((int) other.position.x, 
    				  		(int) other.position.y, 
    				  		(int)(other.getImage().getWidth()*other.scale), 
    				  		(int)(other.getImage().getHeight()*other.scale));
 
    	return me.intersects(him);
    }
	
	
	public void checkCollisions(GameContainer gc, int delta) {
		
		// Enemy Projectiles, do they hit me?
		if (type == Projectile.AA_GUN_BULLET){
			if(collidesWith(main.plane)) {
    			main.plane.setWasHit(true);
    			main.projectileImpact.play();
    			main.removeProjectileList.add(this);
    			main.statistics.amountOfDamageTaken += Constants.ENEMY_AA_GUN_DAMAGE;
				main.plane.health = main.plane.health - Constants.ENEMY_AA_GUN_DAMAGE;
				main.combatText.add(new ScrollingCombatText("AAGun" + main.plane.health, 1f, main.plane.position.copy(), ("-" + Constants.ENEMY_AA_GUN_DAMAGE + " Health"), Color.red, main.ttfTiny, true));
    			main.statistics.numberOfTimesHitByAAGun++;
    			main.AAGunFireSound.stop();
   			}
		} else if (type == Projectile.ENEMY_FIGHTER_BULLET){
			if(collidesWith(main.plane)) {
    			main.plane.setWasHit(true);
    			main.projectileImpact.play();
    			main.removeProjectileList.add(this);
    			main.statistics.amountOfDamageTaken += Constants.ENEMY_FIGHTER_GUN_DAMAGE;
				main.plane.health = main.plane.health - Constants.ENEMY_FIGHTER_GUN_DAMAGE;
				main.combatText.add(new ScrollingCombatText("EnemyFighter" + main.plane.health, 1f, main.plane.position.copy(), ("-" + Constants.ENEMY_FIGHTER_GUN_DAMAGE + " Health"), Color.red, main.ttfTiny, true));
				main.statistics.numberOfTimesHitByFighter++;
				main.fighterFire.stop();
			}
		} else if (type == Projectile.ENEMY_CRUISE_MISSLE){
			if(!wasHit && !isDestroyed && collidesWith(main.plane)&& mainTarget == PLAYER_IS_TARGET) {
    			main.plane.setWasHit(true);
    			main.projectileImpact.play();
    			main.removeProjectileList.add(this);
    			main.statistics.amountOfDamageTaken += Constants.ENEMY_CRUISE_MISSLE_DAMAGE;
				main.plane.health = main.plane.health - Constants.ENEMY_CRUISE_MISSLE_DAMAGE;
				main.combatText.add(new ScrollingCombatText("CruiseMissle" + main.plane.health, 1f, main.plane.position.copy(), ("-" + Constants.ENEMY_CRUISE_MISSLE_DAMAGE + " Health"), Color.red, main.ttfTiny, true));
				main.statistics.numberOfTimesHitByCruiseMissle++;
				// TODO Need to stop cruise missle sound
			}
			if(!wasHit && !isDestroyed && collidesWith(main.carrier) && mainTarget == CARRIER_IS_TARGET) {
    			main.carrier.setWasHit(true);
    			main.projectileImpact.play();
    			main.removeProjectileList.add(this);
    			main.statistics.amountOfCarrierDamageTaken+= Constants.ENEMY_CRUISE_MISSLE_DAMAGE;
				main.carrier.health = main.carrier.health - Constants.ENEMY_CRUISE_MISSLE_DAMAGE;
				//main.statistics.numberOfTimesHitByCruiseMissle++; add stats for carrier hit by bombs and crusie missile
			}
			
		// My Projectiles - check if I hit stuff
		} else if (type == Projectile.MY_BULLET || type == Projectile.MY_MISSLE){
			
			if (!main.enemyShip.isDestroyed && !main.enemyShip.isSinking && main.enemyShip.collidesWith(this)){
				updateEntityForHit(main.enemyShip);
    			if (main.enemyShip.getHealth() < 1) {
    				if (main.enemyShip.isAttacking) {
    					main.enemyShip.setSinking(true);
    					main.enemyShip.setAttacking(false);
        				main.bigExplosion.play();
        				main.statistics.score = main.statistics.score + Constants.SCORE_ENEMY_SHIP;
        				main.combatText.add(new ScrollingCombatText("BomberScore", 1f, main.plane.position.copy(), ("+" + Constants.SCORE_ENEMY_SHIP + " Score"), Color.white, main.ttfTiny, true));
    				}
    				else main.enemyShip.health = 1;
    			}
    			main.removeProjectileList.add(this);
			}
			
			
	    	for (Factory f : main.factories){
	    		if (f.isDestroyed()) continue;
	    		if (f.collidesWith(this)){
	    			updateEntityForHit(f);
	    			if (f.getHealth() < 1) {
	    				f.setDestroyed(true);
	    				main.bigExplosion.play();
	    				main.statistics.numberOfFactoriesDestroyed++;
	    				if (main.statistics.numberOfFactoriesDestroyed == 6) {
	    					main.combatText.add(new ScrollingCombatText("YouWon", 1f, main.plane.position.copy(), ("YOU HAVE WON!"), Color.green, main.ttfTiny, true));
	    					main.combatText.add(new ScrollingCombatText("BIGWIN", 1f, new Vector2f(Constants.WINDOW_WIDTH/2, Constants.WINDOW_HEIGHT/2 +100), ("YOU HAVE WON!"), Color.green, main.ttfLarge, false));
	    				}
	    				main.statistics.score = main.statistics.score + Constants.SCORE_FACTORY;
	    				main.combatText.add(new ScrollingCombatText("FactoryScore", 1f, main.plane.position.copy(), ("+" + Constants.SCORE_FACTORY + " Score"), Color.white, main.ttfTiny, true));
	    			}
	    			main.removeProjectileList.add(this);
	    		}
	    	}
	    	// You can shoot down cruise missles
	    	for (Projectile f : main.projectiles){
	    		if (f.isDestroyed()) continue;
	    		if (f.type == Projectile.ENEMY_CRUISE_MISSLE && f.collidesWith(this)){
	    			updateEntityForHit(f);
	    			if (f.getHealth() < 1) {
	    				f.setWasHit(true);
	    				main.cruiseOutOfFuel.play();
	    				main.statistics.numberOfCruiseMissilesDestroyed++;
	    				main.statistics.score = main.statistics.score + Constants.SCORE_CRUISE_MISSLE;
	    				main.combatText.add(new ScrollingCombatText("BomberScore", 1f, main.plane.position.copy(), ("+" + Constants.SCORE_CRUISE_MISSLE + " Score"), Color.white, main.ttfTiny, true));
	    			}
	    			main.removeProjectileList.add(this);
	    		}
	    	}
	    	for (AAGun f : main.AAGuns){
	    		if (f.isDestroyed()) continue;
	    		if (f.collidesWith(this)){
	    			updateEntityForHit(f);
	    			if (f.getHealth() < 1) {
	    				f.setDestroyed(true);
	    				main.bigExplosion.play();
	    				main.statistics.numberOfAAGunsDestroyed++;
	    				main.statistics.score = main.statistics.score + Constants.SCORE_AA_GUN;
	    				main.combatText.add(new ScrollingCombatText("AAGunScore", 1f, main.plane.position.copy(), ("+" + Constants.SCORE_AA_GUN + " Score"), Color.white, main.ttfTiny, true));
	    			}
	    			main.removeProjectileList.add(this);
	    		}
	    	}
	    	for (EnemyPlane f : main.enemyPlanes){
	    		if (f.isDestroyed()) continue;
	    		if (f.collidesWith(this)){
	    			updateEntityForHit(f);
	    			if (f.getHealth() < 1) {
	    				f.setDestroyed(true);
	    				main.bigExplosion.play();
	    				if (f.getType() == EnemyPlane.ENEMY_FIGHTER){
	    					main.statistics.numberOfFightersDestroyed++;
	    					main.statistics.score = main.statistics.score + Constants.SCORE_FIGHTER;
	    					main.combatText.add(new ScrollingCombatText("FighterScore", 1f, main.plane.position.copy(), ("+" + Constants.SCORE_FIGHTER + " Score"), Color.white, main.ttfTiny, true));
	    				} else {
	    					main.statistics.numberOfBombersDestroyed++;
	    					main.statistics.score = main.statistics.score + Constants.SCORE_BOMBER;
	    					main.combatText.add(new ScrollingCombatText("BomberScore", 1f, main.plane.position.copy(), ("+" + Constants.SCORE_BOMBER + " Score"), Color.white, main.ttfTiny, true));
	    				}
	    			}
	    			main.removeProjectileList.add(this);
	    			break; 
	    		}
	    	}
		}
	}
	
	// Helper methods
	public void updateEntityForHit(Enemy f){
		int x = f.getHealth();
		if (type == Projectile.MY_MISSLE) {
			main.missleImpact.reset();
			main.fireMissleEffect.stop();
			x = x - Constants.MISSLE_DAMAGE;
			main.statistics.amountOfDamageDealt += Constants.MISSLE_DAMAGE;
			main.statistics.numberOfMissilesLanded++;
			main.mediumExplosion.play();
			f.setWasHitByMissle(true);
			f.setWasHit(true);
		}
		if (type == Projectile.MY_BULLET) {
			//main.m61Sound.stop();
			x = x - Constants.CANNON_DAMAGE;
			main.bulletHitLand.play();
			main.statistics.amountOfDamageDealt += Constants.CANNON_DAMAGE;
			main.statistics.numberOfCannonRoundsLanded++;
			f.setWasHitByCannon(true);
			f.setWasHit(true);
		}
		f.setHealth(x);
	}
	
}
