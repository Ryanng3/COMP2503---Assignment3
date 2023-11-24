package mru.application;

import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * 
 * @author ryanng
 *
 * @param <T>
 */
public class BST<T extends Comparable<T>> {
	class BSTNode implements Comparable<BSTNode> {	//Setters and getters for BST
		private T data;
		private BSTNode left;
		private BSTNode right;

		public BSTNode(T d) {
			setLeft(null);
			setRight(null);
			setData(d);
		}
		
		public T getData() {
			return data;
			}
		
		public void setData(T d) {
			data = d;
			}
		public void setLeft(BSTNode l) {
			left = l;
			}
		public void setRight(BSTNode r) {
			right = r;
		}
		
		public BSTNode getLeft() {
			return left;
			}
		
		public BSTNode getRight() {
			return right;
			}
		
		public boolean isLeaf() {
			return (getLeft() == null) && (getRight() == null);
			}
		
		public int compareTo(BSTNode o) {
			return this.getData().compareTo(o.getData());
		}
	}

	private BSTNode root;
	private int size;
	private Comparator<T> comparator;

	public BST(Comparator<T> comparator) {	//Constructor
		root = null;
		size = 0;
		this.comparator = comparator;
	}
	
	/**
	 * returns size of the tree
	 * @return
	 */
	public int size() {		//Size of the tree
		return size;
	}
	
	/**
	 * Adds data in certain order to tree
	 * @param d
	 */
	public void addInOrder(T d) {		//Adds nodes to tree in comparator order
		BSTNode n = new BSTNode(d);
		if (root == null) {
			root = n;
			size++;
		} else {
			addInOrder(root, n);
		}
	}
	
	/**
	 * Adds data to tree
	 * @param d
	 */
	public void add(T d) {				//Adds nodes to tree in alphabetical ordering
		BSTNode n = new BSTNode(d);
		if (root == null) {
			root = n;
			size++;
		} else {
			add(root, n);
		}
	}
	
	/**
	 * Calculates optimal height of tree
	 * @return optimal height
	 */
	public int optHeight() {							//Calculates optimal height of the tree
		return (int) (Math.log(size)/Math.log(2));
	}
	
	public T find(T d) {
		return find(d, root);
	}

	/**
	 * recursive method to calculate height
	 * @return
	 */
	public int height() {						//finds height of the tree
		return height(root);
	}
	
	/**
	 * recursive method for delete
	 * decrement size when deleting object
	 * @param d
	 */
	public void delete(T d) {				//deletes node from tree and reduces the size
        	root = delete(root, d);
        	size--;
    	}
	
	/**
	 * recursive method for print in order traversal
	 */
	public void printInOrder() {
		inOrderTraversal(root);						//Prints in in order traversal
	}
	
	/**
	 * Adds nodes in a certain order using a comparator
	 * @param r
	 * @param n
	 */
	private void addInOrder(BSTNode r, BSTNode n) {				//Adds in comparator order to the tree
		int c = comparator.compare(n.getData(), r.getData());
		if (c < 0) {
			if(r.getLeft() == null) {
				r.setLeft(n);
				size++;
			}
			else
				addInOrder(r.getLeft(), n);
		}
		else {
			if(r.getRight() == null) {
				r.setRight(n);
				size++;
			}
			else
				addInOrder(r.getRight(), n);
		}
	}
	
	/**
	 * Adds nodes in alphabetical order
	 */
	private void add(BSTNode r, BSTNode n) {		//Adds in alphabetical order to the tree
		int c = n.compareTo(r);
		if (c < 0) {
			if(r.getLeft() == null) {
				r.setLeft(n);
				size++;
			}
			else
				addInOrder(r.getLeft(), n);
		}
		else {
			if(r.getRight() == null) {
				r.setRight(n);
				size++;
			}
			else
				add(r.getRight(), n);
		}
	}
	
	private T find(T d, BSTNode r) {
		if (r == null)
			return null;
		int c = d.compareTo(r.getData());
		if (c == 0)
			return r.getData();
		else if (c < 0)
			return find(d, r.getLeft());
		else
			return find(d, r.getRight());
	}


	/**
	 * calculates height of the tree
	 * @param r
	 * @return
	 */
	private int height(BSTNode r) {				//Calculates height of the tree
		int h = -1;
		
	    if (r == null) {
	        return h;
	        
	    } else if (r.getLeft() == null && r.getRight() == null) {
	        return 0;
	    }
	     else {
	        return 1 + Math.max(height(r.getLeft()), height(r.getRight()));
	    }
	}

	/**
	 * Visits the root of the tree
	 * @param r
	 */
	private void visit(BSTNode r) {				//Visits root of the tree
		if (r != null)
			System.out.println(r.getData());
	}
	
	/**
	 * Traverses through the tree in an in order traversal
	 * @param r
	 */
	private void inOrderTraversal(BSTNode r) {		//Traverses tree in order traversal
		if (r == null)
			return;
		else {
			inOrderTraversal(r.getLeft());
			visit(r);
			inOrderTraversal(r.getRight());
		}
	}

	/**
	 * deletes the object from the tree
	 * @param r
	 * @param d
	 * @return
	 */
	private BSTNode delete(BSTNode r, T d) {		//Deletes Node from tree
        if (r == null) {
            return null;
        }

        int c = d.compareTo(r.getData());
        if (c < 0) {
            r.setLeft(delete(r.getLeft(), d));
        } else if (c > 0) {
            r.setRight(delete(r.getRight(), d));
        } else {
            if (r.getLeft() == null) {
                return r.getRight();
            } else if (r.getRight() == null) {
                return r.getLeft();
            }
            r.setData(minValue(r.getRight()));

            r.setRight(delete(r.getRight(), r.getData()));
        }

        return r;
    }

	/**
	 * Checking for next suitable parent
	 * @param r
	 * @return
	 */
    private T minValue(BSTNode r) {				//Finds smallest value of the sub tree
        T minValue = r.getData();
        while (r.getLeft() != null) {
            minValue = r.getLeft().getData();
            r = r.getLeft();
        }
        return minValue;
    }
	
	private class BSTIterator implements Iterator<T>{
		private Queue<BSTNode> q;
		
		public BSTIterator() {
			q = new LinkedList<>();
			traverse(root);
		}

		private void traverse(BSTNode n) {
			if(n != null) {
				traverse(n.left);
				q.add(n);
				traverse(n.right);
			}
			
		}

		@Override
		public boolean hasNext() {
			return !q.isEmpty();
		}

		@Override
		public T next() {
			if(!hasNext()) {
				throw new NoSuchElementException();
			}
			return q.poll().data;
		}
	}
	
	public Iterator<T> iterator(){
		return new BSTIterator();
	}
}
