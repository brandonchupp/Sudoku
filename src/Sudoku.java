import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.*;
import javax.swing.JComponent;
import java.awt.BasicStroke;

/**
 * Class Sudoku which allows the creation of a Sudoku board
 * @author Brandon Chupp
 */
public class Sudoku extends JComponent{
	//Private instance variables for the numbers of rows, columns, and graphical offset factors
	public final int ROWS = 9;
	public final int COLUMNS = 9;
	public final int BOXSIZE = 3;
	private final int X_SHIFTFACTOR = 80;
	private final int Y_SHIFTFACTOR = 60;
	//Initializes the Sudoku game board array
	private int[][] gameBoard = new int[ROWS][COLUMNS];
	private boolean[][] startingBoard = new boolean[ROWS][COLUMNS]; 
	
	/** Constructs a empty board
	 */
	public Sudoku()
	{
		for (int r = 0; r < ROWS; r++)
		{
			for (int c = 0; c < COLUMNS; c++)
			{
				gameBoard[r][c] = 0;
				startingBoard[r][c] = true;
			}
		}
	}
	
	/**
	 * Constructs a board with the default board layout
	 * @param state if true, default board is initiated
	 */
	public Sudoku(boolean state)
	{
		gameBoard[0][0] = 5;
		gameBoard[1][0] = 6;
		gameBoard[3][0] = 8;
		gameBoard[4][0] = 4;
		gameBoard[5][0] = 7;
		gameBoard[0][1] = 3;
		gameBoard[2][1] = 9;
		gameBoard[6][1] = 6;
		gameBoard[2][2] = 8;
		gameBoard[1][3] = 1;
		gameBoard[4][3] = 8;
		gameBoard[7][3] = 4;
		gameBoard[0][4] = 7;
		gameBoard[1][4] = 9;
		gameBoard[3][4] = 6;
		gameBoard[5][4] = 2;
		gameBoard[7][4] = 1;
		gameBoard[8][4] = 8;
		gameBoard[1][5] = 5;
		gameBoard[4][5] = 3;
		gameBoard[7][5] = 9;
		gameBoard[6][6] = 2;
		gameBoard[2][7] = 6;
		gameBoard[6][7] = 8;
		gameBoard[8][7] = 7;
		gameBoard[3][8] = 3;
		gameBoard[4][8] = 1;
		gameBoard[5][8] = 6;
		gameBoard[7][8] = 5;
		gameBoard[8][8] = 9;
		initializeBoard();
	}
	
	/** Draws the game board graphically in a JFrame
	 * @param g The graphics component passed to paintComponent
	 */
	public void paintComponent(Graphics g)
	{
		//Recover Graphics2D
		Graphics2D g2 = (Graphics2D) g;
		
		// Draws the gameBoard
		for (int i = 0; i < ROWS; i++)
		{
			for (int j = 0; j < COLUMNS; j++)
			{
				g2.setColor(Color.BLACK);
				if (gameBoard[i][j] == 0) g2.drawString(" ", j * X_SHIFTFACTOR + 50, i * Y_SHIFTFACTOR + 50);
				else g2.drawString(String.valueOf(gameBoard[i][j]), j * X_SHIFTFACTOR + 50, i * Y_SHIFTFACTOR + 50);
			}
		}
		// Draws the grid lines
		int rowCounter = 1;
		g2.setColor(Color.BLUE);
		g2.setStroke(new BasicStroke(3));
		Line2D.Double horLine1 = new Line2D.Double(0, 25, 735, 25);
		g2.draw(horLine1);
		for (int r = 0; r < ROWS; r++)
		{
			g2.setColor(Color.BLUE);
			g2.setStroke(new BasicStroke(1));
			if (rowCounter == BOXSIZE)
			{
				g2.setStroke(new BasicStroke(3));
				rowCounter = 0;
			}
			Line2D.Double horLine = new Line2D.Double(0, r * Y_SHIFTFACTOR + 80, 735, r * Y_SHIFTFACTOR + 80);
			rowCounter++;
			g2.draw(horLine);
			
		}
		int colCounter = 1;
		g2.setColor(Color.BLUE);
		g2.setStroke(new BasicStroke(3));
		Line2D.Double vertLine1 = new Line2D.Double(30, 0, 30, 560);
		g2.draw(vertLine1);
		for (int c = 0; c < COLUMNS; c++)
		{
			g2.setColor(Color.BLUE);
			g2.setStroke(new BasicStroke(1));
			if (colCounter == BOXSIZE)
			{
				g2.setStroke(new BasicStroke(3));
				colCounter = 0;
			}
			Line2D.Double vertLine = new Line2D.Double(c * X_SHIFTFACTOR + 95, 0, c * X_SHIFTFACTOR + 95, 560);
			colCounter++;
			g2.draw(vertLine);
			
		}
		
		// Draws column numbers
		for (int r = 0; r < ROWS; r++)
		{
			g2.setColor(Color.RED);
			g2.drawString(String.valueOf(1 + r), 10, r * Y_SHIFTFACTOR + 50);
		}
		// Draws column names
		for (int c = 0; c < COLUMNS; c++)
		{
			g2.setColor(Color.RED);
			g2.drawString(String.valueOf(1 + c), c * X_SHIFTFACTOR + 50, 15);
		}
	}
	
