package threads;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import enums.GameResult;
import resources.PlayerMessage;
import resources.Result;

/**
 * The Referee Class maintains a message queue in which there are players waiting to play. 
 * Once there are two players ready the referee selects these players and let them show their hands. 
 * The referee judges the winner of the game and notify each player independently of the result. 
 * The referee prints out the outcome of the match on the terminal and then select the next two players, and so on. 
 * When the game is over the main program signals the referee thread to stop and, in response, the referee thread terminates.
 */
public class Referee implements Runnable {

	private static BlockingQueue<PlayerMessage> messageQueue = new LinkedBlockingQueue<>(); //uses thread safe queue to store PlayerMessages
	private PlayerMessage playerOne, playerTwo; //two temporary players to populate when taking from the queue
	private volatile boolean threadFinished = false; //variable used to shutdown thread
	private int gameResult; //used to store the game result

	/**
	 * Default constructor of a referee.
	 */
	public Referee (){} 

	/**
	 * Method to add a PlayerMessage object to the queue.
	 * @param pm PlayerMessage object
	 */
	public void addtoQueue(PlayerMessage pm){
		messageQueue.add(pm);
	}

	/**
	 * Method to terminate thread gracefully.
	 */
	public void terminate() {
		threadFinished = true;
		System.out.println("---------------------------------------------------------------------------");
		System.out.println();
		System.out.println("Referee thread stopped, thanks for playing...");
		System.out.println();
		System.out.println("ROCK                    PAPER                           SCISSORS!!");
		System.out.println("    _______                 _______                    _______");
		System.out.println("---'   ____)            ---'   ____)____           ---'   ____)____");
		System.out.println("      (_____)                     ______)                    ______)");
		System.out.println("      (_____)                     _______)                __________)");
		System.out.println("      (____)                    _______)                 (____)");
		System.out.println("---.__(___)             ---.__________)            ---.__(___)");
		System.out.println();
		System.out.println("----------------------------------------------------------------------------");
	}

	/**
	 * Helper method to print the game result
	 * @param gameResult gets the result of the game
	 */
	public void printGameResult(int gameResult) {
		if(gameResult == 1) {
			System.out.println("Player " + playerOne.getPlayer().getId() + " [" + playerOne.getShape().toString() + "] vs Player " + playerTwo.getPlayer().getId() + " [" + 
					playerTwo.getShape().toString() + "] ===> Player " + playerTwo.getPlayer().getId() + " wins\n");
		} else if(gameResult == 2) {
			System.out.println("Player " + playerOne.getPlayer().getId() + " [" + playerOne.getShape().toString() + "] vs Player " + playerTwo.getPlayer().getId() + " [" +
					playerTwo.getShape().toString() + "] ===> Player " + playerOne.getPlayer().getId() + " wins\n");
		} else {
			System.out.println("Player " + playerOne.getPlayer().getId() + " [" + playerOne.getShape().toString() + "] vs Player " + playerTwo.getPlayer().getId() + " [" +
					playerTwo.getShape().toString() + "] ===> Its a draw!\n");
		}

	}

	/**
	 * Run method for the Player Thread.
	 */
	@Override
	public void run() {	
		while(!threadFinished) { //checks if thread is terminated
			synchronized(messageQueue) { //keeps the messageQueue synchronized 
				try{
					//remove two player messages from the queue
					playerOne = messageQueue.take();
					playerTwo = messageQueue.take();

					//result is calculated in result class using the shape from each player message removed from the queue 
					gameResult = Result.getResult(playerOne.getShape(), playerTwo.getShape());

					//switch statement used to print the game result and send an MVar of the outcome to the player
					switch(gameResult) {
					case 0: //Player One: Draw | Player Two: Draw
						printGameResult(0);
						playerOne.getPlayer().getMVar().putMVar(GameResult.DRAW); //puts the result into the Player One MVar
						playerTwo.getPlayer().getMVar().putMVar(GameResult.DRAW); //puts the result into the Player Two MVar
						break;

					case 1: //Player One: Lose | Player Two: Win
						printGameResult(1);
						playerOne.getPlayer().getMVar().putMVar(GameResult.LOSE); //puts the result into the Player One MVar
						playerTwo.getPlayer().getMVar().putMVar(GameResult.WIN); 
						break;

					case 2: //Player One: Win | Player Two: Lose
						printGameResult(2);
						playerOne.getPlayer().getMVar().putMVar(GameResult.WIN); //puts the result into the Player One MVar
						playerTwo.getPlayer().getMVar().putMVar(GameResult.LOSE); //puts the result into the Player Two MVar
						break;
					}
					Thread.sleep(10); //causes current thread to pause for 10ms
				} catch (InterruptedException e) {
					e.printStackTrace();

				}
			}
		}
	}
}
