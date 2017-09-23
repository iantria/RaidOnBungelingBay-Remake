package ca.iantria.raid;

import java.awt.Font;
import java.io.File;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import org.lwjgl.BufferUtils;
import org.lwjgl.Sys;
import org.lwjgl.input.Cursor;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.Animation;
import org.newdawn.slick.AppletGameContainer;
import org.newdawn.slick.ApplicationGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.ScalableGame;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.openal.SoundStore;
import org.newdawn.slick.opengl.CursorLoader;
import org.newdawn.slick.particles.ParticleIO;
import org.newdawn.slick.particles.ParticleSystem;
import org.newdawn.slick.util.Log;

import ca.iantria.raid.entity.AAGun;
import ca.iantria.raid.entity.Carrier;
import ca.iantria.raid.entity.EnemyPlane;
import ca.iantria.raid.entity.EnemyShip;
import ca.iantria.raid.entity.Factory;
import ca.iantria.raid.entity.GameMap;
import ca.iantria.raid.entity.Plane;
import ca.iantria.raid.entity.Projectile;
import ca.iantria.raid.entity.ScrollingCombatText;
import ca.iantria.raid.entity.SecretBase;
import ca.iantria.raid.interfaces.IFadeListener;
import ca.iantria.raid.interfaces.IMode;
import ca.iantria.raid.modes.DemoMode;
import ca.iantria.raid.modes.GameMode;
import ca.iantria.raid.modes.Help1Mode;
import ca.iantria.raid.modes.Help2Mode;
import ca.iantria.raid.modes.HighScoresMode;
import ca.iantria.raid.modes.LoadingMode;
import ca.iantria.raid.modes.MainMenuMode;
import ca.iantria.raid.modes.Modes;
import ca.iantria.raid.modes.OutcomeMode;
import ca.iantria.raid.util.Constants;
import ca.iantria.raid.util.DrawHelper;
import ca.iantria.raid.util.FileManager;
import ca.iantria.raid.util.Statistics;
import ca.iantria.raid.util.highscore.HighScores;

public class Main extends BasicGame {

	// Entities
	public Plane plane;
	public Carrier carrier;
	public GameMap gameMap;
	public EnemyShip enemyShip;
	public SecretBase secretBase;
	public ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
	public ArrayList<Projectile> removeProjectileList = new ArrayList<Projectile>();
	public ArrayList<ScrollingCombatText> combatText = new ArrayList<ScrollingCombatText>();
	public ArrayList<ScrollingCombatText> removeCombatTextList = new ArrayList<ScrollingCombatText>();
	public Projectile[] myMissileShots;
	public Projectile[] myCannonShots;
	public Factory[] factories = new Factory[6];
    public AAGun[] AAGuns = new AAGun[17];
    public EnemyPlane[] enemyPlanes = new EnemyPlane[4];

	// Useful stuff
	public int MAP_WIDTH;
	public int MAP_HEIGHT;
	public static float ASPECT_RATIO;
	public Statistics statistics = new Statistics();
	public DrawHelper drawer = new DrawHelper();
	public static Main main;
	public GameMode gameMode;
	public IMode mode;
	public Random random = new Random();
	public Input input;
	public long nextFrameTime;
	public GameContainer gc;
	public int loadIndex;
	public float map_dx;
	public float map_dy;
	public HighScores highScores = new HighScores();
	public String user_id = "Unknown";
	public String highest_score = "0";
	public File file = new File(Constants.FILE_NAME);

	public static ApplicationGameContainer appGameContainer;
	public static DisplayMode display;
	public Cursor nativeCursor;

	// Fading
	public IFadeListener fadeListener;
	public boolean fading;
	public int fadeIndex;
	public boolean fadeOut;
	public static final Color[] FADES = new Color[23];
	public int fadeTimer;

	static {
		for (int i = 0; i < FADES.length; i++) {
			FADES[i] = new Color(0, 0, 0, 255 * i / (FADES.length - 1));
		}
	}

	// Animations
	public Animation[] explosionAnimations;
	public ParticleSystem missleImpact;
	public ParticleSystem playerDamage;
	public ParticleSystem smokeStack;
	public ParticleSystem engine;

