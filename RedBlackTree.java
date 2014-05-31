/**
 * CS 241: Data Structures and Algorithms II
 * Professor: Edwin Rodr&iacute;guez
 *
 * Programming Assignment #3
 *
 * <Red Black Trees>
 *
 * Madhav Chhura
 */
package edu.csupomona.cs.cs241.prog_assigmnt_3;

import java.util.ArrayList;

/**
 * @author Madhav Chhura 
 *
 */
public class RedBlackTree <K extends Comparable<K>,V> implements RBT<K,V> {
	
	protected final boolean RED = true;
    protected final boolean BLACK = false;
    
    private Node<K, V> nullLeaf;
    private Node<K, V> root;
    
    private int treeLength;

    /**
     * Initializes a Red Black Tree
     * 
     * @pre true
     * @post an instance of RedBlackTree is created.
     */
    public RedBlackTree() {
        root = null;
        nullLeaf = new Node<K, V>(null,null,BLACK,null,null,null);
    }
    
    @SuppressWarnings("unchecked")
	@Override
	public void insert(K key, V value) {
		if(key == null || value == null){
			try {
				throw new Exception();
			} catch (Exception e) {
				System.out.println("Key or Value are of Invalid type.");
				e.printStackTrace();
				System.exit(0);
			}
		}
		if (root == null) {
            root = new Node<K, V>(key,value,BLACK,nullLeaf,nullLeaf,null);
            root.leftTree.parent = root;
            root.rightTree.parent = root;
        }
		Node<K, V> addHere = insertMover(key, root);
        if (addHere.key.compareTo(key) < 0) {
            addHere.rightTree = new Node<K, V>(key,value,RED,nullLeaf,nullLeaf,addHere);
            insertHelper(addHere.rightTree);
        } else {
            addHere.leftTree = new Node<K, V>(key,value,RED,nullLeaf,nullLeaf,addHere);
            insertHelper(addHere.leftTree);
        }
        if (key.toString().length() > treeLength) 
            treeLength = key.toString().length();

}
    @SuppressWarnings("unchecked")
	public V delete(K key) {
		V removed;
        if (root == null) 
            return null;
        Node<K, V> temp = search(key, root);
        if (temp == null) 
            return null;
        removed = (V) temp.rightTree.value;

        if (temp == root && root.rightTree.value == nullLeaf && root.rightTree.value == nullLeaf) {
            root = null;
            return removed;
        }
        boolean successor = false;
        Node<K, V> node;
        if (temp.rightTree.value == nullLeaf && temp.leftTree.value == nullLeaf) 
            node = temp;
        else if (temp.rightTree.value != nullLeaf) {
            successor = true;
            node = successor(temp.rightTree);
        } 
        else 
            node = predecessor(temp.leftTree);
        temp.key = node.key;
        Node<K, V> balanceNode;
        if (node.color) {
            if (node.parent.rightTree.value == node)
            	 node.parent.rightTree = nullLeaf;
            else
                node.parent.leftTree = nullLeaf;
            return removed;
        } 
        else {
            if (successor) {
                if (node.parent.rightTree  == node) 
                    node.parent.rightTree = node.rightTree;
                else 
                    node.parent.leftTree = node.rightTree;
                node.rightTree.parent = node.parent;
                balanceNode = node.rightTree;
                node.rightTree = null;
                node.leftTree = null;
                node.parent = null;
            } 
            else {
                if (node.parent.rightTree == node) {
                    node.parent.rightTree = node.leftTree;
                } else {
                    node.parent.leftTree = node.leftTree;
                }
                node.leftTree.parent = node.parent;
                balanceNode = node.leftTree;
                node.rightTree = null;
                node.leftTree = null;
                node.parent = null;
            }
            if (balanceNode.color) 
                balanceNode.color = BLACK;
            else {
                deleteBalance(balanceNode);
            }
        }
        return removed;

    }