	/**
	 * Checks to see if the supplied input is a valid number
	 * @param r the row where the value is to be inserted
	 * @param c the column where the value is to be inserted
	 * @param val the value to be inserted
	 * @return true if the number is valid and false if it is not
	 */
	private boolean checkInput(int r, int c, int val)
	{
		if (r >= 1 && r <= 9)
		{
			if (c >= 1 && c <= 9)
			{
				if (val >= 1 && val <= 9) return true;
			}
		}
		return false;
	}
	
	/**
	 * Inserts a value into a specific coordinate of the array and returns a boolean if the value works in that row, column, and box
	 * @param r the row where the value is to be inserted
	 * @param c the column where the value is to be inserted
	 * @param val the value to be inserted
	 * @return true if the value works and false if it does not
	 */
	public boolean insertVal(int r, int c, int val)
	{
		if (checkInput(r, c, val) == false || protectInitalValues(r, c) == false) return false;
		
		boolean row = this.checkRow(r, c, val);
		boolean column = this.checkCol(r, c, val);
		boolean box = this.checkBox(r, c, val);
		
		if (row == true && column == true && box == true) 
		{
			gameBoard[r - 1][c - 1] = val;
			return true;
		}
		else return false;
	}
	
	/**
	 * Removes a value from a specific coordinate of the array and returns a boolean
	 * @param r the row where the value is to be removed
	 * @param c the column where the value is to be removed
	 * @param val the value to be removed
	 * @return true if the value is at the specified coordinate and false if it is not
	 */
	public boolean removeVal(int r, int c, int val)
	{
		if (checkInput(r, c, val) == false || protectInitalValues(r, c) == false) return false;
		if (gameBoard[r - 1][c - 1] == val)
		{
			gameBoard[r - 1][c - 1] = 0;
			return true;
		}
		else return false;
	}
	
	/**
	 * Checks the value in a row to see if it is okay to insert it
	 * @param r the row to be checked
	 * @param c the column //not used
	 * @param val the value to be checked
	 * @return returns true if the value will work and false if not
	 */
	private boolean checkRow(int r, int c, int val)
	{
		boolean rowCheck = true;
		for (int i = 0; i < COLUMNS; i++)
		{
			if (gameBoard[r - 1][i] == val) rowCheck = false;
		}
		if (rowCheck == false) System.out.println("Looks like you need to fix row " + r + ".");
		else System.out.println("Row " + r + " looks great!");
		return rowCheck;
	}
	
	/**
	 * Checks the value in a column to see if it is okay to insert it
	 * @param r the row //not used
	 * @param c the column to be checked
	 * @param val the value to be checked
	 * @return returns true if the value will work and false if not
	 */
	private boolean checkCol(int r, int c, int val)
	{
		boolean columnCheck = true;
		for (int i = 0; i < ROWS; i++)
		{
			if (gameBoard[i][c - 1] == val) columnCheck = false;
		}
		if (columnCheck == false) System.out.println("Looks like you need to fix column " + c +".");
		else System.out.println("Column " + c + " looks great!");
		return columnCheck;
	}
	
	/**
	 * Checks the value in a box to see if it is okay to insert it
	 * @param r the row to be checked
	 * @param c the column to be checked
	 * @param val the value to be checked
	 * @return returns true if the value will work and false if not
	 */
	private boolean checkBox(int r, int c, int val)
	{
		int rowCheck = (r - 1) / BOXSIZE + (BOXSIZE - 1) * ((r - 1) / BOXSIZE + 1);
		int columnCheck = (c - 1) / BOXSIZE + (BOXSIZE - 1) * ((c - 1) / BOXSIZE + 1);
		boolean boxCheck = true;
		
		for (int i = rowCheck - (BOXSIZE - 1); i <= rowCheck; i++)
		{
			for (int j = columnCheck - (BOXSIZE - 1); j <= columnCheck; j++)
			{
				if (gameBoard[i][j] == val) boxCheck = false;
			}
		}
		
		if (boxCheck == false) System.out.println("Looks like you need to fix that box. ");
		else System.out.println("That box looks great!");
		return boxCheck;
	}
	
