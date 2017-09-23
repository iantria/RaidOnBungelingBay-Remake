package ca.iantria.raid.modes;

import org.newdawn.slick.AppletGameContainer;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.util.Log;

import ca.iantria.raid.Main;
import ca.iantria.raid.interfaces.IFadeListener;
import ca.iantria.raid.interfaces.IMenuListener;
import ca.iantria.raid.interfaces.IMode;
import ca.iantria.raid.util.Constants;
import ca.iantria.raid.util.FileManager;
import ca.iantria.raid.util.Menu;

public class MainMenuMode implements IMode, IFadeListener, IMenuListener {

	private String[] options = new String[] { "New Game", "Instructions", "High Scores", "Set Nickname",
			"Screen Toggle", "Exit" };
					
	public static final int STATE_FADE_IN = 0;
	public static final int STATE_MENU = 1;
	public static final int STATE_FADE_OUT = 2;
	public static final int STATE_DONE = 3;
	
	public Color trans = new Color(0f,0f,0f,0.5f);

	private Main main;
	private GameContainer gc;
	public Menu menu;
	public boolean optionSelected;
	public int selectedIndex;
	public int state = STATE_FADE_IN;
	public int demoStartTimer;
	public TextField field;
	public boolean setUserID = false;

	@Override
	public void init(Main m, GameContainer gc) throws SlickException {
		this.main = m;
		this.gc = gc;
		menu = new Menu(12, 0, main, 0, this, options);
		loadFile();
		
		field = new TextField(gc, main.ttfSmall, Constants.WINDOW_WIDTH/2 - 50,
				Constants.WINDOW_HEIGHT/2 + 60, 100, 23, new ComponentListener() {
			public void componentActivated(AbstractComponent source) {
				main.user_id = field.getText();
				main.saveFile();
				setUserID = false;
				field.setFocus(false);
				optionSelected = false;
				menu.selectionMade = false;
				main.input.clearKeyPressedRecord();
			}
		});
		field.setText(main.user_id);
		field.setMaxLength(7);
		field.setCursorVisible(true);
		field.setCursorPos(field.getText().length());
		
		main.startFade(false, this);
		if (!main.drums.playing()) main.drums.loop();
	}

	public void render(GameContainer container, Graphics g) {
		main.mainMenuBackgroundImage.draw(0, 0, Constants.WINDOW_WIDTH,
				Constants.WINDOW_HEIGHT);
		g.setColor(Color.white);
		main.ttfLarge.drawString(Constants.WINDOW_WIDTH - 465,
				Constants.WINDOW_HEIGHT - 50, "Raid on Ashbridge's Bay");
		main.ttfTiny.drawString(20, Constants.WINDOW_HEIGHT - 30,
				Constants.VERSION);
		main.ttfTiny.drawString(120, Constants.WINDOW_HEIGHT - 30,
				"Nickname: " +main.user_id);
		main.ttfTiny.drawString(270, Constants.WINDOW_HEIGHT - 30,
				"High Score: " +main.highest_score);
		
		menu.render();
		
		if (setUserID){
			g.setColor(trans);
			g.fillRect(Constants.WINDOW_WIDTH/2 - 200,
					Constants.WINDOW_HEIGHT/2 - 110, 400, 220);
			g.setColor(Color.white);
			g.drawRect(	Constants.WINDOW_WIDTH/2 - 190,
						Constants.WINDOW_HEIGHT/2 - 100, 380, 200);
			
			int top = Constants.WINDOW_HEIGHT/2 - 90;
			String t = "Set Nickname";
			main.ttfMedium.drawString(
					Constants.WINDOW_WIDTH / 2 - main.ttfMedium.getWidth(t) / 2,
					top , t, Color.white);

			t = "Set nickname for global high scores";
			main.ttfSmall.drawString(
					Constants.WINDOW_WIDTH / 2 - main.ttfSmall.getWidth(t) / 2,
					top + 45 , t, Color.white);

			t = "and rankings, then press ENTER.";
			main.ttfSmall.drawString(
					Constants.WINDOW_WIDTH / 2 - main.ttfSmall.getWidth(t) / 2,
					top + 70 , t, Color.white);

			t = "You only have to do this once.";
			main.ttfSmall.drawString(
					Constants.WINDOW_WIDTH / 2 - main.ttfSmall.getWidth(t) / 2,
					top + 110 , t, Color.white);
			
			field.render(gc, g);
			field.setFocus(true);
		} else {

		}
	}

	public void update(GameContainer container, int delta) {
		
		if (setUserID){ 
			
		} else {
			demoStartTimer += delta;
			if (demoStartTimer > 30000){
				demoStartTimer = 0;
				main.drums.stop();
				main.requestMode(Modes.DEMOMODE, gc);
				return;
			}
			
			menu.update();
	
			if (state == STATE_MENU && optionSelected) {
				if (selectedIndex == 4){
					optionSelected = false;
					menu.selectionMade = false;
					try {
						if (!(container instanceof AppletGameContainer.Container)) main.doFullScreenToggle(gc);
					} catch (SlickException e) {
						Log.error("Fullscreen toggle error", e);
						e.printStackTrace();
					}
				} else if (selectedIndex == 3 ){
					setUserID = true;
					field.setText(main.user_id);
					field.setFocus(true);
					optionSelected = false;
					menu.selectionMade = false;
				} else if (selectedIndex != 5 ){
					state = STATE_FADE_OUT;
					main.startFade(true, this);
				} else {
					main.drums.stop();
					gc.exit();
				}
			}
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
				main.drums.stop();
				main.requestMode(Modes.GAME, gc);
				break;
			case 1:
				main.requestMode(Modes.HELP1, gc);
				break;
			case 2:
				main.requestMode(Modes.HIGHSCORE, gc);
				break;
			case 3:
				// Not fading for this option same for case 4
				break;
			case 5:
				main.drums.stop();
				gc.exit();
				break;
			}
		}
	}

	@Override
	public void selectionChanged(int selectedIndex) {
		
	}

	@Override
	public void optionSelected(int selectedIndex) {
		this.optionSelected = true;
		this.selectedIndex = selectedIndex;
		main.menuSound.play();
	}

	private void loadFile() {
		if (!FileManager.getContents(main.file, main)) {  
			setUserID = true;
		} else {
			setUserID = false;
		}
	}
	
}