	@Override
	public V lookUp(K key) {
		return search(key,root).value;
	}
	
	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void prettyPrint() {
	    ArrayList<DepthNode<K, V>> queue = new ArrayList<DepthNode<K, V>>();
	    int depth = 0;
	    int treeDepth = treeDepth(root);
	    int space = treeLength + 2;
	    for (int i = 0; i < treeDepth - 2; i++) {
	        space = (space * 2) + treeLength + 2;
	    }
	
	    int spaceInBetween = space;
	    queue.add(0, new DepthNode<K, V>(depth, root));
	    DepthNode<K, V> depthNode = null;
	    for (int i = 0; i < space; i++) {
	        System.out.print(" ");
	    }
	
	    while (depth < treeDepth) {
	        depthNode = queue.remove(0);
	        if (depthNode.getDepth() > depth) {
	            depth = depthNode.getDepth();
	            if (!(depth < treeDepth)) {
	                break;
	            }
	            System.out.println();
	            spaceInBetween = space;
	            space = ((space - (treeLength + 2)) / 2);
	            for (int i = 0; i < space; i++) {
	                System.out.print(" ");
	            }
	        }
	        Node<K, V> right;
	        Node<K, V> left;
	        if (depthNode.getNode() == null) {
	            right = null;
	            left = null;
	        } else {
	            right = depthNode.getNode().rightTree;
	            left = depthNode.getNode().leftTree;
	        }
	        queue.add(queue.size(), new DepthNode<K, V>(depth + 1, left));
	        queue.add(queue.size(), new DepthNode<K, V>(depth + 1, right));
	        if (depthNode.getNode() == null) {
	            for (int j = 0; j < treeLength + 2; j++) 
	                System.out.print(" ");
	        } else if (depthNode.getNode() == nullLeaf) {
	            for (int j = 0; j < treeLength; j++) 
	                System.out.print("N");
	            System.out.print(":B");
	        } else {
	            System.out.print(depthNode.getNode().key);
	            for (int j = depthNode.getNode().key.toString().length(); j < treeLength; j++) 
	                System.out.print("E");
	            if (depthNode.getNode().color) 
	                System.out.print(":R");
	            else 
	                System.out.print(":B");
	        }
	        for (int k = 0; k < spaceInBetween; k++) 
	            System.out.print(" ");
	    }
	    System.out.println();
	}

