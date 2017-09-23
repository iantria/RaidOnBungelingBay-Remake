package ca.iantria.raid.util;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;

import ca.iantria.raid.Main;
import ca.iantria.raid.entity.ScrollingCombatText;

public class DrawHelper {

	public void drawRotated(Image image, float x, float y, float[] centers,
			float angle) {
		GL11.glPushMatrix();
		GL11.glTranslatef(x, y, 0);
		GL11.glRotatef(angle, 0, 0, 1);
		image.draw(centers[0], centers[1]);
		GL11.glPopMatrix();
	}

	public void drawRotatedScaled(Image image, float x, float y, float centerX,
			float centerY, float angle, float scaleX, float scaleY) {
		GL11.glPushMatrix();
		GL11.glTranslatef(x, y, 0);
		GL11.glRotatef(angle, 0, 0, 1);
		GL11.glScalef(scaleX, scaleY, 1);
		image.draw(centerX, centerY);
		GL11.glPopMatrix();
	}

	public void drawRotatedScaled(Image image, float x, float y, float centerX,
			float centerY, float angle, float scaleX, float scaleY, float alpha) {
		image.setAlpha(alpha);
		GL11.glPushMatrix();
		GL11.glTranslatef(x, y, 0);
		GL11.glRotatef(angle, 0, 0, 1);
		GL11.glScalef(scaleX, scaleY, 1);
		image.draw(centerX, centerY);
		GL11.glPopMatrix();
		image.setAlpha(1f);
	}

	public void drawRotated(Image image, float x, float y, float centerX,
			float centerY, float angle, float scale) {
		GL11.glPushMatrix();
		GL11.glTranslatef(x, y, 0);
		GL11.glRotatef(angle, 0, 0, 1);
		GL11.glScalef(scale, scale, 1);
		image.draw(centerX, centerY);
		GL11.glPopMatrix();
	}

	public void drawRotated(Image image, float x, float y, float centerX,
			float centerY, float angle, float scale, float alpha) {
		image.setAlpha(alpha);
		GL11.glPushMatrix();
		GL11.glTranslatef(x, y, 0);
		GL11.glRotatef(angle, 0, 0, 1);
		GL11.glScalef(scale, scale, 1);
		image.draw(centerX, centerY);
		GL11.glPopMatrix();
		image.setAlpha(1f);
	}

	public void drawRotated(Image image, float x, float y, float centerX,
			float centerY, float angle) {
		GL11.glPushMatrix();
		GL11.glTranslatef(x, y, 0);
		GL11.glRotatef(angle, 0, 0, 1);
		image.draw(centerX, centerY);
		GL11.glPopMatrix();
	}

	public void drawRotated(Image image, float x, float y, float angle,
			float alpha) {
		image.setAlpha(alpha);
		GL11.glPushMatrix();
		GL11.glTranslatef(x, y, 0);
		GL11.glRotatef(angle, 0, 0, 1);
		image.draw(-image.getWidth() * .5f, -image.getHeight() * .5f);
		GL11.glPopMatrix();
		image.setAlpha(1f);
	}

	public void translateGraphics(float x, float y) {
		GL11.glPushMatrix();
		GL11.glTranslatef(x, y, 0);
	}

	public void rotateGraphics(float x, float y, float angle, float scale) {
		GL11.glPushMatrix();
		GL11.glTranslatef(x, y, 0);
		GL11.glRotatef(angle, 0, 0, 1);
		GL11.glScalef(scale, scale, 1);
	}

	public void scaleGraphics(float x, float y, float scaleX, float scaleY) {
		GL11.glPushMatrix();
		GL11.glTranslatef(x, y, 0);
		GL11.glScalef(scaleX, scaleY, 1);
	}

	public void rotateGraphics(float x, float y, float angle) {
		GL11.glPushMatrix();
		GL11.glTranslatef(x, y, 0);
		GL11.glRotatef(angle, 0, 0, 1);
	}

