package gameoflife.test;

import java.io.IOException;

import gameoflife.Board;

public class PrintNumberOfNeighbors {

	/**
	 * Test for number of neighbors
	 * Reads a board from standard input and prints the number of neighbors for each cell to standard output
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		Board board = Board.readFromInputStream(System.in);
		
		for (int i = 0; i < board.numberOfRows(); i++) {
			for (int j = 0; j < board.rowSize(i); j++) {
				System.out.print(board.numberOfNeighbors(i, j));
				
				if (j < board.rowSize(i) - 1) {
					System.out.print(' ');
				}
			}
			
			System.out.println();
		}
	}

}
