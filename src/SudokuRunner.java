import javax.swing.JFrame;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Scanner;
/**
 * Class which allows user to play the game using the Sudoku class
 * @author Brandon Chupp
 */
public class SudokuRunner {

	public static void main(String[] args){
		// Constructs a new Sudoku board with default board layout
		Sudoku mySudoku = new Sudoku(true);			
		
		// Creates a new JFrame
		JFrame frame = new JFrame();
		frame.setSize(800,600);
		frame.setTitle("Sudoku Gameboard");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(mySudoku);
		frame.setVisible(true);
		
		// Scanner allows interaction with user
		Scanner userInput = new Scanner(System.in);
		System.out.println("Welcome to the game of Sudoku!");
		System.out.print("\nHere is your gameboard:\n\n");
		mySudoku.printMySudoku();
		
		boolean value = false;
		boolean remove = false; // Used to store the remove user decision
		int rowValue = 0;
		int columnValue = 0;
		int valueValue = 0;
		int in = 0;
		
		//Main body of program that accepts user input
		while (true)
		{
			//Do-while loops allow for incorrect user input
			do
			{
				System.out.println("\n------Main Menu------");
				System.out.println("\nType 1 to insert a value.");
				System.out.println("Type 2 to import a game file.");
				System.out.println("Type 3 to save your game file.");
				while (!userInput.hasNextInt()) 
				{
		            System.out.println("Invalid Input! Try again: ");
		            userInput.nextLine();
				}
				in = userInput.nextInt();
				if (in > 3 || in < 1)
				{
					System.out.println("Invalid Input! Try again: ");
		            userInput.nextLine();
				}
			}
			while (in > 3 || in < 1);
			
			// Allows user to insert a value
			if (in == 1)
			{
				do {
			        System.out.println("Please enter the row you would like: -\n(Integer from 1 to 9): ");
			        while (!userInput.hasNextInt()) {
			            System.out.println("Invalid Input! Try again: ");
			            userInput.next();
			        }
			        rowValue = userInput.nextInt();
			    } while (rowValue > mySudoku.ROWS || rowValue < 1);
			    
				do {
			        System.out.println("Please enter the column you would like: -\n(Integer from 1 to 9): ");
			        while (!userInput.hasNextInt()) {
			            System.out.println("Invalid Input! Try again: ");
			            userInput.next();
			        }
			        columnValue = userInput.nextInt();
			    } while (columnValue > mySudoku.COLUMNS || columnValue < 1);

			    System.out.println("Please enter the value you would to insert.");
			    System.out.println("Note: to remove a value, enter \"remove\" here. \n(Integer from 1 to 9 or \"remove\"): ");   
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
						if (valueValue > mySudoku.ROWS || valueValue < 1) value = false;
					}
					
					if (value == false)
					{
						System.out.println("\nInvalid input!");
						System.out.println("Enter the value you would like: ");
						userInput.next();
						
					}
				}
				//Received input, now implement it
				if (remove == false)
				{
					System.out.println(mySudoku.acceptInput(rowValue, columnValue, valueValue));
				}
				else
				{
					mySudoku.removeVal(rowValue, columnValue, valueValue);
					remove = false;
				}
				mySudoku.printMySudoku();
				
				// Refresh JFrame graphics
				frame.setVisible(false);
				frame.setVisible(true);
				
				value = false;
			}
			
			// Allows user input file
			if (in == 2)
			{
				boolean fileValid = false;
				boolean fileData = true;
				while (!fileValid) {
					File fileNameInput;
					Scanner fileInput = null;
					try {
						System.out.println("Please enter the name of the file: ");
						String fileName = userInput.next();
						fileNameInput = new File(fileName);
						fileInput = new Scanner(fileNameInput);
						try {
							String input = fileInput.next();
							fileData = mySudoku.setBoard(input);
							fileInput.close();
							fileValid = true;
						} 
						catch (NoSuchElementException e) 
						{
							System.out.println("That file does not contain any data. Please check the file and try again.");
						}
					} 
					catch (FileNotFoundException exception) 
					{
						System.out.println("File not found. Please try again.");
					}
					if (fileData == false) fileValid = false;
				}
			}
			
			// Allows user to save game
			if (in == 3)
			{
				//Read current time to name saved game file
				String time = new SimpleDateFormat("MM.dd.yy_HH.mm_").format(new Date());
				try {
					PrintWriter output = new PrintWriter(time + "savedSudoku.txt");
					try {
						output.print(mySudoku.saveBoard(time));
					}
					finally
					{
						output.close();
					}
				}
				catch (IOException e) {
					System.out.println("File not saved.");
					e.printStackTrace();
				}
			}
			
		}//loop	
	}//main
}