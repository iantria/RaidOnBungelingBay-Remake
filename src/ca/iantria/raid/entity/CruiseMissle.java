package ca.iantria.raid.entity;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import ca.iantria.raid.util.Constants;

public class CruiseMissle extends Projectile {

	public Animation ranOutOfFuel;
	public int remainingFuel;
	
	public CruiseMissle(String id, float scale, Vector2f position,
			float rotation, Image image, int type, int target) {
		
		super(id, scale, position, rotation, image, type);
		this.mainTarget = target;
	}


	@Override
	public void init() {
		remainingFuel = Constants.ENEMY_CRUISE_MISSLE_FUEL;
		ranOutOfFuel = main.explosionAnimations[5].copy();
    	setVector1(position.copy());
    	setVector2(position.copy());
    	setVector3(position.copy());
    	setVector4(position.copy());
    	speed = Constants.MIN_PLANE_SPEED + 0.2f;
    	health = Constants.ENEMY_CRUISE_MISSLE_HEALTH;
		updateVectorsForMovingObjects();
	}

	@Override
	public void reset() {
		health = Constants.ENEMY_CRUISE_MISSLE_HEALTH;
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
	
	@Override
	public void update(GameContainer gc, int delta) {
		
		if (remainingFuel < 1200 || wasHit) speed = 0;
		
    	position.x = position.x - main.map_dx;
    	position.y = position.y - main.map_dy;
		
		updateVectorsForMovingObjects();
		
        remainingFuel = remainingFuel - delta;
        if (remainingFuel < 0 || isDestroyed) {
        	main.removeProjectileList.add(this);
        	return;
        }
		
		float angleToTarget = 0;
		
		if (mainTarget == PLAYER_IS_TARGET) angleToTarget = (float) main.getSignedDegreesToPlane(position);
		if (mainTarget == CARRIER_IS_TARGET) angleToTarget = (float) main.getSignedDegreesToCarrier(position);
		
		double diff = main.calculateDifferenceBetweenAngles(angleToTarget, rotation);
		if (diff < -5) {
			rotation = rotation + 0.15f * delta;
		} else if (diff > 5){
			rotation = rotation - 0.15f * delta;
		}
        
	    float hip = delta * speed;

	    position.x += hip * java.lang.Math.sin(java.lang.Math.toRadians(rotation));
	    position.y -= hip *java.lang.Math.cos(java.lang.Math.toRadians(rotation));

	    if (rotation >= 360) rotation = rotation - 360;
	    if (rotation <= 0) rotation = rotation + 360;
	    
    	checkCollisions(gc, delta);
	    
	    image.rotate(getRotation() - image.getRotation());
	}

	@Override
	public void render(GameContainer gc, Graphics gr) {
        if (remainingFuel > 1200 && !wasHit) {
			image.draw(getVector1().x, getVector1().y, scale);
			image.draw(getVector2().x, getVector2().y, scale);
			image.draw(getVector3().x, getVector3().y, scale);
			image.draw(getVector4().x, getVector4().y, scale);
        } else {
        	if (!main.cruiseOutOfFuel.playing()) main.cruiseOutOfFuel.play(); 
	    	ranOutOfFuel.draw(getVector1().x, getVector1().y);
	    	ranOutOfFuel.draw(getVector2().x, getVector2().y);
	    	ranOutOfFuel.draw(getVector3().x, getVector3().y);
	    	ranOutOfFuel.draw(getVector4().x, getVector4().y);
	    	if (ranOutOfFuel.getFrame() == 15) isDestroyed = true;
	    }
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
}
