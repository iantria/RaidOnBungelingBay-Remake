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

public class Help2Mode implements IMode, IFadeListener, IMenuListener {

	public static final int STATE_FADE_IN = 0;
	public static final int STATE_MENU = 1;
	public static final int STATE_FADE_OUT = 2;
	public static final int STATE_DONE = 3;

	private String[] options = new String[] { "Instructions", "Main Menu" };
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
				Constants.WINDOW_HEIGHT / 2 - 300, 800, 600);

		main.ttfMedium.drawString(
				Constants.WINDOW_WIDTH / 2
						- main.ttfMedium.getWidth("More Information") / 2,
						top + 15, "More Information", Color.white);
		

		g.setColor(Color.white);
		g.drawRect(Constants.WINDOW_WIDTH / 2 - 12, top+50, 390, 320);
		g.drawRect(Constants.WINDOW_WIDTH / 2 - 400 + 25, top+50, 350, 320);
		g.drawRect(Constants.WINDOW_WIDTH / 2 - 378, top+395, 756, 165);
		
		String t = "ENEMY UNITS";
		main.ttfSmall.drawString(
				(378 + Constants.WINDOW_WIDTH) / 2 - main.ttfSmall.getWidth(t) / 2,
				top+70, t, Color.white);
		
		String[] text = new String[] {
			new String("Enemy Fighter (Health = " + Constants.ENEMY_FIGHTER_HEALTH + ", Score = " + Constants.SCORE_FIGHTER +")"),
			new String("Enemy Bomber (Health = " + Constants.ENEMY_BOMBER_HEALTH + ", Score = " + Constants.SCORE_BOMBER +")"),
			new String("Factory (Health = " + Constants.ENEMY_FACTORY_HEALTH + ", Score = " + Constants.SCORE_FACTORY +")"),
			new String("AA Gun (Health = " + Constants.ENEMY_AA_GUN_HEALTH + ", Score = " + Constants.SCORE_AA_GUN +")")
		};
		
		main.enemyFighterImage.draw(Constants.WINDOW_WIDTH / 2 + 40, top+95, 0.175f);
		main.enemyBomberImage.draw(Constants.WINDOW_WIDTH / 2 + 100, top+100, 0.25f);
		main.factoryImage.draw(Constants.WINDOW_WIDTH / 2 + 200, top+95, 0.25f);
		main.AAGunImage.draw(Constants.WINDOW_WIDTH / 2 + 285, top+99, 0.75f);
		
		for (int i = 0; i < text.length; i++){
			main.ttfSmall.drawString(
			(380 + Constants.WINDOW_WIDTH) / 2 - main.ttfSmall.getWidth(text[i]) / 2,
			top+140 + (i * 20), text[i], Color.white);
		}
		
		t = "PLANE SPECS";
		main.ttfSmall.drawString(
				(380 + Constants.WINDOW_WIDTH) / 2 - main.ttfSmall.getWidth(t) / 2,
				top+240, t, Color.white);
		
		text = new String[] {
				new String("Cannon (Damage= " + Constants.CANNON_DAMAGE + ", Rounds = " + Constants.CANNON_ROUNDS +")"),
				new String("Missle (Damage = " + Constants.MISSLE_DAMAGE + ", Rounds = " + Constants.MISSLES_PER_PLANE +")"),
				new String("Fuel (Capacity = " + Constants.FUEL_CAPACITY + ", Fuel Burn/sec = " + Constants.FUEL_DURATION/1000 +")"),
				new String("Plane Health = " + Constants.MAX_HIT_POINTS_PLANE)
			};
		
		for (int i = 0; i < text.length; i++){
			main.ttfSmall.drawString(
			(380 + Constants.WINDOW_WIDTH) / 2 - main.ttfSmall.getWidth(text[i]) / 2,
			top+270 + (i * 20), text[i], Color.white);
		}
		
		t = "ADDITIONAL STRATEGY";
		main.ttfSmall.drawString(
				Constants.WINDOW_WIDTH / 2 - main.ttfSmall.getWidth(t) / 2,
				top+406, t, Color.white);
		
		text = new String[] {  "Modifying the order that you destroy factories can help.",
								"You can out turn enemy fighters and cruise mssiles.",
								"Kill bombers while they are still on the airfield."
										
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
				main.requestMode(Modes.HELP1, gc);
				break;
			case 1:
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
