/*
 	Name: Naman Gangwani
 	NetID: nkg160030
 */

package BSTree;

public class Node {
	public Node left, right; // Node pointers for left and right children
	String title; // Holds movie's title
	int numAvailable, numRented; // Holds movie's number of copies available and rented
	
	/** Overloaded constructor with title, number of available copies, and number of rented copies **/
	public Node(String title, int numAvailable, int numRented)
	{
		this.title = title;
		this.numAvailable = numAvailable;
		this.numRented = numRented;
	}
	
	// Getter method for left node pointer
	public Node getLeft()
	{ return left; }
	
	// Getter method for right node pointer
	public Node getRight()
	{ return right; }
	
	// Getter method for title
	public String getTitle()
	{ return title; }
	
	// Getter method for number of available copies
	public int getNumAvaialable()
	{ return numAvailable; }
	
	// Getter method for number of rented copies
	public int getNumRented()
	{ return numRented; }
	
	// Setter method for left node pointer
	public void setLeft(Node left)
	{ this.left = left; }
	
	// Setter method for right node pointer
	public void setRight(Node right)
	{ this.right = right; }
	
	// Setter method for title
	public void setTitle(String title)
	{ this.title = title; }
	
	// Setter method for number of available copies
	public void setNumAvaialable(int numAvailable)
	{ this.numAvailable = numAvailable; }
	
	// Setter method for number of rented copies
	public void setNumRented(int numRented)
	{ this.numRented = numRented; }
}
