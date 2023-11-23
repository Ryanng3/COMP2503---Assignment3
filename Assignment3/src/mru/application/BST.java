package mru.application;

import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

public class BST<T extends Comparable<T>> {
	class BSTNode implements Comparable<BSTNode> {
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

	public BST(Comparator<T> comparator) {
		root = null;
		size = 0;
		this.comparator = comparator;
	}
	
	public int size() {
		return size;
	}
	
	public void addInOrder(T d) {
		BSTNode n = new BSTNode(d);
		if (root == null) {
			root = n;
			size++;
		} else {
			addInOrder(root, n);
		}
	}
	
	public void add(T d) {
		BSTNode n = new BSTNode(d);
		if (root == null) {
			root = n;
			size++;
		} else {
			add(root, n);
		}
	}
	
	public int optHeight() {
		return (int) (Math.log(size)/Math.log(2));
	}

	public int height() {
		return height(root);
	}
	
	public void delete(T d) {
        	root = delete(root, d);
        	size--;
    	}
	
	public void printInOrder() {
		inOrderTraversal(root);
	}

	private void addInOrder(BSTNode r, BSTNode n) {
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
	
	private void add(BSTNode r, BSTNode n) {
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

	private int height(BSTNode r) {
		int h = -1;
	    if (r == null) {
	        return h;
	    } else if (r.getLeft() == null && r.getRight() == null) {
	        return 0;
	    } else {
	        return 1 + Math.max(height(r.getLeft()), height(r.getRight()));
	    }
	}

	private void visit(BSTNode r) {
		if (r != null)
			System.out.println(r.getData());
	}
	
	private void inOrderTraversal(BSTNode r) {
		if (r == null)
			return;
		else {
			inOrderTraversal(r.getLeft());
			visit(r);
			inOrderTraversal(r.getRight());
		}
	}

	private BSTNode delete(BSTNode r, T d) {
        if (r == null) {
            return null;
        }

        int cmp = d.compareTo(r.getData());
        if (cmp < 0) {
            r.setLeft(delete(r.getLeft(), d));
        } else if (cmp > 0) {
            r.setRight(delete(root.getRight(), d));
        } else {
            if (r.getLeft() == null) {
                return r.getRight();
            } else if (r.getRight() == null) {
                return r.getLeft();
            }
            r.setData(minValue(r.getRight()));

            r.setRight(delete(root.getRight(), r.getData()));
        }

        return root;
    }

    private T minValue(BSTNode r) {
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
