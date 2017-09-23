package ca.iantria.raid.entity;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import ca.iantria.raid.util.Constants;

public class Factory extends Enemy {

    private int factoryHealthElapsedTime;
	private int explosionIndex;
	private int explosionElapsedTime;
	private Animation explodeAnimation1;
	private Animation explodeAnimation2;
	private Animation explodeAnimation3;
	private Animation explodeAnimation4;
	
	public int getExplosionIndex() {
		return explosionIndex;
	}

	public void setExplosionIndex(int explosionIndex) {
		this.explosionIndex = explosionIndex;
	}
	
	public Animation getExplodeAnimation1() {
		return explodeAnimation1;
	}

	public void setExplodeAnimation1(Animation explodeAnimation1) {
		this.explodeAnimation1 = explodeAnimation1;
	}

	public Animation getExplodeAnimation2() {
		return explodeAnimation2;
	}

	public void setExplodeAnimation2(Animation explodeAnimation2) {
		this.explodeAnimation2 = explodeAnimation2;
	}

	public Animation getExplodeAnimation3() {
		return explodeAnimation3;
	}

	public void setExplodeAnimation3(Animation explodeAnimation3) {
		this.explodeAnimation3 = explodeAnimation3;
	}

	public Animation getExplodeAnimation4() {
		return explodeAnimation4;
	}

	public void setExplodeAnimation4(Animation explodeAnimation4) {
		this.explodeAnimation4 = explodeAnimation4;
	}

	
	public Factory(String id, float scale, Vector2f position, float rotation) {
		super(id, scale, position, rotation);
		health = Constants.ENEMY_FACTORY_HEALTH;
	}

	@Override
	public void init() {
		this.image = main.factoryImage.copy();
    	setVector1(position.copy());
    	setVector2(position.copy());
    	setVector3(position.copy());
    	setVector4(position.copy());
    	updateVectorsForStationaryObjects();	
		this.explodeAnimation1 = main.explosionAnimations[0].copy();
		this.explodeAnimation2 = main.explosionAnimations[4].copy();
		this.explodeAnimation3 = main.explosionAnimations[6].copy();
		this.explodeAnimation4 = main.explosionAnimations[7].copy();
		this.explodeAnimation1.stopAt(15);
		this.explodeAnimation2.stopAt(15);
		this.explodeAnimation3.stopAt(15);
		this.explodeAnimation4.stopAt(15);
		wasHitByMissleAnimation = main.explosionAnimations[1].copy();
		wasHitByMissleAnimation.stopAt(15);
		wasHitByCannonAnimation = main.explosionAnimations[3].copy();
		wasHitByCannonAnimation.stopAt(15);
	}

	@Override
	public void reset() {
		health = Constants.ENEMY_FACTORY_HEALTH;
		isDestroyed = false;
		setWasHit(false);
		setWasHitByCannon(false);
		setWasHitByMissle(false);
		updateVectorsForStationaryObjects();
		explosionIndex = 0;
		explodeAnimation1.restart();
		explodeAnimation1.stopAt(15);
		explodeAnimation2.restart();
		explodeAnimation2.stopAt(15);
		explodeAnimation3.restart();
		explodeAnimation3.stopAt(15);
		explodeAnimation4.restart();
		explodeAnimation4.stopAt(15);
	}

