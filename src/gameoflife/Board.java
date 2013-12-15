package gameoflife;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

/**
 * Represents a board of cells in the "game of life"
 * 
 * I have made no assumptions of uniform row size.  For example, these are both valid boards:
 * 
 * 0 1 1
 * 1 0 0
 * 0 0 0
 * 
 * 0 1 1 1 1
 * 0 1 0
 * 1 0 1 1
 * 0 0
 *  
 * @author Daniel Burton
 */
public class Board {
	/**
	 * Vectors for the eight possible directions of adjacent cells
	 */
	private static final int[][] DIRECTION_VECTORS =
		{{-1, 0}, {1, 0}, {0, -1}, {0, 1}, {-1, -1}, {-1, 1}, {1, -1}, {1, 1}};

	ArrayList<ArrayList<Boolean>> isAlive = new ArrayList<ArrayList<Boolean>>();
	
	/**
	 * Reads a board from an input stream
	 * 
	 * For example:
	 * 
	 * 0 1 0
	 * 1 0 0
	 * 0 0 1
	 * 
	 * @param in
	 * @return The new board
	 * @throws IOException 
	 */
	public static Board readFromInputStream(InputStream in) throws IOException {
		Board board = new Board();
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		String input;
		
		while ((input = reader.readLine()) != null) {
			Scanner scanner = new Scanner(input);
			ArrayList<Boolean> row = new ArrayList<Boolean>();
			
			// Read in and process row of input
			while (scanner.hasNextInt()) {
				int isAlive = scanner.nextInt();
				
				if (isAlive < 0 || isAlive > 1) {
					throw new RuntimeException("Invalid value: " + isAlive);
				}
				
				row.add(isAlive == 1);
			}
			
			scanner.close();
			
			// If we found a new row, add it to the list
			if (row.size() > 0) {
				board.isAlive.add(row);
			}
		}
		
		reader.close();
		
		return board;
	}

	/**
	 * Calculate the next generation
	 * 
	 * @return A new board
	 */
	public Board calculateNextGeneration() {
		Board nextGeneration = new Board();
		
		for (int i = 0; i < isAlive.size(); i++) {
			ArrayList<Boolean> row = isAlive.get(i);
			ArrayList<Boolean> newRow = new ArrayList<Boolean>();
			
			for (int j = 0; j < row.size(); j++) {
				int numberOfNeighbors = numberOfNeighbors(i, j);

				if (isAlive(i, j)) {
					// Living cell
					if (numberOfNeighbors < 2 || numberOfNeighbors > 3) {
						// Any cell with less than two or greater than three neighbors dies
						newRow.add(false);
					} else {
						newRow.add(true);
					}
				} else {
					// Dead cell
					if (numberOfNeighbors == 3) {
						// Reproduction
						newRow.add(true);
					} else {
						newRow.add(false);
					}
				}
			}
			
			nextGeneration.isAlive.add(newRow);
		}
		
		return nextGeneration;
	}

	/**
	 * Returns true if the cell at (row,column) is alive
	 * 
	 * @param row
	 * @param column
	 * @return
	 */
	public boolean isAlive(int row, int column) {
		if (!withinBounds(row, column)) {
			throw new RuntimeException("Out of bounds index: (" + row + "," + column + ")");
		}
		
		return isAlive.get(row).get(column);
	}

	public int numberOfNeighbors(int row, int column) {
		int count = 0;
		
		for (int[] vector : DIRECTION_VECTORS) {
			int y = vector[0];
			int x = vector[1];
			
			if (withinBounds(row + y, column + x) && isAlive(row + y, column + x)) {
				count++;
			}
		}
		
		return count;
	}

	/**
	 * True if the position (row,column) is within the bounds of the board
	 * 
	 * @param row
	 * @param column
	 * @return
	 */
	public boolean withinBounds(int row, int column) {
		if (row < 0 || row >= isAlive.size()) {
			return false;
		}
		
		if (column < 0 || column >= isAlive.get(row).size()) {
			return false;
		}
		
		return true;
	}

	/**
	 * Writes the board to an output stream
	 * 
	 * For example:
	 *  
	 * 0 1 0
	 * 1 0 0
	 * 0 0 1
	 * 
	 * @param out
	 */
	public void write(PrintStream out) {
		for (ArrayList<Boolean> row : isAlive) {
			Iterator<Boolean> rowIterator= row.iterator();
			
			while (rowIterator.hasNext()) {
				if (rowIterator.next()) {
					out.print('1');
				} else {
					out.print('0');
				}
				
				if (rowIterator.hasNext()) {
					out.print(' ');
				}
			}
			
			out.println();
		}
	}

	public int numberOfRows() {
		return isAlive.size();
	}

	public int rowSize(int row) {
		return isAlive.get(row).size();
	}

}
