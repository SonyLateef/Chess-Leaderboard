/**
 * Your code goes in this file fill in the empty methods to allow for the
 * required operations. You can add any fields or methods you want to help in
 * your implementations.
 * 
 * Class for the nodes of the AVL tree. 
 * Each node will have a left child, a right child, and a Player object, 
 * a double (the value that the node is sorted based on), 
 * balance factor for AVL balancing, number of nodes in the right subtree.
 * Known Bugs: None
 *
 * @author Sulaiman Lateef
 * sulaimanlateef@brandeis.edu
 * November 12, 2023
 * COSI 21A PA2
 */
package main;

/**
 * AVLPlayerNode class for the nodes of the AVL tree
 * Each node will have a left child, a right child, and a Player object, 
 * a double (the value that the node is sorted based on), 
 * balance factor for AVL balancing, number of nodes in the right subtree.
 */
public class AVLPlayerNode {
	private Player data;
	private double value;
	private AVLPlayerNode parent;
	private AVLPlayerNode leftChild;
	private AVLPlayerNode rightChild;
	private int rightWeight;
	// private int balanceFactor;
    private int height = 1;

	/**
	 * Constructor that takes specified player data and numerical value.
	 *
	 * @param data  the player data to be stored in the node
	 * @param value numerical value associated with the node
	 * O(1) run time
	 */
	
	public AVLPlayerNode(Player data, double value) {
		// TODO
		this.data = data;
		this.value = value;
	}

	// This should return the new root of the tree
	// make sure to update the balance factor and right weight
	// and use rotations to maintain AVL condition
	// newRoot.parent is setup correctly, but parent to newRoot is not.
	/**
	 * Inserts a new player node with the specified player data and numerical value into the AVL tree.
	 * Self-balancing insert method.
	 *
	 * @param newGuy the player data to be inserted into the tree
	 * @param value  value associated with the new player node
	 * @return the new root of the AVL tree after insertion
	 * 
	 * O(h) run time where h represents the height of the tree. Average case
	 * of O(log n).
	 */
	public AVLPlayerNode insert(Player newGuy, double value) {
		// TODO
		AVLPlayerNode root = this;
		// Should insert to the left or to the right?
		boolean left = value < this.value || (value == this.value && newGuy.getID() < data.getID());
		if (left) {
			if (leftChild == null) {
				leftChild = new AVLPlayerNode(newGuy, value);
				leftChild.parent = this;
			} else {
				leftChild = leftChild.insert(newGuy, value);
			}
			updateHeight();
			if (getBalanceFactor() < -1) {
				if (leftChild.getBalanceFactor() > 0) {
					leftChild.rotateLeft();
					// leftChild is no longer root of the left-subtree after rotation.
					leftChild = leftChild.parent;
				}
				rotateRight();
				// this is no longer the root of the current subtree after rotation.
				root = parent;
			}
		} else {
			if (rightChild == null) {
				rightChild = new AVLPlayerNode(newGuy, value);
				rightChild.parent = this;
			} else {
				rightChild = rightChild.insert(newGuy, value);
			}
			updateHeight();
			rightWeight++;
			if (getBalanceFactor() > 1) {
				if (rightChild.getBalanceFactor() < 0) {
					rightChild.rotateRight();
					rightChild = rightChild.parent;
				}
				rotateLeft();
				root = parent;
			}
		}
		return root;
	}

	// This should return the new root of the tree
	// remember to update the right weight
	/**
	 * Deletes a player node with the specified numerical value
	 * The right weight is updated, and the new root of the tree is returned.
	 *
	 * @param value the numerical value associated with the player node to be deleted
	 * @return the new root of the AVL tree after deletion
	 * O(h) run time - h is height of the tree
	 */
	public AVLPlayerNode delete(double value) {
		// TODO: write standard vanilla BST delete method
		// Extra Credit: use rotations to maintain the AVL condition
		AVLPlayerNode[] rets = new AVLPlayerNode[2];
		deleteHelper(value, rets, false);
		return rets[0];
	}

	// Returns two values, one is the new root, the other is the deleted node.
	// if deleteMax == true, delete the max value in the tree instead.
	// newRoot.parent is setup correctly, but parent to newRoot is not.
	