	@Override
	public void update(GameContainer gc, int delta) {
		updateVectorsForStationaryObjects();
		
		// Update Health
        factoryHealthElapsedTime += delta;
        if (factoryHealthElapsedTime >= 3000) {
        	factoryHealthElapsedTime = 0;
			if (!isDestroyed() && health < Constants.ENEMY_FACTORY_HEALTH) {
				int x = getHealth();
				x = x + 1 + (6 - main.gameMode.getRemainingFactories()); // increase health if less factories remain
				if (x > Constants.ENEMY_FACTORY_HEALTH) x = Constants.ENEMY_FACTORY_HEALTH;
				setHealth(x);
			}
        }


        
        if (!isDestroyed && (vector1.getX() <= Constants.WINDOW_WIDTH && vector1.getX() >= 0) && 
		    	(vector1.getY() <= Constants.WINDOW_HEIGHT && vector1.getY() >= 0)) {
        	main.smokeStack.update(delta);
        	//if (wasHitByMissile) main.missleImpact.update(delta);
        }
        
		explosionElapsedTime = explosionElapsedTime + delta;
		if(isDestroyed() && explosionElapsedTime > 750 && explosionIndex <= 5) {
			explosionElapsedTime = 0;
			setExplosionIndex(getExplosionIndex() + 1);
			main.missleImpact.reset();
		}

	}

