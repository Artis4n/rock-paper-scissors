package resources;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

import main.Main;

/**
 * Statistics class is used to generate statistics
 * and save them to a text file.
 */
public class Statistics {

	private static int totalGamesPlayed = Main.getNTURNS(); //used to store the total amount of games played
	private static ArrayList<String> statArray = new ArrayList<String>(); //an array list of type string to store various strings

	/**
	 * A method used to add statistics to the array list.
	 * @param playerNumber is the player number
	 * @param wins is the amount of games won
	 * @param losses is the amount of games lost
	 * @param draws is the amount of draws
	 */
	public static void setStatistics(int playerNumber, int wins, int losses, int draws) {		
		int wonGames = wins;
		int drawnGames = draws;
		int lostGames = losses;

		float winPercentage = ((float) wonGames / (float) totalGamesPlayed) * 100; //percentage calculation
		String fWinPercentage = String.format("%.2f", winPercentage); //formatted to two decimal places

		String stat = "Player Number: " + playerNumber + "\nTotal Wins: " + wonGames + "\nTotal Losses:" + lostGames + "\nTotal Draws: " + drawnGames + "\nWin Percentage: " + fWinPercentage + "%\n\n";
		statArray.add(stat); //adds string to array list
	}

	/**
	 * Method used for saving statistics array 
	 * to a text file.
	 */
	public static void saveStatistics() {

		String total = "Total Games Played Per Player: " + totalGamesPlayed;
		statArray.add(total); //adds string to ArrayList

		String gameStats = Arrays.asList(statArray).toString().substring(1).replace(", ", "").replace("[", "").replace("]", ""); //removes , ] [ from the array list and assigns it to a string

		try(PrintWriter out = new PrintWriter("game-stats.txt")){  //creates new txt file to write to
			out.println(gameStats); //writes gameStats string to text file
			System.out.println("Game Statistics saved to game-stats.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
