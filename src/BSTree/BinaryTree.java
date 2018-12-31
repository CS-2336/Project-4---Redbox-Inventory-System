/*
 	Name: Naman Gangwani
 	NetID: nkg160030
 */

package BSTree;

public class BinaryTree {
	public Node root; // Node pointer for the binary tree's root
	private int longestTitle = 0; // Keeps track of the longest title length-wise for output formatting
	
	// Getter method for the binary tree's root
	public Node getRoot()
	{ return root; }
	
	// Setter method for the binary tree's root
	public void setRoot(Node root)
	{ this.root = root; }
	
	/** Find node based on the passed in title; returns null if not found**/
	public Node find(String title)
	{ 
		if (root != null)
			return find(title.toLowerCase(), root); // Calls to recursive helper method
		return null; // Returns null if there's no root
	}
	
	/** Recursive helper method to find node based on the passed in title and current node it's evaluating; returns null if not found**/
	private Node find(String title, Node currentNode)
	{
		if (title.compareTo(currentNode.title.toLowerCase()) < 0) // If the title comes before the current node't title alphabetically
		{
			if (currentNode.left != null) // If there are more nodes to search
				return find(title, currentNode.left); // Keep searching left
			else
				return null; // Don't search anymore if nothing is left to search
		}
		else if (title.compareTo(currentNode.title.toLowerCase()) > 0) // If the title comes after the current node't title alphabetically
		{
			if (currentNode.right != null) // If there are more nodes to search
				return find(title, currentNode.right); // Keep searching right
			else
				return null; // Don't search anymore if nothing is left to search
		}
		else
			return currentNode; // Returns the current since there is a match
	}
	
	/** Adds a node based on the alphabetical position of its title into the tree **/
	public void add(Node n)
	{ 
		if (n.title.length() > longestTitle) // If the title is longer than the current longest title
			longestTitle = n.title.length(); // Make it the current longest title
		add(n, root);
	}
	
	/** Recursive helper method to add a node based on the alphabetical position of its title into the tree **/
	private void add(Node n, Node currentNode)
	{
		if (root == null || (root != null && root.numAvailable == -1)) // If the root doesn't exist
			root = n; // Set the passed in node to the root
		else
		{
			if (n.title.toLowerCase().compareTo(currentNode.title.toLowerCase()) < 0) // If its title is before than the current node's title alphabetically
			{
				if (currentNode.left == null) // If the current node doesn't have any left children to search
					currentNode.left = n; // Set its left child to the node
				else
					add(n, currentNode.left); // Continue searching left
			} else
			{
				if (currentNode.right == null) // If the current node does not have any right children to search
					currentNode.right = n; // Set its right child to the node
				else
					add(n, currentNode.right); // Continue searching right
			}
		}
	}
	
	/** Method to delete a passed in node from the tree **/
	public void delete(Node n)
	{ delete(n, root); }
	
	/** Recursive helper method to delete a passed in node from the tree **/
	public void delete(Node n, Node currentNode)
	{
		// If it comes before the current node alphabetically and the current node has more to search through its left children
		if (n.title.toLowerCase().compareTo(currentNode.title.toLowerCase()) < 0 && currentNode.left != null)
			delete(n, currentNode.left); // Keep searching left
		// Otherwise, If it comes after the current node alphabetically and the current node has more to search through its right children
		else if (n.title.toLowerCase().compareTo(currentNode.title.toLowerCase()) > 0 && currentNode.right != null)
			delete(n, currentNode.right); // Keep searching right
		else // Otherwise, a match is found
		{
			Node cur = currentNode;
			if (currentNode.title.toLowerCase().compareTo(root.title.toLowerCase()) <= 0 && cur.left != null) // If it is or is on the left side of the root
			{
				// Search for the biggest node alphabetically to the left of the node
				cur = cur.left;
				while (cur.right != null)
					cur = cur.right;
			}
			else if (cur.right != null) // If it's on the right side of the root
			{
				// Search for the biggest node alphabetically to the right of this node
				cur = cur.right;
				while (cur.left != null)
					cur = cur.left;
			}
			
			// Copies next biggest found node's contents to the node being "deleted"
			currentNode.title = cur.title;
			currentNode.numAvailable = cur.numAvailable;
			currentNode.numRented = cur.numRented;
			
			cur.numAvailable = -1; // Renders the node just copied from useless
		}
	}
	
	/** Method to return a string representation of the binary tree alphabetically **/
	@Override
	public String toString()
	{
		if (root != null)
			return toString(root);
		return "";
	}
	
	/** Recursive helper method to return a string representation of the binary tree alphabetically with an in-order traversal **/
	private String toString(Node cur)
	{
		String value = "";
		if (cur.left != null)
			value+=toString(cur.left); // Search to the left of the node
		if (cur.numAvailable != -1) // If it's not a duplicate node
			value+=String.format("%-"+(longestTitle + 3)+"s \t %-3d \t\t %d\n", cur.title, cur.numAvailable, cur.numRented);
		if (cur.right != null)
			value+=toString(cur.right); // Search to the right of the node
    	return value;
	}
}
