package ca.iantria.raid.entity;

import org.newdawn.slick.Animation;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import ca.iantria.raid.util.Constants;

public abstract class Enemy extends Entity {

	public Vector2f vector1; 
	public Vector2f vector2;
	public Vector2f vector3;
	public Vector2f vector4;
    public float temp2_x, temp2_y, temp3_x,temp3_y;
	  
	public boolean isDestroyed;
	public boolean isAttacking;
    public boolean isReadyToFire;
	
	public int elapsedTime;
	public int refireInterval;
	public int refireElapsedTime;
	public int respawnElapsedTime;
	public int respawnTime;
	
	public boolean wasHitByMissile;
	public Animation wasHitByMissleAnimation;
	public boolean wasHitByCannon;
	public Animation wasHitByCannonAnimation;
	
	public Enemy(String id, float scale, Vector2f position, float rotation) {
		super(id, scale, position, rotation);
		this.wasHitByMissleAnimation = main.explosionAnimations[1].copy();
		this.wasHitByMissleAnimation.stopAt(15);
		this.wasHitByCannonAnimation = main.explosionAnimations[3].copy();
		this.wasHitByCannonAnimation.stopAt(15);
		this.setReadyToFire(true);
	}
	
	public int getRespawnTime() {
		return respawnTime;
	}

	public void setRespawnTime(int respawnTime) {
		this.respawnTime = respawnTime;
	}

	public boolean isReadyToFire() {
		return isReadyToFire;
	}

	public void setReadyToFire(boolean isReadyToFire) {
		this.isReadyToFire = isReadyToFire;
	}

	public boolean wasHitByMissle() {
		return wasHitByMissile;
	}

	public void setWasHitByMissle(boolean wasHitByMissle) {
		this.wasHitByMissile = wasHitByMissle;
	}

	public Animation getWasHitByMissleAnimation() {
		return wasHitByMissleAnimation;
	}

	public void setWasHitByMissleAnimation(Animation wasHitByMissleAnimation) {
		this.wasHitByMissleAnimation = wasHitByMissleAnimation;
	}

	public boolean wasHitByCannon() {
		return wasHitByCannon;
	}

	public void setWasHitByCannon(boolean wasHitByCannon) {
		this.wasHitByCannon = wasHitByCannon;
	}

	public Animation getWasHitByCannonAnimation() {
		return wasHitByCannonAnimation;
	}

	public void setWasHitByCannonAnimation(Animation wasHitByCannonAnimation) {
		this.wasHitByCannonAnimation = wasHitByCannonAnimation;
	}
	
	public Vector2f getVector1() {
		return vector1;
	}
	public void setVector1(Vector2f vector1) {
		this.vector1 = vector1;
	}
	public Vector2f getVector2() {
		return vector2;
	}
	public void setVector2(Vector2f vector2) {
		this.vector2 = vector2;
	}
	public Vector2f getVector3() {
		return vector3;
	}
	public void setVector3(Vector2f vector3) {
		this.vector3 = vector3;
	}
	public Vector2f getVector4() {
		return vector4;
	}
	public void setVector4(Vector2f vector4) {
		this.vector4 = vector4;
	}
	public boolean isDestroyed() {
		return isDestroyed;
	}
	public void setDestroyed(boolean isDestroyed) {
		this.isDestroyed = isDestroyed;
	}
	public boolean isAttacking() {
		return isAttacking;
	}
	public void setAttacking(boolean isAttacking) {
		this.isAttacking = isAttacking;
	}
	public int getElapsedTime() {
		return elapsedTime;
	}
	public void setElapsedTime(int elapsedTime) {
		this.elapsedTime = elapsedTime;
	}
	public int getRefireInterval() {
		return refireInterval;
	}
	public void setRefireInterval(int refireInterval) {
		this.refireInterval = refireInterval;
	}
	public int getRefireElapsedTime() {
		return refireElapsedTime;
	}
	public void setRefireElapsedTime(int refireElapsedTime) {
		this.refireElapsedTime = refireElapsedTime;
	}
	public int getRespawnElapsedTime() {
		return respawnElapsedTime;
	}
	public void setRespawnElapsedTime(int respawnElapsedTime) {
		this.respawnElapsedTime = respawnElapsedTime;
	}