	/**
	 * Helper method for deleting a player node with the specified numerical value from the AVL tree.
	 * Returns two values, the new root of the tree and the deleted node.
	 *
	 * @param value     value associated with the player node to be deleted
	 * @param rets      an array to store the new root and the deleted node
	 * @param deleteMax true if deleting the maximum value in the tree, false otherwise
	 * O(h) run time - h is the height of the tree. Average case is O(log n) for a balanced tree
	 */
	
	private void deleteHelper(double value, AVLPlayerNode[] rets, boolean deleteMax) {
		AVLPlayerNode root = this;
		if (this.value == value || (deleteMax && rightChild == null)) {
			if (leftChild == null || rightChild == null) {
				if (leftChild == null) {
					root = rightChild;
				} else {
					root = leftChild;
				}

				if (root != null) {
					root.parent = parent;
				}

				rets[0] = root;
				rets[1] = this;
				return;
			}
			leftChild.deleteHelper(0.0, rets, true);
			root = rets[1];
			root.parent = parent;
			root.leftChild = rets[0];
			if (root.leftChild != null)
				root.leftChild.parent = root;
			root.rightChild = rightChild;
			if (rightChild != null) {
				rightChild.parent = root;
			}
			root.rightWeight = rightWeight;
			rets[1] = this;
		} else if (!deleteMax && value < this.value)

		{
			if (leftChild == null)
				throw new IllegalArgumentException("Not found");
			leftChild.deleteHelper(value, rets, false);
			leftChild = rets[0];
		} else if (deleteMax || value > this.value) {
			if (rightChild == null)
				throw new IllegalArgumentException("Not found");
			rightChild.deleteHelper(value, rets, deleteMax);
			rightChild = rets[0];
			--rightWeight;
		}
		root.updateHeight();

		if (root.getBalanceFactor() > 1) {
			AVLPlayerNode r = root.rightChild;
			if (r.getBalanceFactor() < 0) {
				r.rotateRight();
				root.rightChild = r.parent;
			}
			root.rotateLeft();
			root = root.parent;
		} else if (root.getBalanceFactor() < -1) {
			AVLPlayerNode l = root.leftChild;
			if (l.getBalanceFactor() > 0) {
				l.rotateLeft();
				root.leftChild = l.parent;
			}
			root.rotateRight();
			root = root.parent;
		}

		rets[0] = root;
	}

	// remember to maintain rightWeight
	// newRoot.parent should be updated, but parent to newRoot is not.
	/**
	 * Rotates the AVL tree to the right around this node
	 * The rightWeight is updated.
	 * O(h) run time - h is the height of the tree. Average case is O(log n) for a balanced tree
	 */
	
	public void rotateRight() {
		// TODO
		AVLPlayerNode oldParent = parent;
		AVLPlayerNode left = leftChild;
		AVLPlayerNode leftRight = left.rightChild;
		leftChild = leftRight;
		updateHeight();
		if (leftRight != null)
			leftRight.parent = this;
		left.rightChild = this;
		left.updateHeight();
		parent = left;
		left.parent = oldParent;
		left.rightWeight += 1 + rightWeight;
	}

	// remember to maintain rightWeight
	// newRoot.parent should be updated, but parent to newRoot is not.
	/**
	 * Rotates the AVL tree to the left around this node.
	 * The rightWeight is updated. 
	 * O(h) run time - h is the height of the tree. Average case is O(log n) for a balanced tree
	 */
	public void rotateLeft() {
		// TODO
		AVLPlayerNode oldParent = parent;
		AVLPlayerNode right = rightChild;
		AVLPlayerNode rightLeft = right.leftChild;
		rightChild = rightLeft;
		updateHeight();
		if (rightLeft != null)
			rightLeft.parent = this;
		right.leftChild = this;
		right.updateHeight();
		parent = right;
		right.parent = oldParent;
		rightWeight -= 1 + right.rightWeight;
	}

	// this should return the Player object stored in the node with this.value ==
	// value
	/**
	 * Retrieves the Player object stored in the node with the specified numerical value.
	 *
	 * @param value the numerical value associated with the player node
	 * @return the Player object stored in the node with the specified value, or null if not found
	 * O(h) run time - h is the height of the tree. Average case is O(log n) for a balanced tree
	 */
	public Player getPlayer(double value) {
	    // TODO
	    if (this.value == value) {
	        return data;
	    }

	    if (value < this.value) {
	        if (leftChild != null) {
	            return leftChild.getPlayer(value);
	        } else {
	            return null;
	        }
	    } else {
	        if (rightChild != null) {
	            return rightChild.getPlayer(value);
	        } else {
	            return null;
	        }
	    }
	}