	// Images
	public Image planeImage;
	public Image livesImage;
	public Image factoryIcon;
	public Image miniMap;
	public Image carrierDirectionArrow;
	public Image projectileImage;
	public Image cruiseMissleImage;
	public Image enemyFighterImage;
	public Image enemyBomberImage;
	public Image cannonBulletImage;
	public Image enemyProjectileImage;
	public Image missleImage;
	public Image carrierImage;
	public Image mapImage;
	public Image factoryImage;
	public Image AAGunImage;
	public Image menuSelectionImage;
	public Image mainMenuBackgroundImage;
	public Image helpBackgroundImage;
	public Image cursorImage;
	public Image controlImage;
	public Image backgroundImageWin;
	public Image backgroundImageLose;
	public Image backgroundImageScores;
	public Image enemyShipImage;
	public Image enemyShipIcon;
	public Image carrierIcon;
	public Image secretBaseImage;

	// Sound and Music
	public Music modMusic;
	public Music drums;
	public Music drums2;
	public Sound fireCannonEffect;
	public Sound fireMissleEffect;
	public Sound dropBombEffect;
	public Sound cruiseSpeedEffect;
	public Sound afterBurnerSpeedEffect;
	public Sound landEffect1;
	public Sound outOfFuelCrashSound;
	public Sound oceanSound;
	public Sound bulletHitLand;
	public Sound AAGunFireSound;
	public Sound mediumExplosion;
	public Sound bigExplosion;
	public Sound youWin;
	public Sound carrierAlarm;
	public Sound projectileImpact;
	public Sound fighterFire;
	public Sound enemyCruise;
	public Sound bombsDroppingSound;
	public Sound menuSound;
	public Sound cruiseOutOfFuel;
	public Sound m61Sound;

	// Fonts
	public TrueTypeFont ttfTiny;
	public TrueTypeFont ttfSmall;
	public TrueTypeFont ttfMedium;
	public TrueTypeFont ttfLarge;
	private Font fontLarge;
	private Font fontMedium;
	private Font fontSmall;
	private Font fontTiny;

