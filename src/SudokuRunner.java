import javax.swing.JFrame;
import java.util.Scanner;

public class SudokuRunner {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Sudoku mySudoku = new Sudoku(true);
		
		// Creates a new JFrame
		JFrame frame = new JFrame();
		frame.setSize(800,600);
		frame.setTitle("Sudoku Gameboard");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(mySudoku);
		frame.setVisible(true);
		
		//Values for testing
		mySudoku.insertVal(1, 3, 4);
		mySudoku.insertVal(1, 4, 6);
		mySudoku.insertVal(1, 6, 8);
		mySudoku.insertVal(1, 7, 9);
		mySudoku.insertVal(1, 8, 1);
		mySudoku.insertVal(1, 9, 2);
		
		mySudoku.insertVal(2, 2, 7);
		mySudoku.insertVal(2, 3, 2);
		mySudoku.insertVal(2, 7, 3);
		mySudoku.insertVal(2, 8, 4);
		mySudoku.insertVal(2, 9, 8);
		
		mySudoku.insertVal(3, 1, 1);
		mySudoku.insertVal(3, 4, 3);
		mySudoku.insertVal(3, 5, 4);
		mySudoku.insertVal(3, 6, 2);
		mySudoku.insertVal(3, 7, 5);
		mySudoku.insertVal(3, 9, 7);
		
		mySudoku.insertVal(4, 2, 5);
		mySudoku.insertVal(4, 3, 9);
		mySudoku.insertVal(4, 4, 7);
		mySudoku.insertVal(4, 6, 1);
		mySudoku.insertVal(4, 7, 4);
		mySudoku.insertVal(4, 8, 2);
		
		mySudoku.insertVal(5, 2, 2);
		mySudoku.insertVal(5, 3, 6);
		mySudoku.insertVal(5, 5, 5);
		mySudoku.insertVal(5, 7, 7);
		mySudoku.insertVal(5, 8, 9);
		
		mySudoku.insertVal(6, 2, 1);
		mySudoku.insertVal(6, 3, 3);
		mySudoku.insertVal(6, 4, 9);
		mySudoku.insertVal(6, 6, 4);
		mySudoku.insertVal(6, 7, 8);
		mySudoku.insertVal(6, 8, 5);
		
		mySudoku.insertVal(7, 1, 9);
		mySudoku.insertVal(7, 3, 1);
		mySudoku.insertVal(7, 4, 5);
		mySudoku.insertVal(7, 5, 3);
		mySudoku.insertVal(7, 6, 7);
		mySudoku.insertVal(7, 9, 4);
		
		mySudoku.insertVal(8, 1, 2);
		mySudoku.insertVal(8, 2, 8);
		mySudoku.insertVal(8, 3, 7);
		mySudoku.insertVal(8, 7, 6);
		mySudoku.insertVal(8, 8, 3);
		
		mySudoku.insertVal(9, 1, 3);
		mySudoku.insertVal(9, 2, 4);
		mySudoku.insertVal(9, 3, 5);
		mySudoku.insertVal(9, 4, 2);
		mySudoku.insertVal(9, 6, 6);

		

		
		
		Scanner userInput = new Scanner(System.in);
		System.out.println("Welcome to the game of Sudoku!");
		System.out.print("\nHere is your gameboard:\n\n");
		
		mySudoku.printMySudoku();
		
		boolean row = false;
		boolean column = false;
		boolean value = false;
		boolean remove = false;
		int rowValue = 0;
		int columnValue = 0;
		int valueValue = 0;
		
		//Main body of program that accepts user input
		while (true)
		{
			System.out.println("\nPlease enter the row you would like -\n(Integer from 0 to 9): ");
			while (row == false) {
				if (userInput.hasNextInt()) 
				{
					rowValue = userInput.nextInt();
					row = true;
					if (rowValue > 9 || rowValue < 1) row = false;
				}
				if (row == false)
				{
					System.out.println("\nInvalid input!");
					System.out.println("Enter the row you would like: ");
					userInput.next();
					
				}
			}
			System.out.println("Second, please enter the column you would like: -\n(Integer from 0 to 9): ");
			while (column == false) {
				if (userInput.hasNextInt()) 
				{
					columnValue = userInput.nextInt();
					column = true;
					if (columnValue > 9 || columnValue < 1) column = false;
				}
				if (column == false)
				{
					System.out.println("\nInvalid input!");
					System.out.println("Enter the column you would like: ");
					userInput.next();
					
				}
			}
			System.out.println("Finally, please enter the value you would like.");
			System.out.println("Note: to remove a value, enter \"remove\" here. \n(Integer from 0 to 9 or \"remove\"): ");
			while (value == false) {
				if (userInput.hasNext("remove"))
				{
					System.out.println("Okay, please enter the value to be removed: ");
					userInput.next();
					remove = true;
				}
				if (userInput.hasNextInt()) 
				{
					valueValue = userInput.nextInt();
					value = true;
					if (valueValue > 9 || valueValue < 1) value = false;
				}
				
				if (value == false)
				{
					System.out.println("\nInvalid input!");
					System.out.println("Enter the value you would like: ");
					userInput.next();
					
				}
			}
			
			
			//Got input, now use it
			
			
			if (remove == false)
			{
				mySudoku.insertVal(rowValue, columnValue, valueValue);
			}
			else
			{
				mySudoku.removeVal(rowValue, columnValue, valueValue);
			}
			mySudoku.printMySudoku();
			frame.setVisible(false);
			frame.setVisible(true);
			
			
			row = false;
			column = false;
			value = false;
			if (mySudoku.checkWin() == true) 
			{
				System.out.println("You win!");
				userInput.close();
				break;
			}
		}
	}
}