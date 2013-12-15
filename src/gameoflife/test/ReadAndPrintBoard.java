package gameoflife.test;

import java.io.IOException;

import gameoflife.Board;

/**
 * Test of reading and printing the board
 * Reads the board from standard input and then prints it out to standard output
 * 
 * @author Daniel Burton
 *
 */
public class ReadAndPrintBoard {

	public static void main(String[] args) throws IOException {
		Board board = Board.readFromInputStream(System.in);
		board.write(System.out);
	}

}
