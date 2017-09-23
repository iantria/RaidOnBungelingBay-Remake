package ca.iantria.raid.entity;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Vector2f;

import ca.iantria.raid.Main;

public class ScrollingCombatText {

	public Main main;
    public String id;
    public Vector2f position;
    public float rotation;
    public Color color;
    public String text;
    public TrueTypeFont font;
    public int timer;
    public boolean isFinished;
    public boolean isScroll;
	
    public ScrollingCombatText(String id, float scale, Vector2f position, String text, Color color, TrueTypeFont font, boolean scroll) {
	    this.main = Main.main;
        this.id = id;
        this.position = position;
        this.color = color;
        this.text = text;
        this.font = font;
        this.isScroll = scroll;
        this.isFinished = false;
        init();
    }
	
    public void init(){
    	if (!isScroll){
    		position.x = position.x + main.plane.image.getWidth()/2*main.plane.scale - font.getWidth(text)/2 ;
    	} else if (color == Color.red){
    		position.y = position.y + main.plane.image.getHeight()*main.plane.scale;
    		position.x = position.x - 10 - font.getWidth(text);
    	} else if (color == Color.white){
    		position.y = position.y + main.plane.image.getHeight()*main.plane.scale;
    		position.x = position.x + main.plane.image.getWidth()*main.plane.scale + 10;
    	}  else if (color == Color.green || color == Color.yellow){
    		position.y = position.y + main.plane.image.getHeight()*main.plane.scale + 10;
    		position.x = position.x + main.plane.image.getWidth()/2*main.plane.scale - font.getWidth(text)/2 ;
    	}
    }
    
    public void reset() {
    	this.isFinished = false;
    }
    
    public void update(GameContainer gc, int delta){
    	timer += delta;
    	if (isScroll) {
    		position.y -= 0.06f * delta;
	    	if (timer > 2200){
	    		timer = 0;
	    		isFinished = true;
	    		main.removeCombatTextList.add(this);
	    		return;
	    	}
    	} else {
    		if (timer > 4400){
	    		timer = 0;
	    		isFinished = true;
	    		main.removeCombatTextList.add(this);
	    		return;    			
    		}
    	}
    }
    
    public void render(GameContainer gc, Graphics gr){
    	if (!isFinished) {
    		if (isScroll){
	    		Color c = new Color(255,10,10);
	    		if (color == Color.red)	c = new Color(255, 10, 10, (int) (255 - (timer/2200f)*255));
	    		if (color == Color.white)	c = new Color(255, 255, 255, (int) (255 - (timer/2200f)*255));
	    		if (color == Color.yellow)	c = new Color(255, 255, 10, (int) (255 - (timer/2200f)*255));
	    		if (color == Color.green)	c = new Color(10, 255, 10, (int) (255 - (timer/2200f)*255));
	    		font.drawString(position.x, position.y, text, c);
	    	} else {
	    		Color c = new Color(255,10,10);
	    		if (timer <= 2200){
		    		if (color == Color.red)	c = new Color(255, 10, 10, (int) (0 + (timer/2200f)*255));
		    		if (color == Color.white)	c = new Color(255, 255, 255, (int) (0 + (timer/2200f)*255));
		    		if (color == Color.yellow)	c = new Color(255, 255, 10, (int) (0 + (timer/2200f)*255));
		    		if (color == Color.green)	c = new Color(10, 255, 10, (int) (0 + (timer/2200f)*255));
	    		} else {
		    		if (color == Color.red)	c = new Color(255, 10, 10, (int) (255 - ((timer-2200)/2200f)*255));
		    		if (color == Color.white)	c = new Color(255, 255, 255, (int) (255 - ((timer-2200)/2200f)*255));
		    		if (color == Color.yellow)	c = new Color(255, 255, 10, (int) (255 - ((timer-2200)/2200f)*255));
		    		if (color == Color.green)	c = new Color(10, 255, 10, (int) (255 - ((timer-2200)/2200f)*255));	
	    		}
	    		font.drawString(position.x, position.y, text, c);
	    	}
    	}
    }
}