	public void updateVectorsForMovingObjects() {

        if (position.y < -(main.MAP_HEIGHT/2)) position.y = (main.MAP_HEIGHT/2) + (position.y + main.MAP_HEIGHT/2);
        if (position.y > (main.MAP_HEIGHT/2)) position.y = -(main.MAP_HEIGHT/2) + (position.y - main.MAP_HEIGHT/2);

        if (position.x < -(main.MAP_WIDTH/2)) position.x = (main.MAP_WIDTH/2) + (position.x + main.MAP_WIDTH/2);
        if (position.x > (main.MAP_WIDTH/2)) position.x = -(main.MAP_WIDTH/2) + (position.x - main.MAP_WIDTH/2);
		
		if (position.x < (Constants.WINDOW_WIDTH/2) && position.x > 0) {
	    	temp2_x = -(main.MAP_WIDTH - position.x);
	    } else if (position.x < -(main.MAP_WIDTH - Constants.WINDOW_WIDTH) && position.x > - main.MAP_WIDTH){  
	    	temp2_x = main.MAP_WIDTH + position.x;
	    } else {
	    	temp2_x = main.MAP_WIDTH + position.x;
	    }
	    temp2_y = position.y;
	    
	    if (position.y < (Constants.WINDOW_HEIGHT/2) && position.y > 0) {
	    	temp3_y = -(main.MAP_HEIGHT - position.y); 
	    } else if (position.y < -(main.MAP_HEIGHT - Constants.WINDOW_HEIGHT) && position.y > -main.MAP_HEIGHT){  
	    	temp3_y = main.MAP_HEIGHT + position.y;
	    } else {
	    	temp3_y = main.MAP_HEIGHT + position.y;
	    }
	    temp3_x = position.x;
	
      vector1.x = position.x;
      vector1.y = position.y;
      vector2.x = temp2_x;
      vector2.y = temp2_y;
      vector3.x = temp3_x;
      vector3.y = temp3_y;
      vector4.x = temp2_x;
      vector4.y = temp3_y;
	}
	
	public void updateVectorsForStationaryObjects(){
	  position.x = main.gameMap.vector1.x + relativePositionToMap.x;
	  position.y = main.gameMap.vector1.y + relativePositionToMap.y;
	  
      vector1.x = main.gameMap.vector1.x + relativePositionToMap.x;
      vector1.y = main.gameMap.vector1.y + relativePositionToMap.y;
      vector2.x = main.gameMap.vector2.x + relativePositionToMap.x;
      vector2.y = main.gameMap.vector2.y + relativePositionToMap.y;
      vector3.x = main.gameMap.vector3.x + relativePositionToMap.x;
      vector3.y = main.gameMap.vector3.y + relativePositionToMap.y;
      vector4.x = main.gameMap.vector4.x + relativePositionToMap.x;
      vector4.y = main.gameMap.vector4.y + relativePositionToMap.y;
	}
	
	public void renderMissleImpact(){
		main.missleImpact.setPosition(getVector1().getX() + getImageCenterX(), getVector1().getY()+getImageCenterY());
		main.missleImpact.render();
		main.missleImpact.setPosition(getVector2().getX() + getImageCenterX(), getVector2().getY()+getImageCenterY());
		main.missleImpact.render();
		main.missleImpact.setPosition(getVector3().getX() + getImageCenterX(), getVector3().getY()+getImageCenterY());
		main.missleImpact.render();
		main.missleImpact.setPosition(getVector4().getX() + getImageCenterX(), getVector4().getY()+getImageCenterY());
		main.missleImpact.render();
	}
	
	@Override
    public boolean collidesWith(Entity other) {
    	me = new Rectangle(	(int)vector1.x, 
    				 		(int)vector1.y, 
    				 		(int)(getImage().getWidth()*scale), 
    				 		(int)(getImage().getHeight()*scale));
    	him = new Rectangle((int) other.position.x, 
    				  		(int) other.position.y, 
    				  		(int)(other.getImage().getWidth()*other.scale), 
    				  		(int)(other.getImage().getHeight()*other.scale));
    	if (me.intersects(him)) return true;

    	me = new Rectangle(	(int)vector2.x, 
		 					(int)vector2.y, 
		 					(int)(getImage().getWidth()*scale), 
		 					(int)(getImage().getHeight()*scale));
    	if (me.intersects(him)) return true;
    	
    	me = new Rectangle( (int)vector3.x, 
		 					(int)vector3.y, 
		 					(int)(getImage().getWidth()*scale), 
		 					(int)(getImage().getHeight()*scale));
    	if (me.intersects(him)) return true;

    	me = new Rectangle(	(int)vector4.x, 
							(int)vector4.y, 
							(int)(getImage().getWidth()*scale), 
							(int)(getImage().getHeight()*scale));
    	return me.intersects(him);
    }
}