	/**
	 * @param node
	 */
	@SuppressWarnings("unchecked")
	private void rotateLeft(Node<K,V> node){
		if(node != root){
			if(node.parent != null && node.parent.leftTree == node){
				node.parent.leftTree = node.rightTree;
				node.rightTree.parent = node.parent;
				node.rightTree = node.rightTree.leftTree;
				node.rightTree.parent = node;
				node.parent.leftTree.leftTree = node;
				node.parent = node.parent.leftTree;
			}
			else{
				node.parent.rightTree = node.rightTree;
				node.rightTree.parent = node.parent;
				node.rightTree = node.rightTree.leftTree;
				node.rightTree.parent = node;
				node.parent.rightTree.leftTree = node;
				node.parent = node.parent.rightTree;
			}
		}
		else{
			root = node.rightTree;
			root.parent.rightTree = root.leftTree;
			root.leftTree.parent = root.parent;
			root.leftTree = root.parent;
			root.leftTree.parent = root;
			root.parent = null;
		}
	}
	/**
	 * @param node 
	 */
	@SuppressWarnings("unchecked")
	private void rotateRight(Node<K,V> node){
		if(node != root){
			if(node.parent != null && node.parent.leftTree == node){
				node.parent.leftTree = node.leftTree;
				node.leftTree.parent = node.parent;
				node.leftTree = node.leftTree.rightTree;
				node.leftTree.parent = node;
				node.parent.leftTree.rightTree = node;
				node.parent = node.parent.leftTree;
			}
			else{
				node.parent.rightTree = node.leftTree;
				node.leftTree.parent = node.parent;
				node.leftTree = node.leftTree.rightTree;
				node.leftTree.parent = node;
				node.rightTree.rightTree = node;
				node.parent = node.parent.rightTree;
			}
		}
		else{
			root = node.leftTree;
			root.parent.leftTree = root.rightTree;
			root.rightTree.parent = root.parent;
			root.rightTree = root.parent;
			root.rightTree.parent = root;
			root.parent = null;
		}
	}
	@SuppressWarnings("unchecked")
	private int treeDepth(Node<K, V> start) {
        if (start == null)
            return 0;
        return Math.max(treeDepth(start.rightTree) + 1,treeDepth(start.leftTree) + 1);
    }
	 @SuppressWarnings("unchecked")
	private void deleteBalance(Node<K, V> node) {
	    if (node == root) 
	        return;
	    Node<K, V> tempParent = node.parent;
	    Node<K, V> brother = findSibling(node);
	    if (brother == nullLeaf) 
	        return;
	    if (brother.color) {
	        brother.color = BLACK;
	        tempParent.color = BLACK;
	        if (node == tempParent.leftTree) 
	            rotateLeft(tempParent);
	        else 
	            rotateRight(tempParent);
	        brother = findSibling(node);
	        tempParent = node.parent;
	    }
	    if (!brother.color) {
	        if (!brother.rightTree.color && !brother.leftTree.color) {
	            if (!tempParent.color) {
	                brother.color = RED;
	                deleteBalance(tempParent);
	            }
	            else {
	                brother.color = RED;
	                tempParent.color = BLACK;
	                return;
	            }
	        }
	        if (node == tempParent.leftTree && !brother.rightTree.color && brother.leftTree.color) {
	            brother.color = RED;
	            brother.leftTree.color = BLACK;
	            rotateRight(brother);

	        } else if (node == tempParent.rightTree && !brother.leftTree.color && brother.rightTree.color) {
	            brother.color = RED;
	            brother.rightTree.color = BLACK;
	            rotateLeft(brother);
	        }
	        if (node == nullLeaf) 
	            node.parent = tempParent;
	        brother = findSibling(node);
	        if (brother == nullLeaf) 
	            return;
	        if (node == tempParent.leftTree && brother.rightTree.color) {
	            boolean temp = tempParent.color;
	            tempParent.color = brother.color;
	            brother.color = temp;
	            brother.rightTree.color = BLACK;
	            rotateLeft(tempParent);
	        } else if (node == tempParent.rightTree && brother.leftTree.color) {
	            boolean temp = tempParent.color;
	            tempParent.color = brother.color;
	            brother.color = temp;
	            brother.leftTree.color = BLACK;
	            rotateRight(tempParent);
	        }
	    }
	}
	/**
	 * @param key
	 * @param root2
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Node<K,V> search(K key, Node<K, V> tempNode) {
	    
		if (tempNode.key.compareTo(key) == 0) 
	        return tempNode;
	    if (tempNode.key.compareTo(key) < 0 && tempNode.rightTree != nullLeaf) 
	        return search(key, tempNode.rightTree);
	    if (tempNode.key.compareTo(key) > -1 && tempNode.leftTree != nullLeaf) 
	        return search(key, tempNode.leftTree);
	    return null;
	}


	private Node<K, V> findSibling(Node<K, V> node) {
        if (node.parent.rightTree == node) {
            return node.parent.leftTree;
        } else {
            return node.parent.rightTree;
        }
    }
    /**
	 * @param temp
	 */
	@SuppressWarnings("unchecked")
	private void insertHelper(Node<K,V> node) {
	    Node<K,V> parent = (Node<K,V>) node.parent;
	
	    if (parent == null) {
	        node.color = BLACK;
	        return;
	    }
	
	    if (parent.color == BLACK) {
	        return;
	    }
	    Node<K,V> grandParent = node.getGrandParent();
	    Node<K,V> uncle = node.getUncle();
	    if (parent.color == RED && uncle.color == RED) {
	        parent.color = BLACK;
	        uncle.color = BLACK;
	        if (grandParent != null) {
	            grandParent.color = RED;
	            insertHelper(grandParent);
	        }
	    } else {
	        if (parent.color == RED && uncle.color == BLACK) {
	            if (node.equals(parent.rightTree) && parent.equals(grandParent.leftTree)) {
	                rotateLeft(parent);
	                node = (Node<K,V>) node.leftTree;
	                grandParent = node.getGrandParent();
	                parent = (Node<K,V>) node.parent;
	                uncle = node.getUncle();
	            } else if (node.equals(parent.leftTree) && parent.equals(grandParent.rightTree)) {
	                rotateRight(parent);
	                node = (Node<K,V>) node.rightTree;
	
	                grandParent = node.getGrandParent();
	                parent = (Node<K,V>) node.parent;
	                uncle = node.getUncle();
	            }
	        }
	        if (parent.color == RED && uncle.color == BLACK) {
	            parent.color = BLACK;
	            grandParent.color = RED;
	            if (node.equals(parent.leftTree) && parent.equals(grandParent.leftTree)) {
	                rotateRight(grandParent);
	            } else if (node.equals(parent.rightTree) && parent.equals(grandParent.rightTree)) {
	                rotateLeft(grandParent);
	            }
	        }
	    }
	}


