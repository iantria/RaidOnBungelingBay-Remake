package ca.iantria.raid.modes;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import ca.iantria.raid.Main;
import ca.iantria.raid.entity.ScrollingCombatText;
import ca.iantria.raid.util.Constants;

public class DemoMode extends GameMode {
	
	public int timer;

	@Override
	public void init(Main main, GameContainer gc) throws SlickException {
		super.init(main, gc);
		main.plane.setPlayer(false);
		main.combatText.add(new ScrollingCombatText("DEMO", 1f, new Vector2f(Constants.WINDOW_WIDTH/2, Constants.WINDOW_HEIGHT-50), ("DEMO MODE - Press Escape to Cancel"), Color.green, main.ttfLarge, false));
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		super.update(gc, delta);
		timer += delta;
		if (timer > 5000){
			timer = 0;
			main.combatText.add(new ScrollingCombatText("DEMO", 1f, new Vector2f(Constants.WINDOW_WIDTH/2, Constants.WINDOW_HEIGHT-50), ("DEMO MODE - Press Escape to Cancel"), Color.green, main.ttfLarge, false));
		}
	}
}
