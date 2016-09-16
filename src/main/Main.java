package main;

import java.util.Scanner;

import resources.Statistics;
import threads.Player;
import threads.Referee;

/**
 * The Rock Paper Scissors application that simulates a number of players (NPLAYERS) taking turns to play rock-scissors-paper. 
 * Each player thread will take NTURNS turns. Thus, if NPLAYERS=10 and NTURNS=100 then each player will play 100 times and 10 players take part. 
 * The games will be overseen by a referee thread that will select two players at a time and then arbitrate as they play the game.
 * 
 * This Main class should set up the referee and NPLAYERS players and set them running as concurrent threads. 
 * Once all of the players have finished the main program should signal to the referee that it is time to shut itself down.
 */
public class Main {

	private static int NPLAYERS = 10; //number of players in the game set to 10
	private static int NTURNS = 100; //number of turns in the game set to 100

	/**
	 * Method used to get the number of turns.
	 * @return number of turns specified 
	 */
	public static int getNTURNS() {
		return NTURNS;
	}

	/**
	 * The initial entry point for this program.
	 * @param args The arguments passed to the program.
	 */
	public static void main (String[] args) {
		Scanner sc = new Scanner(System.in); //new scanner is created to get user inputs

		System.out.print("Enter amount of players: ");
		while (!sc.hasNextInt()) sc.next(); //checks if the input is an integer
		NPLAYERS = sc.nextInt(); //NPLAYERS is set to user input

		System.out.print("Enter amount of turns: ");
		while (!sc.hasNextInt()) sc.next(); //checks if the input is an integer 
		NTURNS = sc.nextInt(); //NTURNS is set to user input
		sc.close();//scanner is closed

		Referee referee = new Referee(); //creates new instance of referee
		Thread[] playerThread = new Thread[NPLAYERS]; //creates an array of playerThreads of size NPLAYERS
		Thread refereeThread = new Thread(referee); //creates a referee thread using referee object
		refereeThread.start(); //referee thread is started

		for(int i = 0; i<NPLAYERS; i++){ //iterates through NPLAYERS times
			playerThread[i] = new Thread(new Player(i+1, referee, NTURNS));	//each player thread is given a new player object which contains the player id/referee/nturns as parameters
			playerThread[i].start(); //player thread is started

		}

		try {
			for(int i = 0; i<NPLAYERS; i++)	 //iterates through NPLAYERS times
				playerThread[i].join();	//each player thread is joined
		} catch(InterruptedException e) {
			e.printStackTrace();
		}

		referee.terminate(); //referee is called to terminate gracefully once all tasks complete
		Statistics.saveStatistics(); //saves the games statistics
	}
}