	// this should return the rank of the node with this.value == value
	/**
	 * Retrieves the rank of the node with the specified numerical value in the AVL tree.
	 * The rank represents the number of nodes in the tree with values less than or equal to the specified value.
	 *
	 * @param value value associated with the player node
	 * @return the rank of the node with the specified value in the AVL tree
	 * @throws IllegalArgumentException if the specified value is not found in the AVL tree
	 * O(h) run time - h is the height of the tree. Average case is O(log n) for a balanced tree
	 */
	public int getRank(double value) {
		// TODO
		if (value > this.value) {
			if (rightChild == null)
				throw new IllegalArgumentException("Not found");
			return rightChild.getRank(value);
		}
		int rank = 1 + rightWeight;
		if (value < this.value) {
			if (leftChild == null)
				throw new IllegalArgumentException("Not found");
			rank += leftChild.getRank(value);
		}
		return rank;
	}

	// this should return the tree of names with parentheses separating subtrees
	// eg "((bob)alice(bill))"
	/**
	 * Returns a string representation of the AVL tree of names with parentheses separating subtrees.
	 *
	 * @return a string representation of the AVL tree
	 * O(n) runtime -  n represents the number of nodes in the AVL tree
	 */
	public String treeString() {
		String left = leftChild != null ? leftChild.treeString() : "";
		String right = rightChild != null ? rightChild.treeString() : "";
		return "(" + left + data.getName() + right + ")";
	}

	// this should return a formatted scoreboard in descending order of value
	// see example printout in the pdf for the command L
	/**
	 * Returns a formatted scoreboard in descending order of value.
	 * The format includes the name, ID, and score of each player.
	 *
	 * @return a string representation of the formatted scoreboard
	 * O(n) runtime
	 */
	public String scoreboard() {
		// TODO
		StringBuilder sb = new StringBuilder("NAME          ID  SCORE\n");
		scoreboardHelper(this, sb);
		return sb.toString();
	}

	/**
	 * Helper method for constructing a formatted scoreboard in descending order of value.
	 * Performs an in-order traversal of the AVL tree
	 * @param root the root of the AVL tree to traverse
	 * @param sb   the StringBuilder to append the formatted scoreboard information
	 * O(n) runtime
	 */
	
	private static void scoreboardHelper(AVLPlayerNode root, StringBuilder sb) {
		if (root == null)
			return;
		scoreboardHelper(root.rightChild, sb);
		sb.append(String.format("%-12s %3d %7.2f\n", root.data.getName(), root.data.getID(), root.data.getELO()));
		scoreboardHelper(root.leftChild, sb);
	}

	// Gets the height field, or 0 if node is null.
	/**
	 * Gets the height field of the specified AVLPlayerNode, or 0 if the node is null.
	 *
	 * @param node the AVLPlayerNode for which to retrieve the height
	 * @return the height of the node or 0 if the node is null
	 */
	private static int getHeight(AVLPlayerNode node) {
		if (node == null) {
	        return 0;
	    } else {
	        return node.height;
	    }
	}

	// Calculate balance factor on-the-fly.
	/**
	 * Calculates and returns the balance factor of the AVLPlayerNode on-the-fly.
	 * The balance factor is the difference between the height of the right subtree and the left subtree.
	 *
	 * @return the balance factor of the AVLPlayerNode
	 * O(1) runtime
	 */
	private int getBalanceFactor() {
		return getHeight(rightChild) - getHeight(leftChild);
	}

	// Update height, assuming children's heights are up-to-date.
	/**
	 * Updates the height of the AVLPlayerNode, assuming that the heights of its children are up-to-date.
	 * The height is calculated as 1 plus the maximum height among the left and right subtrees.
	 * O(h) runtime depends on the height of the tree. Worst case of O(log n) for an unbalanced tree.
	 */
	private void updateHeight() {
		height = 1 + Math.max(getHeight(leftChild), getHeight(rightChild));
	}
}
