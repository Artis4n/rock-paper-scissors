package resources;

import enums.Shape;
import threads.Player;

/**
 * PlayerMessage class which contains the message sent 
 * from the Player Thread to the Referee Thread
 */
public class PlayerMessage {

	private Shape randomShape;
	private long playerid;
	private Player currentPlayer;

	/**
	 * Constructor for creating a player message
	 * @param currentPlayer returns a player
	 * @param playerid assigns a player number
	 * @param randomShape assigns a random shape
	 */
	public PlayerMessage(Player currentPlayer, long playerid, Shape randomShape){
		this.currentPlayer = currentPlayer;
		this.randomShape = randomShape;
		this.playerid = playerid;
	}

	/**
	 * Method to return the player
	 * @return player 
	 */
	public Player getPlayer(){
		return currentPlayer;
	}

	/**
	 * 
	 * @param s
	 */
	public void setShape(Shape s) {
		randomShape = s;
	}

	/**
	 * Method to retrieve a player number 
	 * @return player number
	 */
	public long getPlayerID() {
		return playerid;
	}

	/**
	 * Method to retrieve a random shape
	 * from the PlayerMessage.
	 * @return random shape
	 */
	public Shape getShape() {
		return randomShape;
	}

}
