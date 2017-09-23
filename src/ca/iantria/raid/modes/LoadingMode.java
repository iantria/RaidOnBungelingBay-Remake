package ca.iantria.raid.modes;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import ca.iantria.raid.Main;
import ca.iantria.raid.interfaces.IMode;
import ca.iantria.raid.util.Constants;

public class LoadingMode implements IMode {

	public Main main;
	public GameContainer gc;
	public int percentWidth;

	@Override
	public void init(Main main, GameContainer gc) throws SlickException {
		this.main = main;
		this.gc = gc;
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		g.setColor(Color.black);
		g.fillRect(0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
		g.setColor(Color.white);
		g.drawRect(Constants.WINDOW_WIDTH/2-200, Constants.WINDOW_HEIGHT/2, 400, 30);
		g.setColor(Color.red);
		g.fillRect(Constants.WINDOW_WIDTH/2-199, Constants.WINDOW_HEIGHT/2+1, percentWidth, 29);
		main.ttfLarge.drawString(Constants.WINDOW_WIDTH / 2 - main.ttfLarge.getWidth("Loading")/2,
				Constants.WINDOW_HEIGHT / 2 - 60, "Loading");

		main.ttfSmall.drawString(Constants.WINDOW_WIDTH / 2 - main.ttfSmall.getWidth(percentWidth/4 + "%")/2,
				Constants.WINDOW_HEIGHT / 2 + 50, percentWidth/4 + "%");
		
	}

	
	public void update(GameContainer gc, int delta) throws SlickException {
	    try {
	        percentWidth = (int)(400 * main.loadNext());
	      } catch(Throwable t) {
	        throw new SlickException("Loading error", t);
	      }	
	    }
}