	@SuppressWarnings("unchecked")
	private Node<K, V> successor(Node<K, V> startNode) {
        if (startNode.leftTree == nullLeaf)
            return startNode;
        if (startNode.leftTree != nullLeaf) 
            return successor(startNode.leftTree);
        if (startNode.rightTree != nullLeaf) 
            return successor(startNode.rightTree);
        return null;
    }
    /**
     * This method finds the in order predecessor of the subtree with startNode
     * at the root
     * 
     * @param startNode
     *            the root of the subtree
     * @return the node that is the in order predecessor of startNode
     */
    @SuppressWarnings("unchecked")
	private Node<K, V> predecessor(Node<K, V> startNode) {
        if (startNode.rightTree == nullLeaf)
            return startNode;
        if (startNode.rightTree != nullLeaf) 
            return predecessor(startNode.rightTree);
        if (startNode.leftTree != nullLeaf) 
            return predecessor(startNode.leftTree);
        return null;
    }
	private Node<K, V> insertMover(K key, Node<K, V> startNode) {
        Node<K, V> left = startNode.leftTree;
        Node<K, V> right = startNode.rightTree;
        if (left == nullLeaf && right == nullLeaf) 
            return startNode;
        if (left == nullLeaf && right != nullLeaf) {
            if (startNode.key.compareTo(key) > -1) 
                return startNode;
            else 
                return insertMover(key, right);
        }
        if (right == nullLeaf && left != nullLeaf) {
            if (startNode.key.compareTo(key) < 0)
                return startNode;
            else
                return insertMover(key, left);
        }
        if (left != nullLeaf && right != nullLeaf) {
            if (startNode.key.compareTo(key) < 0) 
                return insertMover(key, right);
            else 
                return insertMover(key, left); 
        }
        return null;
    }
}
class DepthNode<K extends Comparable<K>, V> {
    /**
     * the depth of the held node
     */
    private int depth;
    /**
     * a node 
     */
    private Node<K, V> node;
    /**
     * creates a node and depth object 
     * 
     * @param dep the depth of the node
     * @param n the node
     */
    public DepthNode(int dep, Node<K, V> n) {
        node = n;
        depth = dep;
    }
    /**
     * gets the depth
     * @pre true
     * @post the depth is returned
     * @return the depth
     */
    public int getDepth() {
        return depth;
    }
    /**
     * gets the node
     * @pre true
     * @post the node is returned
     * @return the node
     */
    public Node<K, V> getNode() {
        return node;
    }
    /**
     * Sets the depth of the current node.
     * @pre true
     * @post the depth is set
     * 
     * @param depth
     *            the depth to set
     */
    public void setDepth(int depth) {
        this.depth = depth;
    }
    public String toString() {
        if (node != null) {
            return node.toString();
        } else {
            return "DaN null";
        }

    }
}