	public void popGraphics() {
		GL11.glPopMatrix();
	}

	public void drawRotated(Image image, float x, float y, float angle) {
		GL11.glPushMatrix();
		GL11.glTranslatef(x, y, 0);
		GL11.glRotatef(angle, 0, 0, 1);
		image.draw(-image.getWidth() * .5f, -image.getHeight() * .5f);
		GL11.glPopMatrix();
	}

	public void drawRotatedAlpha(Image image, float x, float y, float angle,
			float alpha) {
		image.setAlpha(alpha);
		GL11.glPushMatrix();
		GL11.glTranslatef(x, y, 0);
		GL11.glRotatef(angle, 0, 0, 1);
		image.draw(-image.getWidth() * .5f, -image.getHeight() * .5f);
		GL11.glPopMatrix();
		image.setAlpha(1f);
	}

	public void draw(Image image, float x, float y, float angle, float scale) {
		GL11.glPushMatrix();
		GL11.glTranslatef(x, y, 0);
		GL11.glRotatef(angle, 0, 0, 1);
		GL11.glScalef(scale, scale, 1);
		image.draw(-image.getWidth() * .5f, -image.getHeight() * .5f);
		GL11.glPopMatrix();
	}

	public void drawCenteredAlpha(Image image, float x, float y, float alpha) {
		image.setAlpha(alpha);
		GL11.glPushMatrix();
		GL11.glTranslatef(x, y, 0);
		image.draw(-image.getWidth() * .5f, -image.getHeight() * .5f);
		GL11.glPopMatrix();
		image.setAlpha(1f);
	}

	public void drawCentered(Image image, float x, float y, float scale,
			float alpha) {
		image.setAlpha(alpha);
		GL11.glPushMatrix();
		GL11.glTranslatef(x, y, 0);
		GL11.glScalef(scale, scale, 1);
		image.draw(-image.getWidth() * .5f, -image.getHeight() * .5f);
		GL11.glPopMatrix();
		image.setAlpha(1f);
	}

	public void drawCentered(Image image, float x, float y, float scale) {
		GL11.glPushMatrix();
		GL11.glTranslatef(x, y, 0);
		GL11.glScalef(scale, scale, 1);
		image.draw(-image.getWidth() * .5f, -image.getHeight() * .5f);
		GL11.glPopMatrix();
	}

	public void drawOffset(Image image, float x, float y) {
		image.draw(x, y);
	}

	public void drawOffset(Image image, float x, float y, float alpha) {
		image.setAlpha(alpha);
		image.draw(x, y);
		image.setAlpha(1f);
	}

	public void drawCentered(Image image) {
		image.draw(-image.getWidth() * .5f, -image.getHeight() * .5f);
	}

	public void drawCentered(Image image, float x, float y) {
		GL11.glPushMatrix();
		GL11.glTranslatef(x, y, 0);
		image.draw(-image.getWidth() * .5f, -image.getHeight() * .5f);
		GL11.glPopMatrix();
	}

	public void drawScaled(Image image, float x, float y, float scale,
			float alpha) {
		image.setAlpha(alpha);
		GL11.glPushMatrix();
		GL11.glTranslatef(x, y, 0);
		GL11.glScalef(scale, scale, 1);
		image.draw(-image.getWidth() * .5f, -image.getHeight() * .5f);
		GL11.glPopMatrix();
		image.setAlpha(1f);
	}

	public void drawScaled(Image image, float x, float y, float scale) {
		GL11.glPushMatrix();
		GL11.glTranslatef(x, y, 0);
		GL11.glScalef(scale, scale, 1);
		image.draw(-image.getWidth() * .5f, -image.getHeight() * .5f);
		GL11.glPopMatrix();
	}
	
