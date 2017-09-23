package ca.iantria.raid.entity;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

public class SecretBase extends Enemy {

	public SecretBase(String id, float scale, Vector2f position, float rotation) {
		super(id, scale, position, rotation);
	}

	@Override
	public void init() {
		this.image = main.secretBaseImage.copy();
    	setVector1(position.copy());
    	setVector2(position.copy());
    	setVector3(position.copy());
    	setVector4(position.copy());
    	updateVectorsForStationaryObjects();
	}

	@Override
	public void reset() {
		updateVectorsForStationaryObjects();
	}

	@Override
	public void update(GameContainer gc, int delta) {
		updateVectorsForStationaryObjects();		
	}

	@Override
	public void render(GameContainer gc, Graphics gr) {
//		image.draw(vector1.x, vector1.y, scale);
//		image.draw(vector2.x, vector2.y, scale);
//		image.draw(vector3.x, vector3.y, scale);
//		image.draw(vector4.x, vector4.y, scale);
		
	}

}
