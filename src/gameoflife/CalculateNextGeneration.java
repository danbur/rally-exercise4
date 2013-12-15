package gameoflife;

import java.io.IOException;

/**
 * Main routine
 * Reads the board from standard input, calculates the next generation and prints it to standard output
 * 
 * @author daniel
 *
 */
public class CalculateNextGeneration {

	public static void main(String[] args) throws IOException {
		Board board = Board.readFromInputStream(System.in);
		Board nextGeneration = board.calculateNextGeneration();
		nextGeneration.write(System.out);
	}

}
