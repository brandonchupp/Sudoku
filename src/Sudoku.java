import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.*;
import javax.swing.JComponent;
import java.awt.BasicStroke;

public class Sudoku extends JComponent{
	private static final int ROWS = 9;
	private static final int COLUMNS = 9;
	private static final int VALUE_A = 65;
	private static final int X_SHIFTFACTOR = 80;
	private static final int Y_SHIFTFACTOR = 60;
	private int[][] gameBoard = new int[ROWS][COLUMNS];
	public Sudoku()
	{
		for (int r = 0; r < ROWS; r++)
		{
			for (int c = 0; c < r; c++)
			{
				gameBoard[r][c] = 0;
			}
		}
	}
	public Sudoku(boolean standard)
	{
		if (standard == true)
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
		}
	}
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
			if (rowCounter == 3)
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
			if (colCounter == 3)
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
	public boolean insertVal(int r, int c, int val)
	{
		boolean row = this.checkRow(r, c, val);
		boolean column = this.checkCol(r, c, val);
		boolean box = this.checkBox(r, c, val);
		gameBoard[r - 1][c - 1] = val;
		if (row == true && column == true && box == true) return true;
		return false;
	}
	public boolean removeVal(int r, int c, int val)
	{
		if (gameBoard[r - 1][c - 1] == val)
		{
			gameBoard[r - 1][c - 1] = 0;
			return true;
		}
		else return false;
	}
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
	private boolean checkBox(int r, int c, int val)
	{
		int rowCheck = 0;
		boolean boxCheck = true;
		if (r >= 1 && r <= 3)
		{
			rowCheck = 2; // Check first three rows
		}
		if (r >= 4 && r <= 6)
		{
			rowCheck = 5; // Check middle three rows
		}
		if (r >= 7 && r <= 9)
		{
			rowCheck = 8; // Check last three rows
		}		
		int columnCheck = 0;
		if (c >= 1 && c <= 3)
		{
			columnCheck = 2; // Check first three columns
		}
		if (c >= 4 && c <= 6)
		{
			columnCheck = 5; // Check middle three columns
		}
		if (c >= 7 && c <= 9)
		{
			columnCheck = 8; // Check last three columns
		}
		for (int i = rowCheck - 2; i <= rowCheck; i++)
		{
			for (int j = columnCheck - 2; j <= columnCheck; j++)
			{
				if (gameBoard[i][j] == val) boxCheck = false;
			}
		}
		
		if (boxCheck == false) System.out.println("Looks like you need to fix that box. ");
		else System.out.println("That box looks great!");
		return boxCheck;
	}
	public String acceptInput(int r, int c, int val)
	{
		this.insertVal(r, c, val);
		return "You changed " + c + "-" + r + " to " + val + ".";
	}
	public boolean checkWin()
	{
		for (int i = 0; i < ROWS; i++)
		{
			for (int j = 0; j < i; j++)
			{
				for (int val = 1; val <= 9; val++)
				{
					if (this.checkRow(i, j, val) == false) return false;
					if (this.checkCol(i, j, val) == false) return false;
					if (this.checkBox(i, j, val) == false) return false;
				}
			}
		}
		return true;
	}
	public void printMySudoku()
	{
		int columnBreakCounter = 0;
		int rowBreakCounter = 0;
		String result = "";
		for (int r = 0; r < ROWS; r++)
		{
			for (int c = 0; c < COLUMNS; c++)
			{
				result += "|" + gameBoard[r][c];
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
			if (rowBreakCounter == 3)
			{
				result += "\n";
				rowBreakCounter = 0;
			}
			
		}
		System.out.println(result);
	}
}