	public Main() {
		super("Raid on Ashbridge's Bay");
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		Main.main = this;
		this.gc = gc;

		if (gc instanceof AppletGameContainer.Container) {
			ASPECT_RATIO = 4f/3f;
			gc.setShowFPS(false);
			gc.setVSync(false);
			gc.setTargetFrameRate(300);
			Constants.WINDOW_WIDTH = gc.getWidth();
			Constants.WINDOW_HEIGHT = gc.getHeight();
			miniMap = new Image(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
			hideMouseCursor();
		} else {
			if (gc.isFullscreen()) {
				hideMouseCursor();
				miniMap = new Image(Main.display.getWidth(), Main.display.getHeight());
				gc.setVSync(true);
				gc.setSmoothDeltas(true);
			} else {
				showMouseCursor();
				miniMap = new Image(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
			}			
		}

		gc.setUpdateOnlyWhenVisible(true);
		gc.setClearEachFrame(false);
		SoundStore.get().setCurrentMusicVolume(1);		
		SoundStore.get().setSoundVolume(1);
		input = gc.getInput();
		loadFonts();
		requestMode(Modes.LOADING, gc);
	}

	@Override
	public void render(GameContainer gc, Graphics gr) throws SlickException {
		mode.render(gc, gr);
		if (fading) {
			gr.setColor(FADES[fadeIndex]);
			gr.fillRect(0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
		}
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		if (fading) {
			fadeTimer += delta;
			if (fadeTimer > 15){
				fadeTimer = 0;
				if (fadeOut) {
					if (++fadeIndex == FADES.length) {
						fading = false;
						if (fadeListener != null) fadeListener.fadeCompleted();
					}
				} else {
					if (--fadeIndex == -1) {
						fading = false;
						if (fadeListener != null) fadeListener.fadeCompleted();
					}
				}
			}
		}
		fullScreenToggleCheck(gc);
		mode.update(gc, delta);
	}

	public void fullScreenToggleCheck(GameContainer gc) throws SlickException {
		if (input.isKeyPressed(Input.KEY_F12)) {
			doFullScreenToggle(gc);
		}
	}
	
	public void doFullScreenToggle(GameContainer gc) throws SlickException {
		if (gc instanceof AppletGameContainer.Container) return;  
		if (gc.isFullscreen()) {
			main.showMouseCursor();
			miniMap = new Image(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
			gc.setFullscreen(false);
			gc.setVSync(false);
			gc.setSmoothDeltas(false);
			gc.setTargetFrameRate(display.getFrequency()*2);
		} else {
			hideMouseCursor();
			miniMap = new Image(Main.display.getWidth(), Main.display.getHeight());			
			appGameContainer.setDisplayMode(Main.display.getWidth(), Main.display.getHeight(), true);
			gc.setVSync(true);
			gc.setSmoothDeltas(true);
		}
	}

	public void showMouseCursor() {
		try {
			Mouse.setNativeCursor(nativeCursor);
		} catch (Exception e) {
			Log.error("Failed to load and apply cursor.", e);
		}
	}

	public void hideMouseCursor() {
		try {
			ByteBuffer buffer = BufferUtils.createByteBuffer(32 * 32 * 4);
			Cursor cursor = CursorLoader.get().getCursor(buffer, 0, 0, 32, 32);
			nativeCursor = Mouse.getNativeCursor();
			Mouse.setNativeCursor(cursor);
		} catch (Exception e) {
			Log.error("Failed to load and apply cursor.", e);
		}
	}

	public void startFade(boolean fadeOut, IFadeListener fadeListener) {
		fading = true;
		this.fadeOut = fadeOut;
		this.fadeListener = fadeListener;

		if (fadeOut) {
			fadeIndex = 0;
		} else {
			fadeIndex = FADES.length - 1;
		}
	}

	public void removeFadeListener() {
		fadeListener = null;
	}

	public void requestMode(Modes mode, GameContainer gc) {
		switch (mode) {
		case GAME:
			gameMode = new GameMode();
			setMode(gameMode, gc);
			break;
		case MAINMENU:
			setMode(new MainMenuMode(), gc);
			break;
		case LOADING:
			setMode(new LoadingMode(), gc);
			break;
		case HELP1:
			setMode(new Help1Mode(), gc);
			break;
		case HELP2:
			setMode(new Help2Mode(), gc);
			break;			
		case OUTCOME:
			setMode(new OutcomeMode(), gc);
		break;
		case HIGHSCORE:
			setMode(new HighScoresMode(), gc);
		break;		
		case DEMOMODE:
			gameMode = new DemoMode();
			setMode(gameMode, gc);
		break;		
		}
	}

	public void setMode(IMode mode, GameContainer gc) {
		try {
			input.clearKeyPressedRecord();
			this.mode = mode;
			mode.init(this, gc);
		} catch (Throwable t) {
			Log.error("setMode error", t);
		}
	}

	private void loadSprites() throws SlickException {
		SpriteSheet sheet = new SpriteSheet("graphics/explosions.png", 64, 64);
		explosionAnimations = new Animation[8];
		for (int j = 0; j < 8; j++) {
			explosionAnimations[j] = new Animation();
			explosionAnimations[j].stopAt(15);
		}
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 8; j++) {
				explosionAnimations[j].addFrame(sheet.getSprite(i, j), 100);
			}
		}
	}

	private void loadFonts() {
		fontTiny = new Font("Verdana", Font.BOLD, 10);
		fontSmall = new Font("Verdana", Font.BOLD, 16);
		fontMedium = new Font("Verdana", Font.BOLD, 24);
		fontLarge = new Font("Verdana", Font.BOLD, 32);

		ttfTiny = new TrueTypeFont(fontTiny, true);
		ttfSmall = new TrueTypeFont(fontSmall, true);
		ttfMedium = new TrueTypeFont(fontMedium, true);
		ttfLarge = new TrueTypeFont(fontLarge, true);
	}

	public double getUnsignedDegreesToPlane(Vector2f screenPoint) {
		double dy = screenPoint.getY() - plane.getPosition().getY();
		double dx = plane.getPosition().getX() - screenPoint.getX();
		return (Math.atan2(-dx, -dy) / Math.PI * 180 + 180);
	}

	public double getSignedDegreesToPlane(Vector2f screenPoint) {
		double dy = screenPoint.getY() - plane.getPosition().getY();
		double dx = plane.getPosition().getX() - screenPoint.getX();
		return Math.toDegrees(Math.atan2(dx, dy));
	}

	public double getUnsignedDegrees(Vector2f from, Vector2f to) {
		double dy = from.getY() - to.getY();
		double dx = to.getX() - from.getX();
		return (Math.atan2(-dx, -dy) / Math.PI * 180 + 180);
	}

	public double getSignedDegrees(Vector2f from, Vector2f to) {
		double dy = from.getY() - to.getY();
		double dx = to.getX() - from.getX();
		return Math.toDegrees(Math.atan2(dx, dy));
	}
	
	public double getSignedDegreesToCarrier(Vector2f screenPoint) {
		double dy = screenPoint.getY() - (carrier.getPosition().getY() + carrier.image.getHeight()/2* carrier.scale);
		double dx = carrier.getPosition().getX() - screenPoint.getX();
		return (Math.atan2(-dx, -dy) / Math.PI * 180 + 180);
	}

	public double getUnsignedSignedDegreesToCarrier(Vector2f screenPoint) {
		double dy = screenPoint.getY() - (carrier.getPosition().getY() + carrier.image.getHeight()/2* carrier.scale);
		double dx = (carrier.getPosition().getX() + 16) - screenPoint.getX() ;
		return Math.toDegrees(Math.atan2(dx, dy));
	}

	public double calculateDifferenceBetweenAngles(double firstAngle,
			double secondAngle) {
		double difference = secondAngle - firstAngle;
		while (difference < -180)
			difference += 360;
		while (difference > 180)
			difference -= 360;
		return difference;
	}

	public double getAngleToPlane(Vector2f screenPoint) {
        double dy = screenPoint.getY() - Constants.WINDOW_HEIGHT/2 + planeImage.getHeight()*plane.scale/2f;
		double dx = Constants.WINDOW_WIDTH/2 - planeImage.getWidth()*plane.scale/2f - screenPoint.getX();
        return Math.toDegrees(Math.atan2(dx, dy));
    }
	
	public float loadNext() throws Throwable {
		switch (loadIndex) {
		case 0:
			drums = new Music("sounds/Drums.ogg");
			drums.loop();
			break;
		case 1:
			modMusic = new Music("sounds/spacedeb.mod");
			break;
		case 2:
			mapImage = new Image("graphics/newmap.gif");
			break;
		case 3:
			menuSound = new Sound("sounds/menuMove.ogg");
			break;
		case 4:
			fireMissleEffect = new Sound("sounds/MissleLaunch.ogg");
			secretBaseImage = new Image("graphics/secretBase.gif"); 
			break;
		case 5:
			fighterFire = new Sound("sounds/FighterGun.ogg");
			bombsDroppingSound = new Sound("sounds/B2Bombing.ogg");
			break;
		case 6:
			cruiseSpeedEffect = new Sound("sounds/RocketThrusters.ogg");
			backgroundImageScores = new Image("graphics/scores.jpg");
			break;
		case 7:
			fireCannonEffect = new Sound("sounds/machinegun1.ogg");
			carrierIcon = new Image("graphics/carrierIcon.gif");
			
			break;
		case 8:
			afterBurnerSpeedEffect = new Sound("sounds/afterburn.ogg");
			break;
		case 9:
			landEffect1 = new Sound("sounds/jetstop.ogg");
			drums2 = new Music("sounds/Drums2.ogg");
			break;
		case 10:
			projectileImpact = new Sound("sounds/projectileImpact.ogg");
			backgroundImageWin = new Image("graphics/win.jpg");
			backgroundImageLose = new Image("graphics/lose.jpg");
			break;
		case 11:
			outOfFuelCrashSound = new Sound("sounds/jetcrashfuel.ogg");
			enemyShipIcon = new Image("graphics/ddgIcon.gif");
			break;
		case 12:
			youWin = new Sound("sounds/youwin.ogg");
			m61Sound =  new Sound("sounds/m61.ogg");
			break;
		case 13:
			bigExplosion = new Sound("sounds/explosion_big.ogg");
			break;
		case 14:
			enemyCruise = new Sound("sounds/enemyCruise.ogg");
			engine = ParticleIO.loadConfiguredSystem("particles/engine.xml");
			break;
		case 15:
			mediumExplosion = new Sound("sounds/explosion_medium.ogg");
			break;
		case 16:
			oceanSound = new Sound("sounds/ocean.ogg");
			cruiseOutOfFuel = new Sound("sounds/cruiseOutFuel.ogg");
			break;
		case 17:
			AAGunFireSound = new Sound("sounds/AAGun.ogg");
			missleImpact = ParticleIO.loadConfiguredSystem("particles/missle.xml");
			break;
		case 18:
			bulletHitLand = new Sound("sounds/landhit.ogg");
			enemyShipImage = new Image("graphics/ddg.gif");
			break;
		case 19:
			playerDamage = ParticleIO.loadConfiguredSystem("particles/player_dmg.xml");
			carrierAlarm = new Sound("sounds/carrierAlarm.ogg");
			break;
		case 20:
			factoryImage = new Image("graphics/factoryNew.png");
			AAGunImage = new Image("graphics/AAGunNew.gif");
			menuSelectionImage = new Image("graphics/planeIcon.png");
			menuSelectionImage.rotate(270);
			planeImage = new Image("graphics/plane.png");
			break;
		case 21:
			loadSprites();
			smokeStack = ParticleIO.loadConfiguredSystem("particles/factory.xml");
			break;
		case 22:
			cannonBulletImage = new Image("graphics/bullets.png");
			enemyBomberImage = new Image("graphics/bomber.gif");
			enemyFighterImage = new Image("graphics/enemyFighter.png");
			mainMenuBackgroundImage = new Image("graphics/menu.jpg");
			cursorImage = new Image("graphics/cursor.gif");
			break;
		case 23:
			enemyProjectileImage = new Image("graphics/projectile.png");
			cruiseMissleImage = new Image("graphics/CruiseMissle.png");
			factoryIcon = new Image("graphics/factoryIcon.gif");
			carrierDirectionArrow = new Image("graphics/arrow.png");
			break;
		case 24:
			carrierImage = new Image("graphics/Carrier.gif");
			missleImage = new Image("graphics/Missle.png");
			livesImage = new Image("graphics/planeIcon.png");
			controlImage = new Image("graphics/control.gif");
			helpBackgroundImage = new Image("graphics/help.jpg");
			break;
		case 25:
			requestMode(Modes.MAINMENU, gc);
			break;
		}

		return ++loadIndex / 25f;
	}

	public void stopAllSounds(){
		cruiseSpeedEffect.stop();
		afterBurnerSpeedEffect.stop();
		oceanSound.stop();
		modMusic.stop();
		enemyCruise.stop();
	}
	
	public void saveFile(){
		FileManager.setContents(main.file, main);
	}
	
	public static void main(String[] args) throws SlickException {
//		Calendar cal = Calendar.getInstance();
//		Date today = cal.getTime();
//		Log.info("Today is: " + today);
//		cal.set(2013, 4, 30);
//		Date expiry = cal.getTime();
//		Log.info("Expiry date is: " + expiry);
//		Log.info("Expired = " +  today.after(expiry));
//		if (today.after(expiry)) {
//			Log.info("Alpha version of this game has expired.");
//			Sys.alert("Expired","Alpha version of this game has expired.");
//			System.exit(0);
//		}
		
		display = Display.getDisplayMode();
		ASPECT_RATIO = (float)display.getWidth()/(float)display.getHeight();
		
		Log.info("Screen display: " + display.getWidth() + "x"+ display.getHeight());
		Log.info("Aspect Ratio: " + ASPECT_RATIO);

		if (Math.abs(ASPECT_RATIO - 4f/3f) < 0.01f){
			Constants.WINDOW_WIDTH = 1024; 
			Constants.WINDOW_HEIGHT = 768;
		} else if (Math.abs(ASPECT_RATIO - 16f/10f) < 0.01){
			//ASPECT_RATIO = 4f/3f; //for testing
			Constants.WINDOW_WIDTH = 1280; //1280 
			Constants.WINDOW_HEIGHT = 800; //800
		} else if (Math.abs(ASPECT_RATIO - 16f/9f)< 0.01){
			Constants.WINDOW_WIDTH = 1366; 
			Constants.WINDOW_HEIGHT = 768;
		}else if (Math.abs(ASPECT_RATIO - 5f/4f)< 0.01){
			Constants.WINDOW_WIDTH = 1280; 
			Constants.WINDOW_HEIGHT = 1024;			
		} else {
			Constants.WINDOW_WIDTH = 1024; 
			Constants.WINDOW_HEIGHT = 768;			
			Log.info(">>> Non standard aspect ratio detected.");
		}

		Main main = new Main();
		appGameContainer = new ApplicationGameContainer(new ScalableGame(main, Constants.WINDOW_WIDTH,
			Constants.WINDOW_HEIGHT, true),	Constants.WINDOW_WIDTH,	Constants.WINDOW_HEIGHT, true);
		try {
			//appGameContainer.setIcon("graphics/32x32.png");
		} catch (Throwable t) {
			Log.error("Icon error", t);
		}
		appGameContainer.setResizable(false);
		appGameContainer.setShowFPS(false);
		appGameContainer.setDisplayMode(display.getWidth(), display.getHeight(), true);
		appGameContainer.start();
	}
}