package ca.iantria.raid.entity;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import ca.iantria.raid.Main;

public abstract class Entity {
    
	public Main main;
    public String id;
    public Vector2f position;
    public Vector2f startingPosition;
    public float scale;
    public float rotation;
    public float speed;
	public int health;
	public boolean wasHit;
	public Vector2f relativePositionToMap;
	public Image image;
	public float direction;
	public boolean isPlayer;
	public Entity primaryTarget;
	
    public Entity(String id, float scale, Vector2f position, float rotation) {
	    main = Main.main;
        this.id = id;
        this.scale = scale;
        this.position = position;
        this.relativePositionToMap = position.copy();
        this.rotation = rotation;
        this.startingPosition = position.copy();
        init();
    }
    
	public Entity getPrimaryTarget() {
		return primaryTarget;
	}

	public void setPrimaryTarget(Entity primaryTarget) {
		this.primaryTarget = primaryTarget;
	}
    
	public boolean isPlayer() {
		return isPlayer;
	}

	public void setPlayer(boolean isPlayer) {
		this.isPlayer = isPlayer;
	}

	public float getDirection() {
		return direction;
	}

	public void setDirection(float direction) {
		this.direction = direction;
	}

	public Vector2f getStartingPosition() {
		return startingPosition;
	}

	public void setStartingPosition(Vector2f startingPosition) {
		this.startingPosition = startingPosition;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public boolean isWasHit() {
		return wasHit;
	}

	public void setWasHit(boolean wasHit) {
		this.wasHit = wasHit;
	}

	/** The rectangle used for this entity during collisions  resolution */
	protected Rectangle me;
     
    /** The rectangle used for other entities during collision resolution */
    protected Rectangle him;
    
    public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public Vector2f getRelativePositionToMap() {
		return relativePositionToMap;
	}

	public void setRelativePositionToMap(Vector2f relativePositionToMap) {
		this.relativePositionToMap = relativePositionToMap;
	}
    
    public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}
    
	public Vector2f getPosition() {
		return position;
    }
     
    public float getScale() {
    	return scale;
    }
     
    public float getRotation() {
    	return rotation;
    }
     
    public String getId() {
        return id;
    }
 
    public void setPosition(Vector2f position) {
    	this.position = position;
    }
 
    public void setRotation(float rotate) {
        rotation = rotate;
    }
 
    public void setScale(float scale) {
    	this.scale = scale;
    }

    public abstract void init();
    public abstract void reset();
    public abstract void update(GameContainer gc, int delta);  
    public abstract void render(GameContainer gc, Graphics gr);

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
    
    public float getImageCenterX(){
    	return image.getWidth()*scale/2;
    }

    public float getImageCenterY(){
    	return image.getHeight()*scale/2;
    }

    
}