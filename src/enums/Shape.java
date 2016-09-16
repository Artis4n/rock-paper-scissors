package enums;

import java.util.Random;

/**
 * This class sets up an enum of Shape
 * which contains ROCK/PAPER/SCISSORS
 */
public enum Shape {
	ROCK, PAPER, SCISSORS;

	/**
	 * Chooses a random shape enum 
	 * based on the length of enums
	 * @return random shape of rock/paper/scissors
	 */
	public static Shape Random(){
		Shape[] s = Shape.values();
		Random selection = new Random();
		return s[selection.nextInt(s.length)];
	}
}

