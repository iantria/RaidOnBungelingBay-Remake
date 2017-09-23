package ca.iantria.raid.util;

public class Constants {
	
	public static final String VERSION = "V0.30 BETA";

	//Window attributes
	public static int WINDOW_WIDTH = 1024; 
	public static int WINDOW_HEIGHT = 768;
	
	// Plane
	public static final int MISSLES_PER_PLANE = 6;
	public static final int CANNON_ROUNDS = 50;
	public static final int FUEL_CAPACITY = 100;
	public static final int FUEL_DURATION = 1000; // # miliseconds for 1 fuel unit
	public static final int NUMBER_OF_LIVES = 3;
	
	// Damage
	public static final int CANNON_DAMAGE = 5;
	public static final int MISSLE_DAMAGE = 20;
	public static final int ENEMY_AA_GUN_DAMAGE = 10;
	public static final int ENEMY_BOMBER_BOMB_DMG = 20;
	public static final int ENEMY_CRUISE_MISSLE_DAMAGE = 20;
	public static final int ENEMY_FIGHTER_GUN_DAMAGE = 10;
	
	// Timing
	public static final int FIRING_INTERVAL_MISSLE = 3000;
	public static final int FIRING_INTERVAL_CANNON = 1000;
	public static final int ENEMY_SHIP_FIRING_INTERVAL = 3000;
	public static final int ENEMY_SHIP_RESPAWN_TIMER = 20000;
	public static final int ENEMY_FIGHTER_RESPAWN_TIMER = 3000;
	public static final int ENEMY_FIGHTER_FIRING_INTERVAL = 1500;
	public static final int ENEMY_CRUISE_MISSLE_FIRING_INTERVAL = 16000; 
	public static final int ENEMY_CRUISE_MISSLE_FUEL = 12000;
	public static final int ENEMY_BOMBER_RESPAWN_TIMER = 5000;
	
	// Speeds
	public static final float CARRIER_SPEED = 0.01f;
	public static final float ENEMY_SHIP_SPEED = 0.03f;
	public static final float MISSLE_SPEED = 1.0f;
	public static final float MAX_PLANE_SPEED = 0.7f;
	public static final float MIN_PLANE_SPEED = 0.3f;
	public static final float ENEMY_FIGHTER_SPEED = 0.3f;
	
	// Health
	public static final int ENEMY_SHIP_HEALTH = 100;
	public static final int ENEMY_FACTORY_HEALTH = 100;
	public static final int ENEMY_AA_GUN_HEALTH = 10;
	public static final int ENEMY_CRUISE_MISSLE_HEALTH = 5;
	public static final int ENEMY_BOMBER_HEALTH = 10;
	public static final int ENEMY_FIGHTER_HEALTH = 10;
	public static final int MAX_HIT_POINTS_PLANE = 100;
	public static final int MAX_HIT_POINTS_CARRIER = 200;
	
	//Score
	public static final int SCORE_ENEMY_SHIP = 1000;
	public static final int SCORE_AA_GUN = 10;
	public static final int SCORE_FACTORY = 1000;
	public static final int SCORE_BOMBER = 50;
	public static final int SCORE_CRUISE_MISSLE = 75;
	public static final int SCORE_FIGHTER = 100;
	public static final int SCORE_CARRIER_ALIVE = 1000;
	public static final int SCORE_ENEMY_SHIP_NOT_COMPLETED = 2500;
	public static final int SCORE_PER_PLANE_REMAINING = 500;
	
											// 5     2      3    6      4     1
	public static final float[] FACTORY_X = {3440, 2992, 5844, 4924, 1776, 1952};
	public static final float[] FACTORY_Y = {2204, 1084,  700, 3338, 3212, 1436};
	
									 	//  2      2      4     4     5    5     1    3     3     3     6     6     1                   g
	public static final float[] AAGUN_X = {2562, 3020, 1936, 1460, 3558, 3582, 1750, 5900, 5645, 6154, 4630, 5460, 2150,  762,  2970, 5468, 4086};
	public static final float[] AAGUN_Y = {1100,  990, 3360,  3360, 2138, 2436, 1555, 560,  755,  984, 3244, 3214, 1186, 2300,  3686, 2070, 1450};
	
	public static final float[] BOMBER_X = {2323 ,2145};
	public static final float[] BOMBER_Y = { 929, 929};
	public static final float[] FIGHTER_X = {5290 ,5390};
	public static final float[] FIGHTER_Y = {2955, 2955};
	public static final float[] ENEMY_SHIP_XY = {4966 ,404};

	public static final float[] SECRET_BASE_XY = {2210, 1064};
	
	public static final String FILE_NAME = "raid.ini";
	
}
