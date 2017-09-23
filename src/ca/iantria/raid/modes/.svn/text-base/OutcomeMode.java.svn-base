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

public class OutcomeMode implements IMode, IMenuListener, IFadeListener {

	public static final int STATE_FADE_IN = 0;
	public static final int STATE_MENU = 1;
	public static final int STATE_FADE_OUT = 2;
	public static final int STATE_DONE = 3;

	private String[] options = new String[] {"High Scores", "New Game", "Main Menu", "Exit"};
	private Main main;
	private GameContainer gc;
	public Menu menu;
	public boolean optionSelected;
	public int selectedIndex;
	public int state = STATE_FADE_IN;
	public int myRank;
	public boolean isFristTime = true;
	
	@Override
	public void init(Main main, GameContainer gc) throws SlickException {
		this.main = main;
		this.gc = gc;
		if (main.statistics.score > Integer.parseInt(main.highest_score))
			main.highest_score = main.statistics.score + "";
		main.saveFile();
		menu = new Menu(0, 0, main, 0, this, options);
		main.startFade(false, this);
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		menu.update();
		main.stopAllSounds();
		if (isFristTime){
			myRank = main.highScores.saveMyScore(main.highScores.getMyScore(main));
			isFristTime = false;
		}
		if (state == STATE_MENU && optionSelected) {
			 if (selectedIndex != 3){
				state = STATE_FADE_OUT;
				main.startFade(true, this);
			} else {
				main.drums.stop();
				gc.exit();
			}
		}
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		String text;
		Color color;
        if (!main.statistics.youWon){
        	text = "You have FAILED!";
        	color = Color.red;
        	main.backgroundImageLose.draw(0,0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        } else {
        	text = "You have WON!";
        	color = Color.white;
        	main.backgroundImageWin.draw(0,0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        }
        
        
		statistics(g);
        main.ttfLarge.drawString(Constants.WINDOW_WIDTH/2 - main.ttfLarge.getWidth(text)/2, 
        		Constants.WINDOW_HEIGHT/2 -280, text, color);
        
        menu.render();
	}

	private void statistics(Graphics g) {
		Color trans = new Color(0f,0f,0f,0.3f);
		g.setColor(trans);
		g.fillRect(Constants.WINDOW_WIDTH/2-350, Constants.WINDOW_HEIGHT/2 -300, 700, 600);
		
		main.ttfMedium.drawString(Constants.WINDOW_WIDTH/2- main.ttfMedium.getWidth("Statistics")/2, 
				Constants.WINDOW_HEIGHT/2 -228, "Statistics");
		
		float f;
		int y =-175;
		int x = Constants.WINDOW_WIDTH/2 -175;
		
		String text = "Score: " + main.statistics.score;
		main.ttfSmall.drawString(x- main.ttfSmall.getWidth(text)/2, 
				Constants.WINDOW_HEIGHT/2 + y, text, Color.yellow);
		y = y + 25;

		text = "Carrier Status: ";
		if (!main.statistics.carrierSurvived) text = text + "Destoyed";
		else text = text + "Survived";
		main.ttfSmall.drawString(x- main.ttfSmall.getWidth(text)/2, 
				Constants.WINDOW_HEIGHT/2+ y, text);
		y = y + 25;

		text = "Planes lost: " + main.statistics.numberOfLivesLost;
		main.ttfSmall.drawString(x- main.ttfSmall.getWidth(text)/2, 
				Constants.WINDOW_HEIGHT/2 + y, text, Color.yellow);
		y = y + 25;

		text = "Factories Destroyed: " + main.statistics.numberOfFactoriesDestroyed;
		main.ttfSmall.drawString(x- main.ttfSmall.getWidth(text)/2, 
				Constants.WINDOW_HEIGHT/2+ y, text);
		y = y + 25;

		text = "Bombers Destroyed: " + main.statistics.numberOfBombersDestroyed;
		main.ttfSmall.drawString(x- main.ttfSmall.getWidth(text)/2, 
				Constants.WINDOW_HEIGHT/2 + y, text);
		y = y + 25;

		text = "AAGuns Fired: " + main.statistics.numberOfTimesAAGunFired;
		main.ttfSmall.drawString(x- main.ttfSmall.getWidth(text)/2, 
				Constants.WINDOW_HEIGHT/2 + y, text);
		y = y + 25;
		
		text = "Hit by AAGuns: " + main.statistics.numberOfTimesHitByAAGun;
		main.ttfSmall.drawString(x- main.ttfSmall.getWidth(text)/2, 
				Constants.WINDOW_HEIGHT/2 + y, text);
		y = y + 25;

		text = "AAGuns Destroyed: " + main.statistics.numberOfAAGunsDestroyed;
		main.ttfSmall.drawString(x- main.ttfSmall.getWidth(text)/2, 
				Constants.WINDOW_HEIGHT/2 + y, text);
		y = y + 25;
		
		text = "Fighters Fired: " + main.statistics.numberOfTimesFighterFired;
		main.ttfSmall.drawString(x- main.ttfSmall.getWidth(text)/2, 
				Constants.WINDOW_HEIGHT/2 + y, text);
		y = y + 25;
		
		text = "Hit by Fighters: " + main.statistics.numberOfTimesHitByFighter;
		main.ttfSmall.drawString(x- main.ttfSmall.getWidth(text)/2, 
				Constants.WINDOW_HEIGHT/2 + y, text);
		y = y + 25;

		text = "Fighters Destroyed: " + main.statistics.numberOfFightersDestroyed;
		main.ttfSmall.drawString(x- main.ttfSmall.getWidth(text)/2, 
				Constants.WINDOW_HEIGHT/2 + y, text);
		y = y + 25;
		
		text = "Cruise Missiles Fired: " + main.statistics.numberOfTimesCruiseMissileFired;
		main.ttfSmall.drawString(x- main.ttfSmall.getWidth(text)/2, 
				Constants.WINDOW_HEIGHT/2 + y, text);
		y = y + 25;
		
		text = "Hit by Cruise Missiles: " + main.statistics.numberOfTimesHitByCruiseMissle;
		main.ttfSmall.drawString(x- main.ttfSmall.getWidth(text)/2, 
				Constants.WINDOW_HEIGHT/2 + y, text);
		y = y + 25;
		
		text = "Cruise Missiles Destroyed: " + main.statistics.numberOfCruiseMissilesDestroyed;
		main.ttfSmall.drawString(x- main.ttfSmall.getWidth(text)/2, 
				Constants.WINDOW_HEIGHT/2 + y, text);
		y = y + 25;

		text = "Enemy Ship was: ";
		if (main.statistics.enemyShipWasCompleted) text = text + "Completed";
		else text = text + "Not Completed";
		main.ttfSmall.drawString(x- main.ttfSmall.getWidth(text)/2, 
				Constants.WINDOW_HEIGHT/2 + y, text, Color.yellow);
		y = y + 25;
		
		// Column 2
		y =-175;
		x = Constants.WINDOW_WIDTH/2 +175;

		int seconds = (int) (main.statistics.gameTime / 1000) % 60 ;
		int minutes = (int) ((main.statistics.gameTime / (1000*60)) % 60);
		int hours   = (int) ((main.statistics.gameTime / (1000*60*60)) % 24);
		if (hours == 0)	text = "Game Time: " + minutes + " min " + seconds + " sec";
		else text = "Game Time: "+ hours + " hr " + minutes + " min " + seconds + " sec";
		main.ttfSmall.drawString(x- main.ttfSmall.getWidth(text)/2, 
				Constants.WINDOW_HEIGHT/2 + y, text);
		y = y + 25;

		text = "My Missles Fired: " + main.statistics.numberOfMissilesFired;
		main.ttfSmall.drawString(x- main.ttfSmall.getWidth(text)/2, 
				Constants.WINDOW_HEIGHT/2 + y, text);
		y = y + 25;

		text = "My Missiles Landed: " + main.statistics.numberOfMissilesLanded;
		main.ttfSmall.drawString(x- main.ttfSmall.getWidth(text)/2, 
				Constants.WINDOW_HEIGHT/2 + y, text);
		y = y + 25;

		if (main.statistics.numberOfMissilesFired !=0) f = ((float)main.statistics.numberOfMissilesLanded/(float)main.statistics.numberOfMissilesFired)*100;
		else f = 0;
		text = "My Missiles Accuracy: " + (int)f +"%";
		main.ttfSmall.drawString(x- main.ttfSmall.getWidth(text)/2, 
				Constants.WINDOW_HEIGHT/2 + y, text, Color.yellow);
		y = y + 25;

		text = "My Cannon Fired: " + main.statistics.numberOfCannonRoundsFired;
		main.ttfSmall.drawString(x- main.ttfSmall.getWidth(text)/2, 
				Constants.WINDOW_HEIGHT/2 + y, text);
		y = y + 25;

		text = "My Cannon Landed: " + main.statistics.numberOfCannonRoundsLanded;
		main.ttfSmall.drawString(x- main.ttfSmall.getWidth(text)/2, 
				Constants.WINDOW_HEIGHT/2 + y, text);
		y = y + 25;
		
		if (main.statistics.numberOfCannonRoundsFired != 0) f = ((float)main.statistics.numberOfCannonRoundsLanded/(float)main.statistics.numberOfCannonRoundsFired)*100;
		else f = 0;
		text = "My Cannon Accuracy: " + (int)f +"%";
		main.ttfSmall.drawString(x- main.ttfSmall.getWidth(text)/2, 
				Constants.WINDOW_HEIGHT/2 + y, text, Color.yellow);
		y = y + 25;
		
		text = "Plane Damage Taken: " + main.statistics.amountOfDamageTaken;
		main.ttfSmall.drawString(x- main.ttfSmall.getWidth(text)/2, 
				Constants.WINDOW_HEIGHT/2 + y, text);
		y = y + 25;
		
		text = "Carrier Damage Taken: " + main.statistics.amountOfCarrierDamageTaken;
		main.ttfSmall.drawString(x- main.ttfSmall.getWidth(text)/2, 
				Constants.WINDOW_HEIGHT/2 + y, text);
		y = y + 25;

		int t = main.statistics.amountOfDamageTaken + main.statistics.amountOfCarrierDamageTaken;
		text = "Total Damage Taken: " + t;
		main.ttfSmall.drawString(x- main.ttfSmall.getWidth(text)/2, 
				Constants.WINDOW_HEIGHT/2 + y, text);
		y = y + 25;

		text = "Total Damage Dealt: " + main.statistics.amountOfDamageDealt;
		main.ttfSmall.drawString(x- main.ttfSmall.getWidth(text)/2, 
				Constants.WINDOW_HEIGHT/2 + y, text);
		y = y + 25;
		
		if (t == 0 ) t = 1;
		f = (float) main.statistics.amountOfDamageDealt / (float)t;
		text = "Dealt/Taken Ratio: " + String.format("%.2f", f);
		main.ttfSmall.drawString(x- main.ttfSmall.getWidth(text)/2, 
				Constants.WINDOW_HEIGHT/2 + y, text, Color.yellow);
		y = y + 25;

		f = (float)main.statistics.amountOfDamageDealt/ (float)(main.statistics.gameTime / 1000);
		text = "Damage/Second: " + String.format("%.2f", f);
		main.ttfSmall.drawString(x- main.ttfSmall.getWidth(text)/2, 
				Constants.WINDOW_HEIGHT/2 + y, text);
		y = y + 25;

		text = "Carrier Landings: " + main.statistics.numberOfLandings;
		main.ttfSmall.drawString(x- main.ttfSmall.getWidth(text)/2, 
				Constants.WINDOW_HEIGHT/2 + y, text);
		y = y + 25;
		
		text = "Fuel Used: " + main.statistics.amountOfFuelUsed;
		main.ttfSmall.drawString(x- main.ttfSmall.getWidth(text)/2, 
				Constants.WINDOW_HEIGHT/2 + y, text);
		y = y + 25;
		
		if (!isFristTime){
			text = "You are ranked >>> " + myRank + " <<< worldwide.";
			y = y + 25;
			main.ttfSmall.drawString(Constants.WINDOW_WIDTH/2 - main.ttfSmall.getWidth(text)/2, 
					Constants.WINDOW_HEIGHT/2 + y, text, Color.yellow);
		}
		
		main.ttfTiny.drawString(20, Constants.WINDOW_HEIGHT - 30,
				Constants.VERSION);
		main.ttfTiny.drawString(120, Constants.WINDOW_HEIGHT - 30,
				"Nickname: " + main.user_id);
		main.ttfTiny.drawString(270, Constants.WINDOW_HEIGHT - 30,
				"High Score: " + main.highest_score);
		
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
				main.requestMode(Modes.HIGHSCORE, gc);
				break;
			case 1:
				main.drums.stop();
				main.requestMode(Modes.GAME, gc);
				break;
			case 2:
				main.requestMode(Modes.MAINMENU, gc);
				break;
			case 3:
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
	
}
