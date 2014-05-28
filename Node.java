
/**
 * @author Madhav Chhura
 *
 */
public class Node <K extends Comparable<K>,V> {

	/**
	 * 
	 * @value color The boolean color value of this node, if true then node is red, if false, black 
	 */
	protected boolean color;
	protected Node leftTree, rightTree, parent;
	protected V value; 
	protected K key;
	
	/**
	 * Initializes this node with the given attributes
	 * 
	 * @pre true
	 * @post the node is created
	 * 
	 * 
	 * @param color
	 *            The color value of this node, if true, red, if false, black
	 * @param leftTree
	 *            The left child of this node
	 * @param rightTree
	 *            The right child of this node
	 * @param parent
	 *            The parent of this node
	 * @param value
	 *            The value held by this node
	 * @param key
	 *            The key associated with the value held by this node
	 */
	public Node(K key, V value, boolean color, Node leftTree, Node rightTree, Node parent) 
	{
		this.key = key;
		this.value = value;
		this.color = color;
		this.leftTree = leftTree;
		this.rightTree = rightTree;
		this.parent = parent;
	}
}

