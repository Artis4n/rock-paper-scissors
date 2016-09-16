package threads;

import java.lang.Thread;

import enums.GameResult;
import enums.Shape;
import main.Main;
import resources.PlayerMessage;
import resources.Statistics;

/**
 * The Player thread class that iterates NTURNS times. 
 * For each iteration the player randomly selects a (rock/scissors/paper) ‘shape’ and then notifies the referee of their wish to play. 
 * The player will put their ‘turn’ in a message queue maintained by the referee and wait for the outcome. 
 * When the game has been played the player will receive an outcome from the referee which will be either win, lose, or draw. 
 * The player updates its internal state to keep a count of each of these outcomes. Then the loop repeats itself. 
 * Once the player has exhausted all of its NTURNS turns then the player prints out its final scores and then terminate its thread.
 */
public class Player implements Runnable {

	private int NTURNS; //used to get the amount of turns for the Player and is looped through until 0
	private int win, draw, lose; //used for storing a players scored from each game
	private int turnsLeft = Main.getNTURNS(); //sets how many turns a player has by retrieving the NTURNS statically from the game class
	private final int playerid; //this is the player number which is given by the id generate in the game

	private Referee referee; //grants the player thread access to the referee class
	private Shape shape; //shape used to assign a random shape 
	private MVar<GameResult> result = new MVar<GameResult>(); //mutable variable which is used to hold the result

	private volatile boolean threadFinished = false; //variable used to shutdown thread

	/**
	 * Constructor for creating a player.
	 * @param playerid assigns an id for the player
	 * @param referee allows access to referee
	 * @param NTURNS sets number for turns a player has
	 */
	public Player(int playerid, Referee referee, int NTURNS) {
		this.playerid = playerid;
		this.referee = referee;
		this.NTURNS = NTURNS;
	}

	/**
	 * Method returns the id of the current player.
	 * @return player number
	 */
	public int getId(){
		return playerid;
	}

	/**
	 * Method to retrieve a mutable variable
	 * of type String.
	 * @return mutable variable with a string type which should be used for a result
	 */
	public MVar<GameResult> getMVar(){
		return result;
	}

	/**
	 * Method to increment Results by taking
	 * from the mutable variable and matching the string.
	 */
	public void incrementResult(){
		GameResult r = result.takeMVar();

		switch(r) {
		case WIN: //result is a win
			win++;
			break;

		case LOSE: //result is a loss
			lose++;
			break;

		case DRAW: //result is a draw
			draw++;
			break;
		}
	}

	/**
	 * Method to terminate thread gracefully.
	 */
	public void terminate() {
		threadFinished = true;
	}

	/**
	 * Run method for the Player Thread.
	 */
	@Override
	public void run() {
		while(!threadFinished)	{
			PlayerMessage message = new PlayerMessage(this, playerid, shape); //creates new instance of message which sends the class player id and shape
			try {
				for(int i = 0; i < NTURNS; NTURNS--) {	//iterates through NTURNS
					message.setShape(Shape.Random()); //sets a random shape in the message object 
					referee.addtoQueue(message); //adds the message object to the queue in the referee
					Thread.sleep(10); //causes current thread to pause for 10ms
				}

				incrementResult(); //increments the result
				turnsLeft--; //decrements total amount of turns left 

				if(turnsLeft == 0){ //checks if no turns are left
					Statistics.setStatistics(message.getPlayer().getId(), win, lose, draw);
					System.out.println("\t\t\t\t\t\t\t\tPlayer "  + message.getPlayer().getId() +  " thread stopped " + "(" + win + " Wins - " + draw + " Draws - " + lose + " losses)\n");
					terminate(); //terminates thread gracefully
				}

			} catch (InterruptedException e) {
				System.out.println("An Interrupted Exception has occured!");
				e.printStackTrace();
			}
		}
	}
}
