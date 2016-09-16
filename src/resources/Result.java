package resources;

import enums.Shape;

/**
 * The Result class is used to calculate the result of a game.
 */
public class Result {

	private static int result, lose, draw, win;

	/**
	 * Takes in two shapes and calculates a result based on the shape enums
	 * @param FirstShape 
	 * @param SecondShape 
	 * @return result of the rock paper scissors game
	 */
	public static int getResult(Shape FirstShape, Shape SecondShape) {
		draw = 0; lose = 1; win = 2;
		//FirstShape = Rock
		if(FirstShape == Shape.ROCK && SecondShape == Shape.PAPER) {
			result = lose;
		} else if(FirstShape == Shape.ROCK && SecondShape == Shape.SCISSORS) {
			result = win;
		} else if(FirstShape == Shape.ROCK && SecondShape == Shape.ROCK) {
			result = draw;
		}
		//FirstShape = Paper
		else if(FirstShape == Shape.PAPER && SecondShape == Shape.PAPER) {
			result = draw;
		} else if(FirstShape == Shape.PAPER && SecondShape == Shape.SCISSORS) {
			result = lose;
		} else if(FirstShape == Shape.PAPER && SecondShape == Shape.ROCK) {
			result = win;
		}
		//FirstShape = Scissors
		else if(FirstShape == Shape.SCISSORS && SecondShape == Shape.PAPER) {
			result = win;
		} else if(FirstShape == Shape.SCISSORS && SecondShape == Shape.SCISSORS) {
			result = draw;
		} else if(FirstShape == Shape.SCISSORS && SecondShape == Shape.ROCK) {
			result = lose;
		}
		return result;

	}
}