	@Override
	public void render(GameContainer gc, Graphics gr) {
		
    	if (isDestroyed() && getExplosionIndex() < 7) {
    		wasHitByMissile = false;
        	if (getExplosionIndex() < 5) renderMissleImpact();
    		if (getExplosionIndex() < 3){
	        	getImage().draw(getVector1().getX(), getVector1().getY(), getScale());
	        	getImage().draw(getVector2().getX(), getVector1().getY() ,getScale());
	        	getImage().draw(getVector3().getX(), getVector3().getY() ,getScale());
	        	getImage().draw(getVector4().getX(), getVector4().getY(), getScale());
    		}
    		if (getExplosionIndex() > 0) {
        		getExplodeAnimation1().draw(getVector1().getX(), getVector1().getY()-44, getExplodeAnimation1().getWidth()*2, getExplodeAnimation1().getHeight()*2);
        		getExplodeAnimation1().draw(getVector2().getX(), getVector2().getY()-44, getExplodeAnimation1().getWidth()*2, getExplodeAnimation1().getHeight()*2);
        		getExplodeAnimation1().draw(getVector3().getX(), getVector3().getY()-44, getExplodeAnimation1().getWidth()*2, getExplodeAnimation1().getHeight()*2);
        		getExplodeAnimation1().draw(getVector4().getX(), getVector4().getY()-44, getExplodeAnimation1().getWidth()*2, getExplodeAnimation1().getHeight()*2);
    		}
        	if (getExplosionIndex() > 1) {
    			getExplodeAnimation2().draw(getVector1().getX() -10, getVector1().getY()-20, getExplodeAnimation2().getWidth()*2, getExplodeAnimation2().getHeight()*2);
        		getExplodeAnimation2().draw(getVector2().getX() -10, getVector2().getY()-20, getExplodeAnimation2().getWidth()*2, getExplodeAnimation2().getHeight()*2);
        		getExplodeAnimation2().draw(getVector3().getX() -10, getVector3().getY()-20, getExplodeAnimation2().getWidth()*2, getExplodeAnimation2().getHeight()*2);
        		getExplodeAnimation2().draw(getVector4().getX() -10, getVector4().getY()-20, getExplodeAnimation2().getWidth()*2, getExplodeAnimation2().getHeight()*2);
    		}
        	if (getExplosionIndex() > 2) {
    			getExplodeAnimation3().draw(getVector1().getX(), getVector1().getY()-10, getExplodeAnimation3().getWidth()*2, getExplodeAnimation3().getHeight()*2);
        		getExplodeAnimation3().draw(getVector2().getX(), getVector2().getY()-10, getExplodeAnimation3().getWidth()*2, getExplodeAnimation3().getHeight()*2);
        		getExplodeAnimation3().draw(getVector3().getX(), getVector3().getY()-10, getExplodeAnimation3().getWidth()*2, getExplodeAnimation3().getHeight()*2);
        		getExplodeAnimation3().draw(getVector4().getX(), getVector4().getY()-10, getExplodeAnimation3().getWidth()*2, getExplodeAnimation3().getHeight()*2);
    		}
        	if (getExplosionIndex() > 3) {
    			getExplodeAnimation4().draw(getVector1().getX() -10, getVector1().getY() -20, getExplodeAnimation4().getWidth()*2, getExplodeAnimation4().getHeight()*2);
        		getExplodeAnimation4().draw(getVector2().getX() -10, getVector2().getY() -20, getExplodeAnimation4().getWidth()*2, getExplodeAnimation4().getHeight()*2);
        		getExplodeAnimation4().draw(getVector3().getX() -10, getVector3().getY() -20, getExplodeAnimation4().getWidth()*2, getExplodeAnimation4().getHeight()*2);
        		getExplodeAnimation4().draw(getVector4().getX() -10, getVector4().getY() -20, getExplodeAnimation4().getWidth()*2, getExplodeAnimation4().getHeight()*2);
    		}
    	} else {
            if ((vector1.getX() <= Constants.WINDOW_WIDTH && vector1.getX() >= 0) && 
    		    (vector1.getY() <= Constants.WINDOW_HEIGHT && vector1.getY() >= 0)) {
            	main.smokeStack.setPosition(vector1.x+18, vector1.y+8);
            	main.smokeStack.render();
            	main.smokeStack.setPosition(vector1.x+30, vector1.y+15);
            	main.smokeStack.render();
            }
    		image.draw(vector1.x, vector1.y, scale);
    		image.draw(vector2.x, vector2.y, scale);
    		image.draw(vector3.x, vector3.y, scale);
    		image.draw(vector4.x, vector4.y, scale);
        	
        	if (wasHitByMissle()){
        		renderMissleImpact();
        		if (getWasHitByMissleAnimation().getFrame() < 15){
        			getWasHitByMissleAnimation().draw(getVector1().getX()+15, getVector1().getY()+15);
        			getWasHitByMissleAnimation().draw(getVector2().getX()+15, getVector2().getY()+15);
        			getWasHitByMissleAnimation().draw(getVector3().getX()+15, getVector3().getY()+15);
        			getWasHitByMissleAnimation().draw(getVector4().getX()+15, getVector4().getY()+15);
        			
        		} else {
        			setWasHitByMissle(false);
        			setWasHit(false);
        			getWasHitByMissleAnimation().restart();
        		}
        	}

        	if (wasHitByCannon()){
        		if (getWasHitByCannonAnimation().getFrame() < 15){
        			getWasHitByCannonAnimation().draw(getVector1().getX()+15, getVector1().getY()+15);
        			getWasHitByCannonAnimation().draw(getVector2().getX()+15, getVector2().getY()+15);
        			getWasHitByCannonAnimation().draw(getVector3().getX()+15, getVector3().getY()+15);
        			getWasHitByCannonAnimation().draw(getVector4().getX()+15, getVector4().getY()+15);
        		} else {
        			setWasHit(false);
        			setWasHitByCannon(false);
        			getWasHitByCannonAnimation().restart();
        		}
        	}
        	
        	gr.setColor(Color.green);
        	Rectangle rect = new Rectangle(getVector1().getX() + getImage().getWidth()/2*getScale()-25, 
        				getVector1().getY() + getImage().getHeight()*getScale() + 3, 
        				getHealth()/2, 3);
        	gr.fill(rect);
        	rect = new Rectangle(getVector2().getX() + getImage().getWidth()/2*getScale()-25, 
        				getVector2().getY() + getImage().getHeight()*getScale() + 3, 
        				getHealth()/2, 3);
        	gr.fill(rect);
        	rect = new Rectangle(getVector3().getX() + getImage().getWidth()/2*getScale()-25, 
        				getVector3().getY() + getImage().getHeight()*getScale() + 3, 
        				getHealth()/2, 3);
        	gr.fill(rect);
        	rect = new Rectangle(getVector4().getX() + getImage().getWidth()/2*getScale()-25, 
        				getVector4().getY() + getImage().getHeight()*getScale() + 3, 
        				getHealth()/2, 3);
        	gr.fill(rect);
    	}
    }

}
