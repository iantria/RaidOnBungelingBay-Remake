package ca.iantria.raid.modes;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import ca.iantria.raid.Main;
import ca.iantria.raid.interfaces.IFadeListener;
import ca.iantria.raid.interfaces.IMenuListener;
import ca.iantria.raid.interfaces.IMode;
import ca.iantria.raid.util.Constants;
import ca.iantria.raid.util.Menu;

public class Help1Mode implements IMode, IFadeListener, IMenuListener {

	public static final int STATE_FADE_IN = 0;
	public static final int STATE_MENU = 1;
	public static final int STATE_FADE_OUT = 2;
	public static final int STATE_DONE = 3;

	private String[] options = new String[] {  "More Info", "Main Menu" };
	private Main main;
	private GameContainer gc;
	public int state = STATE_FADE_IN;
	public Menu menu;
	public boolean optionSelected;
	public int selectedIndex;

	@Override
	public void init(Main main, GameContainer gc) throws SlickException {
		this.main = main;
		this.gc = gc;
		menu = new Menu(Constants.WINDOW_WIDTH / 2 - 320, Constants.WINDOW_HEIGHT/2 - 180, main, 0, this, options);
		main.startFade(false, this);
	}

	public void render(GameContainer container, Graphics g) {
		main.helpBackgroundImage.draw(0, 0, Constants.WINDOW_WIDTH,
				Constants.WINDOW_HEIGHT);
		g.setColor(Color.white);
		main.ttfLarge.drawString(Constants.WINDOW_WIDTH - 465,
				Constants.WINDOW_HEIGHT - 50, "Raid on Ashbridge's Bay");
		main.ttfTiny.drawString(20, Constants.WINDOW_HEIGHT - 30,
				Constants.VERSION);

		renderHelp(g);
		menu.render();
	}

	private void renderHelp(Graphics g) {
		
		int top = Constants.WINDOW_HEIGHT / 2 - 300;
		
		Color trans = new Color(0f, 0f, 0f, 0.5f);
		g.setColor(trans);
		g.fillRect(Constants.WINDOW_WIDTH / 2 - 400,
				top, 800, 600);

		main.ttfMedium.drawString(
				Constants.WINDOW_WIDTH / 2
						- main.ttfMedium.getWidth("Instructions") / 2,
				top + 15, "Instructions", Color.white);

		main.cursorImage.draw(
				(350 + Constants.WINDOW_WIDTH) / 2 - main.cursorImage.getWidth() / 2,
				top +80);
		main.controlImage.draw(
				(350 + Constants.WINDOW_WIDTH) / 2 - main.controlImage.getWidth() / 2,
				top + 229);
		
		g.setColor(Color.white);
		g.drawRect(Constants.WINDOW_WIDTH / 2 - 12, top+50, 390, 320);
		g.drawRect(Constants.WINDOW_WIDTH / 2 - 400 + 25, top+50, 350, 320);
		g.drawRect(Constants.WINDOW_WIDTH / 2 - 378, top+395, 756, 165);

		main.ttfSmall.drawString(336 + (-500 + Constants.WINDOW_WIDTH) / 2, top + 60,
				"Afterburner / TakeOff", Color.white);
		main.ttfSmall.drawString(262 + (-500 + Constants.WINDOW_WIDTH) / 2, top + 157,
				"Turn Left", Color.white);
		main.ttfSmall.drawString(505 + (-500 + Constants.WINDOW_WIDTH) / 2, top + 157,
				"Turn Right", Color.white);
		main.ttfSmall.drawString(403 + (-500 + Constants.WINDOW_WIDTH) / 2, top + 185,
				"Land", Color.white);
		main.ttfSmall.drawString(308 + (-500 + Constants.WINDOW_WIDTH) / 2, top + 200,
				"(Cruise speed, within 10\u00B0)", Color.white);
		main.ttfSmall.drawString(380 + (-500 + Constants.WINDOW_WIDTH) / 2, top + 293,
				"Fire Missle", Color.white);
		main.ttfSmall.drawString(330 + (-500 + Constants.WINDOW_WIDTH) / 2, top + 309,
				"(3 second cooldown)", Color.white);
		main.ttfSmall.drawString(328 + (-500 + Constants.WINDOW_WIDTH) / 2, top + 340,
				"SPACEBAR for Cannon", Color.white);

		String t = "STRATEGY";
		main.ttfSmall.drawString(
				Constants.WINDOW_WIDTH / 2 - main.ttfSmall.getWidth(t) / 2,
				top + 406, t, Color.white);
		
		String[] text = new String[] {  "Destroy all 6 factories.  Factories have a lot of health.",
										"Bombers will attack your Carrier. Protect your Carrier.",
										"Land on Carrier to repair, refuel and reload weapons.",
										"To land plane you must be within 10\u00B0 off vertical at cruise speed.",
										"Use your missiles wisely.  Save them for Factories and Bombers.",
										"Game will get progressively harder as you destroy factories."
		};
		
		for (int i = 0; i < text.length; i++){
			main.ttfSmall.drawString(
				Constants.WINDOW_WIDTH / 2 - main.ttfSmall.getWidth(text[i]) / 2,
				top+430 + (i * 20), text[i], Color.white);
		}
		
	}

	public void update(GameContainer container, int delta) {
		menu.update();
		if (state == STATE_MENU &&  optionSelected) {
			state = STATE_FADE_OUT;
			main.startFade(true, this);
		}
	}

	@Override
	public void fadeCompleted() {
		if (state == STATE_FADE_IN) {
			state = STATE_MENU;
		} else if (state == STATE_FADE_OUT) {
			state = STATE_DONE;
			switch (selectedIndex) {
			case 0:
				main.input.clearControlPressedRecord();
				main.requestMode(Modes.HELP2, gc);
				break;
			case 1:
				main.input.clearControlPressedRecord();
				main.requestMode(Modes.MAINMENU, gc);
				break;
			}
		}
	}

	@Override
	public void selectionChanged(int selectedIndex) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void optionSelected(int selectedIndex) {
		this.optionSelected = true;
		this.selectedIndex = selectedIndex;
		main.menuSound.play();
	}
}