	/**
	 * The method to pass user inputs to which returns a string of output for the user
	 * @param r the row that the user inputs
	 * @param c the column that the user inputs
	 * @param val the value that the user inputs
	 * @return a string that can be printed to the console to provide user feedback
	 */
	public String acceptInput(int r, int c, int val)
	{
		boolean result = this.insertVal(r, c, val);
		if (result) 
		{
			return "\nYou changed (" + c + "," + r + ") to " + val + ".\n";
		}
		else return "That is not a valid value for that location. Failed to enter value.";
	}
	
	/**
	 * Prints a string representation of the array to the console
	 */
	public void printMySudoku()
	{
		int columnBreakCounter = 0;
		int rowBreakCounter = 0;
		String result = "";
		for (int r = 0; r < ROWS; r++)
		{
			for (int c = 0; c < COLUMNS; c++)
			{
				if (gameBoard[r][c] == 0) result += "| ";
				else result += "|" + gameBoard[r][c];
				columnBreakCounter++;
				if (c == 8) columnBreakCounter = 0;
				if (columnBreakCounter == 3)
				{
					result += "|   ";
					columnBreakCounter = 0;
				}
			}
			result += "|\n";
			rowBreakCounter++;
			if (r == 8) rowBreakCounter = 0;
			if (rowBreakCounter == BOXSIZE)
			{
				result += "\n";
				rowBreakCounter = 0;
			}
			
		}
		System.out.println(result);
	}
	
	/**
	 * Allows user to set initial gameboard
	 * @param input a string of values separated by commas
	 * @return true if file was set as board and false if there was a problem
	 */
	public boolean setBoard(String input)
	{
		String result = "";
		for (String individual: input.split(","))
		{
			if (individual.equals("")) result += "0";
	        else result += individual;
		}
		while (result.length() < (ROWS * COLUMNS))
		{
			result += "0";
		}

		int counter = 0;
		for (int r = 0; r < ROWS; r++)
		{
			for (int c = 0; c < COLUMNS; c++)
			{
				try {
					int value = Character.getNumericValue(result.charAt(counter));
					if (value > ROWS || value < 0) throw new IllegalArgumentException("IllegalArgumentException: That game board file is invalid!");
					else gameBoard[r][c] = value;
					counter++;
				} 
				catch (IllegalArgumentException e) 
				{
					System.out.println(e.getMessage());
					System.out.println("Please check your file data format and try again.");
					return false;
				}
			}
		}
		System.out.println("\nSuccessfully imported game board:\n");
		initializeBoard();
		printMySudoku();
		return true;
	}
	
	/**
	 * Allows the user to convert the current game board to a game save string
	 * @param time the current date and time
	 * @return a string in the save game format
	 */
	public String saveBoard(String time)
	{
		String result = "";
		for (int r = 0; r < ROWS; r++)
		{
			for (int c = 0; c < COLUMNS; c++)
			{
				if (gameBoard[r][c] != 0)
				{
					result += gameBoard[r][c] + ",";
				}
				else result += ",";
			}
		}
		if (Character.isDigit(result.charAt(result.length() - 2))) result = result.subSequence(0, result.length() - 1).toString();
		System.out.println("Successfully saved game board as \"" + time + "savedSudoku.txt\"");
		return result;
	}
	
	/**
	 * This method creates a boolean game board which does not allow user to change initial values
	 */
	private void initializeBoard()
	{
		for (int r = 0; r < ROWS; r++)
		{
			for (int c = 0; c < COLUMNS; c++)
			{
				if (gameBoard[r][c] == 0) startingBoard[r][c] = true;
				else startingBoard[r][c] = false;
			}
		}
	}
	
	/**
	 * This method returns false if the user tries to modify an initial game board value
	 * @param r the row to check
	 * @param c the column to check
	 * @return true if that value is not an initial value or false if it is
	 */
	private boolean protectInitalValues(int r, int c)
	{
		if (startingBoard[r-1][c-1] == false) 
		{
			System.out.println("\nUnable to modify initial values.");
			return false;
		}
		else return true;
	}
}
