package ca.iantria.raid.util.highscore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.newdawn.slick.util.Log;

import ca.iantria.raid.Main;

public class HighScores {

	
	public ArrayList<Score> getHighScores(String gameMode){
		ArrayList<Score> results = new ArrayList<Score>();
        URL url = null;
		try {
			url = new URL("http://iantria.com/services/raidHighScores.php?game_mode=" + gameMode + "&service=1");
		} catch (MalformedURLException e) {
			Log.error("URL to HighScore Service bad", e);
			return results;
		}
        URLConnection yc;
		try {
			yc = url.openConnection();
	        BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
	        String inputLine;
	        while ((inputLine = in.readLine()) != null) {
	        	String[] parts = inputLine.split(";");
	            results.add(new Score(gameMode, parts[0], parts[1], parts[2], parts[3], parts[4]));
	        }
	        in.close();
	        return results;
		} catch (IOException e) {
			Log.error("IO Exception trying to parse HighScores from Service.", e);
			return results;
		}
	}
	
	public Score getMyScore(Main main){
		String win;
		if (main.statistics.youWon) win = "Won";
		else win = "Lost";
		Score s = new Score("1", main.user_id, ""+main.statistics.score, win , ""+main.statistics.gameTime, null);
		return s;
	}
	
	public int saveMyScore(Score s){
        URL url = null;
		try {
			url = new URL("http://iantria.com/services/raidHighScores.php?service=2&data=" + s.toString());
			//System.out.println(url);
		} catch (MalformedURLException e) {
			Log.error("URL to HighScore Service bad", e);
			return -1;
		}
        URLConnection yc;
		try {
			yc = url.openConnection();
	        BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
	        String inputLine = in.readLine();
	        in.close();
	        return Integer.parseInt(inputLine) + 1;
		} catch (IOException e) {
			Log.error("IO Exception trying to update HighScores from Service.", e);
			return -1;
		}
	}
	
}
