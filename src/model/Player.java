package model;

import java.util.UUID;

public class Player {

	public String playerName;
	public int numberOfClicks;
	public String id;

	public Player(String playerName) {
		this.playerName = playerName;
		this.numberOfClicks = 0; 
		this.id = UUID.randomUUID().toString();
	}
	
	public Player(String id, String playerName, int numberOfClicks) {
		this.playerName = playerName;
		this.numberOfClicks = numberOfClicks;
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public int getNumberOfClicks() {
		return numberOfClicks;
	}

	public void setNumberOfClicks(int numberOfClicks) {
		this.numberOfClicks = numberOfClicks;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
}