    public void renderWeaponStatus(Main main, Graphics gr) {
    	//MiniMap
    	gr.copyArea(main.miniMap, 0, 0);
    	
    	gr.setColor(Color.white);
    	gr.drawRect((float)(Constants.WINDOW_WIDTH - 190) - 1, 0, 190+1, 190/Main.ASPECT_RATIO + 1);
    	main.miniMap.draw(Constants.WINDOW_WIDTH - 190 , 1 , 190, 190/Main.ASPECT_RATIO); 
    	
    	int m = (int)(190/Main.ASPECT_RATIO);
    	gr.setColor(Color.white);
    	if (main.plane.missileCount == 0 || (System.currentTimeMillis() - main.plane.lastFireMissile < Constants.FIRING_INTERVAL_MISSLE)) gr.setColor(Color.red);
    	gr.drawString("Missile: ", Constants.WINDOW_WIDTH - 190 , m+3  + 10);
    	if (main.plane.missileCount > 4) {
    		gr.setColor(Color.green);
    	} else if (main.plane.missileCount > 2){
    		gr.setColor(Color.yellow);
    	} else if (main.plane.missileCount > 1){
    		gr.setColor(Color.orange);
    	} else {
    		gr.setColor(Color.red);
    	}
    	int spacing = (100 - Constants.MISSLES_PER_PLANE*2) / Constants.MISSLES_PER_PLANE;
    	for (int i=0;i< main.plane.missileCount;i++) {
    		Rectangle r= new Rectangle(Constants.WINDOW_WIDTH - 115 + ((spacing + 3) * i), m+3+ 12, spacing, 15);
    		gr.fill(r);
    	}

    	// Cannon
    	gr.setColor(Color.white);
    	int f = main.plane.cannonCount*(100/Constants.CANNON_ROUNDS);
    	gr.drawString("Cannon:", Constants.WINDOW_WIDTH - 190 , m+3+ 30);
    	gr.drawRect(Constants.WINDOW_WIDTH - 115 , m+3+32, 101, 15);
    	if (f> 70) {
    		gr.setColor(Color.green);
    	} else if (f > 50){
    		gr.setColor(Color.yellow);
    	} else if (f > 25){
    		gr.setColor(Color.orange);
    	} else {
    		gr.setColor(Color.red);
    	}
    	Rectangle rectCannon = new Rectangle(Constants.WINDOW_WIDTH - 114, m+3+33, f, 14);
    	gr.fill(rectCannon);

    	// Fuel
    	f = main.plane.fuelCount*100/Constants.FUEL_CAPACITY;
    	gr.setColor(Color.white);
    	gr.drawString("Fuel:", Constants.WINDOW_WIDTH - 190 ,m + 3 + 50);
    	gr.drawRect(Constants.WINDOW_WIDTH - 115 , m + 3 + 53, 101, 15);
    	if (f > 70) {
    		gr.setColor(Color.green);
    	} else if (f > 50){
    		gr.setColor(Color.yellow);
    	} else if (f > 25){
    		gr.setColor(Color.orange);
    	} else {
    		gr.setColor(Color.red);
    	}
    	
    	Rectangle rect = new Rectangle(Constants.WINDOW_WIDTH - 114, m+3+54, f, 14);
    	gr.fill(rect);
    	
    	// Plane Health
    	if (main.plane.health < 0) main.plane.health = 0;
    	gr.setColor(Color.white);
    	gr.drawString("Plane:", Constants.WINDOW_WIDTH - 190 ,m + 3 + 70);    	
    	gr.drawRect(Constants.WINDOW_WIDTH -115 ,m + 3 + 73,  101, 15);
    	if (main.plane.health > 70) {
    		gr.setColor(Color.green);
    	} else if (main.plane.health > 50){
    		gr.setColor(Color.yellow);
    	} else if (main.plane.health > 25){
    		gr.setColor(Color.orange);
    	} else {
    		gr.setColor(Color.red);
    	}
    	Rectangle rectPlaneHealth = new Rectangle(Constants.WINDOW_WIDTH  - 114, m+3+74, main.plane.health, 14);
    	gr.fill(rectPlaneHealth);
    	
    	
    	// Lives
    	for (int i = 0; i < main.plane.livesCount - 1; i++){
    		main.livesImage.draw(13 + i*40 , 10, 0.3f);
    	}
    	
    	// Factories
    	gr.setColor(Color.white);
    	if (main.gameMode.getRemainingFactories() > 0) {
	    	for (int i = 0; i < main.gameMode.getRemainingFactories(); i++){
	    		main.factoryIcon.draw(13 + (i*38), 52);
	    	}
    	} else {
    		gr.setColor(Color.red);
    		gr.drawString("CONGRATULATIONS!  You have won!", 10 , 62);
    	}

    	// Carrier Health
    	if (!main.carrier.isDestroyed()){
    		main.carrierIcon.drawFlash(10, 92);
    		gr.setColor(Color.white);
    		gr.drawString("Carrier", 10, 153);
    		if (main.enemyPlanes[2].isAttacking() || main.enemyPlanes[3].isAttacking() || 
    			(main.enemyShip.isAttacking && main.enemyShip.speed == 0)) {
	    		gr.drawString("Carrier UNDER ATTACK", 10, 153);
	    		main.carrierIcon.getSubImage(0, 0, main.carrier.health, 58).draw(10, 92, Color.red);
	    	} else {
	    		main.carrierIcon.getSubImage(0, 0, main.carrier.health, 58).draw(10, 92);
	    	}
    	}

    	if (!main.enemyShip.isDestroyed && !main.enemyShip.isSinking) {
    		main.enemyShipIcon.drawFlash(10, Constants.WINDOW_HEIGHT - 80);
    		gr.setColor(Color.white);
    		gr.drawString("Guided Missile Detroyer", 10, Constants.WINDOW_HEIGHT - 22);
	    	if (main.enemyShip.isAttacking)
	    		main.enemyShipIcon.getSubImage(0, 0, main.enemyShip.health*2, 55).draw(10, Constants.WINDOW_HEIGHT - 80, Color.red);
	    	else 
	    		main.enemyShipIcon.getSubImage(0, 0, main.enemyShip.health*2, 55).draw(10, Constants.WINDOW_HEIGHT - 80);
    	}
    
    	gr.setColor(Color.white);
    	gr.drawString("Score: ", Constants.WINDOW_WIDTH - 190 , Constants.WINDOW_HEIGHT - 25);
    	String s = "000000".substring(Integer.toString(main.statistics.score).length(), 6);
    	main.ttfMedium.drawString((float)(Constants.WINDOW_WIDTH - 115), (float) Constants.WINDOW_HEIGHT - 35, s + main.statistics.score); 

    	if (!main.carrier.isDestroyed){ 
	    	main.carrierDirectionArrow.setRotation((float)main.getUnsignedSignedDegreesToCarrier(main.plane.getPosition()));
	    	main.carrierDirectionArrow.draw(Constants.WINDOW_WIDTH/2, 32, 0.2f);

	    	// Carrier Landing Helper
	    	gr.setColor(Color.white);
	    	Rectangle directionRect = new Rectangle(
	    			main.carrier.getPosition().x + main.carrier.getImage().getWidth()*main.carrier.getScale()/2 -2, 0, 4 , 8);
	    	gr.fill(directionRect);
	    	Rectangle directionRect2 = new Rectangle(
	    			main.carrier.getPosition().x, 0, 2 , 8);
	    	gr.fill(directionRect2);
	    	Rectangle directionRect3 = new Rectangle(
	    			main.carrier.getPosition().x + main.carrier.getImage().getWidth()*main.carrier.getScale(), 0, 2 , 8);
	    	gr.fill(directionRect3);
	    	main.ttfTiny.drawString(Constants.WINDOW_WIDTH/2, 10, (int) (Math.abs(main.plane.getRotation()%360)) + "\u00B0");
    	}
    	
    	for(ScrollingCombatText scr: main.combatText){
    		scr.render(main.gc, gr);
    	}
    	
    }
}
