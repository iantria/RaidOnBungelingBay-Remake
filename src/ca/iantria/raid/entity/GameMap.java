package ca.iantria.raid.entity;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;

import ca.iantria.raid.util.Constants;

public class GameMap extends Enemy {

	public GameMap(String id, float scale, Vector2f position, float rotation) {
		super(id, scale, position, rotation);
	}

	@Override
	public void init() {
		image = main.mapImage.copy();
        main.MAP_HEIGHT = (int) ((int) image.getHeight()*scale);
        main.MAP_WIDTH = (int) ((int) image.getWidth()*scale);
        
    	setVector1(position.copy());
    	setVector2(position.copy());
    	setVector3(position.copy());
    	setVector4(position.copy());

        updateMapSegments();
	}

	@Override
	public void reset() {
		position = startingPosition.copy();
        updateMapSegments(); // last thing you do
	}

	@Override
	public void update(GameContainer gc, int delta) {

		if (main.plane.isCrashed()){
			speed = 0;
			main.map_dx = 0;
			main.map_dy = 0;
			return;
		} 
		
		setDirection(main.plane.getRotation());
		
        if(!main.plane.isCrashed && main.input.isKeyDown(Input.KEY_UP)) {
        	main.plane.setAfterburner(true);
        	
        	if (main.plane.isLanded()) {
            	main.plane.setLanded(false);
        		speed = Constants.MIN_PLANE_SPEED;
        		main.afterBurnerSpeedEffect.play();
        	} else {
        		speed = speed + (0.0005f * delta);
        	}
        	
	        if (main.gameMap.speed > (Constants.MAX_PLANE_SPEED - main.plane.dmg )) main.gameMap.speed = Constants.MAX_PLANE_SPEED - main.plane.dmg;
        } else if (main.plane.isLanded()){
        	main.engine.reset();
        	if (main.plane.collidesWith(main.carrier)) speed = main.carrier.speed;
        	else speed = 0;
        	main.plane.setAfterburner(false);
        	setDirection(main.carrier.getRotation());
        } else if (!main.plane.isCrashed()) {
          	
        	if (main.plane.isPlayer()) {
        		main.engine.reset();
        		main.plane.setAfterburner(false);
        	}
        	speed = speed - (0.00025f * delta);
        	if (speed < Constants.MIN_PLANE_SPEED) speed = Constants.MIN_PLANE_SPEED;
        }

        main.map_dx = getPosition().x;
        main.map_dy = getPosition().y;
        
        float hip = delta * speed;
        
        position.x -= hip * java.lang.Math.sin(java.lang.Math.toRadians(direction));
        position.y += hip * java.lang.Math.cos(java.lang.Math.toRadians(direction));

        main.map_dx -= getPosition().x;
        main.map_dy -= getPosition().y;

        updateMapSegments();
	}

	@Override
	public void render(GameContainer gc, Graphics gr) {
	
		image.draw(getVector1().x,getVector1().y, scale);
		image.draw(getVector2().x,getVector2().y, scale);
		image.draw(getVector3().x,getVector3().y, scale);
		image.draw(getVector4().x,getVector4().y, scale);
	}

	public void updateMapSegments() {
        if (position.x > Constants.WINDOW_WIDTH/2) position.x = -(main.MAP_WIDTH - Constants.WINDOW_WIDTH/2) + (position.x-Constants.WINDOW_WIDTH/2) ; // 400 3200 400
        if (position.x < -main.MAP_WIDTH) position.x = main.MAP_WIDTH + position.x ;
        
        if (position.y > Constants.WINDOW_HEIGHT/2) position.y = -(main.MAP_HEIGHT - Constants.WINDOW_HEIGHT/2) + (position.y - Constants.WINDOW_HEIGHT/2);
        if (position.y < -main.MAP_HEIGHT) position.y = main.MAP_HEIGHT + position.y;

    	if (position.x < (Constants.WINDOW_WIDTH) && position.x > 0) {
        	temp2_x = -(main.MAP_WIDTH - position.x);
        } else if (position.x < -(main.MAP_WIDTH - Constants.WINDOW_WIDTH) && position.x > -main.MAP_WIDTH){  
        	temp2_x = main.MAP_WIDTH + position.x;
        } else {
        	temp2_x = main.MAP_WIDTH + position.x;
        }
        temp2_y = position.y;

        if (position.y < (Constants.WINDOW_HEIGHT) && position.y > 0) {
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
