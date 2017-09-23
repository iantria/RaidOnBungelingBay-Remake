package ca.iantria.raid.modes;

import java.util.ArrayList;

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
import ca.iantria.raid.util.highscore.Score;

public class HighScoresMode implements IMode, IMenuListener, IFadeListener {

	public static final int STATE_FADE_IN = 0;
	public static final int STATE_MENU = 1;
	public static final int STATE_FADE_OUT = 2;
	public static final int STATE_DONE = 3;

	private String[] options = new String[] {"New Game", "Main Menu"};
	private Main main;
	private GameContainer gc;
	public Menu menu;
	public boolean optionSelected;
	public int selectedIndex;
	public int state = STATE_FADE_IN;
	public boolean isFristTime = true;
	public ArrayList<Score> scores;
	
	@Override
	public void init(Main main, GameContainer gc) throws SlickException {
		this.main = main;
		this.gc = gc;
		menu = new Menu(0, 0, main, 0, this, options);
		main.startFade(false, this);
		if (!main.drums2.playing()) main.drums2.loop();
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		menu.update();
		if (isFristTime){
			scores = main.highScores.getHighScores("1");
			isFristTime = false;
		}
		if (state == STATE_MENU && optionSelected) {
			state = STATE_FADE_OUT;
			main.startFade(true, this);
		}
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		main.backgroundImageScores.draw(0, 0, Constants.WINDOW_WIDTH,	Constants.WINDOW_HEIGHT);
		statistics(g);
        menu.render();
	}

	private void statistics(Graphics g) {
		Color trans = new Color(0f,0f,0f,0.3f);
		g.setColor(trans);
		g.fillRect(Constants.WINDOW_WIDTH/2 - 350, Constants.WINDOW_HEIGHT/2 -300, 700, 600);
		
		main.ttfMedium.drawString(Constants.WINDOW_WIDTH/2- main.ttfMedium.getWidth("Global High Scores")/2, 
				Constants.WINDOW_HEIGHT/2 - 250, "Global High Scores");
		
		int y =-175;
		int xRank = Constants.WINDOW_WIDTH/2 - 326;
		int xUser = Constants.WINDOW_WIDTH/2 - 255;
		int xScore = Constants.WINDOW_WIDTH/2 - 138;
		int xWin = Constants.WINDOW_WIDTH/2 - 55;
		int xDuration = Constants.WINDOW_WIDTH/2 + 60;
		int xDate = Constants.WINDOW_WIDTH/2 + 200;
		
		main.ttfSmall.drawString(xRank, Constants.WINDOW_HEIGHT/2 + y, "RANK", Color.yellow);
		main.ttfSmall.drawString(xUser, Constants.WINDOW_HEIGHT/2 + y, "PLAYER", Color.yellow);
		main.ttfSmall.drawString(xScore, Constants.WINDOW_HEIGHT/2 + y, "SCORE", Color.yellow);
		main.ttfSmall.drawString(xWin, Constants.WINDOW_HEIGHT/2 + y, "OUTCOME", Color.yellow);
		main.ttfSmall.drawString(xDuration, Constants.WINDOW_HEIGHT/2 + y, "DURATION", Color.yellow);
		main.ttfSmall.drawString(xDate, Constants.WINDOW_HEIGHT/2 + y, "DATE", Color.yellow);
		y = y + 35;

		int r = 1;
		for (Score s: scores){
			main.ttfSmall.drawString(xRank+ (main.ttfSmall.getWidth("RANK")/2-main.ttfSmall.getWidth(r+"")/2),
					Constants.WINDOW_HEIGHT/2 + y, r +"");
			main.ttfSmall.drawString(xUser, Constants.WINDOW_HEIGHT/2 + y, s.getUserID());
			main.ttfSmall.drawString(xScore + (main.ttfSmall.getWidth("SCORE")/2-main.ttfSmall.getWidth(s.score)/2),
					Constants.WINDOW_HEIGHT/2 + y, s.getScore());
			main.ttfSmall.drawString(xWin+25, Constants.WINDOW_HEIGHT/2 + y, s.getWinLose());
			main.ttfSmall.drawString(xDuration, Constants.WINDOW_HEIGHT/2 + y, s.getDuration());
			main.ttfSmall.drawString(xDate, Constants.WINDOW_HEIGHT/2 + y, s.getGameDate());
			y = y + 35;
			r++;
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
				main.drums2.stop();
				main.requestMode(Modes.GAME, gc);
				break;
			case 1:
				main.drums2.stop();
				main.requestMode(Modes.MAINMENU, gc);
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
	
}
