package mru.application;

import java.util.Comparator;
import java.util.LinkedList;
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

	public BST(Comparator<T> avengerComparator) {
		root = null;
		size = 0;
	}
	
	public int size() {
		return size;
	}
	
	public T find(T d) {
		return find(d, root);
	}

	public void add(T d) {
		BSTNode n = new BSTNode(d);
		if (root == null) {
			root = n;
		} else {
			add(root, n);
		}
		size++;
	}

	public int height() {
		return height(root);
	}

	public void printInOrder() {
		inOrderTraversal(root);
	}
	
	public void printPreOrder() {
		preOrderTraversal(root);
	}
	
	public void printPostOrder() {
		postOrderTraversal(root);
	}
	
	public void printLevelOrder() {
		levelOrderTraversal(root);
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

	private void add(BSTNode r, BSTNode n) {
		int c = n.compareTo(r);
		if(c < 0) {
			if(r.getLeft() == null) {
				r.setLeft(n);
			}else {
				add(r.getLeft(), n);
			}
		}else if(c > 0) {
			if(r.getRight() == null) {
				r.setRight(n);
			}else {
				add(r.getRight(), n);
			}
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
	
	private void preOrderTraversal(BSTNode r) {
		if(r == null) {
			return;
		}else {
			visit(r);
			preOrderTraversal(r.getLeft());
			preOrderTraversal(r.getRight());
			
		}
	}
	
	private void postOrderTraversal(BSTNode r) {
		if(r == null) {
			return;
		}else {
			postOrderTraversal(r.getLeft());
			postOrderTraversal(r.getRight());
			visit(r);
		}
	}
	
	private void levelOrderTraversal(BSTNode r) {
		  if (r == null) {
		        return;
		    }

		    Queue<BSTNode> queue = new LinkedList<>();
		    queue.add(root);

		    while (!queue.isEmpty()) {
		        BSTNode current = queue.poll();
		        visit(current);

		        if (current.getLeft() != null) {
		            queue.add(current.getLeft());
		        }

		        if (current.getRight() != null) {
		            queue.add(current.getRight());
		        }
		    }
		
	}
}