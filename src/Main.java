/*
 	Name: Naman Gangwani
 	NetID: nkg160030
 */

import BSTree.*;
import java.util.*;
import java.io.*;

public class Main {
	public static void main(String[] args) throws FileNotFoundException
	{
		BinaryTree redbox = new BinaryTree(); // New binary tree to search
		PrintWriter errorLog = new PrintWriter(new File("error.log")); // Error log file
		
		Scanner inventory = new Scanner(new File("inventory.dat"));
		while (inventory.hasNextLine())
		{
			String[] line = inventory.nextLine().split(","); // Splits up the line to read between the commas
			// Adds a new node in the tree based on the information in the line
			redbox.add(new Node(line[0].replaceAll("\"", ""), Integer.parseInt(line[1]), Integer.parseInt(line[2])));
		}
		inventory.close();
				
		Scanner transaction = new Scanner(new File("transaction.log"));
		while (transaction.hasNextLine())
		{
			String line = transaction.nextLine();
			
			// Separates the line based in important keywords
			String manipulated = line.replaceFirst(" ", "_");
			manipulated = manipulated.replaceAll(",", "_");

			String[] words = manipulated.split("_"); // Splits to read the important keywords
			
			// Switch-case to interpret the first keyword
			switch(words[0])
			{
				case "add": // If the keyword says to add
					if (!isValidTransaction(words, 3)) // If it's not a valid line
						errorLog.println(line); // Add it to the error log
					else
					{
						String title = words[1].replaceAll("\"", "");
						Node movie = redbox.find(title);
						if (movie != null) // If the movie exists
							movie.setNumAvaialable(movie.getNumAvaialable() + Integer.parseInt(words[2])); // Increase its number of copies
						else
							redbox.add(new Node(title, Integer.parseInt(words[2]), 0)); // Create a new movie with that title
					}
					break;
				case "remove": // If the keyword says to remove
					if (!isValidTransaction(words, 3)) // If it's not a valid line
						errorLog.println(line); // Add it to the error log
					else
					{
						String title = words[1].replaceAll("\"", "");
						Node movie = redbox.find(title);
						movie.setNumAvaialable(movie.getNumAvaialable() - Integer.parseInt(words[2])); // Remove the inputed number of copies
						if (movie.getNumAvaialable() == 0 && movie.getNumRented() == 0) // If it's not rented and there are no more copies left
							redbox.delete(movie); // Remove the movie from the redbox
					}
					break;
				case "rent": // If the keyword says to rent
					if (!isValidTransaction(words, 2)) // If it's not a valid line
						errorLog.println(line); // Add it to the error log
					else
					{
						String title = words[1].replaceAll("\"", "");
						Node movie = redbox.find(title);
						movie.setNumAvaialable(movie.getNumAvaialable() - 1); // Decrease its availability by one
						movie.setNumRented(movie.getNumRented() + 1); // Increase its number of rentals by one
					}
					break;
				case "return": // If the keyword says to return
					if (!isValidTransaction(words, 2)) // If it's not a valid line
						errorLog.println(line); // Add it to the error log
					else
					{
						String title = words[1].replaceAll("\"", "");
						Node movie = redbox.find(title);
						movie.setNumAvaialable(movie.getNumAvaialable() + 1); // Increase its availability by one
						movie.setNumRented(movie.getNumRented() - 1); // Decrease its number of rentals by one
					}
					break;
				default: // If it's not a valid keyword
					errorLog.println(line); // Add it to the error log
					break;
			}
		}
		transaction.close();
		
		errorLog.close();
		
		PrintWriter report = new PrintWriter(new File("redbox_kiosk.txt"));
		report.print(redbox); // Outputs the results to a new file
		report.close();
	}
	
	/** Method that checks to see if the words in a line are valid to be processed as a transaction **/
	public static boolean isValidTransaction(String[] words, int numExpected)
	{
		if (words.length != numExpected) // If it doesn't meet the expected number of words
			return false; // It is invalid
		else
			if (words.length >= 2)
				if (words[1].charAt(0) == '\"' && words[1].charAt(words[1].length() - 1) == '\"' &&
					words[1].length() - words[1].replaceAll("\"", "").length() == 2) // If the movie title has two quotation marks in the proper places
				{
					if (words.length == 3) // If it has a third word
					{
						try
						{
							Integer checkIfInt = Integer.parseInt(words[2]);
							checkIfInt+=1;
							return true; // It is valid since the third keyword has passed the integer check
						} catch(Exception e)
						{
							return false; // The third keyword is not an integer, therefore it is not valid
						}
					}
					else
						return true; // It has already passed all the checks
				}
				else
					return false; // It is invalid if the movie title doesn't have two quotation marks
		return true;
	}
}
