package ca.iantria.raid.util.highscore;

public class Score {

	public String gameMode;
	public String userID;
	public String score;
	public String winLose;
	public String duration;
	public String gameDate;
	
	public Score(String gameMode, String userID, String score, String winLose, String duration, String gameDate) {
		this.gameMode = gameMode;
		this.userID = userID;
		this.score = score;
		this.winLose = winLose;
		this.duration = duration;
		this.gameDate = gameDate;
	}
	
	
	@Override
	public String toString(){
		//System.out.println(gameMode + ";" + userID + ";" + score + ";" + winLose + ";" + duration);
		return gameMode + ";" + userID + ";" + score + ";" + winLose + ";" + duration;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getWinLose() {
		return winLose;
	}

	public void setWinLose(String winLose) {
		this.winLose = winLose;
	}

	public String getDuration() {
		String text;
		int seconds = (int) ((Integer.parseInt(duration) / 1000) % 60) ;
		int minutes = (int) ((Integer.parseInt(duration) / (1000*60)) % 60);
		int hours   = (int) ((Integer.parseInt(duration) / (1000*60*60)) % 24);
		if (hours == 0)	text = minutes + " min " + seconds + " sec";
		else text = hours + " hr " + minutes + " min " + seconds + " sec";
		return text;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getGameDate() {
		return gameDate;
	}

	public void setGameDate(String gameDate) {
		this.gameDate = gameDate;
	}
}
